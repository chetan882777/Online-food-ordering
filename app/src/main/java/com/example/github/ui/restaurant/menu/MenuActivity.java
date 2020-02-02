package com.example.github.ui.restaurant.menu;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.github.R;
import com.example.github.model.Menu;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MenuActivity extends DaggerAppCompatActivity {


    public static final String INTENT_MENU_MSG = "INTENT_MENU_MSG";
    public static final String MENU_FRESH_ADD = "MENU_FRESH_ADD";
    public static final String MENU_EXIST_EDIT = "MENU_EXIST_EDIT";

    private RecyclerView menuRecyclerView;
    private Button addMenuButton;
    private MenuAdapter menuAdapter;
    private List<Menu> menuList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        String msg = intent.getStringExtra(INTENT_MENU_MSG);

        addMenuButton = findViewById(R.id.button_addMenu);
        setAdapter();


        if(msg.equals(MENU_FRESH_ADD)){
            Snackbar.make(addMenuButton, "Click add menu button to add some menu",
                    Snackbar.LENGTH_LONG).show();
                addMenuButton.setOnClickListener(v -> {

                });
        }else{

        }
    }

    private void setAdapter() {
        menuRecyclerView = findViewById(R.id.menu_recyclerView);

        menuAdapter = new MenuAdapter(this, menuList, v -> {
            int itemPosition = menuRecyclerView.getChildLayoutPosition(v);
        });

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        menuRecyclerView.setLayoutManager(layoutManager);
        menuRecyclerView.setAdapter(menuAdapter);
    }
}