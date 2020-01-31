package com.example.github.di.auth;

import androidx.lifecycle.ViewModel;

import com.example.github.di.ViewModelKey;
import com.example.github.ui.auth.AuthViewModel;
import com.example.github.ui.main.MainViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class AuthViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    public abstract ViewModel bindAuthViewModel(AuthViewModel viewModel);
}
