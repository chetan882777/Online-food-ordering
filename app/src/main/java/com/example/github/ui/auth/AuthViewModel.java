package com.example.github.ui.auth;

import androidx.lifecycle.ViewModel;

import com.example.github.repository.RestaurantRepository;

import javax.inject.Inject;

public class AuthViewModel extends ViewModel {

    private final RestaurantRepository repository;

    @Inject
    public AuthViewModel(RestaurantRepository repository){
        this.repository = repository;
    }

}
