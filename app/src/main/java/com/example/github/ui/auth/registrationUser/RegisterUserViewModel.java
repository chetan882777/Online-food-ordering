package com.example.github.ui.auth.registrationUser;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.github.repository.RestaurantRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import javax.inject.Inject;

public class RegisterUserViewModel extends ViewModel {

    private static final String TAG = "RegisterUserViewModel";

    private final RestaurantRepository repository;

    @Inject
    public RegisterUserViewModel(RestaurantRepository repository){
        this.repository = repository;
    }

    public void register(String email, String password, String contact, String address){
        FirebaseDatabase database = FirebaseDatabase.getInstance();



        DatabaseReference user = database.getReference("user/" + contact);

        HashMap<String, String> map = new HashMap<>();

        map.put("email", email);
        map.put("password", password);
        map.put("contact", contact);
        map.put("address", address);

        user.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d(TAG, "onComplete: successful");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: failed = " + e.getMessage());
            }
        });
    }
}
