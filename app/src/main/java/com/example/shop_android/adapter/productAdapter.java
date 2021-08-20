package com.example.shop_android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.shop_android.R;
import com.example.shop_android.model.product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class productAdapter extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<product> data;

    public productAdapter(@NonNull Context context, int resource, ArrayList<product> data) {
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
        TextView title = convertView.findViewById(R.id.product_title);
        ImageView img = convertView.findViewById(R.id.product_img);

        product product = data.get(position);
        title.setText(product.getTitle());
        Picasso.get()
                .load(product.getImg())
                .into(img);

        return convertView;
    }
}
