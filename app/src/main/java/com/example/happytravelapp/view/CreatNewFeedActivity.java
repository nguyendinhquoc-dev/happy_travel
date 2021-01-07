package com.example.happytravelapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.example.happytravelapp.R;
import com.example.happytravelapp.databinding.ActivityCreatNewFeedBinding;
import com.example.happytravelapp.databinding.FragmentFeedBinding;
import com.example.happytravelapp.viewmodel.PostViewModel;
import com.squareup.picasso.Picasso;

public class CreatNewFeedActivity extends AppCompatActivity {
    ActivityCreatNewFeedBinding binding;
    PostViewModel postViewModel;
    private static final int RC_PHOTO_PICKER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_creat_new_feed);
        postViewModel= ViewModelProviders.of(this).get(PostViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setPostVM(postViewModel);

        binding.photoPickerButton.setOnClickListener(v->{
            chooseImage();
        });

        binding.title.addTextChangedListener(new TextWatcher() {
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

        postViewModel.getPostCreatIsSuccessfull().observe(this,isSuccessful -> {
            if (isSuccessful) {
                Toast.makeText(getApplicationContext(), "Đăng thành công", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Đăng thất bại", Toast.LENGTH_LONG).show();
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
                Picasso.get().load(imgUri).placeholder(R.drawable.ic_image_black_24dp).into(binding.imageUrl);
                postViewModel.uploadImage(data);
                break;

            default:
        }

    }
}
