package com.example.shop_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class home extends AppCompatActivity {
    private static final String TAG = "test";
    // khai bao
    Button btnlogout;
    FirebaseAuth fAuth;
    UserAdapter userAdapter;
    User user;
    ListView listView;
    ArrayList<User> listuser = new ArrayList<>();
    FirebaseDatabase Database;
    DatabaseReference mDatabase;
    String currentuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setControl();
        setEnvet();
    }

    private void setEnvet() {
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
                fAuth.getInstance().signOut();

                finish();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = listuser.get(position);
                String full = user.getFist() + " " + user.getLast();
                Log.d(TAG, full);

                Intent intent = new Intent(home.this, chat.class);
                Bundle bundle = new Bundle();
                bundle.putString("otherID", user.getId().toString());
                bundle.putString("userID", currentuser);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }


    private void setControl() {
        btnlogout = findViewById(R.id.logout);
        listView = findViewById(R.id.list);
        userAdapter = new UserAdapter(getApplicationContext(), R.layout.list, listuser);
        Database = FirebaseDatabase.getInstance();
        mDatabase = Database.getReference("User");
        listuser.removeAll(listuser);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    user = ds.getValue(User.class);
                    listuser.add(user);
                    String TAG = "URL:";
                    Log.d(TAG, user.getPic());
                }
                listView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}