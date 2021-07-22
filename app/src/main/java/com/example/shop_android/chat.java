package com.example.shop_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class chat extends AppCompatActivity {
    private static final String TAG = "currentuser:";
    ImageView ortherAvata,imgsend,imgbback;
    TextView fullname;
    EditText message;
    String otherID;
    FirebaseDatabase Database;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        setConntrol();
        setEnvent();
    }

    private void setEnvent() {
        imgbback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ortherAvata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),profile.class);
                intent.putExtra("otherID",otherID);
                startActivity(intent);
            }
        });
    }

    private void setConntrol() {
        ortherAvata = findViewById(R.id.ortherAvata);
        fullname = findViewById(R.id.username);
        imgsend=findViewById(R.id.imgsend);
        imgbback=findViewById(R.id.imgback);
        message=findViewById(R.id.message);
        Bundle bundle=getIntent().getExtras();
        otherID=bundle.getString("otherID");
        Log.d("otherID:", otherID);
        Toast.makeText(this, otherID, Toast.LENGTH_SHORT).show();
        String userID = bundle.getString("userID");
        Log.d(TAG, String.valueOf(userID));

//        fullname.setText(full);
//        Picasso.get()
//                .load(avata)
//                .into(ortherAvata);
    }
}