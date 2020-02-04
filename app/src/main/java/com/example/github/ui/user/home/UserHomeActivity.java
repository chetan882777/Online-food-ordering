package com.example.github.ui.user.home;

import android.content.Intent;
import android.os.Bundle;

import com.example.github.R;
import com.example.github.model.Restaurant;
import com.example.github.model.User;
import com.example.github.ui.TypeActivity;
import com.example.github.ui.main.ViewModelProviderFactory;
import com.example.github.util.SharedPrefUtil;

import android.util.Log;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class UserHomeActivity extends AppCompatActivity {

    private static final String TAG = "UserHomeActivity";
    private ProgressBar progressBar;

    @Inject
    ViewModelProviderFactory providerFactory;
    UserHomeViewModel viewModel;
    private SharedPrefUtil sharedPrefUtil;
    private NavigationView navigationView;
    private TextView textViewNavHeaderName;
    private TextView textViewNavHeaderContact;
    private User user;
    private RecyclerView recyclerView;
    private List<Restaurant> restaurantList;
    private UserHomeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        viewModel = ViewModelProviders.of(this).get(UserHomeViewModel.class);
        sharedPrefUtil = new SharedPrefUtil(this);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        setNavBar();
        getUserInfo();
        setAdapter();
    }

    private void setAdapter() {
        restaurantList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView_userHome);

        adapter = new UserHomeAdapter(this, restaurantList, v ->{

        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void getRestaurants() {
        progressBar.setVisibility(View.VISIBLE);
        viewModel.getRestaurants(user.getCity());
        viewModel.getRestaurants().observe(this, new Observer<List<Restaurant>>() {

            @Override
            public void onChanged(List<Restaurant> restaurants) {
                restaurantList = restaurants;
                progressBar.setVisibility(View.GONE);
                adapter.setRestaurants(restaurantList);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void getUserInfo() {
        String userId = sharedPrefUtil.getCredentials();

        viewModel.getUserInfo(userId);
        viewModel.getUser().observe(this, user ->{
            this.user = user;
            progressBar.setVisibility(View.GONE);
            Log.d(TAG, "onChanged: " + user);
            setUserInfo(user);
            getRestaurants();
        });
    }

    private void setUserInfo(User user) {
        textViewNavHeaderName = navigationView.findViewById(R.id.textView_navHeader_name);
        textViewNavHeaderContact = navigationView.findViewById(R.id.textView_navHeader_contact);

        textViewNavHeaderName.setText(user.getEmail());
        textViewNavHeaderContact.setText(user.getContact());
        getSupportActionBar().setTitle("Restaurants in " + user.getCity());
    }

    private void setNavBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        setNavClicks(navigationView);
    }

    private void setNavClicks(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            if(id == R.id.nav_history){
                return true;
            }
            else if(id == R.id.nav_logout){
                SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(UserHomeActivity.this);
                sharedPrefUtil.logout();
                startActivity(new Intent(UserHomeActivity.this, TypeActivity.class));
                return true;
            }
            return false;
        });
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
