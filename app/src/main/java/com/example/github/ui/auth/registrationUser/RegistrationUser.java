package com.example.github.ui.auth.registrationUser;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;

import com.example.github.R;
import com.example.github.ui.main.MainViewModel;
import com.example.github.ui.main.ViewModelProviderFactory;
import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class RegistrationUser extends DaggerAppCompatActivity {

    private EditText textViewEmail;
    private EditText textViewPassword;
    private EditText textViewContact;
    private EditText textViewAddress;
    private Button buttonSignUp;

    @Inject
    ViewModelProviderFactory providerFactory;

    private RegisterUserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_user);

        textViewEmail = findViewById(R.id.editText_regUserEmail);
        textViewPassword = findViewById(R.id.editText_regUserPass);
        textViewContact = findViewById(R.id.editText_regUserContact);
        textViewAddress = findViewById(R.id.editText_regUserAddress);

        buttonSignUp = findViewById(R.id.button_regUser);

        String regex = "^(.+)@(.+)$";

        Pattern pattern = Pattern.compile(regex);

        viewModel = ViewModelProviders.of(this, providerFactory).get(RegisterUserViewModel.class);


        buttonSignUp.setOnClickListener( v -> {

                String email = textViewEmail.getText().toString();
                String password = textViewPassword.getText().toString();
                String contact = textViewContact.getText().toString();
                String address = textViewAddress.getText().toString();

                Matcher matcher = pattern.matcher(email);

                if((email.isEmpty() || email.equals("")) ||
                        (password.isEmpty() || password.equals("")) ||
                        (contact.isEmpty() || contact.equals("")) ||
                        (address.isEmpty() || address.equals(""))
                ) {
                    Snackbar.make(buttonSignUp,
                            "Any of the field cannot be empty",
                            Snackbar.LENGTH_SHORT)
                            .show();
                }else if(!matcher.matches()){
                    Snackbar.make(buttonSignUp,
                            "Enter valid E-Mail",
                            Snackbar.LENGTH_SHORT)
                            .show();
                }else{
                    viewModel.register(email, password, contact, address);
                }
        });
    }
}
