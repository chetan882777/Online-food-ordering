package com.example.github.ui.main;

import com.example.github.repository.RestaurantRepository;

import javax.inject.Inject;

import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private final RestaurantRepository repository;

    @Inject
    public MainViewModel(RestaurantRepository repository){
        this.repository = repository;
    }




}
