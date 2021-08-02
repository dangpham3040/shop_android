package com.example.shop_android.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shop_android.R;
import com.example.shop_android.adapter.UserAdapter;
import com.example.shop_android.adapter.friendrequest_Adapter;
import com.example.shop_android.model.Friend_Request;
import com.example.shop_android.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_friends#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_friends extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ListView listView;
    //Firebase
    FirebaseDatabase Database;
    DatabaseReference mfRequest;
    ///Bien
    String currentuser="";
    private Friend_Request request;


    private ArrayList<Friend_Request> listfriends = new ArrayList<>();
    private static friendrequest_Adapter friendrequest_adapter;
    View view;

    public fragment_friends() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_friends.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_friends newInstance(String param1, String param2) {
        fragment_friends fragment = new fragment_friends();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_friends, container, false);
        // Inflate the layout for this fragment

        setControl();
        setEnvet();
        return view;
        // Inflate the layout for this fragment
    }

    private void setControl() {
        listView = view.findViewById(R.id.listfriend);

        currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Database=FirebaseDatabase.getInstance();
        mfRequest = Database.getReference("friend_request/"+currentuser);

        friendrequest_adapter = new friendrequest_Adapter(getContext(), R.layout.friends_request, listfriends);
        listView.setAdapter(friendrequest_adapter);

        mfRequest.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listfriends.removeAll(listfriends);
                for(DataSnapshot ds: snapshot.getChildren()){
                    if(ds.exists())
                    {
                        Friend_Request friend_request=new Friend_Request();
                        friend_request=ds.getValue(Friend_Request.class);
                        listfriends.add(friend_request);
                        friendrequest_adapter.notifyDataSetChanged();
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public static void update(int i) {
        friendrequest_adapter.remove(i);
    }

    private void setEnvet() {

    }
}