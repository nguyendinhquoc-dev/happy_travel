package com.example.happytravelapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happytravelapp.data.FirebaseQueryLiveData;
import com.example.happytravelapp.model.HotelBooking;
import com.example.happytravelapp.ultil.Common;
import com.example.happytravelapp.ultil.Status;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.Date;

public class AddHotelBookingViewModel extends ViewModel {
    private static final DatabaseReference HOTEl_BOOKING_REF = FirebaseDatabase.getInstance().getReference("/hotelboookings");
    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(HOTEl_BOOKING_REF);
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();

    public MutableLiveData<String> bookingId = new MutableLiveData<>();
    public MutableLiveData<String> bookingDate = new MutableLiveData<>();

    public MutableLiveData<String> checkinDate = new MutableLiveData<>();
    public MutableLiveData<String> checkoutDate = new MutableLiveData<>();
    public MutableLiveData<Integer> quantity = new MutableLiveData<>();
    public MutableLiveData<Float> amount = new MutableLiveData<>();

    private final MutableLiveData<Boolean> bookingCreatIsSuccessful = new MutableLiveData<>();

    public MutableLiveData<Boolean> getBookingCreatIsSuccessful() {
        return bookingCreatIsSuccessful;
    }

    int clickQuantity=0;
    float sumamount=0;
    public MutableLiveData<Integer> getQuantity() {
        quantity.setValue(clickQuantity);
        return quantity;
    }

    public MutableLiveData<Float> getAmount() {
        amount.setValue(sumamount);
        return amount;
    }

    public void increaseQuantity(){
        clickQuantity+=1;
        quantity.setValue(clickQuantity);
        sumamount=clickQuantity*Common.ROOM_PRICE;
        amount.setValue(sumamount);
    }

    public void reductionQuantity(){
        if(clickQuantity>0) {
            clickQuantity-=1;
            quantity.setValue(clickQuantity);
            sumamount=clickQuantity*Common.ROOM_PRICE;
            amount.setValue(sumamount);
        }
    }

    public void addBooking(String roomTypeId, String roomtypeName, String hotelId, String hotelName, String userId) {
        String key = HOTEl_BOOKING_REF.push().getKey();
        bookingId.setValue(key);
        Task uploadTask = HOTEl_BOOKING_REF
                .child(key)
                .setValue(new HotelBooking(bookingId.getValue(),roomTypeId,roomtypeName, hotelId,hotelName, userId, ServerValue.TIMESTAMP,
                        checkinDate.getValue(), checkoutDate.getValue(),quantity.getValue(),amount.getValue(), Status.BOOKING));
        uploadTask.addOnSuccessListener(o -> bookingCreatIsSuccessful.setValue(true));
    }
}
