package com.example.github.ui.restaurant.menu;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.github.R;
import com.example.github.firebase.FirebaseRequestListener;
import com.example.github.model.Menu;
import com.example.github.ui.main.ViewModelProviderFactory;
import com.example.github.ui.restaurant.table.TableActivity;
import com.example.github.util.Constants;
import com.example.github.util.SharedPrefUtil;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

import static android.view.View.GONE;

public class MenuActivity extends DaggerAppCompatActivity {


    public static final String INTENT_MENU_MSG = "INTENT_MENU_MSG";
    public static final String MENU_FRESH_ADD = "MENU_FRESH_ADD";
    public static final String MENU_EXIST_EDIT = "MENU_EXIST_EDIT";

    public static final String MENU_TYPE_VEG = "Veg";
    public static final String MENU_TYPE_NON_VEG = "Non Veg";

    private RecyclerView menuRecyclerView;
    private Button addMenuButton;
    private Button saveMenuButton;
    private MenuAdapter menuAdapter;
    private List<Menu> menuList = new ArrayList<>();
    private ProgressBar progressBar;

    @Inject
    ViewModelProviderFactory providerFactory;
    MenuViewModel viewModel;
    private String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setTitle("Add menus");

        Intent intent = getIntent();
        msg = intent.getStringExtra(INTENT_MENU_MSG);

        viewModel = ViewModelProviders.of(this, providerFactory).get(MenuViewModel.class);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(GONE);

        addMenuButton = findViewById(R.id.button_addTable);
        saveMenuButton = findViewById(R.id.button_addTable_save);
        setAdapter();
        setSaveMenu();


        if(msg.equals(MENU_FRESH_ADD)){
            showMessage("Click add menu button to add some menu");
            setAddMenuClick();
        }else{

        }
    }

    private void setSaveMenu() {
        saveMenuButton.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);

            FirebaseRequestListener<String> listener = data -> {
                progressBar.setVisibility(GONE);
                showMessage(data);
                if(data.equals(Constants.FIREBASE_SUCCESS)){
                    if(msg.equals(MENU_FRESH_ADD)) {
                        Intent intent = new Intent(MenuActivity.this, TableActivity.class);
                        intent.putExtra(TableActivity.INTENT_TABLE_MSG, TableActivity.TABLE_FRESH_ADD);
                        startActivity(intent);
                    }
                }
            };

            SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(MenuActivity.this);
            String credentials = sharedPrefUtil.getCredentials();

            viewModel.saveMenus(credentials, menuList, listener);
        });
    }

    private void setAddMenuClick() {
        addMenuButton.setOnClickListener(v -> {
            final Dialog dialog = new Dialog(MenuActivity.this);
            dialog.setContentView(R.layout.add_menu_dialog);
            dialog.setTitle("Add Menu");
            RadioGroup radioGroup = dialog.findViewById(R.id.radioGroup_menuType);

            Button add = dialog.findViewById(R.id.button_addTableDialog_add);
            Button cancel = dialog.findViewById(R.id.button_addTableDialog_cancel);

            EditText menuName = dialog.findViewById(R.id.editText_addTableDialog_tableSize);
            EditText menuPrice = dialog.findViewById(R.id.editMenu_addTableDialog_tableCount);

            add.setOnClickListener(v2 -> {
                setValueToList(radioGroup, menuName, menuPrice);
                dialog.dismiss();
            });

            cancel.setOnClickListener(v2 -> {
                dialog.dismiss();
            });
            dialog.show();
        });
    }

    private void setValueToList(RadioGroup radioGroup, EditText menuName, EditText menuPrice) {
        String menuType = MENU_TYPE_VEG;
        int radioSelected = radioGroup.getCheckedRadioButtonId();
        if(radioSelected == R.id.radioVeg){
            menuType = MENU_TYPE_VEG;
        }else if(radioSelected == R.id.radioNonVeg){
            menuType = MENU_TYPE_NON_VEG;
        }
        String name = menuName.getText().toString();
        String price = menuPrice.getText().toString();

        if(name.isEmpty() || price.isEmpty()){
            showMessage("Any of the field cannot be empty");
            return;
        }

        Menu menu = new Menu(name, price, menuType);

        menuList.add(menu);
        menuAdapter.setMenus(menuList);
        menuAdapter.notifyDataSetChanged();
    }

    private void setAdapter() {
        menuRecyclerView = findViewById(R.id.table_recyclerView);

        menuAdapter = new MenuAdapter(this, menuList, v -> {
            int itemPosition = menuRecyclerView.getChildLayoutPosition(v);
        });

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        menuRecyclerView.setLayoutManager(layoutManager);
        menuRecyclerView.setAdapter(menuAdapter);
    }

    private void showMessage(String s) {
        Snackbar.make(addMenuButton,
                s,
                Snackbar.LENGTH_SHORT)
                .show();
    }
}
