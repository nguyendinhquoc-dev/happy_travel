package com.example.happytravelapp.repository;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;

import com.example.happytravelapp.data.FirebaseQueryLiveData;
import com.example.happytravelapp.model.Hotel;
import com.example.happytravelapp.model.Post;
import com.example.happytravelapp.ultil.Common;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HotelRepository {
    private DatabaseReference hotel_ref;

    private FirebaseAuth firebaseAuth;
    static private List<Hotel> mListHotel;
    static  private Hotel hotel;

    public DatabaseReference getHotel_ref() {
        return hotel_ref;
    }

    public HotelRepository(){
        hotel_ref = FirebaseDatabase.getInstance().getReference("/hotels");
        this.firebaseAuth = FirebaseAuth.getInstance();
        mListHotel=new ArrayList<>();
        hotel=new Hotel();
    }

    public static class DeserializerHotel implements Function<DataSnapshot, Hotel> {
        @Override
        public Hotel apply(DataSnapshot dataSnapshot) {
            for (DataSnapshot snap : dataSnapshot.getChildren()) {
                if(snap.getKey().equalsIgnoreCase(Common.HOTEL_ID)) {
                    hotel = snap.getValue(Hotel.class);
                }
            }
            return hotel ;
        }
    }

    public static class DeserializerHotelList implements Function<DataSnapshot, List<Hotel>> {
        @Override
        public List<Hotel> apply(DataSnapshot dataSnapshot) {
            mListHotel.clear();
            for (DataSnapshot snap : dataSnapshot.getChildren()) {
                Hotel hotel = snap.getValue(Hotel.class);
                mListHotel.add(hotel);
            }
            return mListHotel;
        }
    }
}
