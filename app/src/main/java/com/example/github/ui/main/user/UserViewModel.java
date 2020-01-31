package com.example.github.ui.main.user;



import com.example.github.repository.RestaurantRepository;

import javax.inject.Inject;

import androidx.lifecycle.ViewModel;

public class UserViewModel extends ViewModel {

    private RestaurantRepository repository;

    @Inject
    public UserViewModel(RestaurantRepository repository) {
        this.repository = repository;
    }



}
