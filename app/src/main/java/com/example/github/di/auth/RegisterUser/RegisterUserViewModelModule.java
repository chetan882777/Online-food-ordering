package com.example.github.di.auth.RegisterUser;

import androidx.lifecycle.ViewModel;

import com.example.github.di.ViewModelKey;
import com.example.github.ui.auth.AuthViewModel;
import com.example.github.ui.auth.registrationUser.RegisterUserViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class RegisterUserViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(RegisterUserViewModel.class)
    public abstract ViewModel bindAuthViewModel(RegisterUserViewModel viewModel);
}
