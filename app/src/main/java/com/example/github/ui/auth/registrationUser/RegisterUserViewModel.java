package com.example.github.ui.auth.registrationUser;

import androidx.lifecycle.ViewModel;

import com.example.github.repository.RestaurantRepository;

import javax.inject.Inject;

public class RegisterUserViewModel extends ViewModel {

    private final RestaurantRepository repository;

    @Inject
    public RegisterUserViewModel(RestaurantRepository repository){
        this.repository = repository;
    }


}
