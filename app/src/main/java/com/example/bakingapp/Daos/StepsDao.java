package com.example.bakingapp.Daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.bakingapp.Pojos.IngredientPojo;

import java.util.List;

@Dao
public interface StepsDao {

    @Query("SELECT * FROM steps")
    LiveData<List<IngredientPojo>> getAllIngredients();

    @Query("SELECT * FROM steps WHERE id = :steps_id")
    LiveData<IngredientPojo> getIngredient(String steps_id);
}