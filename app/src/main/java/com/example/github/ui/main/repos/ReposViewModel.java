package com.example.github.ui.main.repos;

import com.example.github.repository.GithubRepository;

import javax.inject.Inject;

import androidx.lifecycle.ViewModel;

public class ReposViewModel extends ViewModel {

    private GithubRepository repository;

    @Inject
    public ReposViewModel(GithubRepository repository) {
        this.repository = repository;
    }

}
