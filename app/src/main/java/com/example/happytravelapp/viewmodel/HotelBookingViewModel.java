package com.example.happytravelapp.viewmodel;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.happytravelapp.data.FirebaseQueryLiveData;
import com.example.happytravelapp.model.Hotel;
import com.example.happytravelapp.model.HotelBooking;
import com.example.happytravelapp.ultil.Common;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HotelBookingViewModel extends ViewModel {
    private static final DatabaseReference HOTEl_BOOKING_REF = FirebaseDatabase.getInstance().getReference("/hotelboookings");
    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(HOTEl_BOOKING_REF);
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();

    private List<HotelBooking> mList = new ArrayList<>();
    private final LiveData<List<HotelBooking>> hotelbookingListLivedata = Transformations.map(liveData, new DeserializerList());
    public LiveData<List<HotelBooking>> getHotelbookingListLivedata() {
        return hotelbookingListLivedata;
    }
    private class DeserializerList implements Function<DataSnapshot, List<HotelBooking>> {
        @Override
        public List<HotelBooking> apply(DataSnapshot input) {
            mList.clear();
            for (DataSnapshot snap : input.getChildren()) {
                if(snap.child("userId").getValue(String.class).equalsIgnoreCase(Common.USER_ID)) {
                    HotelBooking hotelBooking = snap.getValue(HotelBooking.class);
                    mList.add(hotelBooking);
                }
            }
            return mList;
        }
    }

    //remove booking
    private final MutableLiveData<Boolean> removeBookingIsSuccessful = new MutableLiveData<>();
    public MutableLiveData<Boolean> getRemoveBookingIsSuccessful() {
        return removeBookingIsSuccessful;
    }
    public void removeFavourite(String bookingId) {
        Task deleteTask = HOTEl_BOOKING_REF.child(bookingId).removeValue();
        deleteTask.addOnCompleteListener(o -> removeBookingIsSuccessful.setValue(true));
    }
}
