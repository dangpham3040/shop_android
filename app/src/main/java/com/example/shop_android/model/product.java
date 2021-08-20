package com.example.shop_android.model;

public class product {

    String title;
    String img;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public product(String title, String img) {
        this.title = title;
        this.img = img;
    }
}
