package com.example.shop_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase Database;
    DatabaseReference mDatabase;
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
        //màn hình loading....
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //kiểm tra coi đã login chưa
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    startActivity(new Intent(getApplicationContext(), starup.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }
            }
        }, 3500);


    }
    @Override
    protected void onDestroy(){
        Database = FirebaseDatabase.getInstance();
        mDatabase = Database.getReference("User");
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase.child(currentuser).child("status").setValue("ofline");
        super.onDestroy();

    }


}