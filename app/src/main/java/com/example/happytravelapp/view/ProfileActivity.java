package com.example.happytravelapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.happytravelapp.R;
import com.example.happytravelapp.databinding.ActivityProfileBinding;
import com.example.happytravelapp.model.User;
import com.example.happytravelapp.viewmodel.UserViewmodel;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding binding;
    UserViewmodel userViewmodel;
    private static final int RC_PHOTO_PICKER = 1;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        userViewmodel = ViewModelProviders.of(this).get(UserViewmodel.class);
        binding.setLifecycleOwner(this);
        binding.setUserviewmodel(userViewmodel);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(R.string.account_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userViewmodel.getUserDetail();

        userViewmodel.getUserUpdateIsSuccessful().observe(this, isSuccessful -> {
            if (isSuccessful) {
                Toast.makeText(getApplicationContext(), "Update done", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "login fail", Toast.LENGTH_LONG).show();
            }
        });

        binding.profileImage.setOnClickListener(v->{
            chooseImage();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
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
                Picasso.get().load(imgUri).placeholder(R.drawable.ic_image_black_24dp).into(binding.profileImage);
                userViewmodel.uploadImage(data);
                break;
            default:
        }

    }

}
