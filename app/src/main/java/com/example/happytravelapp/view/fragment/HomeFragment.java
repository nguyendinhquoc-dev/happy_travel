package com.example.happytravelapp.view.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.happytravelapp.R;
import com.example.happytravelapp.adapter.HighlightsHotelAdapter;
import com.example.happytravelapp.adapter.HotelAdapter;
import com.example.happytravelapp.databinding.FragmentAccountBinding;
import com.example.happytravelapp.databinding.FragmentHomeBinding;
import com.example.happytravelapp.model.FavouriteHotel;
import com.example.happytravelapp.model.Hotel;
import com.example.happytravelapp.ultil.Common;
import com.example.happytravelapp.view.BookHotelActivity;
import com.example.happytravelapp.view.HotelDetailActivity;
import com.example.happytravelapp.viewmodel.FavouriteHotelViewmodel;
import com.example.happytravelapp.viewmodel.HotelViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    HotelViewModel hotelViewModel;
    FavouriteHotelViewmodel favouriteHotelViewmodel;
    ViewFlipper v_fliper;
    String location="FPT Polytechnic HCM, Nam Kỳ Khởi Nghĩa, phường 7, Quận 3, Thành phố Hồ Chí Minh";


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hotelViewModel= ViewModelProviders.of(this).get(HotelViewModel.class);
        favouriteHotelViewmodel=ViewModelProviders.of(this).get(FavouriteHotelViewmodel.class);

        binding.hotelboook.setOnClickListener(v->{
            startActivity(new Intent(getContext(), BookHotelActivity.class));
        });


        initRecycleview();

        v_fliper = view.findViewById(R.id.v_fliper);
        int image[] = {R.drawable.hotel1,R.drawable.hotel2,R.drawable.hotel1};
//         for (int i = 0; i<image.length;i++){
//             FliperImage(image[i]);
//
//         }
        for (int images: image){
            FliperImage(images);
        }
    }

    private void initRecycleview(){
        LiveData<List<Hotel>> listLiveData=hotelViewModel.getHotelListLivedata();
        listLiveData.observe(this, new Observer<List<Hotel>>() {
            @Override
            public void onChanged(List<Hotel> hotels) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
                HighlightsHotelAdapter adapter=new HighlightsHotelAdapter(hotels, new HighlightsHotelAdapter.ItemClickListener() {
                    @Override
                    public void onClick(Hotel hotel) {
                        //item click
                        String hotelId = hotel.getHotelId();
                        Common.HOTEL_ID=hotelId;
                        Common.HOTEL_NAME=hotel.getHotelName();
                        Common.HOTEL_IMAGE=hotel.getImageUrl();
                        Intent intent = new Intent(getContext(), HotelDetailActivity.class);
                        startActivity(intent);
                    }
                }, new HighlightsHotelAdapter.ItemLongClickListener() {
                    @Override
                    public boolean onLongClick(Hotel hotel) {
                        //item long ckick
                        return false;
                    }
                }, new HighlightsHotelAdapter.FavouriteClickListener() {
                    @Override
                    public void onClick(Hotel hotel) {
                        favouriteHotelViewmodel.addFavourite(hotel,Common.USER_ID);
                    }
                });
                binding.recyclerview.setLayoutManager(layoutManager);
                binding.recyclerview.setAdapter(adapter);
            }
        });
    }

    public void FliperImage(int image){
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundResource(image);
        v_fliper.addView(imageView);
        v_fliper.setFlipInterval(4000);
        v_fliper.setAutoStart(true);
        //animation
        v_fliper.setInAnimation(getContext(), android.R.anim.slide_in_left);
        v_fliper.setOutAnimation(getContext(), android.R.anim.slide_out_right);
    }
}
