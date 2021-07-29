package com.example.shop_android;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.Connection;
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

                mDatabase = Database.getReference("friends_list");
                //add friend test
//                mDatabase.child(currentuser).child(otherID).child("friendsID").setValue(otherID);
//                mDatabase.child(currentuser).child(otherID).child("UserID").setValue(currentuser);
                //kiểm tra cói phải bạn không nếu là bạn sẽ vào chat không sẽ vào profile
                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            if (ds.exists()) {
                                String dk1 = "";
                                String dk2 = "";
                                //gán giá trị friendsID vào ban để so sách
                                if (ds.hasChild(otherID)) {
                                    dk1 = ds.child(otherID).child("friendsID").getValue(String.class);
                                }
                                //Kiểm tra xem phải là bạn khôg
                                if (ds.hasChild(currentuser)) {
                                    if (ds.child(currentuser).child("UserID").getValue(String.class).equals(otherID) && ds.child(currentuser).child("friendsID").getValue(String.class).equals(currentuser)) {
                                        dk2 = ds.child(currentuser).child("friendsID").getValue(String.class);

                                    }
                                }

                                //coi xem có trong danh sách bạn không
                                if (dk1.equals(otherID) || dk2.equals(currentuser)) {
                                    //gửi other ID truyền qua chat
                                    Intent intent = new Intent(getContext(), chat.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("otherID", user.getId().toString());
                                    bundle.putString("userID", currentuser);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                } else if (!dk1.equals(otherID)) {
                                    Intent intent = new Intent(getContext(), profile.class);
                                    intent.putExtra("otherID", otherID);
                                    startActivity(intent);
                                }

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("Firebase Error:", error.toException().toString());
                    }
                });

            }
        });
    }


}
