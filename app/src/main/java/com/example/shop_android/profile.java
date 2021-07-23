package com.example.shop_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
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
    RadioButton male,female;
    FirebaseDatabase Database;
    DatabaseReference mDatabase;
    FirebaseAuth fAuth;
    TextView save, cancel;
    String gioitinh;
    String Fist, Last, Email;
    String otherID;
    String currentuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setControl();
        setEnvet();
    }

    private void setEnvet() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Update();
            }
        });
        fist.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(iscurrentuser()==true){
                    if (Fist.equals( fist.getText().toString())) {

                    } else {
                        save.setEnabled(true);
                        save.setTextColor(Color.parseColor("#FFFFFF"));
                    }
                }

            }
        });
        last.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (iscurrentuser()==true){
                    if (Last.equals( last.getText().toString())) {
                    } else {
                        save.setEnabled(true);
                        save.setTextColor(Color.parseColor("#FFFFFF"));
                    }
                }

            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(iscurrentuser()==true){
                    if (Email.equals( fist.getText().toString())) {
                    } else {
                        save.setEnabled(true);
                        save.setTextColor(Color.parseColor("#FFFFFF"));
                    }
                }

            }
        });


    }

    private void Update() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Update?");
        b.setMessage("Are you sure ?");
        b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mDatabase.child(otherID).child("fist").setValue(fist.getText().toString());
                mDatabase.child(otherID).child("last").setValue(last.getText().toString());
                mDatabase.child(otherID).child("email").setValue(email.getText().toString());
                startActivity(new Intent(getApplicationContext(), home.class));
            }
        });
        b.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        //Tạo dialog
        AlertDialog al = b.create();
        //Hiển thị
        al.show();


    }


    private void setControl() {
        profileimg = findViewById(R.id.imageView);
        fist = findViewById(R.id.fist);
        last = findViewById(R.id.last);
        email = findViewById(R.id.email);
        male=findViewById(R.id.male);
        female=findViewById(R.id.female);
        cancel = findViewById(R.id.cancel);
        save = findViewById(R.id.save);

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
                        Fist = ds.child("fist").getValue(String.class);
                        fist.setText(Fist);
                        Last = ds.child("last").getValue(String.class);
                        last.setText(Last);
                        Email = ds.child("email").getValue(String.class);
                        email.setText(Email);
                        gioitinh=ds.child("sex").getValue(String.class);
                        String nam= "Nam";
                        if(gioitinh.equals(nam))
                        {
                            male.setChecked(true);
                        }
                        else{
                            female.setChecked(true);
                        }
                        String img = ds.child("pic").getValue(String.class);
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
       if(iscurrentuser()==true)
       {
           fist.setEnabled(true);
           last.setEnabled(true);
           email.setEnabled(true);
           female.setEnabled(true);
           male.setEnabled(true);
       }
    }

    private boolean iscurrentuser() {
        if (otherID.equals(currentuser)) {
            return true;
        }
        return false;
    }

}