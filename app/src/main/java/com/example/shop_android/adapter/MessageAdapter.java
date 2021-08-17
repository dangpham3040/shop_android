package com.example.shop_android.adapter;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.ConversationActions;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.shop_android.R;
import com.example.shop_android.model.Messages;

import java.util.ArrayList;

public class MessageAdapter extends ArrayAdapter {

    Context context;
    int resource;
    ArrayList<Messages> data;

    public MessageAdapter(@NonNull Context context, int resource, ArrayList<Messages> data) {
        super(context, resource, data);
        this.context=context;
        this.resource=resource;
        this.data=data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource,null);
        TextView mess = convertView.findViewById(R.id.tvmessage);
        Messages mes = data.get(position);
        mess.setText(mes.getText());
        return convertView;
    }
}
