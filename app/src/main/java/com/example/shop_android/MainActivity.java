package com.example.shop_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

public class MainActivity extends AppCompatActivity {
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
    String currentuser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setControl();
        setEnvet();
    }

    private void setEnvet() {

    }


    private void setControl() {
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Login.isLogin == true) {
                    startActivity(new Intent(getApplicationContext(), home.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }
            }
        },4000);


    }


}