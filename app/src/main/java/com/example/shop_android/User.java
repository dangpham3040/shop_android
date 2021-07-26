package com.example.shop_android;

public class User {
    String id,fist,last,email,sex;
    String pic;

    public User(String id, String fist, String last, String email, String sex, String pic) {
        this.id = id;
        this.fist = fist;
        this.last = last;
        this.email = email;
        this.sex = sex;
        this.pic = pic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User(){

    }
    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
    public String fullname(){
        return fist+" "+last;
    }
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getFist() {
        return fist;
    }

    public void setFist(String fist) {
        this.fist = fist;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
