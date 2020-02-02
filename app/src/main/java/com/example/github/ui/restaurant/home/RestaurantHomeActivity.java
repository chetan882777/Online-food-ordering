package com.example.github.ui.restaurant.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.github.R;
import com.example.github.model.Restaurant;
import com.example.github.ui.TypeActivity;
import com.example.github.ui.user.home.UserHomeActivity;
import com.example.github.util.SharedPrefUtil;
import com.google.android.material.navigation.NavigationView;

public class RestaurantHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_restaurant);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Restaurant");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id == R.id.nav_history){

                    return true;
                }
                else if(id == R.id.nav_logout){
                    SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(RestaurantHomeActivity.this);
                    sharedPrefUtil.logout();
                    startActivity(new Intent(RestaurantHomeActivity.this, TypeActivity.class));
                    return true;
                }
                return false;
            }
        });
        Toast.makeText(this,"Restaurant", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_home, menu);
        return true;
    }
}
