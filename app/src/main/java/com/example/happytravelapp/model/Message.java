package com.example.happytravelapp.model;

import androidx.databinding.BaseObservable;

public class Message extends BaseObservable {
    private String inboxId;
    private String senderId;
    private String senderName;
    private String senderPicture;
    private String imageUrl;
    private String messageId;
    private String message;
    private Object timestamp;

    public Message() {
    }

    public Message(String inboxId, String senderId,String senderName,String senderPicture,String imageUrl, String messageId, String message, Object timestamp) {
        this.inboxId = inboxId;
        this.senderId=senderId;
        this.senderName=senderName;
        this.senderPicture=senderPicture;
        this.imageUrl = imageUrl;
        this.messageId = messageId;
        this.message = message;
        this.timestamp = timestamp;
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

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderPicture() {
        return senderPicture;
    }

    public void setSenderPicture(String senderPicture) {
        this.senderPicture = senderPicture;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }
}
