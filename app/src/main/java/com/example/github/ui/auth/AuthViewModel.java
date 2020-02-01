package com.example.github.ui.auth;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.github.firebase.FirebaseRequestListener;
import com.example.github.model.User;
import com.example.github.repository.RestaurantRepository;
import com.example.github.util.Constants;
import com.example.github.util.SharedPrefUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javax.inject.Inject;

public class AuthViewModel extends ViewModel {

    private final RestaurantRepository repository;

    @Inject
    public AuthViewModel(RestaurantRepository repository){
        this.repository = repository;
    }

    public void login(String type, String contact, String password, FirebaseRequestListener<String> listener){
        if(type.equals(AuthActivity.INTENT_MESSAGE_AUTH_TYPE_USER)){
            FirebaseDatabase database = FirebaseDatabase.getInstance();

            DatabaseReference userRef = database.getReference("user");

            userRef.child(contact).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        User user = dataSnapshot.getValue(User.class);

                        if(user.getPassword().equals(password)){

                            SharedPrefUtil.saveCredentials(contact);
                            SharedPrefUtil.saveType(AuthActivity.INTENT_MESSAGE_AUTH_TYPE_USER);
                            listener.OnFirebaseRequest(Constants.FIREBASE_SUCCESS);

                        }else{
                            listener.OnFirebaseRequest("Invalid Password");
                        }
                    }else{
                        listener.OnFirebaseRequest("No User found");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    listener.OnFirebaseRequest(Constants.FIREBASE_FAILED);
                }
            });
        }
    }

}
