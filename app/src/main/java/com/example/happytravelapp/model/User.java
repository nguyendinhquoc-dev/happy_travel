package com.example.happytravelapp.model;

import android.util.Patterns;
import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.example.happytravelapp.R;
import com.example.happytravelapp.ultil.Role;
import com.squareup.picasso.Picasso;
import androidx.databinding.library.baseAdapters.BR;

public class User extends BaseObservable {
    private String email;
    private String phone;
    private String fullname;
    private String avatarUrl;
    private Gender gender;
    private String password;
    private Role role;

    public User(String email, String phone, String fullname, String avatarUrl, Gender gender,Role role) {
        this.email = email;
        this.phone = phone;
        this.fullname = fullname;
        this.avatarUrl = avatarUrl;
        this.gender = gender;
        this.role=role;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User() {
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
    }

    @Bindable
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
        notifyPropertyChanged(BR.fullname);
    }

    @Bindable
    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Bindable
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
        notifyPropertyChanged(BR.gender);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Bindable
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public enum Gender {
        MALE, FEMALE
    }
}
