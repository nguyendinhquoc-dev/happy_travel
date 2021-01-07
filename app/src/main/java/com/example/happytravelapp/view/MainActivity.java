package com.example.happytravelapp.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.happytravelapp.R;
import com.example.happytravelapp.databinding.ActivityMainBinding;
import com.example.happytravelapp.ultil.BottomNavigationBehavior;
import com.example.happytravelapp.ultil.Common;
import com.example.happytravelapp.view.fragment.AccountFragment;
import com.example.happytravelapp.view.fragment.FavouriteFragment;
import com.example.happytravelapp.view.fragment.FeedFragment;
import com.example.happytravelapp.view.fragment.HomeFragment;
import com.example.happytravelapp.view.fragment.InboxFragment;
import com.example.happytravelapp.viewmodel.UserViewmodel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    UserViewmodel userViewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        userViewmodel= ViewModelProviders.of(this).get(UserViewmodel.class);
        //toolbar
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(R.string.toolbar_main);

        loadFragment(new HomeFragment());

        binding.navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.navigation_main:
                        getSupportActionBar().setTitle("Đặt phòng");
                        fragment = new HomeFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_feed:
                        getSupportActionBar().setTitle("Feed");
                        fragment = new FeedFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_inbox:
                        getSupportActionBar().setTitle("Inbox");
                        fragment = new InboxFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_favourite:
                        getSupportActionBar().setTitle("Favourite");
                        fragment = new FavouriteFragment();
                        loadFragment(fragment);
                        return true;
                    case R.id.navigation_account:
                        getSupportActionBar().setTitle("Account");
                        fragment = new AccountFragment();
                        loadFragment(fragment);
                        return true;
                }
                return false;
            }
        });

        // attaching bottom sheet behaviour - hide / show on scroll
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) binding.navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }


    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        userViewmodel.getUserLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser.getPhotoUrl()!=null&&firebaseUser.getDisplayName()!=null) {
                    Common.USER_ID = firebaseUser.getUid();
                    Common.IMAGE_URL = firebaseUser.getPhotoUrl().toString();
                    Common.FULLNAME = firebaseUser.getDisplayName();
                }
            }
        });
    }
}
