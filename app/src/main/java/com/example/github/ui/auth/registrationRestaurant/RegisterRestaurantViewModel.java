package com.example.github.ui.auth.registrationRestaurant;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.github.firebase.FirebaseRequestListener;
import com.example.github.model.Restaurant;
import com.example.github.util.Constants;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Inject;

public class RegisterRestaurantViewModel extends ViewModel {

    private static final String TAG = "RegisterRestaurantViewM";

    @Inject
    public RegisterRestaurantViewModel(){}

    public void register(Restaurant restaurant, FirebaseRequestListener<String> listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference reference = database.getReference("restaurant/" + restaurant.getContact());

        reference.setValue(restaurant).addOnCompleteListener(task -> {
            Log.d(TAG, "register: register Success");
            listener.OnFirebaseRequest(Constants.FIREBASE_SUCCESS);

        }).addOnFailureListener(e ->
                listener.OnFirebaseRequest(Constants.FIREBASE_FAILED));
    }
}
