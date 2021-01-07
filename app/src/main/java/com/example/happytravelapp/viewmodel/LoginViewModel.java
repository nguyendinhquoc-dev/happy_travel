package com.example.happytravelapp.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happytravelapp.data.FirebaseQueryLiveData;
import com.example.happytravelapp.model.User;
import com.example.happytravelapp.repository.UserRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.regex.Pattern;

public class LoginViewModel extends ViewModel {
//    private static final DatabaseReference USER_REF = FirebaseDatabase.getInstance().getReference("/users");
//    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(USER_REF);
//    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
//    FirebaseUser user = mAuth.getCurrentUser();
//
//    private final MutableLiveData<FirebaseUser> userLiveData = new MutableLiveData<>();
//    public MutableLiveData<FirebaseUser> getUserLiveData() {
//        if (mAuth.getCurrentUser() != null) {
//            userLiveData.postValue(mAuth.getCurrentUser());
//        }
//        return userLiveData;
//    }
//
//    private final MutableLiveData<Boolean> userLoginIsSuccessful = new MutableLiveData<>();
//    public MutableLiveData<Boolean> getUserLoginIsSuccessful() {
//        return userLoginIsSuccessful;
//    }
//
//    public MutableLiveData<String> email=new MutableLiveData<>();
//    public MutableLiveData<String> password=new MutableLiveData<>();
//
//    public void login() {
//        mAuth.signInWithEmailAndPassword(email.getValue(), password.getValue()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    userLiveData.postValue(mAuth.getCurrentUser());
//                    userLoginIsSuccessful.setValue(true);
//                }
//            }
//        });
//    }

    private UserRepository userRepository;
    private MutableLiveData<FirebaseUser> userLiveData;
    private MutableLiveData<Boolean> userLoginIsSuccessful;
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<String> emailError = new MutableLiveData<>();
    public MutableLiveData<String> passwordError = new MutableLiveData<>();

    public LoginViewModel() {
        super();
        userRepository = new UserRepository();
        userLiveData = userRepository.getUserLiveData();
        this.userLoginIsSuccessful = userRepository.getUserLoginIsSuccessful();
    }

    public void login() {
        if(validator()==true) {
            userRepository.login(email.getValue(), password.getValue());
        }
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<Boolean> getUserLoginIsSuccessful() {
        return userLoginIsSuccessful;
    }

    public boolean validator(){
        Boolean flag=true;
        emailError.setValue(null);
        passwordError.setValue(null);
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        if (email.getValue() == null || email.getValue().isEmpty()) {
            emailError.setValue("Enter email address.");
            flag=false;
        }
        if (email.getValue() != null&& (!Pattern.compile(expression, Pattern.CASE_INSENSITIVE).matcher(email.getValue()).matches())) {
            emailError.setValue("Enter a valid email address.");
            flag=false;
        }

        if (password.getValue() == null || password.getValue().isEmpty()) {
            passwordError.setValue("Enter password.");
            flag=false;
        }

        if (password.getValue() != null&&password.getValue().length() < 5) {
            passwordError.setValue("Password Length should be greater than 5.");
            flag=false;
        }
        return flag;
    }
}
