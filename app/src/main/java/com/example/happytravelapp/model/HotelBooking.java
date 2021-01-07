package com.example.happytravelapp.model;

import android.graphics.drawable.Drawable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.example.happytravelapp.ultil.Status;

import java.util.Date;

public class HotelBooking extends BaseObservable {
    private String bookingId;
    private String roomtypeId;
    private String roomtypeName;
    private String hotelId;
    private String hotelName;
    private String userId;
    private Object bookingDate;
    private String checkinDate;
    private String checkoutDate;
    private int quantity;
    private float amount;
    private Status status;

    public HotelBooking() {
    }

    public HotelBooking(String bookingId,String roomtypeId,String roomtypeName ,String hotelId,
                        String hotelName, String userId, Object bookingDate, String checkinDate,
                        String checkoutDate, int quantity,float amount,Status status) {
        this.bookingId=bookingId;
        this.roomtypeId = roomtypeId;
        this.roomtypeName=roomtypeName;
        this.hotelId = hotelId;
        this.hotelName=hotelName;
        this.userId = userId;
        this.bookingDate = bookingDate;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.quantity=quantity;
        this.amount=amount;
        this.status = status;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getRoomtypeName() {
        return roomtypeName;
    }

    public void setRoomtypeName(String roomtypeName) {
        this.roomtypeName = roomtypeName;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    @Bindable
    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
        notifyPropertyChanged(BR.bookingId);
    }

    @Bindable
    public String getRoomtypeId() {
        return roomtypeId;
    }

    public void setRoomtypeId(String roomtypeId) {
        this.roomtypeId = roomtypeId;
        notifyPropertyChanged(BR.roomtypeId);
    }

    @Bindable
    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
        notifyPropertyChanged(BR.hotelId);
    }

    @Bindable
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
        notifyPropertyChanged(BR.userId);
    }

    @Bindable
    public Object getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Object bookingDate) {
        this.bookingDate = bookingDate;
        notifyPropertyChanged(BR.bookingDate);
    }

    @Bindable
    public String getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(String checkinDate) {
        this.checkinDate = checkinDate;
        notifyPropertyChanged(BR.checkinDate);
    }

    @Bindable
    public String getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = checkoutDate;
        notifyPropertyChanged(BR.checkoutDate);
    }

    @Bindable
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
        notifyPropertyChanged(BR.status);
    }
}


