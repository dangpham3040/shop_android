package com.example.shop_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


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


}