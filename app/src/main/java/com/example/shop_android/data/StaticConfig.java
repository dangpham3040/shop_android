package com.example.shop_android.data;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class StaticConfig {
    public static FirebaseDatabase Database = FirebaseDatabase.getInstance();
    public static DatabaseReference mUser = Database.getReference("User");
    public static DatabaseReference mFriend = Database.getReference("friendship");
    public static DatabaseReference mfRequest = Database.getReference("friend_request");
    public static DatabaseReference mChat = Database.getReference("Chat");

    public static final int PICK_IMAGE_REQUEST = 10;

    public static FirebaseAuth fAuth;
    public static FirebaseStorage storage = FirebaseStorage.getInstance();
    public static StorageReference storageReference = storage.getReference();
    public static String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
    public static FirebaseUser updateUser = FirebaseAuth.getInstance().getCurrentUser();
    public static String Default_avatar = "https://firebasestorage.googleapis.com/v0/b/android-shop-ae9d2.appspot.com/o/Image%2Fuser.jpg?alt=media&token=710dd3aa-8bb1-4048-bb0f-13320ad94825";

}
