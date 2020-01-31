package com.example.github.ui.main.repos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.github.R;
import com.example.github.ui.main.ViewModelProviderFactory;
import com.example.github.ui.main.user.UserViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import dagger.android.support.DaggerFragment;

public class ReposFragment extends DaggerFragment {

    private static final String TAG = "ReposFragment";
    private boolean isFiltered = false;

    @Inject
    ViewModelProviderFactory providerFactory;
    private UserViewModel viewModel;
    private RecyclerView recyclerView;
    private ReposAdapter adapter;
    private FloatingActionButton fab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_repos, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);

        fab = root.findViewById(R.id.fab);

        viewModel = ViewModelProviders.of(this, providerFactory).get(UserViewModel.class);

        return root;
    }
}