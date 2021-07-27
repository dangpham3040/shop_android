package com.example.shop_android;

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
    //Firebase
    private FirebaseDatabase Database;
    private DatabaseReference mDatabase;
    //bien
    private String currentuser = "";
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

        //load list user
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (!ds.child("id").getValue(String.class).equals(currentuser)) {
                        //xoá list user
                        listuser.removeAll(listuser);
                        user = ds.getValue(User.class);
                        listuser.add(user);

                    }
                }
                listView.setAdapter(userAdapter);

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
                //gửi other ID truyền qua chat
                Intent intent = new Intent(getContext(), chat.class);
                Bundle bundle = new Bundle();
                bundle.putString("otherID", user.getId().toString());
                bundle.putString("userID", currentuser);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
