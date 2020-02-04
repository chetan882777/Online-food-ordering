package com.example.github.ui.user.home;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.github.model.Restaurant;
import com.example.github.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class UserHomeViewModel extends ViewModel {

    private static final String TAG = "UserHomeViewModel";
    MutableLiveData<User> mutableLiveDataUser;
    MutableLiveData<List<Restaurant>> mutableLiveDataRestaurants;
    private FirebaseDatabase database;

    @Inject
    public UserHomeViewModel(){
        mutableLiveDataUser = new MutableLiveData<>();
        mutableLiveDataRestaurants = new MutableLiveData<>();
        database = FirebaseDatabase.getInstance();
    }

    public void getUserInfo(String userId) {
        DatabaseReference reference = database.getReference("user/" + userId);

        Log.d(TAG, "getUserInfo: getting Info ...");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setUserValue(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: failed");
            }
        });
    }

    private void setUserValue(@NonNull DataSnapshot dataSnapshot) {
        Log.d(TAG, "onDataChange: info fetched");
        if(dataSnapshot.exists()){
            Log.d(TAG, "onDataChange: data exist .. " + dataSnapshot.getValue());
            if(dataSnapshot.hasChildren()){
                Log.d(TAG, "onDataChange: children exist");
                User user = dataSnapshot.getValue(User.class);
                if(user != null){
                    Log.d(TAG, "onDataChange: user not null.. " + user);
                    mutableLiveDataUser.setValue(user);
                }
            }
        }
    }

    public void getRestaurants(String city) {
        DatabaseReference reference = database.getReference("restaurant");

        Log.d(TAG, "getRestaurants: getting info ...");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: info fetched ...");
                setRestaurantsValue(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: failed");
            }
        });

    }

    private void setRestaurantsValue(DataSnapshot dataSnapshot) {
        Log.d(TAG, "onDataChange: info fetched");
        if(dataSnapshot.exists()){
            Log.d(TAG, "onDataChange: data exist .. " + dataSnapshot.getValue());
            if(dataSnapshot.hasChildren()){
                Log.d(TAG, "onDataChange: children exist");

                List<Restaurant> restaurantList = new ArrayList<>();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Restaurant restaurant = data.getValue(Restaurant.class);
                    restaurantList.add(restaurant);
                }
                mutableLiveDataRestaurants.setValue(restaurantList);
            }
        }
    }

    public LiveData<User> getUser(){
        return mutableLiveDataUser;
    }

    public LiveData<List<Restaurant>> getRestaurants(){
        return mutableLiveDataRestaurants;
    }
}
