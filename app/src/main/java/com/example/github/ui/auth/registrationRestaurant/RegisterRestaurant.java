package com.example.github.ui.auth.registrationRestaurant;


import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;

import com.example.github.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dagger.android.support.DaggerAppCompatActivity;

public class RegisterRestaurant extends DaggerAppCompatActivity {

    public static final String RESTAURANT_TYPE_VEG = "Veg only";
    public static final String RESTAURANT_TYPE_NON_VEG = "Non-Veg only";
    public static final String RESTAURANT_TYPE_VEG_AND_NON_VEG = "Veg and Non-Veg";
    private String resType = RESTAURANT_TYPE_VEG;

    private Spinner spinner;
    private TextView textViewOffDays;
    private Button buttonAddOffDays;

    private TextView textViewOpeningTime;
    private Button buttonSetOpeningTime;
    private TextView textViewClosingTime;
    private Button buttonSetClosingTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_restaurant);

        setOffDays();
        setSpinner();
        setRestaurantTimings();


    }

    private void setRestaurantTimings() {

        textViewOpeningTime = findViewById(R.id.textView_regRes_activeTimeStart);
        textViewClosingTime = findViewById(R.id.textView_regRes_activeTimeEnd);
        buttonSetOpeningTime = findViewById(R.id.button_regRes_activeTimesStart);
        buttonSetClosingTime = findViewById(R.id.button_regRes_activeTimesEnd);

        buttonSetOpeningTime.setOnClickListener(v -> {
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(
                    RegisterRestaurant.this,
                    (timePicker, selectedHour, selectedMinute) ->
                            textViewOpeningTime.setText
                                    ( selectedHour + ":" + selectedMinute), hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Opening Time");
            mTimePicker.show();

        });

        buttonSetClosingTime.setOnClickListener(v -> {
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(
                    RegisterRestaurant.this,
                    (timePicker, selectedHour, selectedMinute) ->
                            textViewClosingTime.setText( selectedHour + ":" + selectedMinute), hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Opening Time");
            mTimePicker.show();

        });
    }

    private void setOffDays() {
        textViewOffDays = findViewById(R.id.textView_regRes_noOffDays);
        buttonAddOffDays = findViewById(R.id.button_regRes_addOffDays);

        buttonAddOffDays.setOnClickListener(v -> {
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(RegisterRestaurant.this);
            builderSingle.setIcon(R.drawable.ic_menu_send);
            builderSingle.setTitle("Select One Name:-");

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    RegisterRestaurant.this, android.R.layout.select_dialog_singlechoice);
            arrayAdapter.add("Sunday");
            arrayAdapter.add("Monday");
            arrayAdapter.add("Tuesday");
            arrayAdapter.add("Wednesday");
            arrayAdapter.add("Friday");
            arrayAdapter.add("Saturday");

            builderSingle.setNegativeButton("cancel", (dialog, which) -> dialog.dismiss());

            builderSingle.setAdapter(arrayAdapter, (dialog, which) -> {
                String strName = arrayAdapter.getItem(which);
                AlertDialog.Builder builderInner = new AlertDialog.Builder(RegisterRestaurant.this);
                builderInner.setMessage(strName);
                builderInner.setTitle("Your Selected Item is");
                builderInner.setPositiveButton("Ok", (dialog1, which1) -> {
                    textViewOffDays.setText(strName);
                    dialog1.dismiss();
                });
                builderInner.show();
            });
            builderSingle.show();
        });
    }

    private void setSpinner() {
        spinner = findViewById(R.id.spinner_regRes_resType);

        List<String> spinnerList = new ArrayList<>();
        spinnerList.add(RESTAURANT_TYPE_VEG);
        spinnerList.add(RESTAURANT_TYPE_NON_VEG);
        spinnerList.add(RESTAURANT_TYPE_VEG_AND_NON_VEG);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, spinnerList );
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                resType = spinnerList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
