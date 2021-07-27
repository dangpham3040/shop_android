package com.example.shop_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

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
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_up);

        Database = FirebaseDatabase.getInstance();
        mDatabase = Database.getReference("User");
        //lấy id của user hiện tại
//        currentuser = fAuth.getInstance().getCurrentUser().getUid();
//        //cập nhập tình trạng online
//        mDatabase.child(currentuser).child("status").setValue("online");

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
                }
                return false;
            }
        });


    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}


