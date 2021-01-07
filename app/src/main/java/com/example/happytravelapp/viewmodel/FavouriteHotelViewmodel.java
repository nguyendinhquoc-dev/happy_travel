package com.example.happytravelapp.viewmodel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.happytravelapp.data.FirebaseQueryLiveData;
import com.example.happytravelapp.model.FavouriteHotel;
import com.example.happytravelapp.model.Hotel;
import com.example.happytravelapp.model.HotelBooking;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavouriteHotelViewmodel extends ViewModel {
    private static final DatabaseReference FAVOURITEHOTEL_REF = FirebaseDatabase.getInstance().getReference("/favourite_hotels");
    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(FAVOURITEHOTEL_REF);
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();


    private final MutableLiveData<Boolean> addFavouriteIsSuccessful = new MutableLiveData<>();

    public MutableLiveData<Boolean> getAddFavouriteIsSuccessful() {
        return addFavouriteIsSuccessful;
    }

    public void addFavourite(Hotel hotel, String userId) {
        String key = "FH" +"_"+userId+"_"+hotel.getHotelId();
        Task uploadTask = FAVOURITEHOTEL_REF
                .child(key)
                .setValue(new FavouriteHotel(userId,hotel,true));
        uploadTask.addOnSuccessListener(o -> addFavouriteIsSuccessful.setValue(true));
    }

    private final MutableLiveData<Boolean> removeFavouriteIsSuccessful = new MutableLiveData<>();

    public MutableLiveData<Boolean> getRemoveFavouriteIsSuccessful() {
        return removeFavouriteIsSuccessful;
    }

    public void removeFavourite(String userId, String hotelId) {
        Task deleteTask = FAVOURITEHOTEL_REF.child("FH" + userId + hotelId).removeValue();
        deleteTask.addOnCompleteListener(o -> removeFavouriteIsSuccessful.setValue(true));
    }

    private List<FavouriteHotel> mList = new ArrayList<>();
    private final LiveData<List<FavouriteHotel>> favouriteHotelListLivedata = Transformations.map(liveData, new DeserializerList());

    public LiveData<List<FavouriteHotel>> getFavouriteHotelListLivedata() {
        return favouriteHotelListLivedata;
    }

    private class DeserializerList implements Function<DataSnapshot, List<FavouriteHotel>> {
        @Override
        public List<FavouriteHotel> apply(DataSnapshot input) {
            mList.clear();
            for (DataSnapshot snap : input.getChildren()) {
                FavouriteHotel favouriteHotel = snap.getValue(FavouriteHotel.class);
                mList.add(favouriteHotel);
            }
            return mList;
        }
    }
}
