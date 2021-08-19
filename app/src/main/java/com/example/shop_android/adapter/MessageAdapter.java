package com.example.shop_android.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.shop_android.R;
import com.example.shop_android.data.StaticConfig;
import com.example.shop_android.model.Messages;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


import static com.example.shop_android.R.drawable.ic_chat_bubble_other;


public class MessageAdapter extends ArrayAdapter {

    Context context;
    int resource;
    ArrayList<Messages> data;

    public MessageAdapter(@NonNull Context context, int resource, ArrayList<Messages> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        TextView mess = convertView.findViewById(R.id.tvmessage);
        Messages mes = data.get(position);
        LinearLayout linearLayout = convertView.findViewById(R.id.bubble);

        mess.setText(mes.getText());
        Query chattest = StaticConfig.mChat.child(StaticConfig.currentuser).child(mes.getIdReceiver()).orderByChild("timestamp");
        chattest.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.exists()) {
                        //Kiểm tra người dùng hiện tại có phải người gửi không
                        if (ds.child("idSender").getValue(String.class).equals(StaticConfig.currentuser)) {
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            params.weight = 1.0f;
                            params.gravity = Gravity.RIGHT;
                            linearLayout.setLayoutParams(params);
                            linearLayout.setBackgroundResource(ic_chat_bubble_other);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return convertView;
    }
}
