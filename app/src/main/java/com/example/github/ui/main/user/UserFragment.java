package com.example.github.ui.main.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import dagger.android.support.DaggerFragment;

import com.bumptech.glide.request.RequestOptions;
import com.example.github.R;
import com.example.github.ui.main.ViewModelProviderFactory;

import javax.inject.Inject;

/**
 * A placeholder fragment containing a simple view.
 */
public class UserFragment extends DaggerFragment {

    private static final String TAG = "UserFragment";

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    RequestOptions requestOptions;

    private UserViewModel viewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user, container, false);

        return root;
    }
}