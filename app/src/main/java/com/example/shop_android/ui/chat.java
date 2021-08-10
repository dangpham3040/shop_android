package com.example.shop_android.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shop_android.R;
import com.example.shop_android.data.StaticConfig;
import com.example.shop_android.ui.profile;
import com.example.shop_android.ui.starup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class chat extends AppCompatActivity {
    //activity
    private ImageView imgsend, imgbback;
    private CircularImageView ortherAvata;
    private TextView fullname, tvstatus;
    private EditText message;
    //bien
    private static final String TAG = "currentuser:";
    public String otherID = "";
    private String fist, last, status;
    private String userID = "";
    //Firebase



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        setConntrol();
        setEnvent();
    }

    private void setEnvent() {
        imgsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //add friend
//                String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
//                mUser.child(currentuser).child("friends").child(otherID).child("friendsID").setValue(otherID);

            }
        });
        fullname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), profile.class);
                intent.putExtra("otherID", otherID);
                startActivity(intent);
            }
        });
        imgbback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), starup.class));
            }
        });
        ortherAvata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), profile.class);
                intent.putExtra("otherID", otherID);
                startActivity(intent);
            }
        });
    }

    private void setConntrol() {
        ortherAvata = findViewById(R.id.ortherAvata);
        fullname = findViewById(R.id.username);
        imgsend = findViewById(R.id.imgsend);
        imgbback = findViewById(R.id.imgback);
        message = findViewById(R.id.message);
        tvstatus = findViewById(R.id.status);

        //Lấy otherID
        Bundle bundle = getIntent().getExtras();
        otherID = bundle.getString("otherID");
        Log.d("otherID:", otherID);
//        Toast.makeText(this, otherID, Toast.LENGTH_SHORT).show();
        userID = bundle.getString("userID");
        Log.d(TAG, String.valueOf(userID));


        //Load danh sách user sắp xếp bằng id

        Query check = StaticConfig.mUser.orderByChild("id").equalTo(otherID);
        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.exists()) {
                        fist = ds.child("fist").getValue(String.class);
                        last = ds.child("last").getValue(String.class);
                        status = ds.child("status").getValue(String.class);
                        String full = fist + " " + last;
                        fullname.setText(full);
                        tvstatus.setText(status);
                        isOnline();
                        String img = ds.child("pic").getValue(String.class);
                        Picasso.get().load(img).into(ortherAvata);
                    } else {
                        Log.d("user", "khong ton tai");
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void isOnline() {
        if (status.equals("online")) {
            tvstatus.setTextColor(Color.parseColor("#21ED0A"));
            ortherAvata.setBorderColor(Color.BLUE);
        }
        if (status.equals("offline")) {
            tvstatus.setTextColor(Color.parseColor("#5500ce"));
            ortherAvata.setBorderColor(Color.GRAY);
        }
    }
}