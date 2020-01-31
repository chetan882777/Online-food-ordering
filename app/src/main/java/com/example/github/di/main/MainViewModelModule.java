package com.example.github.di.main;

import com.example.github.di.ViewModelKey;
import com.example.github.ui.main.MainViewModel;
import com.example.github.ui.main.repos.ReposViewModel;
import com.example.github.ui.main.user.UserViewModel;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel.class)
    public abstract ViewModel bindUserViewModel(UserViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ReposViewModel.class)
    public abstract ViewModel bindReposViewModel(ReposViewModel viewModel);

        @Binds
        @IntoMap
        @ViewModelKey(MainViewModel.class)
        public abstract ViewModel bindMainViewModel(MainViewModel viewModel);

}
