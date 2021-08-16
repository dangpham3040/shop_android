package com.example.shop_android.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.shop_android.R;
import com.example.shop_android.data.StaticConfig;
import com.example.shop_android.fragment.fragment_friends;
import com.example.shop_android.model.Friend_Request;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.sql.Array;
import java.util.ArrayList;

public class friendrequest_Adapter extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<Friend_Request> data;

    //bien
    String fullname = "";
    String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

    public friendrequest_Adapter(@NonNull Context context, int resource, ArrayList<Friend_Request> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        TextView name = convertView.findViewById(R.id.tvname);
        CircularImageView pic = convertView.findViewById(R.id.Avata);
        Button btnAdd = convertView.findViewById(R.id.addfriend);
        Button btnRemove = convertView.findViewById(R.id.remove);

        Friend_Request request = data.get(position);
        Query check = StaticConfig.mUser.orderByChild("id").equalTo(request.getIdSender());
        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.exists()) {

                        fullname = ds.child("fist").getValue(String.class) + " " + ds.child("last").getValue(String.class);
                        name.setText(fullname);

                        if (ds.child("status").getValue(String.class).equals("online")) {
                            pic.setBorderColor(Color.BLUE);
                        } else {
                            pic.setBorderColor(Color.GRAY);
                        }
                        Picasso.get()
                                .load(ds.child("pic").getValue(String.class))
                                .resize(50, 50) // here you resize your image to whatever width and height you like
                                .into(pic);


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //cho vào danh sách bạn của người dùng
                StaticConfig.mFriend.child(currentuser).child(request.getIdSender()).child("userID").setValue(currentuser);
                StaticConfig.mFriend.child(currentuser).child(request.getIdSender()).child("friendID").setValue(request.getIdSender());
                //cho vào danh sách bạn của bạn
                StaticConfig.mFriend.child(request.getIdSender()).child(currentuser).child("userID").setValue(request.getIdSender());
                StaticConfig.mFriend.child(request.getIdSender()).child(currentuser).child("friendID").setValue(currentuser);
                // xoá khỏi danh sách yêu cầu kết bạn
                // cập nhập ui
                fragment_friends.update(position);
                StaticConfig.mfRequest.child(request.getIdSender()).child(currentuser).removeValue();
            }
        });
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cập nhật ui
                fragment_friends.update(position);
                // xoá khỏi danh sách yêu cầu kết bạn
                StaticConfig.mfRequest.child(currentuser).child(request.getIdSender()).removeValue();
            }
        });
        return convertView;
    }
}
