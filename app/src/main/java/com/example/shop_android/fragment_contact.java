package com.example.shop_android;

import android.app.Service;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.INotificationSideChannel;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.config.GservicesValue;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.ServerValue;

import java.security.Provider;
import java.security.Timestamp;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class fragment_contact extends Fragment {
    private static final String TAG = "test";
    // khai bao
    private UserAdapter userAdapter;
    private User user;
    private ListView listView;
    //arraylist user
    private ArrayList<User> listuser = new ArrayList<>();
    //Firebase

    private FirebaseDatabase Database;
    private DatabaseReference mDatabase;
    private DatabaseReference fDatabase;
    //bien
    private String currentuser = "";
    private String otherID = "";
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_contact, container, false);
        setControl();
        setEvent();
        return view;
    }


    private void setControl() {

        listView = (ListView) view.findViewById(R.id.list);
        userAdapter = new UserAdapter(getContext(), R.layout.list, listuser);

        Database = FirebaseDatabase.getInstance();
        mDatabase = Database.getReference("User");

        //lấy id của user hiện tại
        currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        listView.setAdapter(userAdapter);
        //load list user
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //xoá list user
                listuser.removeAll(listuser);
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (!ds.child("id").getValue(String.class).equals(currentuser)) {
                        user = ds.getValue(User.class);
                        listuser.add(user);
                    }
                    userAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i(TAG, "loadPost:onCancelled", error.toException());
            }
        });
        //test giờ hien tai
//        String timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
//        Toast.makeText(getContext(), "Current Time Stamp: " + timeStamp, Toast.LENGTH_SHORT).show();
    }

    private void setEvent() {
        //ấn vào item danh sách
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = listuser.get(position);
                String full = user.getFist() + " " + user.getLast();
                Log.d(TAG, full);
                otherID = user.getId().toString();
                fDatabase = Database.getReference("friendship");
                //add friend test

//                mDatabase.child(otherID).child("id").setValue(otherID);
//                mDatabase.child(otherID).child(currentuser).child("friendID").setValue(currentuser);
//                fDatabase.child(currentuser).child(otherID).child("userID").setValue(currentuser);
//                fDatabase.child(currentuser).child(otherID).child("friendID").setValue(otherID);

                fDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot ds : snapshot.getChildren()) {
                            if (ds.exists()) {
                                if (ds.hasChild(otherID)) {
                                    if (ds.child(otherID).child("userID").getValue(String.class).equals(currentuser)
                                            && ds.child(otherID).child("friendID").getValue(String.class).equals(otherID)) {
                                        //gửi other ID truyền qua chat
                                        Intent intent = new Intent(getContext(), chat.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("otherID", user.getId().toString());
                                        bundle.putString("userID", currentuser);
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                    }
                                    else {
                                        Intent intent = new Intent(getContext(), profile.class);
                                        intent.putExtra("otherID", otherID);
                                        startActivity(intent);
                                    }

                                } else {
                                    Intent intent = new Intent(getContext(), profile.class);
                                    intent.putExtra("otherID", otherID);
                                    startActivity(intent);
                                }
                            }

                        }

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("Firebase Error: ", error.toException().getMessage());
                    }
                });
            }
        });
    }


}
