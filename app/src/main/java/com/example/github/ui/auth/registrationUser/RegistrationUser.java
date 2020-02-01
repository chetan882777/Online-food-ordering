package com.example.github.ui.auth.registrationUser;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;

import com.example.github.R;
import com.example.github.firebase.FirebaseRequestListener;
import com.example.github.ui.auth.AuthActivity;
import com.example.github.ui.main.MainActivity;
import com.example.github.ui.main.MainViewModel;
import com.example.github.ui.main.ViewModelProviderFactory;
import com.example.github.ui.user.home.UserHomeActivity;
import com.example.github.util.Constants;
import com.example.github.util.SharedPrefUtil;
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
    private ProgressBar progressBar;

    @Inject
    ViewModelProviderFactory providerFactory;

    private RegisterUserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_user);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

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
                    showMessage("Any of the field cannot be empty");
                }else if(!matcher.matches()){
                    showMessage("Enter valid E-Mail");
                }else{
                    register(email, password, contact, address);
                }
        });
    }

    private void showMessage(String s) {
        Snackbar.make(buttonSignUp,
                s,
                Snackbar.LENGTH_SHORT)
                .show();
    }

    private void register(String email, String password, String contact, String address) {
        progressBar.setVisibility(View.VISIBLE);
        FirebaseRequestListener<String> listener = data -> {
            showMessage(data);

            progressBar.setVisibility(View.GONE);
            if(data.equals(Constants.FIREBASE_SUCCESS)){
                SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(RegistrationUser.this);
                sharedPrefUtil.saveCredentials(contact);
                sharedPrefUtil.saveType(AuthActivity.INTENT_MESSAGE_AUTH_TYPE_USER);

                Intent intent = new Intent(this, UserHomeActivity.class);
                startActivity(intent);
            }
        };
        viewModel.register(email, password, contact, address, listener);
    }
}
