package com.example.bakingapp.Daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.bakingapp.Pojos.IngredientPojo;

import java.util.List;

@Dao
public interface IngredientsDao {

        @Query("SELECT * FROM ingredients")
        LiveData<List<IngredientPojo>> getAllIngredients();

        @Query("SELECT * FROM ingredients WHERE id = :ingredients_id")
        LiveData<IngredientPojo> getIngredient(String ingredients_id);

}
