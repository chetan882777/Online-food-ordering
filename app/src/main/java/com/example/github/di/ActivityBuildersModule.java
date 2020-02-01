package com.example.github.di;

import com.example.github.di.auth.AuthViewModelModule;
import com.example.github.di.auth.RegisterUser.RegisterUserViewModelModule;
import com.example.github.di.main.MainFragmentBuildersModule;
import com.example.github.di.main.MainModule;
import com.example.github.di.main.MainViewModelModule;
import com.example.github.ui.TypeActivity;
import com.example.github.ui.auth.AuthActivity;
import com.example.github.ui.auth.registrationUser.RegistrationUser;
import com.example.github.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
            modules = {
                    MainFragmentBuildersModule.class,
                    MainViewModelModule.class,
                    MainModule.class
            }
    )
    abstract MainActivity contributeMainActivity();


    @ContributesAndroidInjector(
            modules = {
                    AuthViewModelModule.class,
            }
    )
    abstract AuthActivity contributeAuthActivity();


    @ContributesAndroidInjector(

    )
    abstract TypeActivity contributeTypeActivity();



    @ContributesAndroidInjector(
            modules = {
                    RegisterUserViewModelModule.class,
            }
    )
    abstract RegistrationUser contributeRegisterUserActivity();
}
