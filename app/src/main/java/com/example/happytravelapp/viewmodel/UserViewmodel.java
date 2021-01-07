package com.example.happytravelapp.viewmodel;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.happytravelapp.R;
import com.example.happytravelapp.data.FirebaseQueryLiveData;
import com.example.happytravelapp.model.ObservableString;
import com.example.happytravelapp.model.User;
import com.example.happytravelapp.repository.UserRepository;
import com.example.happytravelapp.ultil.FormErrors;
import com.example.happytravelapp.ultil.Role;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserViewmodel extends ViewModel {
    UserRepository userRepository;

    private static final DatabaseReference USER_REF = FirebaseDatabase.getInstance().getReference("/users");
    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(USER_REF);
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();

    //    private List<User> mList = new ArrayList<>();
//    private final LiveData<List<User>> userListLiveData = Transformations.map(liveData, new DeserializerUserList());
    private final MutableLiveData<FirebaseUser> userLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> userUpdateIsSuccessful = new MutableLiveData<>();
    private final MutableLiveData<Boolean> imageUpdateIsSuccessful = new MutableLiveData<>();

    //    public LiveData<List<User>> getUserListLiveData() {
//        return userListLiveData;
//    }
    public MutableLiveData<FirebaseUser> getUserLiveData() {
        if (mAuth.getCurrentUser() != null) {
            userLiveData.postValue(mAuth.getCurrentUser());
        }
        return userLiveData;
    }

    public MutableLiveData<Boolean> getUserUpdateIsSuccessful() {
        return userUpdateIsSuccessful;
    }

    public MutableLiveData<String> imageUrl = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> phone = new MutableLiveData<>();
    public MutableLiveData<String> fullname = new MutableLiveData<>();
    public MutableLiveData<User.Gender> gender = new MutableLiveData<>();

    public UserViewmodel() {
        super();
        userRepository = new UserRepository();
    }

    public void getUserDetail(){
        String uid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        USER_REF.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=new User();
                user=snapshot.getValue(User.class);
                imageUrl.setValue(user.getAvatarUrl());
                email.setValue(user.getEmail());
                phone.setValue(user.getPhone());
                fullname.setValue(user.getFullname());
                gender.setValue(user.getGender());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updateUser() {
        String uid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        User user = new User(email.getValue(), phone.getValue(), fullname.getValue(), imageUrl.getValue(), gender.getValue(), Role.USER);
        Task uploadTask = USER_REF
                .child(uid)
                .setValue(user);
        uploadTask.addOnSuccessListener(o -> {
            userUpdateIsSuccessful.setValue(true);
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(fullname.getValue())
                    .setPhotoUri(Uri.parse(imageUrl.getValue()))
                    .build();
            FirebaseAuth.getInstance().getCurrentUser().updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                }
            });
        });
    }

    public void logout() {
        mAuth.signOut();
    }

    public String uploadImage(Intent intentData) {
        Uri selectedUri = intentData.getData();
        StorageReference photoRef = FirebaseStorage.getInstance()
                .getReference().child("users")
                .child(selectedUri.getLastPathSegment());

        UploadTask uploadTask = photoRef.putFile(selectedUri);
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return photoRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    imageUrl.setValue(String.valueOf(downloadUri));
                    imageUpdateIsSuccessful.setValue(true);
                } else {
                    imageUpdateIsSuccessful.setValue(false);
                }
            }
        });
        return imageUrl.getValue();
    }

//    private class DeserializerUserList implements Function<DataSnapshot, List<User>> {
//        @Override
//        public List<User> apply(DataSnapshot dataSnapshot) {
//            mList.clear();
//            for (DataSnapshot snap : dataSnapshot.getChildren()) {
//                User user = snap.getValue(User.class);
//                mList.add(user);
//            }
//            return mList;
//        }
//    }
}
