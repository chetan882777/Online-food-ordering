package com.example.github.ui.restaurant.table;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.github.R;
import com.example.github.firebase.FirebaseRequestListener;
import com.example.github.model.Tables;
import com.example.github.ui.main.ViewModelProviderFactory;
import com.example.github.util.Constants;
import com.example.github.util.SharedPrefUtil;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class TableActivity extends AppCompatActivity {

    public static final String INTENT_TABLE_MSG = "INTENT_TABLE_MSG";
    public static final String TABLE_FRESH_ADD = "TABLE_FRESH_ADD";
    public static final String TABLE_EXIST_EDIT = "TABLE_EXIST_EDIT";

    private RecyclerView tableRecyclerView;
    private Button addTableButton;
    private Button saveTableButton;
    private TableAdapter tableAdapter;
    private List<Tables> tableList = new ArrayList<>();

    @Inject
    ViewModelProviderFactory providerFactory;
    TableViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        Intent intent = getIntent();
        String msg = intent.getStringExtra(INTENT_TABLE_MSG);

        viewModel = ViewModelProviders.of(this, providerFactory).get(TableViewModel.class);

        addTableButton = findViewById(R.id.button_addTable);
        saveTableButton = findViewById(R.id.button_addTable_save);
        setAdapter();
        setSaveTable();


        if(msg.equals(TABLE_FRESH_ADD)){
            showMessage("Click add menu button to add some tables");
            setAddTableClick();
        }else{

        }
    }

    private void setSaveTable() {
        saveTableButton.setOnClickListener(v -> {

            FirebaseRequestListener<String> listener = data -> {
                showMessage(data);
                if(data.equals(Constants.FIREBASE_SUCCESS)){

                }
            };

            SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(TableActivity.this);
            String credentials = sharedPrefUtil.getCredentials();
            viewModel.saveTables(credentials, tableList, listener);
        });
    }

    private void setAddTableClick() {
        addTableButton.setOnClickListener(v -> {
            final Dialog dialog = new Dialog(TableActivity.this);
            dialog.setContentView(R.layout.add_table_dialog);
            dialog.setTitle("Add Tables");

            Button add = dialog.findViewById(R.id.button_addTableDialog_add);
            Button cancel = dialog.findViewById(R.id.button_addTableDialog_cancel);

            EditText tableSize = dialog.findViewById(R.id.editText_addTableDialog_tableSize);
            EditText tableCount = dialog.findViewById(R.id.editMenu_addTableDialog_tableCount);

            add.setOnClickListener(v2 -> {
                setValueToList(tableSize, tableCount);
                dialog.dismiss();
            });

            cancel.setOnClickListener(v2 -> {
                dialog.dismiss();
            });
            dialog.show();
        });
    }

    private void setValueToList(EditText menuName, EditText menuPrice) {

        String tableSize = menuName.getText().toString();
        String tableCount = menuPrice.getText().toString();

        if(tableSize.isEmpty() || tableCount.isEmpty()){
            showMessage("Any of the field cannot be empty");
            return;
        }

        int size = tableList.size() + 1;

        Tables tables = new Tables("Table " + size, tableSize, tableCount);

        tableList.add(tables);
        tableAdapter.setTables(tableList);
        tableAdapter.notifyDataSetChanged();
    }

    private void setAdapter() {
        tableRecyclerView = findViewById(R.id.table_recyclerView);

        tableAdapter = new TableAdapter(this, tableList, v -> {
            int itemPosition = tableRecyclerView.getChildLayoutPosition(v);
        });

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        tableRecyclerView.setLayoutManager(layoutManager);
        tableRecyclerView.setAdapter(tableAdapter);
    }

    private void showMessage(String s) {
        Snackbar.make(addTableButton,
                s,
                Snackbar.LENGTH_SHORT)
                .show();
    }
}
