package com.example.happytravelapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.happytravelapp.R;
import com.example.happytravelapp.databinding.ActivityLoginBinding;
import com.example.happytravelapp.viewmodel.LoginViewModel;
import com.example.happytravelapp.viewmodel.UserViewmodel;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    ActivityLoginBinding binding;
    LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setLoginviewmodel(loginViewModel);

        loginViewModel.getUserLoginIsSuccessful().observe(this, isSuccessful -> {
            if (isSuccessful) {
                startActivity(new Intent(this, MainActivity.class));
                Toast.makeText(getApplicationContext(), "login success", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Email hoặc mật khẩu không đúng", Toast.LENGTH_LONG).show();
            }
        });

        binding.switchregister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loginViewModel.getUserLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser!=null){
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
            }
        });
    }
}
