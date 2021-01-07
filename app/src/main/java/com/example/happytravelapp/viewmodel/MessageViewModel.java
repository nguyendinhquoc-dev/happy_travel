package com.example.happytravelapp.viewmodel;

import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.happytravelapp.data.FirebaseQueryLiveData;
import com.example.happytravelapp.model.Inbox;
import com.example.happytravelapp.model.Message;
import com.example.happytravelapp.ultil.Common;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MessageViewModel extends ViewModel {
    private static final DatabaseReference MESSAGE_REF = FirebaseDatabase.getInstance().getReference("/messages");
    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(MESSAGE_REF);

    //get all inbox livedata
    private List<Message> mList=new ArrayList<>();
    private final LiveData<List<Message>> listInboxLivedata= Transformations.map(liveData,new DeserializerListInbox());
    public LiveData<List<Message>> getListInboxLivedata() {
        return listInboxLivedata;
    }
    private class DeserializerListInbox implements Function<DataSnapshot, List<Message>> {
        @Override
        public List<Message> apply(DataSnapshot input) {
            mList.clear();
            for(DataSnapshot snapshot:input.getChildren()) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Message message = snapshot1.getValue(Message.class);
                    if (message.getInboxId().equalsIgnoreCase(Common.USER_ID + "_" + Common.HOTEL_ID)) {
                        mList.add(message);
                    }
                }
            }
            return mList;
        }
    }


    public MutableLiveData<String> message=new MutableLiveData<>();
    private final MutableLiveData<Boolean> addMessageIsSuccessful=new MutableLiveData<>();
    public MutableLiveData<Boolean> getAddMessageIsSuccessful() {
        return addMessageIsSuccessful;
    }
    public void addMessage(){
        String inboxId=Common.USER_ID+"_"+Common.HOTEL_ID;
        String senderId=Common.USER_ID;
        String senderName=Common.FULLNAME;
        String senderPicture=Common.IMAGE_URL;
        String messageId=MESSAGE_REF.push().getKey();
        Task uploadTask = MESSAGE_REF
                .child(inboxId)
                .child(messageId)
                .setValue(new Message(inboxId,senderId,senderName,senderPicture,imageUrl,messageId,message.getValue(), ServerValue.TIMESTAMP));
        uploadTask.addOnSuccessListener(o -> addMessageIsSuccessful.setValue(true));
    }

    //image upload
    private String imageUrl;
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    private final MutableLiveData<Boolean> imageUploadIsSuccessful = new MutableLiveData<>();
    public MutableLiveData<Boolean> getImageUploadIsSuccessful() {
        return imageUploadIsSuccessful;
    }
    public String uploadImage(Intent intentData) {
        Uri selectedUri = intentData.getData();
        StorageReference photoRef = FirebaseStorage.getInstance()
                .getReference().child("inboxs")
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
                    imageUrl=(String.valueOf(downloadUri));
                    imageUploadIsSuccessful.setValue(true);
                } else {
                    imageUploadIsSuccessful.setValue(false);
                }
            }
        });
        return imageUrl;
    }
}
