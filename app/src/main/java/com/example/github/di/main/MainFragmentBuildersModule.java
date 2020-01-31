package com.example.github.di.main;

import com.example.github.ui.main.repos.ReposFragment;
import com.example.github.ui.main.user.UserFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract UserFragment contributeUserFragment();

    @ContributesAndroidInjector
    abstract ReposFragment contributeReposFragment();
}
