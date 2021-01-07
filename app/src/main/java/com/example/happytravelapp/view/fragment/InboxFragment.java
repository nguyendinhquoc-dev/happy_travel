package com.example.happytravelapp.view.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.happytravelapp.R;
import com.example.happytravelapp.adapter.InboxAdapter;
import com.example.happytravelapp.databinding.FragmentHomeBinding;
import com.example.happytravelapp.databinding.FragmentInboxBinding;
import com.example.happytravelapp.model.Inbox;
import com.example.happytravelapp.ultil.Common;
import com.example.happytravelapp.view.MessageActivity;
import com.example.happytravelapp.viewmodel.HotelViewModel;
import com.example.happytravelapp.viewmodel.InboxViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class InboxFragment extends Fragment {
    FragmentInboxBinding binding;
    InboxViewModel inboxViewModel;
    HotelViewModel hotelViewModel;


    public InboxFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInboxBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inboxViewModel = ViewModelProviders.of(this).get(InboxViewModel.class);
        hotelViewModel = ViewModelProviders.of(this).get(HotelViewModel.class);
        binding.setLifecycleOwner(this);

        initRecycleview();
    }

    public void initRecycleview() {
        inboxViewModel.getListInboxLivedata().observe(this, new Observer<List<Inbox>>() {
            @Override
            public void onChanged(List<Inbox> inboxes) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                InboxAdapter adapter = new InboxAdapter(inboxes, new InboxAdapter.ItemClickListener() {
                    @Override
                    public void onClick(Inbox inbox) {
                        String receiverId=inbox.getReceiverId();
                        Common.HOTEL_ID=receiverId;
                        Common.INBOX_ID=inbox.getInboxId();
                        Common.HOTEL_NAME=inbox.getReceiverName();
                        Intent intent=new Intent(getContext(), MessageActivity.class);
                        startActivity(intent);
                    }
                });
                binding.recyclerview.setLayoutManager(layoutManager);
                binding.recyclerview.setAdapter(adapter);
            }
        });
    }

}
