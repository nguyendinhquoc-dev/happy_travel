package com.example.happytravelapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.happytravelapp.R;
import com.example.happytravelapp.databinding.ActivityRegisterBinding;
import com.example.happytravelapp.model.User;
import com.example.happytravelapp.viewmodel.RegisterViewModel;
import com.example.happytravelapp.viewmodel.UserViewmodel;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.squareup.picasso.Picasso;

import static com.example.happytravelapp.BR.registerViewmodel;


public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    RegisterViewModel registerViewModel;
    private static final int RC_PHOTO_PICKER = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setRegisterViewmodel(registerViewModel);

        registerViewModel.getUserRegisterIsSuccessful().observe(this, isSuccessful -> {
            if (isSuccessful) {
                registerViewModel.saveToDatabase();
                startActivity(new Intent(this, MainActivity.class));
                Toast.makeText(getApplicationContext(), "Đăng ký thành công", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Đăng ký thất bại", Toast.LENGTH_LONG).show();
            }
        });

        binding.avatar.setOnClickListener(v -> {
            chooseImage();
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
                Picasso.get().load(imgUri).placeholder(R.drawable.ic_image_black_24dp).into(binding.avatar);
                registerViewModel.uploadImage(data);
                break;

            default:
        }

    }
}
