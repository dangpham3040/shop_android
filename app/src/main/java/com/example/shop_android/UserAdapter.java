package com.example.shop_android;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class UserAdapter extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<User> data;

    public UserAdapter(@NonNull Context context, int resource, ArrayList<User> data) {
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
        TextView tvname = convertView.findViewById(R.id.tvname);
        TextView tvemail = convertView.findViewById(R.id.tvemail);
        CircularImageView anh = convertView.findViewById(R.id.ortherAvata);
        TextView tvstatus = convertView.findViewById(R.id.tvstatus);


        User user = data.get(position);
        tvname.setText(user.fullname());
        tvemail.setText(user.getEmail());
        tvstatus.setText(user.getStatus());
        //anh.setImageResource(R.drawable.user);
        Picasso.get()
                .load(user.getPic())
                .resize(50, 50) // here you resize your image to whatever width and height you like
                .into(anh);
        if(user.getStatus().equals("online")){
            anh.setBorderColor(Color.BLUE);
        }
        if(user.getStatus().equals("offline")){
            anh.setBorderColor(Color.GRAY);
        }
        return convertView;
    }
}
