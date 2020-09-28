package com.example.bakingapp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ExoplayerViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private Application application;

    public ExoplayerViewModelFactory(Application application) {
        this.application=application;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ExoplayerViewModel(application);
    }
}