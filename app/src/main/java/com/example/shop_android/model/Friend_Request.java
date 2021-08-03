package com.example.shop_android.model;

public class Friend_Request {
    String idSender;
   String idReceiver;

    public Friend_Request() {

    }

    public String getIdSender() {
        return idSender;
    }

    public void setIdSender(String idSender) {
        this.idSender = idSender;
    }

    public String getIdReceiver() {
        return idReceiver;
    }

    public void setIdReceiver(String idReceiver) {
        this.idReceiver = idReceiver;
    }

    public Friend_Request(String idSender, String idReceiver) {
        this.idSender = idSender;
        this.idReceiver = idReceiver;
    }
}
