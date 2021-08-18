package com.example.shop_android.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shop_android.R;
import com.example.shop_android.adapter.MessageAdapter;
import com.example.shop_android.data.StaticConfig;
import com.example.shop_android.model.Messages;
import com.example.shop_android.model.User;
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
import com.google.firebase.database.core.Context;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class chat extends AppCompatActivity {
    //activity
    private ImageView imgsend, imgbback;
    private CircularImageView ortherAvata;
    private TextView fullname, tvstatus;
    private EditText message;
    private ListView listView;
    private Messages messages;
    private String tin_nhan;
    private ArrayList<Messages> listMess = new ArrayList<>();
    private MessageAdapter messageAdapter;
    //bien
    private static final String TAG = "currentuser";
    public String otherID = "";
    private String fist, last, status;
    private String userID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        setConntrol();
        setEnvent();
    }

    private void setEnvent() {
        message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (message.length() > 2) {
                    tin_nhan = message.getText().toString() + "\n";
                }
            }
        });
        imgsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!message.getText().toString().equals("")) {
                    Messages messages = new Messages(StaticConfig.currentuser, otherID, tin_nhan);
                    //lưu tin nhắn người nhận người gửi trên firebase
                    String idSender = StaticConfig.currentuser + "/" + otherID;
                    String idReceiver = otherID + "/" + StaticConfig.currentuser;
                    StaticConfig.mChat.child(idSender).push().setValue(messages);
                    StaticConfig.mChat.child(idReceiver).push().setValue(messages);
                    //xáo trắng
                    message.setText("");
                    //cập nhập
                    messageAdapter.notifyDataSetChanged();

                }

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
        listView = findViewById(R.id.listmess);

        //Lấy otherID
        Bundle bundle = getIntent().getExtras();
        otherID = bundle.getString("otherID");
        //Log.d("otherID:", otherID);

        userID = bundle.getString("userID");
        // Log.d(TAG, String.valueOf(userID));

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

//        test chat
        messageAdapter = new MessageAdapter(getApplicationContext(), R.layout.list_mess, listMess);
        listView.setAdapter(messageAdapter);
        Query chattest = StaticConfig.mChat.child(StaticConfig.currentuser).child(otherID).orderByChild("timestamp");
        chattest.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //xoá list mess
                listMess.removeAll(listMess);
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.exists()) {
                        Messages tinnhan = new Messages();
                        tinnhan = ds.getValue(Messages.class);
                        listMess.add(tinnhan);

                    }
                }
                messageAdapter.notifyDataSetChanged();
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