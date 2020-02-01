package com.example.github.ui.auth.registrationUser;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.github.firebase.FirebaseRequestListener;
import com.example.github.model.User;
import com.example.github.repository.RestaurantRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.prefs.Preferences;

import javax.inject.Inject;

public class RegisterUserViewModel extends ViewModel {

    private static final String TAG = "RegisterUserViewModel";

    private final RestaurantRepository repository;

    @Inject
    public RegisterUserViewModel(RestaurantRepository repository){
        this.repository = repository;
    }

    public void register(String email, String password, String contact, String address, FirebaseRequestListener<String> listener){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("user/" + contact);

        User user = new User(email, password, contact, address, null, null);

        userRef.setValue(user).addOnCompleteListener(task -> {
            Log.d(TAG, "onComplete: successful");
            listener.OnFirebaseRequest("Success");
        }).addOnFailureListener(e -> {
            Log.d(TAG, "onFailure: failed = " + e.getMessage());
            listener.OnFirebaseRequest("Failed");
        });
    }
}
