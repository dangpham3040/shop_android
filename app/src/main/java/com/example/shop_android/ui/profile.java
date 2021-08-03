package com.example.shop_android.ui;

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
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.shop_android.R;
import com.example.shop_android.model.Friend_Request;
import com.example.shop_android.model.User;
import com.example.shop_android.ui.starup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class profile extends AppCompatActivity {
    TextView save, cancel;
    private CircularImageView profileimg;
    private EditText fist, last, email;
    private RadioGroup radioGroup;
    private RadioButton male, female;
    //firebase
    private FirebaseDatabase Database;
    private DatabaseReference mUser;
    private DatabaseReference mfRequest;
    private FirebaseAuth fAuth;
    //bien
    private String gioitinh;
    private String Fist, Last, Email;
    private String otherID;
    private String currentuser;
    private String nam = "Nam", nu = "Nữ";
    private String link;
    private String status;
    private String fistname = "", lastname = "", emailuser = "";
    //kiêm tra
    private boolean isfist = false;
    private boolean islast = false;
    private boolean isemail = false;
    private boolean issex = false;


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

                startActivity(new Intent(getApplicationContext(), starup.class));
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
                if (iscurrentuser() == true) {
                    fistname = fist.getText().toString();
                    if (fistname.isEmpty()) {
                        fist.setError("fistname is require!!");
                        isfist = false;
                        checUpdate();
                    }
                    if (Fist.equals(fistname)) {
                        isfist = false;
                        checUpdate();
                    } else if (!Fist.equals(fistname) && !fistname.isEmpty()) {
                        isfist = true;
                        checUpdate();
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
                if (iscurrentuser() == true) {
                    lastname = last.getText().toString();
                    if (lastname.isEmpty()) {
                        last.setError("lastname is require!!");
                        islast = false;
                        checUpdate();
                    }
                    if (Last.equals(lastname)) {
                        islast = false;
                        checUpdate();
                    } else if (!Last.equals(lastname) && !Last.isEmpty()) {
                        islast = true;
                        checUpdate();
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
                if (iscurrentuser() == true) {
                    emailuser = email.getText().toString();
                    if (emailuser.isEmpty()) {
                        isemail = false;
                        checUpdate();
                        email.setError("email is require!!");
                    }
                    if (Email.equals(emailuser)) {
                        isemail = false;
                        checUpdate();
                    }
                    if (!Email.equals(emailuser) && !Email.isEmpty()) {
                        isemail = true;
                        checUpdate();
                    }
                    if (!Patterns.EMAIL_ADDRESS.matcher(emailuser).matches()) {
                        email.setError("Enter Valid Email Address");
                        isemail = false;
                        checUpdate();
                    }
                }

            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String choose_sex = "";
                if (male.isChecked()) {
                    choose_sex = nam;
                }
                if (female.isChecked()) {
                    choose_sex = nu;
                }
                if (!gioitinh.equals(choose_sex)) {
                    issex = true;
                    checUpdate();
                } else {
                    issex = false;
                    checUpdate();
                }
            }
        });


    }

    private void checUpdate() {
        if (!fistname.isEmpty() && !lastname.isEmpty() && !emailuser.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailuser).matches()) {
            if (isfist == true || islast == true || isemail == true || issex == true) {

                save.setEnabled(true);
                save.setTextColor(Color.parseColor("#FFFFFF"));
            } else {
                save.setTextColor(Color.parseColor("#5500ce"));
                save.setEnabled(false);
            }
        } else {
            save.setTextColor(Color.parseColor("#5500ce"));
            save.setEnabled(false);
        }


    }

    private void Update() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Update?");
        b.setMessage("Are you sure ?");
        b.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mUser.child(otherID).child("fist").setValue(fist.getText().toString());
                mUser.child(otherID).child("last").setValue(last.getText().toString());
                mUser.child(otherID).child("email").setValue(email.getText().toString());
                if (male.isChecked()) {
                    mUser.child(otherID).child("sex").setValue(nam);
                }
                if (female.isChecked()) {
                    mUser.child(otherID).child("sex").setValue(nu);
                }
                startActivity(new Intent(getApplicationContext(), starup.class));
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
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        cancel = findViewById(R.id.cancel);
        save = findViewById(R.id.save);
        radioGroup = findViewById(R.id.RadioGroup);

        Intent intent = getIntent();
        currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        otherID = intent.getStringExtra("otherID");
        Database = FirebaseDatabase.getInstance();
        mfRequest = Database.getReference("friend_request");
        mUser = Database.getReference("User");
        Query check = mUser.orderByChild("id").equalTo(otherID);

        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.exists()) {
                        Fist = ds.child("fist").getValue(String.class);
                        Last = ds.child("last").getValue(String.class);
                        Email = ds.child("email").getValue(String.class);
                        gioitinh = ds.child("sex").getValue(String.class);
                        status = ds.child("status").getValue(String.class);

                        fist.setText(Fist);
                        last.setText(Last);
                        email.setText(Email);
                        if (gioitinh.equals(nam)) {
                            male.setChecked(true);
                        } else {
                            female.setChecked(true);
                        }
                        link = ds.child("pic").getValue(String.class);

                        Picasso.get()
                                .load(link)
                                .fit()
//                                .transform(transformation)
                                .into(profileimg);
                        isOnline();
//                         //test gửi lời mời kết bạn

                        mUser = Database.getReference("User");
                        Query check = mUser.orderByChild("id").equalTo(currentuser);
                        check.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for (DataSnapshot ds : snapshot.getChildren()) {
                                    Friend_Request friend_request= new Friend_Request(currentuser,otherID);
                                    mfRequest.child(otherID).child(currentuser).setValue(friend_request);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.i("TAG", "loadPost:onCancelled", error.toException());
                            }
                        });

                    } else {
                        Log.d("user", "khong ton tai");
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if (iscurrentuser() == true) {
            fist.setEnabled(true);
            last.setEnabled(true);
            email.setEnabled(true);
            female.setEnabled(true);
            male.setEnabled(true);
        }
    }

    private void isOnline() {
        if (status.equals("online")) {
            profileimg.setBorderColor(Color.BLUE);
        }
        if (status.equals("offline")) {
            profileimg.setBorderColor(Color.GRAY);
        }
    }

    private boolean iscurrentuser() {
        if (otherID.equals(currentuser)) {
            return true;
        }
        return false;
    }

}