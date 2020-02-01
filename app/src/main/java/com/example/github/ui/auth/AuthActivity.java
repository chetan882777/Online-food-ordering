package com.example.github.ui.auth;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;

import com.example.github.R;
import com.example.github.firebase.FirebaseRequestListener;
import com.example.github.ui.auth.registrationUser.RegistrationUser;
import com.example.github.ui.main.MainActivity;
import com.example.github.ui.main.MainViewModel;
import com.example.github.ui.main.ViewModelProviderFactory;
import com.example.github.util.Constants;
import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity {

    public static final String INTENT_MESSAGE_AUTH_TYPE = "auth type";
    public static final String INTENT_MESSAGE_AUTH_TYPE_RESTAURANT = "auth type restaurant";
    public static final String INTENT_MESSAGE_AUTH_TYPE_USER = "auth type user";

    private EditText textViewEmail;
    private EditText textViewPassword;
    private Button buttonLogIn;
    private Button buttonSignUp;

    @Inject
    ViewModelProviderFactory providerFactory;

    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        authViewModel = ViewModelProviders.of(this, providerFactory).get(AuthViewModel.class);

        Intent intent = getIntent();

        String data = intent.getStringExtra(INTENT_MESSAGE_AUTH_TYPE);

        buttonLogIn = findViewById(R.id.button_login);
        buttonSignUp = findViewById(R.id.button_signup);
        textViewEmail = findViewById(R.id.editText_email);
        textViewPassword = findViewById(R.id.editText_password);

        buttonLogIn.setOnClickListener(v -> {
            String contact = textViewEmail.getText().toString();
            String password = textViewPassword.getText().toString();


            if((contact.isEmpty() || contact.equals("")) || (password.isEmpty() || password.equals(""))) {
                Snackbar.make(buttonLogIn,
                        "Any of the field cannot be empty",
                        Snackbar.LENGTH_SHORT)
                        .show();
            }else{

                FirebaseRequestListener<String> listener = data1 -> {
                    Snackbar.make(buttonSignUp, data1, Snackbar.LENGTH_SHORT).show();
                    if(data.equals(Constants.FIREBASE_SUCCESS)){
                        Intent intent1 = new Intent(this, MainActivity.class);
                        startActivity(intent1);
                    }
                };
                authViewModel.login(data, contact, password, listener);
            }
        });

        buttonSignUp.setOnClickListener( v-> {

            Intent intent1 = new Intent(AuthActivity.this, RegistrationUser.class);
            startActivity(intent1);

        });
    }
}
