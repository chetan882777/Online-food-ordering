package com.example.github.ui.main;

import com.example.github.repository.GithubRepository;

import javax.inject.Inject;

import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private final GithubRepository repository;

    @Inject
    public MainViewModel(GithubRepository repository){
        this.repository = repository;
    }




}
