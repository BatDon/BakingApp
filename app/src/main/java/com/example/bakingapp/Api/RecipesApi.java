package com.example.bakingapp.Api;

import com.example.bakingapp.Pojos.RecipePojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipesApi {

    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<List<RecipePojo>> getAllRecipes();

}
