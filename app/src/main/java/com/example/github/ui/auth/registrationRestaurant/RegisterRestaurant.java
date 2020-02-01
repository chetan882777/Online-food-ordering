package com.example.github.ui.auth.registrationRestaurant;


import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;

import com.example.github.R;
import com.example.github.firebase.FirebaseRequestListener;
import com.example.github.model.Restaurant;
import com.example.github.ui.auth.AuthActivity;
import com.example.github.ui.auth.registrationUser.RegisterUserViewModel;
import com.example.github.ui.auth.registrationUser.RegistrationUser;
import com.example.github.ui.main.ViewModelProviderFactory;
import com.example.github.ui.user.home.UserHomeActivity;
import com.example.github.util.Constants;
import com.example.github.util.SharedPrefUtil;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class RegisterRestaurant extends DaggerAppCompatActivity {

    public static final String RESTAURANT_TYPE_VEG = "Veg only";
    public static final String RESTAURANT_TYPE_NON_VEG = "Non-Veg only";
    public static final String RESTAURANT_TYPE_VEG_AND_NON_VEG = "Veg and Non-Veg";

    private String resType = RESTAURANT_TYPE_VEG;
    private List<String> offDays = new ArrayList<>();
    private int startHour = 10;
    private int endHour = 23;
    private int startMinute = 0;
    private int endMinute = 0;

    private Spinner spinner;
    private TextView textViewOffDays;
    private Button buttonAddOffDays;

    private TextView textViewOpeningTime;
    private Button buttonSetOpeningTime;
    private TextView textViewClosingTime;
    private Button buttonSetClosingTime;

    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextContact;
    private EditText editTextAddress;

    private Button nextButton;

    @Inject
    ViewModelProviderFactory providerFactory;
    RegisterRestaurantViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_restaurant);

        viewModel = ViewModelProviders.of(this, providerFactory).get(RegisterRestaurantViewModel.class);

        setOffDays();
        setSpinner();
        setRestaurantTimings();
        signUp();

    }

    private void signUp() {
        nextButton = findViewById(R.id.button_regRes_next);
        editTextEmail = findViewById(R.id.editText_regResEmail);
        editTextPassword = findViewById(R.id.editText_regResPassword);
        editTextContact = findViewById(R.id.editText_regResContact);
        editTextAddress = findViewById(R.id.editText_regResAddress);

        nextButton.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();
            String contact = editTextContact.getText().toString();
            String address = editTextAddress.getText().toString();

            String regex = "^(.+)@(.+)$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);

            if((email.isEmpty() || email.equals("")) ||
                    (password.isEmpty() || password.equals("")) ||
                    (contact.isEmpty() || contact.equals("")) ||
                    (address.isEmpty() || address.equals(""))
            ) {
                showMessage("Any of the field cannot be empty");
            }else if(!matcher.matches()){
                showMessage("Enter valid E-Mail");
            }else{
                String[] offDay = new String[offDays.size()];
                int i=0;
                for(String s: offDays){
                    offDay[i] = s;
                    i++;
                }
                register(email, password, contact, address, offDay);
            }
        });
    }

    private void register(String email, String password, String contact, String address, String[] offDay) {
        Restaurant restaurant = new Restaurant(email, password, contact, address,
                null, null, ""+startHour, ""+startMinute,
                ""+endHour, ""+endMinute, offDay);
        FirebaseRequestListener<String> listener = (s -> {
            showMessage(s);
            if(s.equals(Constants.FIREBASE_SUCCESS)){
                SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(RegisterRestaurant.this);
                sharedPrefUtil.saveCredentials(contact);
                sharedPrefUtil.saveType(AuthActivity.INTENT_MESSAGE_AUTH_TYPE_RESTAURANT);

                Intent intent = new Intent(this, UserHomeActivity.class);
                startActivity(intent);
            }
        });
        viewModel.register(restaurant, listener);
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
                    (timePicker, selectedHour, selectedMinute) -> {
                        textViewOpeningTime.setText
                                (selectedHour + " : " + selectedMinute);
                        startHour = selectedHour;
                        startMinute = selectedMinute;
                    }, hour, minute, true);//Yes 24 hour time

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
                    (timePicker, selectedHour, selectedMinute) -> {
                        textViewClosingTime.setText(selectedHour + " : " + selectedMinute);
                        endHour = selectedHour;
                        endMinute = selectedMinute;
                    }, hour, minute, true);//Yes 24 hour time
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
                    if (!offDays.contains(strName)) {
                        offDays.add(strName);
                        String msg = "";
                        for (String s : offDays) {
                            msg = msg + s + "\n";
                        }
                        textViewOffDays.setText(msg);
                    }
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
                android.R.layout.simple_spinner_dropdown_item, spinnerList);
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

    private void showMessage(String s) {
        Snackbar.make(nextButton,
                s,
                Snackbar.LENGTH_SHORT)
                .show();
    }
}
