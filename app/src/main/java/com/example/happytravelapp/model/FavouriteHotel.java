package com.example.happytravelapp.model;

public class FavouriteHotel {
    private String userId;
    private Hotel hotel;
    private Boolean isFavourite;

    public FavouriteHotel(String userId, Hotel hotel,Boolean isFavourite) {
        this.userId = userId;
        this.hotel=hotel;
        this.isFavourite=isFavourite;
    }

    public FavouriteHotel() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Boolean getFavourite() {
        return isFavourite;
    }

    public void setFavourite(Boolean favourite) {
        isFavourite = favourite;
    }
}
