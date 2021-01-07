package com.example.happytravelapp.repository;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.happytravelapp.data.DatabaseRouter;
import com.example.happytravelapp.data.FirebaseQueryLiveData;
import com.example.happytravelapp.model.User;
import com.example.happytravelapp.ultil.Role;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserRepository {
    private DatabaseReference user_ref;
    private FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(user_ref);

    private FirebaseAuth firebaseAuth;
    private MutableLiveData<FirebaseUser> userLiveData;
    private MutableLiveData<Boolean> loggedOutLiveData;
    private MutableLiveData<Boolean> userLoginIsSuccessful;
    private MutableLiveData<Boolean> userRegisterIsSuccessful;
    private MutableLiveData<String> imageUrl;
    private MutableLiveData<Boolean> imageUpdateIsSuccessful;

    //    private List<User> mList = new ArrayList<>();
//    private final LiveData<List<User>> userListLiveData = Transformations.map(liveData, new DeserializerUserList());

    public UserRepository() {
        user_ref = FirebaseDatabase.getInstance().getReference("/users");
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.userLiveData = new MutableLiveData<>();
        this.loggedOutLiveData = new MutableLiveData<>();
        this.userLoginIsSuccessful = new MutableLiveData<>();
        this.userRegisterIsSuccessful = new MutableLiveData<>();
        this.imageUrl = new MutableLiveData<>();
        this.imageUpdateIsSuccessful = new MutableLiveData<>();
        if (firebaseAuth.getCurrentUser() != null) {
            userLiveData.postValue(firebaseAuth.getCurrentUser());
            loggedOutLiveData.postValue(false);
        }
    }

    public void login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userLiveData.postValue(firebaseAuth.getCurrentUser());
                            userLoginIsSuccessful.setValue(true);
                        } else {
                            userLoginIsSuccessful.setValue(false);
                        }
                    }
                });
    }

    public void register(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userLiveData.postValue(firebaseAuth.getCurrentUser());
                            userRegisterIsSuccessful.setValue(true);
                        } else {
                            userRegisterIsSuccessful.setValue(false);
                        }
                    }
                });
    }

    public void saveToDatabase(String email, String phone, String fullname, String imageUrl, User.Gender gender) {
        String uid = firebaseAuth.getUid();
        Task uploadTask = user_ref
                .child(uid)
                .setValue(new User(email, phone, fullname, imageUrl, gender, Role.USER));
        uploadTask.addOnCompleteListener(v -> {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(fullname)
                    .setPhotoUri(Uri.parse(imageUrl))
                    .build();
            FirebaseAuth.getInstance().getCurrentUser().updateProfile(profileUpdates);

        });
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

    public void logOut() {
        firebaseAuth.signOut();
        loggedOutLiveData.postValue(true);
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return loggedOutLiveData;
    }

    public MutableLiveData<Boolean> getUserLoginIsSuccessful() {
        return userLoginIsSuccessful;
    }

    public MutableLiveData<Boolean> getUserRegisterIsSuccessful() {
        return userRegisterIsSuccessful;
    }

    public MutableLiveData<String> getImageUrl() {
        return imageUrl;
    }

    public MutableLiveData<Boolean> getImageUpdateIsSuccessful() {
        return imageUpdateIsSuccessful;
    }


//    public class DeserializerUser implements Function<DataSnapshot, User> {
//        @Override
//        public User apply(DataSnapshot dataSnapshot) {
//            for (DataSnapshot snap : dataSnapshot.getChildren()) {
//                String uid = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
//                if (snap.getKey().equalsIgnoreCase("LhrnRuwgtJU7TICcKkbC0052Xe73")) {
//                    user = snap.getValue(User.class);
//                }
//            }
//            return user;
//        }
//    }

    //    public LiveData<List<User>> getUserListLiveData() {
//        return userListLiveData;
//    }
//
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
