package com.example.bakingapp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bakingapp.ViewModels.MainActivityViewModel;


public class MainActivityViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;
    public MainActivityViewModelFactory(Application application) {
        this.application=application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(MainActivityViewModel.class))
            return (T) new MainActivityViewModel(application);
        throw new IllegalArgumentException("Unknown ViewModel class");
    }


}

