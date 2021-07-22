package com.example.shop_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class profile extends AppCompatActivity {
    ImageView profileimg;
    EditText fist, last, email;
    EditText sex;
    FirebaseDatabase Database;
    DatabaseReference mDatabase;
    FirebaseAuth fAuth;
    String gioitinh;
    String otherID;
    String currentuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setControl();
    }

    private void setControl() {
        profileimg = findViewById(R.id.imageView);
        fist = findViewById(R.id.fist);
        last = findViewById(R.id.last);
        email = findViewById(R.id.email);

        sex = findViewById(R.id.sex);
        Intent intent = getIntent();
        currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        otherID = intent.getStringExtra("otherID");
        Database = FirebaseDatabase.getInstance();
        mDatabase = Database.getReference("User");
        Query check = mDatabase.orderByChild("id").equalTo(otherID);

        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.exists()) {
                        fist.setText(ds.child("fist").getValue(String.class));
                        last.setText(ds.child("last").getValue(String.class));
                        email.setText(ds.child("email").getValue(String.class));
                        sex.setText(ds.child("sex").getValue(String.class));
                        String img=ds.child("pic").getValue(String.class);
                        Picasso.get().load(img).into(profileimg);

                    } else {
                        Log.d("user", "khong ton tai");
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        iscurrentuser();
    }

    private void iscurrentuser() {
        if (otherID.equals(currentuser)) {
            fist.setEnabled(true);
            last.setEnabled(true);
            email.setEnabled(true);
        }
    }

}