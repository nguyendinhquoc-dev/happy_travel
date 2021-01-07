package com.example.happytravelapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.happytravelapp.R;
import com.example.happytravelapp.adapter.RoomTypeAdapter;
import com.example.happytravelapp.databinding.ActivityHotelDetailBinding;
import com.example.happytravelapp.model.Hotel;
import com.example.happytravelapp.model.Message;
import com.example.happytravelapp.model.RoomType;
import com.example.happytravelapp.ultil.Common;
import com.example.happytravelapp.view.fragment.InboxFragment;
import com.example.happytravelapp.viewmodel.HotelViewModel;
import com.example.happytravelapp.viewmodel.InboxViewModel;
import com.example.happytravelapp.viewmodel.RoomTypeViewmodel;
import com.example.happytravelapp.viewmodel.UserViewmodel;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class HotelDetailActivity extends AppCompatActivity {
    ActivityHotelDetailBinding binding;
    HotelViewModel hotelViewModel;
    RoomTypeViewmodel roomTypeViewmodel;
    UserViewmodel userViewmodel;
    InboxViewModel inboxViewModel;
    String hotel_uid;
    String user_uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hotel_detail);
        userViewmodel=ViewModelProviders.of(this).get(UserViewmodel.class);
        hotelViewModel = ViewModelProviders.of(this).get(HotelViewModel.class);
        roomTypeViewmodel=ViewModelProviders.of(this).get(RoomTypeViewmodel.class);
        inboxViewModel= ViewModelProviders.of(this).get(InboxViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setHotelviewmodel(hotelViewModel);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(R.string.hotel_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        hotel_uid=Common.HOTEL_ID;
        hotelViewModel.getHotelLivedata().observe(this, new Observer<Hotel>() {
            @Override
            public void onChanged(Hotel hotel) {
                binding.hotelName.setText(hotel.getHotelName());
                binding.provine.setText(hotel.getProvine());
                binding.district.setText(hotel.getDistrict());
                binding.standardStar.setText(hotel.getStandardStar());
                Common.HOTEL_IMAGE=hotel.getImageUrl();
                Common.PROVINE=hotel.getProvine();
                Common.DISTRICT=hotel.getDistrict();
                Common.ADRESS=hotel.getAddress();
            }
        });

        initRecycleview();

        binding.message.setOnClickListener(v->{
            inboxViewModel.addInbox(Common.USER_ID,Common.HOTEL_ID,Common.IMAGE_URL,Common.HOTEL_IMAGE,Common.FULLNAME,Common.HOTEL_NAME);
            Intent intent=new Intent(HotelDetailActivity.this, MessageActivity.class);
            startActivity(intent);
        });

        binding.llAdress.setOnClickListener(v->{
            String uri = "http://maps.google.com/maps?q="+Common.ADRESS+", "+Common.PROVINE+", "+Common.DISTRICT;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));

            if(intent !=null){
                intent.setPackage("com.google.android.apps.maps");

                startActivity(intent);
            }{
                Toast.makeText(getApplicationContext(), "Not App Maps", Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void initRecycleview(){
        LiveData<List<RoomType>> listLiveData=roomTypeViewmodel.getRoomtypeListLivedata();
        listLiveData.observe(this, new Observer<List<RoomType>>() {
            @Override
            public void onChanged(List<RoomType> roomTypes) {
                List<RoomType> newList=new ArrayList<>();
                for(int i=0;i<roomTypes.size();i++){
                    if(roomTypes.get(i).getHotelId().equalsIgnoreCase(hotel_uid)){
                        newList.add(roomTypes.get(i));
                    }
                }

                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                RoomTypeAdapter adapter=new RoomTypeAdapter(newList, new RoomTypeAdapter.ItemClickListener() {
                    @Override
                    public void onClick(RoomType roomType) {
                        //item click
                        userViewmodel.getUserLiveData().observe(HotelDetailActivity.this, new Observer<FirebaseUser>() {
                            @Override
                            public void onChanged(FirebaseUser firebaseUser) {
                                user_uid=firebaseUser.getUid();
                                Intent intent=new Intent(HotelDetailActivity.this,HotelBookingActivity.class);
                                Common.ROOMTYPE_ID=roomType.getRoomTypeid();
                                Common.ROOMTYPE_NAME=roomType.getTypeName();
                                Common.ROOM_PRICE=roomType.getPrice();
                                //Bundle bundle=new Bundle();
                                //bundle.putString("hotelId",hotel_uid);
                                //bundle.putString("roomId",roomType.getRoomTypeid());
                                //bundle.putString("roomtypeName",roomType.getTypeName());
                                //bundle.putString("userId",user_uid);
                                //intent.putExtra("booking",bundle);
                                startActivity(intent);
                            }
                        });

                    }
                }, new RoomTypeAdapter.ItemLongClickListener() {
                    @Override
                    public boolean onLongClick(RoomType roomType) {
                        //item long ckick
                        return false;
                    }
                });
                binding.recyclerview.setLayoutManager(layoutManager);
                binding.recyclerview.setAdapter(adapter);
            }
        });
    }
}
