package com.example.bakingapp;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bakingapp.Pojos.RecipePojo;
//import com.example.bakingapp.Repositories.RecipeRepository;
import com.example.bakingapp.RetrofitRequester.RetrofitRequester;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel implements
        RetrofitRequester.OnRetrofitListener{

    public MutableLiveData<List<RecipePojo>> liveDataRecipeList=new MutableLiveData<List<RecipePojo>>(){};
    List<RecipePojo> recipesPojoList;
    Application application;

    public static final String TAG=MainActivityViewModel.class.getSimpleName();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        requestData();
        liveDataRecipeList.setValue(null);
    }

//    public MainActivityViewModel() {
//        super();
//        this.application=application;
//        requestData();
//    }

    public void requestData(){
        RetrofitRequester retrofitRequester=new RetrofitRequester();
        retrofitRequester.requestRecipes(this);
    }

    @Override
    public void onRetrofitFinished(List<RecipePojo> recipes) {
        Log.i(TAG, "onRetrofitFinished");
        recipesPojoList=recipes;
        liveDataRecipeList.setValue(recipes);
//        Toast.makeText(this, recipes.size()+" recipes", Toast.LENGTH_SHORT).show();
//        recipes.size();
    }

//    public List<RecipePojo> getAllRecipes(){
//        return recipesPojoList;
//    }

    public MutableLiveData<List<RecipePojo>> getAllRecipes(){
        //requestGeneralMovies();
        return this.liveDataRecipeList;
    }

}

//public class MainActivityViewModel extends AndroidViewModel implements
//        RetrofitRequester.OnRetrofitListener{
//
//    List<RecipePojo> recipesPojoList;
//    Application application;
//
//    public static final String TAG=MainActivityViewModel.class.getSimpleName();
//
//    public MainActivityViewModel(@NonNull RecipeRepository recipeRepository,Application application) {
//        super(application);
//        this.application=application;
//        requestData();
//    }
//
//    public void requestData(){
//        RetrofitRequester retrofitRequester=new RetrofitRequester();
//        retrofitRequester.requestRecipes(this);
//    }
//
//    @Override
//    public void onRetrofitFinished(List<RecipePojo> recipes) {
//        Log.i(TAG, "onRetrofitFinished");
//
//        recipesPojoList=recipes;
//        RecipeRepository recipeRepository= RecipeRepository.getInstance(application);
//        recipeRepository.getAllRecipes();
////        Toast.makeText(this, recipes.size()+" recipes", Toast.LENGTH_SHORT).show();
////        recipes.size();
//    }
//
//    public List<RecipePojo> getAllRecipes(){
//        return recipesPojoList;
//    }
//
//
//
//}
