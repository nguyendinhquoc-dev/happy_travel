package com.example.happytravelapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.happytravelapp.R;
import com.example.happytravelapp.adapter.HotelAdapter;
import com.example.happytravelapp.databinding.ActivityBookHotelBinding;
import com.example.happytravelapp.model.Hotel;
import com.example.happytravelapp.ultil.Common;
import com.example.happytravelapp.viewmodel.FavouriteHotelViewmodel;
import com.example.happytravelapp.viewmodel.HotelViewModel;
import com.example.happytravelapp.viewmodel.UserViewmodel;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class BookHotelActivity extends AppCompatActivity {
    ActivityBookHotelBinding binding;
    HotelViewModel hotelViewModel;
    UserViewmodel userViewmodel;
    FavouriteHotelViewmodel favouriteHotelViewmodel;
    String userId;
    String hotelId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_hotel);
        hotelViewModel = ViewModelProviders.of(this).get(HotelViewModel.class);
        userViewmodel=ViewModelProviders.of(this).get(UserViewmodel.class);
        favouriteHotelViewmodel=ViewModelProviders.of(this).get(FavouriteHotelViewmodel.class);
        binding.setLifecycleOwner(this);
        binding.setHotelvm(hotelViewModel);

        initRecycleview();


//        binding.hochiminh.setOnClickListener(v->{
//                initRecycleviewHCM();
//            });
//
//        binding.hanoi.setOnClickListener(v->{
//                initRecycleviewHN();
//            });

        favouriteHotelViewmodel.getAddFavouriteIsSuccessful().observe(this,isSuccessful->{
            if (isSuccessful) {
                Toast.makeText(getApplicationContext(), "Thêm vào yêu thích thành công", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Thêm thất bại", Toast.LENGTH_LONG).show();
            }
        });

        }

        private void initRecycleview(){
            LiveData<List<Hotel>> listLiveData=hotelViewModel.getHotelListLivedata();
            listLiveData.observe(this, new Observer<List<Hotel>>() {
                @Override
                public void onChanged(List<Hotel> hotels) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                HotelAdapter adapter=new HotelAdapter(hotels, new HotelAdapter.ItemClickListener() {
                    @Override
                    public void onClick(Hotel hotel) {
                        //item click
                        Common.HOTEL_ID=hotelId;
                        Intent intent = new Intent(BookHotelActivity.this, HotelDetailActivity.class);
                        startActivity(intent);
                    }
                }, new HotelAdapter.ItemLongClickListener() {
                    @Override
                    public boolean onLongClick(Hotel hotel) {
                        //item long ckick
                        return false;
                    }
                });
                binding.recyclerview.setLayoutManager(layoutManager);
                binding.recyclerview.setAdapter(adapter);
            }
        });
    }

//    private void initRecycleviewHCM(){
//        LiveData<List<Hotel>> listLiveData=hotelViewModel.getHotelListLivedata2();
//        listLiveData.observe(this, new Observer<List<Hotel>>() {
//            @Override
//            public void onChanged(List<Hotel> hotels) {
//                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//                HotelAdapter adapter=new HotelAdapter(hotels, new HotelAdapter.ItemClickListener() {
//                    @Override
//                    public void onClick(Hotel hotel) {
//                        //item click
//                        String hotelId = hotel.getHotelId();
//                        Intent intent = new Intent(BookHotelActivity.this, HotelDetailActivity.class);
//                        intent.putExtra("hotel_uid", hotelId);
//                        startActivity(intent);
//                    }
//                }, new HotelAdapter.ItemLongClickListener() {
//                    @Override
//                    public boolean onLongClick(Hotel hotel) {
//                        //item long ckick
//                        return false;
//                    }
//                });
//                binding.recyclerview.setLayoutManager(layoutManager);
//                binding.recyclerview.setAdapter(adapter);
//            }
//        });
//    }
//
//    private void initRecycleviewHN(){
//        LiveData<List<Hotel>> listLiveData=hotelViewModel.getHotelListLivedata3();
//        listLiveData.observe(this, new Observer<List<Hotel>>() {
//            @Override
//            public void onChanged(List<Hotel> hotels) {
//                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//                HotelAdapter adapter=new HotelAdapter(hotels, new HotelAdapter.ItemClickListener() {
//                    @Override
//                    public void onClick(Hotel hotel) {
//                        //item click
//                        String hotelId = hotel.getHotelId();
//                        Intent intent = new Intent(BookHotelActivity.this, HotelDetailActivity.class);
//                        intent.putExtra("hotel_uid", hotelId);
//                        startActivity(intent);
//                    }
//                }, new HotelAdapter.ItemLongClickListener() {
//                    @Override
//                    public boolean onLongClick(Hotel hotel) {
//                        //item long ckick
//                        return false;
//                    }
//                });
//                binding.recyclerview.setLayoutManager(layoutManager);
//                binding.recyclerview.setAdapter(adapter);
//            }
//        });
//    }
}
