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
import com.example.shop_android.model.Friend_Request;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.sql.Array;
import java.util.ArrayList;

public class friendrequest_Adapter extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<Friend_Request> data;

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
        name.setText(request.getName());
        Picasso.get()
                .load(request.getPic())
                .resize(50, 50) // here you resize your image to whatever width and height you like
                .into(pic);
        if (request.getStatus().equals("online")) {
            pic.setBorderColor(Color.BLUE);
        }
        if (request.getStatus().equals("offline")) {
            pic.setBorderColor(Color.GRAY);
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "helo", Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }
}
