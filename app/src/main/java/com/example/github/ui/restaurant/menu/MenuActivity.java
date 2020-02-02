package com.example.github.ui.restaurant.menu;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import com.example.github.R;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MenuActivity extends DaggerAppCompatActivity {


    public static final String INTENT_MENU_MSG = "INTENT_MENU_MSG";
    public static final String MENU_FRESH_ADD = "MENU_FRESH_ADD";
    public static final String MENU_EXIST_EDIT = "MENU_EXIST_EDIT";

    private RecyclerView menuRecyclerView;
    private Button addMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        String msg = intent.getStringExtra(INTENT_MENU_MSG);

        addMenuButton = findViewById(R.id.button_addMenu);
        menuRecyclerView = findViewById(R.id.menu_recyclerView);


        if(msg.equals(MENU_FRESH_ADD)){
            Snackbar.make(addMenuButton, "Click add menu button to add some menu",
                    Snackbar.LENGTH_LONG).show();
        }else{

        }
    }
}
