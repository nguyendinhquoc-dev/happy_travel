package com.example.happytravelapp.viewmodel;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.happytravelapp.data.FirebaseQueryLiveData;
import com.example.happytravelapp.model.RoomType;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RoomTypeViewmodel extends ViewModel {
    private static final DatabaseReference ROOMTYPE_REF = FirebaseDatabase.getInstance().getReference("/roomtypes");
    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(ROOMTYPE_REF);

    private List<RoomType> mList = new ArrayList<>();
    private final LiveData<List<RoomType>> roomtypeListLivedata = Transformations.map(liveData, new DeserializerList());
    public LiveData<List<RoomType>> getRoomtypeListLivedata() {
        return roomtypeListLivedata;
    }
    private class DeserializerList implements Function<DataSnapshot, List<RoomType>> {
        @Override
        public List<RoomType> apply(DataSnapshot input) {
            mList.clear();
            for (DataSnapshot snap : input.getChildren()) {
                RoomType roomType = snap.getValue(RoomType.class);
                mList.add(roomType);
            }
            return mList;
        }
    }

    MutableLiveData<String> roomtypename=new MutableLiveData<>();
    public String getRoomName(String roomTypeId){
        ROOMTYPE_REF.child(roomTypeId).child("typeName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                roomtypename.setValue(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return roomtypename.getValue();
    }
}
