package com.example.shop_android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.shop_android.R;
import com.example.shop_android.adapter.UserAdapter;
import com.example.shop_android.data.StaticConfig;
import com.example.shop_android.ui.chat;

import com.example.shop_android.model.User;
import com.example.shop_android.ui.profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class fragment_contact extends Fragment {
    private static final String TAG = "test";
    // khai bao
    private UserAdapter userAdapter;
    private User user;
    private ListView listView;
    //arraylist user
    private ArrayList<User> listuser = new ArrayList<>();

    //bien

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
        userAdapter = new UserAdapter(getContext(), R.layout.list_user, listuser);
        listView.setAdapter(userAdapter);
        //load list user
        StaticConfig.mUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //xoá list user
                listuser.removeAll(listuser);
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (!ds.child("id").getValue(String.class).equals(StaticConfig.currentuser)) {

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
    }

    private void setEvent() {
        //ấn vào item danh sách
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = listuser.get(position);
                String full = user.getFist() + " " + user.getLast();
                Log.d(TAG, full);
                otherID = user.getId();

                StaticConfig.mFriend.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            if (ds.exists()) {
                                if (ds.hasChild(otherID)) {
                                    if (ds.child(otherID).child("userID").getValue(String.class).equals(StaticConfig.currentuser)
                                            && ds.child(otherID).child("friendID").getValue(String.class).equals(otherID)) {
                                        //gửi other ID truyền qua chat
                                        Intent intent = new Intent(getContext(), chat.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("otherID", otherID);
                                        bundle.putString("userID", StaticConfig.currentuser);
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                        //Dừng
                                        break;
                                    }

                                } else {
                                    // Vào profile
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
