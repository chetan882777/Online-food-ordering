package com.example.github.di.restaurant.menu;

import androidx.lifecycle.ViewModel;

import com.example.github.di.ViewModelKey;
import com.example.github.ui.auth.registrationUser.RegisterUserViewModel;
import com.example.github.ui.restaurant.menu.MenuViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MenuViewModelModule  {

    @Binds
    @IntoMap
    @ViewModelKey(MenuViewModel.class)
    public abstract ViewModel bindMenuViewModel(MenuViewModel viewModel);
}

