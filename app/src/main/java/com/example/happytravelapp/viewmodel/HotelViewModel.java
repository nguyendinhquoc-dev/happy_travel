package com.example.happytravelapp.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.happytravelapp.data.FirebaseQueryLiveData;
import com.example.happytravelapp.model.Hotel;
import com.example.happytravelapp.repository.HotelRepository;
import com.example.happytravelapp.ultil.Common;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HotelViewModel extends ViewModel {
    private DatabaseReference hotel_ref = FirebaseDatabase.getInstance().getReference("hotels");
    ;
    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(hotel_ref);

    private HotelRepository hotelRepository;
    private final LiveData<List<Hotel>> hotelListLivedata = Transformations.map(liveData, new HotelRepository.DeserializerHotelList());
    private final LiveData<Hotel> hotelLivedata = Transformations.map(liveData, new HotelRepository.DeserializerHotel());

    public LiveData<List<Hotel>> getHotelListLivedata() {
        return hotelListLivedata;
    }

    public LiveData<Hotel> getHotelLivedata() {
        return hotelLivedata;
    }

    public MutableLiveData<String> imageUrl = new MutableLiveData<>();
    public MutableLiveData<String> hotelName = new MutableLiveData<>();
    public MutableLiveData<String> provine = new MutableLiveData<>();
    public MutableLiveData<String> district = new MutableLiveData<>();
    public MutableLiveData<String> standardStar = new MutableLiveData<>();
    Hotel hotel;

    public HotelViewModel() {
        super();
        hotelRepository = new HotelRepository();
        hotel = new Hotel();
    }


//    public void getHotelDetail(String hotelId) {
//        hotel = hotelRepository.getHotel(hotelId);
//        imageUrl.setValue(hotel.getImageUrl());
//        hotelName.setValue(hotel.getHotelName());
//        provine.setValue(hotel.getProvine());
//        district.setValue(hotel.getDistrict());
//        standardStar.setValue(hotel.getStandardStar());
//        Common.ADRESS = hotel.getAddress();
//        Common.PROVINE = hotel.getProvine();
//        Common.DISTRICT = hotel.getDistrict();
//    }


//    private List<Hotel> mList2 = new ArrayList<>();
//    private final LiveData<List<Hotel>> hotelListLivedata2 = Transformations.map(liveData, new DeserializerList2());
//    public LiveData<List<Hotel>> getHotelListLivedata2() {
//        return hotelListLivedata2;
//    }
//    private class DeserializerList2 implements Function<DataSnapshot, List<Hotel>> {
//        @Override
//        public List<Hotel> apply(DataSnapshot input) {
//            mList.clear();
//            for (DataSnapshot snap : input.getChildren()) {
//                if (snap.child("provine").getValue().toString().equalsIgnoreCase("Thành phố Hồ Chí Minh")) {
//                    Hotel hotel = snap.getValue(Hotel.class);
//                    mList2.add(hotel);
//                }
//            }
//            return mList2;
//        }
//    }
//
//    private List<Hotel> mList3 = new ArrayList<>();
//    private final LiveData<List<Hotel>> hotelListLivedata3 = Transformations.map(liveData, new DeserializerList3());
//    public LiveData<List<Hotel>> getHotelListLivedata3() {
//        return hotelListLivedata3;
//    }
//    private class DeserializerList3 implements Function<DataSnapshot, List<Hotel>> {
//        @Override
//        public List<Hotel> apply(DataSnapshot input) {
//            mList.clear();
//            for (DataSnapshot snap : input.getChildren()) {
//                if (snap.child("provine").getValue().toString().equalsIgnoreCase("Thành phố Hà Nội")) {
//                    Hotel hotel = snap.getValue(Hotel.class);
//                    mList3.add(hotel);
//                }
//            }
//            return mList3;
//        }
//    }


    public String query;

    public SearchView.OnQueryTextListener getOnQueryTextListener() {
        return onQueryTextListener;
    }

    private final SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };
}
