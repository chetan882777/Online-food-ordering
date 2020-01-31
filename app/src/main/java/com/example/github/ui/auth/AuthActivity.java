package com.example.github.ui.auth;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;

import com.example.github.R;
import com.example.github.ui.auth.registrationUser.RegistrationUser;
import com.example.github.ui.main.MainViewModel;
import com.example.github.ui.main.ViewModelProviderFactory;
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

        String regex = "^(.+)@(.+)$";

        Pattern pattern = Pattern.compile(regex);

        buttonLogIn.setOnClickListener(v -> {
            String email = textViewEmail.getText().toString();
            String password = textViewPassword.getText().toString();

            Matcher matcher = pattern.matcher(email);

            if((email.isEmpty() || email.equals("")) || (password.isEmpty() || password.equals(""))) {
                Snackbar.make(buttonLogIn,
                        "Any of the field cannot be empty",
                        Snackbar.LENGTH_SHORT)
                        .show();
            }else if(!matcher.matches()){
                Snackbar.make(buttonLogIn,
                        "Enter valid E-Mail",
                        Snackbar.LENGTH_SHORT)
                        .show();
            } else{
                authViewModel.login(data, email, password);
            }
        });

        buttonSignUp.setOnClickListener( v-> {

            Intent intent1 = new Intent(AuthActivity.this, RegistrationUser.class);
            startActivity(intent1);

        });
    }
}
