package com.example.happytravelapp.model;

import androidx.databinding.BaseObservable;

public class RoomType extends BaseObservable {
    private String roomTypeid;
    private String typeName;
    private String imageUrl;
    private int capacity;
    private String description;
    private int quantity;
    private float price;
    private String hotelId;

    public RoomType() {
    }

    public RoomType(String roomTypeid,String typeName, String imageUrl, int capacity, String description, int quantity, float price, String hotelId) {
        this.roomTypeid=roomTypeid;
        this.typeName = typeName;
        this.imageUrl = imageUrl;
        this.capacity = capacity;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.hotelId = hotelId;
    }

    public String getRoomTypeid() {
        return roomTypeid;
    }

    public void setRoomTypeid(String roomTypeid) {
        this.roomTypeid = roomTypeid;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String floatToString(float p){
        return String.format("%.0f",p)+" VND";
    }

    public String intToString(int p){
        return String.format("",p)+" VND";
    }


}
