package com.example.github.ui.main.repos;

import com.example.github.repository.RestaurantRepository;

import javax.inject.Inject;

import androidx.lifecycle.ViewModel;

public class ReposViewModel extends ViewModel {

    private RestaurantRepository repository;

    @Inject
    public ReposViewModel(RestaurantRepository repository) {
        this.repository = repository;
    }

}
