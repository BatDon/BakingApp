package com.example.bakingapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

//import com.example.bakingapp.Repositories.RecipeRepository;

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

//public class MainActivityViewModelFactory implements ViewModelProvider.Factory {
//    private final RecipeRepository recipeRepository;
//    private final Application application;
//    public MainActivityViewModelFactory(Application application, RecipeRepository recipeRepository) {
//        this.recipeRepository = recipeRepository;
//        this.application=application;
//    }
//
//    @NonNull
//    @Override
//    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//
//        if (modelClass.isAssignableFrom(MainActivityViewModel.class))
//            return (T) new MainActivityViewModel(recipeRepository, application);
//        throw new IllegalArgumentException("Unknown ViewModel class");
//    }
//
//
//}
