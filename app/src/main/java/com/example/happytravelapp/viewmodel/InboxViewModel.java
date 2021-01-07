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
import com.example.happytravelapp.ultil.Common;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class InboxViewModel extends ViewModel {
    private static final DatabaseReference INBOX_REF = FirebaseDatabase.getInstance().getReference("/inboxs");
    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(INBOX_REF);

    //get all inbox livedata
    private List<Inbox> mList=new ArrayList<>();
    private final LiveData<List<Inbox>> listInboxLivedata= Transformations.map(liveData,new DeserializerListInbox());
    public LiveData<List<Inbox>> getListInboxLivedata() {
        return listInboxLivedata;
    }
    private class DeserializerListInbox implements Function<DataSnapshot, List<Inbox>> {
        @Override
        public List<Inbox> apply(DataSnapshot input) {
            mList.clear();
            for(DataSnapshot snapshot:input.getChildren()){
                    Inbox inbox=snapshot.getValue(Inbox.class);
                    if(inbox.getSenderId().contains(Common.USER_ID)) {
                        mList.add(inbox);
                    }
                    for(int i=0;i<mList.size()-1;i++){
                        if(mList.get(i+1).getInboxId().equalsIgnoreCase(mList.get(i).getInboxId())){
                            mList.remove(mList.get(i+1));
                        }
                    }
            }
            return mList;
        }
    }

    //add inbox
    public MutableLiveData<String> message=new MutableLiveData<>();
    private final MutableLiveData<Boolean> addInboxIsSuccessful=new MutableLiveData<>();
    public MutableLiveData<Boolean> getAddInboxIsSuccessful() {
        return addInboxIsSuccessful;
    }

    public void addInbox(String senderId,String receiverId,String profilePictureSender,String profilePictureReceiver,String senderName,String receiverName ){
        String key=senderId+"_"+receiverId;
        String inboxId=key;
        Task uploadTask = INBOX_REF
                .child(key)
                .setValue(new Inbox(inboxId,senderId,receiverId,profilePictureSender,profilePictureReceiver,senderName,receiverName));
        uploadTask.addOnSuccessListener(o -> addInboxIsSuccessful.setValue(true));
    }
}
