package com.example.happytravelapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.happytravelapp.R;
import com.example.happytravelapp.databinding.ActivityPaymentBinding;

public class PaymentActivity extends AppCompatActivity {
    ActivityPaymentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_payment);

        binding.cash.setOnClickListener(v->{
           startActivity(new Intent(getApplicationContext(),PaymentPaypal.class));
        });


    }
}
