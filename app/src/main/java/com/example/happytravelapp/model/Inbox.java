package com.example.happytravelapp.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.google.firebase.database.ServerValue;

import java.util.Map;

public class Inbox extends BaseObservable {
    private String inboxId;
    private String senderId;
    private String receiverId;
    private String profilePictureSender;
    private String profilePictureReceiver;
    private String senderName;
    private String receiverName;

    public Inbox() {
    }

    public Inbox(String inboxId, String senderId, String receiverId, String profilePictureSender, String profilePictureReceiver,String senderName,
                 String receiverName) {
        this.inboxId = inboxId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.profilePictureSender = profilePictureSender;
        this.profilePictureReceiver = profilePictureReceiver;
        this.senderName=senderName;
        this.receiverName=receiverName;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getProfilePictureSender() {
        return profilePictureSender;
    }

    public void setProfilePictureSender(String profilePictureSender) {
        this.profilePictureSender = profilePictureSender;
    }

    public String getProfilePictureReceiver() {
        return profilePictureReceiver;
    }

    public void setProfilePictureReceiver(String profilePictureReceiver) {
        this.profilePictureReceiver = profilePictureReceiver;
    }

    public String getInboxId() {
        return inboxId;
    }

    public void setInboxId(String inboxId) {
        this.inboxId = inboxId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }
}
