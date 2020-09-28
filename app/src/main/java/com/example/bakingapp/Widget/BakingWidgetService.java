package com.example.bakingapp.Widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import androidx.core.content.ContextCompat;

import com.example.bakingapp.JSONUtility;
import com.example.bakingapp.R;

import java.util.ArrayList;

import timber.log.Timber;

import static com.example.bakingapp.Constants.ACTION_INTENT_OPEN_RECIPE;
import static com.example.bakingapp.Constants.JOB_ID;
import static com.example.bakingapp.Constants.RECIPE_POSITION;
import static com.example.bakingapp.Constants.RECIPE_PREFERENCE_FILE;

//public class BakingWidgetService extends JobIntentService {
public class BakingWidgetService extends IntentService {

    //declare this name as constant
    public BakingWidgetService() {
        super("BakingWidgetService");
        //super(this.getString(R.string.baking_widget_service));
    }


//    @Override
//    protected void onHandleWork(@NonNull Intent intent) {
//        if(intent!=null){
//            if(intent.getAction().equals(ACTION_INTENT_OPEN_RECIPE)){
//                updateRecipeWidgets();
//                //work done here
//
//
//            }
//        }
//    }

    public void updateRecipeWidget(){
        Timber.i("updateREcipeWidgets called");

        //getSharedPreferences find current recipe and place string in textview
        //        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

    }



    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INTENT_OPEN_RECIPE.equals(action)) {
                int recipePosition=intent.getIntExtra(RECIPE_POSITION,0);
                //after passing intent filter start background work
                handleActionRecipeUpdate(recipePosition);
            }
        }
    }


//    public static void enqueueRecipeWork(Context context, Intent workIntent) {
//        Timber.i("enqueRecipeWork called");
//        enqueueWork(context, BakingWidgetService.class, JOB_ID, workIntent);
//    }

    private void handleActionRecipeUpdate(int recipePosition) {
        JSONUtility jsonUtility= JSONUtility.createJSONUtilityInstance();
        ArrayList<String> recipeNames=jsonUtility.getRecipeNames();
        String recipeName = recipeNames.get(recipePosition);
        String recipe=jsonUtility.getRecipeOnlyIngredientsAsString(recipePosition);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingWidgetProvider.class));
        BakingWidgetProvider.updateRecipeWidgets(this, recipeName, recipe, appWidgetManager, appWidgetIds);

    }



    public static void startActionUpdateRecipe(Context context, int recipePosition){
        //SharedPreferences sharedPreferences=context.getApplicationContext().getSharedPreferences(RECIPE_PREFERENCE_FILE, MODE_PRIVATE);
//        int recipePosition=sharedPreferences.getInt(RECIPE_POSITION,0);
        Intent createRecipeStringintent = new Intent(context, BakingWidgetService.class);
        createRecipeStringintent.setAction(ACTION_INTENT_OPEN_RECIPE);
        createRecipeStringintent.putExtra(RECIPE_POSITION, recipePosition);
        Timber.i("startActionUpdateRecipe called in service");
        context.startService(createRecipeStringintent);
    }

    public static void startActionUpdateRecipe(Context context){
        SharedPreferences sharedPreferences=context.getApplicationContext().getSharedPreferences(RECIPE_PREFERENCE_FILE, MODE_PRIVATE);
        int recipePosition=sharedPreferences.getInt(RECIPE_POSITION,0);
        Intent createRecipeStringintent = new Intent(context, BakingWidgetService.class);
        createRecipeStringintent.setAction(ACTION_INTENT_OPEN_RECIPE);
        createRecipeStringintent.putExtra(RECIPE_POSITION, recipePosition);
        Timber.i("startActionUpdateRecipe called in service");
        context.startService(createRecipeStringintent);
    }

}
