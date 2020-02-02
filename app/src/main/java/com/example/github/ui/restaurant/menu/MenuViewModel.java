package com.example.github.ui.restaurant.menu;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.github.firebase.FirebaseRequestListener;
import com.example.github.model.Menu;
import com.example.github.util.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import javax.inject.Inject;

public class MenuViewModel extends ViewModel {

    @Inject
    public MenuViewModel() {
    }

    public void saveMenus(String restaurant, List<Menu> menus, FirebaseRequestListener<String> listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference reference = database.getReference("restaurant/" + restaurant + "/menus");

        reference.setValue(menus).addOnCompleteListener(task -> {
            listener.OnFirebaseRequest(Constants.FIREBASE_SUCCESS);
        }).addOnFailureListener(e -> {
            listener.OnFirebaseRequest(Constants.FIREBASE_FAILED);
        });
    }
}
