package com.example.bakingapp.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.RemoteViews;

import com.example.bakingapp.JSONUtility;
import com.example.bakingapp.MainActivity;
import com.example.bakingapp.Pojos.IngredientPojo;
import com.example.bakingapp.R;
import com.example.bakingapp.RecipeIngredientsSteps;

import java.util.ArrayList;

import timber.log.Timber;

import static android.content.Context.MODE_PRIVATE;
import static com.example.bakingapp.Constants.ACTION_INTENT_OPEN_RECIPE;
import static com.example.bakingapp.Constants.RECIPE_POSITION;
import static com.example.bakingapp.Constants.RECIPE_PREFERENCE_FILE;

//public class BakingWidgetProvider extends AppWidgetProvider {
public class BakingWidgetProvider extends AppWidgetProvider {

    static void updateRecipeAppWidgets(Context context, String recipeName, String recipeIngredients, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget_provider);

        SharedPreferences sharedPreferences=context.getApplicationContext().getSharedPreferences(RECIPE_PREFERENCE_FILE, MODE_PRIVATE);
        int recipePosition=sharedPreferences.getInt(RECIPE_POSITION, 0);

        Intent intent = new Intent(context, RecipeIngredientsSteps.class);
        intent.putExtra(RECIPE_POSITION, recipePosition);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);

        if(recipeName.equals("")){
            recipeName = context.getString(R.string.place_holder_title_ingredients);
        }
        
        if(recipeIngredients.equals("")){
            recipeIngredients = context.getString(R.string.awaiting_ingredients);
        }

        Timber.i("recipeName= "+recipeName);
        Timber.i("recipeIngredients= "+recipeIngredients);

        views.setTextViewText(R.id.widget_title_ingredients_tv, recipeName);
        views.setTextViewText(R.id.widget_ingredients_tv, recipeIngredients);

        //pending intent when text view in widget clicked opens RecipeIngredientsSteps activity
        views.setOnClickPendingIntent(R.id.widget_ingredients_tv, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        BakingWidgetService.startActionUpdateRecipe(context);
    }


    public static void updateRecipeWidgets(Context context, String recipeName , String recipeIngredients, AppWidgetManager appWidgetManager,
                                          int[] appWidgetIds){
        //There can be more one appWidget placed on users screen
        //all must be updated
        for (int appWidgetId : appWidgetIds) {
            updateRecipeAppWidgets(context, recipeName, recipeIngredients, appWidgetManager, appWidgetId);
        }
    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}
