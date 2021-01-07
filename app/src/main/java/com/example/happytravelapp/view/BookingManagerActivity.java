package com.example.happytravelapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.happytravelapp.R;
import com.example.happytravelapp.adapter.HotelAdapter;
import com.example.happytravelapp.adapter.HotelBookingAdapter;
import com.example.happytravelapp.databinding.ActivityBookingManagerBinding;
import com.example.happytravelapp.model.Hotel;
import com.example.happytravelapp.model.HotelBooking;
import com.example.happytravelapp.viewmodel.HotelBookingViewModel;
import com.example.happytravelapp.viewmodel.UserViewmodel;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class BookingManagerActivity extends AppCompatActivity {
    ActivityBookingManagerBinding binding;
    HotelBookingViewModel hotelBookingViewModel;
    UserViewmodel userViewmodel;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_booking_manager);
        hotelBookingViewModel = ViewModelProviders.of(this).get(HotelBookingViewModel.class);
        userViewmodel = ViewModelProviders.of(this).get(UserViewmodel.class);
        binding.setLifecycleOwner(this);

        initRecycleview();
    }

    private void initRecycleview() {
        LiveData<List<HotelBooking>> listLiveData = hotelBookingViewModel.getHotelbookingListLivedata();
        listLiveData.observe(this, new Observer<List<HotelBooking>>() {
            @Override
            public void onChanged(List<HotelBooking> hotelBookings) {
                                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                                HotelBookingAdapter adapter = new HotelBookingAdapter(hotelBookings, new HotelBookingAdapter.ItemClickListener() {
                                    @Override
                                    public void onClick(HotelBooking hotelBooking) {
                                        //item click
                                    }
                                }, new HotelBookingAdapter.ItemLongClickListener() {
                                    @Override
                                    public boolean onLongClick(HotelBooking hotelBooking) {
                                        //item long ckick
                                        return false;
                                    }
                                });
                                binding.recyclerview.setLayoutManager(layoutManager);
                                binding.recyclerview.setAdapter(adapter);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

}
