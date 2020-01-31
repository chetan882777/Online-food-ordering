package com.example.github.di;

import com.bumptech.glide.request.RequestOptions;
import com.example.github.R;
import com.example.github.repository.RestaurantRepository;
import com.example.github.util.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class AppModule {

    @Singleton
    @Provides
    static Retrofit provideRetrofitInstance(){
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }



    @Singleton
    @Provides
    static RestaurantRepository providesGithubRepository( ){
        return new RestaurantRepository();
    }

    @Singleton
    @Provides
    static RequestOptions providesRequestOptions(){
        return new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);
    }


}
