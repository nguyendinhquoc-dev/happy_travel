package com.example.happytravelapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.happytravelapp.R;
import com.example.happytravelapp.databinding.ActivityHotelBookingBinding;
import com.example.happytravelapp.ultil.Common;
import com.example.happytravelapp.viewmodel.AddHotelBookingViewModel;

import java.util.Calendar;

public class HotelBookingActivity extends AppCompatActivity {
    ActivityHotelBookingBinding binding;
    AddHotelBookingViewModel addHotelBookingViewModel;
    String hotel_uid,hotelName;
    String room_uid,roomtypeName;
    String user_uid;
    String payamout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hotel_booking);
        addHotelBookingViewModel = ViewModelProviders.of(this).get(AddHotelBookingViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setHotelbookingVM(addHotelBookingViewModel);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(R.string.title_addbooking);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //getDataBundle();
        user_uid= Common.USER_ID;
        hotel_uid=Common.HOTEL_ID;
        hotelName=Common.HOTEL_NAME;
        room_uid=Common.ROOMTYPE_ID;
        roomtypeName=Common.ROOMTYPE_NAME;
        binding.userId.setText(user_uid);

        addHotelBookingViewModel.getQuantity().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.quantity.setText(integer+"");
            }
        });

        addHotelBookingViewModel.getAmount().observe(this, new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
               binding.amount.setText(aFloat+"");
                Common.AMOUNT=aFloat;
                payamout= String.valueOf(aFloat);

            }
        });


        binding.checkindate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                chooseCheckinDate();
            }
        });
        binding.checkoutdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                chooseCheckoutDate();
            }
        });

        binding.booking.setOnClickListener(v->{
            addHotelBookingViewModel.addBooking(room_uid,roomtypeName,hotel_uid,hotelName,user_uid);
        });
        addHotelBookingViewModel.getBookingCreatIsSuccessful().observe(this, isSuccessful->{
            if (isSuccessful) {
                Toast.makeText(getApplicationContext(), "Đặt phòng", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(HotelBookingActivity.this,PaymentPaypal.class);
                intent.putExtra("amount",payamout);
                startActivity(intent);

            } else {
                Toast.makeText(getApplicationContext(), "Không thành công", Toast.LENGTH_LONG).show();
            }
        });
    }

//    public void getDataBundle(){
//        Bundle bundle=getIntent().getBundleExtra("booking");
//        //hotel_uid=bundle.getString("hotelId");
//        room_uid=bundle.getString("roomId");
//        roomtypeName=bundle.getString("roomtypeName");
//        //user_uid=bundle.getString("userId");
//    }

    public void chooseCheckinDate(){
        Calendar mcurrentTime = Calendar.getInstance();
        int year=mcurrentTime.get(Calendar.YEAR);
        int month=mcurrentTime.get(Calendar.MONTH);
        int day=mcurrentTime.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog;
        datePickerDialog=new DatePickerDialog(HotelBookingActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                binding.checkindate.setText(dayOfMonth+"-"+month+"-"+year);
            }
        },year,month,day);
        datePickerDialog.setTitle("Chọn ngày checkin");
        datePickerDialog.show();
    }

    public void chooseCheckoutDate(){
        Calendar mcurrentTime = Calendar.getInstance();
        int year=mcurrentTime.get(Calendar.YEAR);
        int month=mcurrentTime.get(Calendar.MONTH);
        int day=mcurrentTime.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog;
        datePickerDialog=new DatePickerDialog(HotelBookingActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                binding.checkoutdate.setText(dayOfMonth+"-"+month+"-"+year);
            }
        },year,month,day);
        datePickerDialog.setTitle("Chọn ngày checkout");
        datePickerDialog.show();
    }
}
