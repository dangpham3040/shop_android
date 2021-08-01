package com.example.shop_android.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shop_android.R;
import com.example.shop_android.fragment.fragment_contact;
import com.example.shop_android.fragment.fragment_friends;
import com.example.shop_android.fragment.fragment_profile;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class starup extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private NavController navigation;
    private String currentuser;

    //firebase
    private FirebaseAuth fAuth;
    FirebaseDatabase Database;
    private DatabaseReference mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_up);
//        isOnline();
        Database = FirebaseDatabase.getInstance();
        mUser = Database.getReference("User");
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom);
        navigation = Navigation.findNavController(this, R.id.fragment);
        //NavigationUI.setupWithNavController(bottomNavigationView,navigation);
        Fragment fragment = new fragment_contact();
        loadFragment(fragment);
        bottomNavigationView.setItemHorizontalTranslationEnabled(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.fragment_contact:
                        fragment = new fragment_contact();
                        loadFragment(fragment);
                        return true;
                    case R.id.fragment_profile:
                        fragment = new fragment_profile();
                        loadFragment(fragment);
                        return true;
                    case R.id.fragment_friends:
                        fragment = new fragment_friends();
                        loadFragment(fragment);
                        return true;
                }
                return false;
            }
        });


    }

    private void isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

            if (activeNetworkInfo != null) { // connected to the internet
                // connected to the mobile provider's data plan
                if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
                    Toast.makeText(this, "online", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "offline", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }




}


