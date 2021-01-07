package com.example.happytravelapp.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.example.happytravelapp.R;
import com.example.happytravelapp.adapter.MessageAdapter;
import com.example.happytravelapp.databinding.ActivityMessageBinding;
import com.example.happytravelapp.model.Hotel;
import com.example.happytravelapp.model.Inbox;
import com.example.happytravelapp.model.Message;
import com.example.happytravelapp.ultil.Common;
import com.example.happytravelapp.view.fragment.InboxFragment;
import com.example.happytravelapp.viewmodel.HotelViewModel;
import com.example.happytravelapp.viewmodel.InboxViewModel;
import com.example.happytravelapp.viewmodel.MessageViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {
    ActivityMessageBinding binding;
    InboxViewModel inboxViewModel;
    MessageViewModel messageViewModel;
    HotelViewModel hotelViewModel;
    private static final int RC_PHOTO_PICKER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_message);
        inboxViewModel= ViewModelProviders.of(this).get(InboxViewModel.class);
        hotelViewModel=ViewModelProviders.of(this).get(HotelViewModel.class);
        messageViewModel=ViewModelProviders.of(this).get(MessageViewModel.class);
        binding.setMessageVM(messageViewModel);
        binding.setLifecycleOwner(this);

        binding.messageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.toString().trim().length() > 0) {
                    binding.sendButton.setEnabled(true);
                } else {
                    binding.sendButton.setEnabled(false);
                }
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.photoPickerButton.setOnClickListener(v->{
            chooseImage();
        });
        messageViewModel.getImageUploadIsSuccessful().observe(this,isSuccessful->{
            if (isSuccessful) {
                binding.sendButton.setOnClickListener(v->{
                    messageViewModel.addMessage();
                });

            } else {
                Toast.makeText(getApplicationContext(), "Could not fetch the picture!", Toast.LENGTH_LONG).show();
            }
        });

        initRecycleview();
    }

    public void initRecycleview(){
        messageViewModel.getListInboxLivedata().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> inboxes) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(MessageActivity.this);
                MessageAdapter adapter=new MessageAdapter(inboxes);
                binding.recyclerview.setLayoutManager(layoutManager);
                binding.recyclerview.setAdapter(adapter);
            }
        });
    }

    public void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "complete action using"), RC_PHOTO_PICKER);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplicationContext(), "Action canceled", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (requestCode) {
            case RC_PHOTO_PICKER:
                Uri imgUri = data.getData();
                Picasso.get().load(imgUri).placeholder(R.drawable.ic_image_black_24dp).into(binding.image);
                messageViewModel.uploadImage(data);
                break;
            default:
        }
    }



}