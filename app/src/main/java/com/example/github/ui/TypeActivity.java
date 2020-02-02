package com.example.github.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.github.R;
import com.example.github.di.DaggerAppComponent;
import com.example.github.model.Restaurant;
import com.example.github.ui.auth.AuthActivity;
import com.example.github.ui.main.MainActivity;
import com.example.github.ui.restaurant.home.RestaurantHomeActivity;
import com.example.github.ui.user.home.UserHomeActivity;
import com.example.github.util.SharedPrefUtil;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

import static com.example.github.ui.auth.AuthActivity.*;

public class TypeActivity extends DaggerAppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        setTitle("Food ordering system");

        SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(this);

        String type = sharedPrefUtil.getType();

        checkCredentials(sharedPrefUtil, type);

        progressBar.setVisibility(View.GONE);
        Intent intent = new Intent(TypeActivity.this , AuthActivity.class);

        findViewById(R.id.user_type_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(INTENT_MESSAGE_AUTH_TYPE, INTENT_MESSAGE_AUTH_TYPE_USER);
                startActivity(intent);
            }
        });


        findViewById(R.id.restaurant_type_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(INTENT_MESSAGE_AUTH_TYPE, INTENT_MESSAGE_AUTH_TYPE_RESTAURANT);
                startActivity(intent);
            }
        });
    }

    private void checkCredentials(SharedPrefUtil sharedPrefUtil, String type) {
        if(type != null && type.equals(INTENT_MESSAGE_AUTH_TYPE_USER)){
            String credentials = sharedPrefUtil.getCredentials();
            if(credentials != null && !credentials.isEmpty()){
                Intent intent = new Intent(this, UserHomeActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(this, "Something went wrong! Login again", Toast.LENGTH_LONG).show();
            }
        }else if(type != null && type.equals(INTENT_MESSAGE_AUTH_TYPE_RESTAURANT)){
            String credentials = sharedPrefUtil.getCredentials();
            if(credentials != null && !credentials.isEmpty()){
                Intent intent = new Intent(this, RestaurantHomeActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(this, "Something went wrong! Login again", Toast.LENGTH_LONG).show();
            }
        }
    }
}
