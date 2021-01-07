package com.example.happytravelapp.view.fragment;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.happytravelapp.R;
import com.example.happytravelapp.databinding.FragmentAccountBinding;
import com.example.happytravelapp.view.BookingManagerActivity;
import com.example.happytravelapp.view.LoginActivity;
import com.example.happytravelapp.view.MainActivity;
import com.example.happytravelapp.view.ProfileActivity;
import com.example.happytravelapp.viewmodel.UserViewmodel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {
    FragmentAccountBinding binding;
    UserViewmodel userViewmodel;


    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userViewmodel= ViewModelProviders.of(this).get(UserViewmodel.class);


        userViewmodel.getUserLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    ((MainActivity)getContext()).getSupportActionBar().setTitle("Xin chào "+firebaseUser.getDisplayName());
                } else {

                }
            }
        });

        binding.profile.setOnClickListener(v->{
            startActivity(new Intent(getContext(), ProfileActivity.class));
        });

        binding.bookingManager.setOnClickListener(v->{
            startActivity(new Intent(getContext(), BookingManagerActivity.class));
        });

        binding.addhotel.setOnClickListener(v->{
            // [START subscribe_topics]
            FirebaseMessaging.getInstance().subscribeToTopic("weather")
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            String msg = getString(R.string.senderId);
                            if (!task.isSuccessful()) {
                                msg = getString(R.string.booking_id);
                            }
                            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        }
                    });
            // [END subscribe_topics]
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.account_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            userViewmodel.logout();
            startActivity(new Intent(getContext(), LoginActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
