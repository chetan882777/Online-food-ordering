package com.example.github.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.github.R;
import com.example.github.ui.auth.AuthActivity;

import static com.example.github.ui.auth.AuthActivity.*;

public class TypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
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
}
