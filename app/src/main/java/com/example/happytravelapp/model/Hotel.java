package com.example.happytravelapp.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import com.example.happytravelapp.ultil.PaymentMethods;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.Timer;

public class Hotel extends BaseObservable {
    private String hotelId;
    private String hotelName;
    private String imageUrl;
    private String provine;
    private String district;
    private String description;
    private String address;
    private String standardStar;
    private String typeHotel;
    private Map<String, Boolean> utilities;
    private PaymentMethods paymentMethods;
    private String openhours;
    private String userId;
    private float sumRating;


    public Hotel(String hotelId,String hotelName, String imageUrl, String provine, String district, String description,
                 String address, String standardStar, String typeHotel, Map<String,Boolean> utilities,
                 PaymentMethods paymentMethods,String openhours, String userId,float sumRating) {
        this.hotelId=hotelId;
        this.hotelName = hotelName;
        this.imageUrl = imageUrl;
        this.provine = provine;
        this.district = district;
        this.description = description;
        this.address = address;
        this.standardStar = standardStar;
        this.typeHotel = typeHotel;
        this.utilities = utilities;
        this.paymentMethods = paymentMethods;
        this.openhours=openhours;
        this.userId = userId;
        this.sumRating=sumRating;
    }

    public Hotel() {
    }


    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    @Bindable
    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
        notifyPropertyChanged(BR.hotelName);
    }

    public float getSumRating() {
        return sumRating;
    }

    public void setSumRating(float sumRating) {
        this.sumRating = sumRating;
    }

    @Bindable
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        notifyPropertyChanged(BR.imageUrl);
    }

    @Bindable
    public String getProvine() {
        return provine;
    }

    public void setProvine(String provine) {
        this.provine = provine;
        notifyPropertyChanged(BR.provine);
    }

    @Bindable
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
        notifyPropertyChanged(BR.district);
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    @Bindable
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        notifyPropertyChanged(BR.address);
    }

    @Bindable
    public String getStandardStar() {
        return standardStar;
    }

    public void setStandardStar(String standardStar) {
        this.standardStar = standardStar;
        notifyPropertyChanged(BR.standardStar);
    }

    @Bindable
    public String getTypeHotel() {
        return typeHotel;
    }

    public void setTypeHotel(String typeHotel) {
        this.typeHotel = typeHotel;
        notifyPropertyChanged(BR.typeHotel);
    }

    @Bindable
    public Map<String, Boolean> getUtilities() {
        return utilities;
    }

    public void setUtilities(Map<String, Boolean> utilities) {
        this.utilities = utilities;
        notifyPropertyChanged(BR.utilities);
    }

    @Bindable
    public PaymentMethods getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(PaymentMethods paymentMethods) {
        this.paymentMethods = paymentMethods;
        notifyPropertyChanged(BR.paymentMethods);
    }

    public String getOpenhours() {
        return openhours;
    }

    public void setOpenhours(String openhours) {
        this.openhours = openhours;
    }

    @Bindable
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
        notifyPropertyChanged(BR.userId);
    }
}
