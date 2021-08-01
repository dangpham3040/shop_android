package com.example.shop_android.model;

public class Friend_Request {
    String id;
    String name;
    String pic;
    String status;

    public Friend_Request(String id, String name, String pic, String status) {
        this.id = id;
        this.name = name;
        this.pic = pic;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
