package com.example.happytravelapp.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseRouter {
    static FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    public static DatabaseReference getPostsRef() {
        return firebaseDatabase.getReference("/users");
    }
}
