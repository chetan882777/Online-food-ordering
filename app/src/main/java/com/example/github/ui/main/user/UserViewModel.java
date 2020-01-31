package com.example.github.ui.main.user;



import com.example.github.repository.GithubRepository;

import javax.inject.Inject;

import androidx.lifecycle.ViewModel;

public class UserViewModel extends ViewModel {

    private GithubRepository repository;

    @Inject
    public UserViewModel(GithubRepository repository) {
        this.repository = repository;
    }



}
