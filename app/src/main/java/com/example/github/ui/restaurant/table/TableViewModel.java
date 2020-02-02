package com.example.github.ui.restaurant.table;

import androidx.lifecycle.ViewModel;

import com.example.github.firebase.FirebaseRequestListener;
import com.example.github.model.Tables;
import com.example.github.util.Constants;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import javax.inject.Inject;

public class TableViewModel extends ViewModel {

    @Inject
    public TableViewModel(){}

    public void saveTables(String credentials, List<Tables> tableList,
                           FirebaseRequestListener<String> listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference reference = database.getReference("restaurant/" + credentials + "/tables");

        reference.setValue(tableList).addOnCompleteListener(task -> {
            listener.OnFirebaseRequest(Constants.FIREBASE_SUCCESS);
        }).addOnFailureListener(e -> {
            listener.OnFirebaseRequest(Constants.FIREBASE_FAILED);
        });
    }
}
