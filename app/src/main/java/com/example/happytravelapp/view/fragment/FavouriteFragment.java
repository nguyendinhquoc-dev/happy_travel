package com.example.happytravelapp.view.fragment;


import android.content.Intent;
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
import android.widget.Toast;

import com.example.happytravelapp.R;
import com.example.happytravelapp.adapter.FavouriteHotelAdapter;
import com.example.happytravelapp.adapter.HotelAdapter;
import com.example.happytravelapp.databinding.FragmentFavouriteBinding;
import com.example.happytravelapp.databinding.FragmentHomeBinding;
import com.example.happytravelapp.model.FavouriteHotel;
import com.example.happytravelapp.model.Hotel;
import com.example.happytravelapp.view.BookHotelActivity;
import com.example.happytravelapp.view.HotelDetailActivity;
import com.example.happytravelapp.viewmodel.FavouriteHotelViewmodel;
import com.example.happytravelapp.viewmodel.UserViewmodel;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment {
    FragmentFavouriteBinding binding;
    FavouriteHotelViewmodel favouriteHotelViewmodel;
    UserViewmodel userViewmodel;
    String hotelId;
    String userId;


    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFavouriteBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favouriteHotelViewmodel = ViewModelProviders.of(this).get(FavouriteHotelViewmodel.class);
        userViewmodel=ViewModelProviders.of(this).get(UserViewmodel.class);

        initRecycleview();

        favouriteHotelViewmodel.getRemoveFavouriteIsSuccessful().observe(this,isSuccessful->{
            if (isSuccessful) {
                Toast.makeText(getContext(), "Xóa yêu thích thành công", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Xóa thất bại", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initRecycleview() {
        LiveData<List<FavouriteHotel>> listLiveData = favouriteHotelViewmodel.getFavouriteHotelListLivedata();
        listLiveData.observe(this, new Observer<List<FavouriteHotel>>() {
            @Override
            public void onChanged(List<FavouriteHotel> favouriteHotels) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                FavouriteHotelAdapter adapter=new FavouriteHotelAdapter(favouriteHotels, new FavouriteHotelAdapter.ItemClickListener() {
                    @Override
                    public void onClick(FavouriteHotel favouriteHotel) {

                    }
                }, new FavouriteHotelAdapter.ItemLongClickListener() {
                    @Override
                    public boolean onLongClick(FavouriteHotel favouriteHotel) {
                        return false;
                    }
                }, new FavouriteHotelAdapter.RemoveClickListener() {
                    @Override
                    public void onClick(FavouriteHotel favouriteHotel) {
                        hotelId=favouriteHotel.getHotel().getHotelId();
                        favouriteHotelViewmodel.removeFavourite(userId,hotelId);
                    }
                });
                binding.recyclerview.setLayoutManager(layoutManager);
                binding.recyclerview.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        userViewmodel.getUserLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                userId=firebaseUser.getUid();
            }
        });
    }
}
