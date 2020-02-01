package com.example.github.di.auth.RegisterRestaurant;

import androidx.lifecycle.ViewModel;

import com.example.github.di.ViewModelKey;
import com.example.github.ui.auth.registrationRestaurant.RegisterRestaurantViewModel;
import com.example.github.ui.auth.registrationUser.RegisterUserViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class RegisterRestaurantViewModelModule {

        @Binds
        @IntoMap
        @ViewModelKey(RegisterRestaurantViewModel.class)
        public abstract ViewModel bindRegisterRestaurantViewModel(RegisterUserViewModel viewModel);
}
