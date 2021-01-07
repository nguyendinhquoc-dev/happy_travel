package com.example.happytravelapp.viewmodel;

import android.content.Intent;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happytravelapp.data.FirebaseQueryLiveData;
import com.example.happytravelapp.model.User;
import com.example.happytravelapp.repository.UserRepository;
import com.example.happytravelapp.ultil.FormErrors;
import com.example.happytravelapp.ultil.Role;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.regex.Pattern;

public class RegisterViewModel extends ViewModel {
    //    private static final DatabaseReference USER_REF = FirebaseDatabase.getInstance().getReference("/users");
//    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(USER_REF);
//    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
//
//    private final MutableLiveData<Boolean> userCreatIsSuccessful = new MutableLiveData<>();
//    public MutableLiveData<Boolean> getUserCreatIsSuccessful() {
//        return userCreatIsSuccessful;
//    }
//    private final MutableLiveData<Boolean> imageUpdateIsSuccessful = new MutableLiveData<>();
//
//
//    public MutableLiveData<String> imageUrl=new MutableLiveData<>();
//    public MutableLiveData<String> email=new MutableLiveData<>();
//    public MutableLiveData<String> fullname=new MutableLiveData<>();
//    public MutableLiveData<String> phone=new MutableLiveData<>();
//    public MutableLiveData<String> password=new MutableLiveData<>();
//    public MutableLiveData<String> confpassword=new MutableLiveData<>();
//    public MutableLiveData<User.Gender> gender=new MutableLiveData<>();
//
//
//    public void register() {
//            mAuth.createUserWithEmailAndPassword(email.getValue(), password.getValue()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    String uid = FirebaseAuth.getInstance().getUid();
//                    Task uploadTask = USER_REF
//                            .child(uid)
//                            .setValue(new User(email.getValue(), phone.getValue(), fullname.getValue(), imageUrl.getValue(), gender.getValue(), Role.USER));
//                    uploadTask.addOnCompleteListener(v->{
//                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                                .setDisplayName(fullname.getValue())
//                                .setPhotoUri(Uri.parse(imageUrl.getValue()))
//                                .build();
//                        FirebaseAuth.getInstance().getCurrentUser().updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//
//                            }
//                        });
//
//                    });
//                }
//            });
//    }
    private UserRepository userRepository;
    private MutableLiveData<FirebaseUser> userLiveData;
    private MutableLiveData<Boolean> userRegisterIsSuccessful;
    private MutableLiveData<Boolean> imageUpdateIsSuccessful;
    public MutableLiveData<String> imageUrl = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> fullname = new MutableLiveData<>();
    public MutableLiveData<String> phone = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<String> confpassword = new MutableLiveData<>();
    public MutableLiveData<User.Gender> gender = new MutableLiveData<>();

    public RegisterViewModel() {
        super();
        userRepository = new UserRepository();
        userLiveData = userRepository.getUserLiveData();
        this.userRegisterIsSuccessful = userRepository.getUserRegisterIsSuccessful();
        this.imageUpdateIsSuccessful=userRepository.getImageUpdateIsSuccessful();
        this.imageUrl=userRepository.getImageUrl();
    }

    public void register() {
        userRepository.register(email.getValue(), password.getValue());
    }

    public void uploadImage(Intent intentData){
        userRepository.uploadImage(intentData);
    }

    public void saveToDatabase() {
        userRepository.saveToDatabase(email.getValue(), phone.getValue(), fullname.getValue(), imageUrl.getValue(), gender.getValue());
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<Boolean> getUserRegisterIsSuccessful() {
        return userRegisterIsSuccessful;
    }


//    public String uploadImage(Intent intentData) {
//        Uri selectedUri = intentData.getData();
//        StorageReference photoRef = FirebaseStorage.getInstance()
//                .getReference().child("users")
//                .child(selectedUri.getLastPathSegment());
//
//        UploadTask uploadTask = photoRef.putFile(selectedUri);
//        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//            @Override
//            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                if (!task.isSuccessful()) {
//                    throw task.getException();
//                }
//
//                // Continue with the task to get the download URL
//                return photoRef.getDownloadUrl();
//            }
//        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//            @Override
//            public void onComplete(@NonNull Task<Uri> task) {
//                if (task.isSuccessful()) {
//                    Uri downloadUri = task.getResult();
//                    imageUrl.setValue(String.valueOf(downloadUri));
//                    imageUpdateIsSuccessful.setValue(true);
//                } else {
//                    imageUpdateIsSuccessful.setValue(false);
//                }
//            }
//        });
//        return imageUrl.getValue();
//    }

//    public ObservableArrayList<FormErrors> formErrors=new ObservableArrayList<FormErrors>();
//    public Boolean isFormValid(){
//        formErrors.clear();
//        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
//        if (email.getValue()==null||!email.getValue().isEmpty()||!Pattern.compile(EMAIL_PATTERN).matcher(email.getValue()).matches()) {
//            formErrors.add(FormErrors.INVAID_EMAIL);
//        }
//        if (fullname.getValue()==null||!fullname.getValue().isEmpty()||fullname.getValue().length()<3) {
//            formErrors.add(FormErrors.INVALID_FULLNAME);
//        }
//        if (phone.getValue()==null||!phone.getValue().isEmpty()||!Pattern.matches("[a-zA-Z]+", phone.getValue())||phone.getValue().length()<9||phone.getValue().length()>11) {
//            formErrors.add(FormErrors.INVALID_PHONE);
//        }
//        if (password.getValue()==null||!password.getValue().isEmpty()||password.getValue().length()<6) {
//            formErrors.add(FormErrors.INVALID_PASSWORD);
//        }
//        if (confpassword.getValue()==null||!confpassword.getValue().isEmpty()||confpassword.getValue().equalsIgnoreCase(password.getValue())) {
//            formErrors.add(FormErrors.PASSWORD_NOTMATCHING);
//        }
//        return formErrors.isEmpty();
//    }
//
//    public TextWatcher textWatcher(){
//        return new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        };
//    }
}
