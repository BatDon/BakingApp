package com.example.bakingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bakingapp.Pojos.IngredientPojo;
import com.example.bakingapp.Pojos.RecipePojo;
import com.example.bakingapp.Pojos.StepPojo;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import timber.log.Timber;

import static com.example.bakingapp.Constants.RECIPE_AS_JSON_STRING;
import static com.example.bakingapp.Constants.RECIPE_PREFERENCE_FILE;

public class JSONUtility extends AppCompatActivity {

    private static JSONUtility jsonUtility;
    public static final String TAG=JSONUtility.class.getSimpleName();
    Context mContext;
    com.google.gson.Gson mGson;
    SharedPreferences sharedPreferences;

    public JSONUtility(){
//        if(jsonUtility==null){
//            jsonUtility=new JSONUtility();
//        }
//        createJSONUtilityInstance();
//        return jsonUtility;
    }

    public static JSONUtility createJSONUtilityInstance(){
        if(jsonUtility==null){
            jsonUtility=new JSONUtility();
        }
        return jsonUtility;
    }

    public void storeRecipeDataInSharedPreferences(Context context, List<RecipePojo> recipeList) {
        this.mContext=context;

        //SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
//        SharedPreferences sharedPreferences = this.getSharedPreferences(RECIPE_PREFERENCE_FILE, Context.MODE_PRIVATE);
        //SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        sharedPreferences=mContext.getApplicationContext().getSharedPreferences(RECIPE_PREFERENCE_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> recipesSet = new HashSet<String>();
        //ArrayList<String> jsonAsStringData;


        for (RecipePojo recipe: recipeList) {
            String json = new Gson().getInstance().toJson(recipe);
            Log.i(TAG,"json= "+json);
            recipesSet.add(json);
            //recipeList
        }

        editor.putStringSet(RECIPE_AS_JSON_STRING, recipesSet);
        editor.apply();

    }

//    public Set<String> getRecipeSet(Context context) {
    public Set<String> getRecipeSet() {

        Set<String> recipeSet;

//        SharedPreferences sharedPreferences = context.getSharedPreferences(RECIPE_PREFERENCE_FILE, Context.MODE_PRIVATE);
//        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        //SharedPreferences sharedPreferences=mContext.getApplicationContext().getSharedPreferences(RECIPE_PREFERENCE_FILE, MODE_PRIVATE);
        recipeSet = sharedPreferences.getStringSet(RECIPE_AS_JSON_STRING, null);

        if(recipeSet==null){
            Log.i(TAG, "recipeSet equals null");
        }

        return recipeSet;

    }


    public String getRecipe(int position) {

        Set<String> recipeSet;

        //SharedPreferences sharedPreferences = context.getSharedPreferences(RECIPE_PREFERENCE_FILE, Context.MODE_PRIVATE);
//        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        //SharedPreferences sharedPreferences=mContext.getApplicationContext().getSharedPreferences(RECIPE_PREFERENCE_FILE, MODE_PRIVATE);
        recipeSet = sharedPreferences.getStringSet(RECIPE_AS_JSON_STRING, null);


        String recipeString=null;



        if (recipeSet != null) {
            String[] recipeArray = new String[recipeSet.size()];
            int i = 0;
            for (String str : recipeSet) {
                recipeArray[i++] = str;
            }

            recipeString=recipeArray[position];

        }

        return recipeString;
    }

    public ArrayList<String> getRecipeNames(){
        Set<String> recipeSet=getRecipeSet();
        ArrayList<String> recipeNames=new ArrayList<>();
        for(String recipe:recipeSet){
            recipeNames.add(mGson.fromJson(recipe, RecipePojo.class).getName());
        }
        return recipeNames;
    }

    public ArrayList<IngredientPojo> getRecipeIngredients(int position) {

        String recipeString = getRecipe(position);
        Timber.i("getRecipeIngredients recipeString= "+recipeString);
        ArrayList<IngredientPojo> recipeIngredientsAL = new ArrayList<>();
        ArrayList<RecipePojo> recipePojoArrayList = new ArrayList<RecipePojo>();
        recipePojoArrayList.add(mGson.fromJson(recipeString, RecipePojo.class));
        RecipePojo recipePojo = recipePojoArrayList.get(0);
        Timber.i("getRecipeIngredients recipePojo= "+recipePojo.getIngredients().get(0).getIngredient());
//        for (int i = 0; i < recipePojo.getIngredients().size(); i++) {
        List<IngredientPojo> recipePojoIngredients=recipePojo.getIngredients();
        recipeIngredientsAL.addAll(recipePojoIngredients);
        //recipeIngredients.add(recipePojo.getIngredients());
//        }
        //  recipeIngredients.add(recipePojo.getIngredients());
        return recipeIngredientsAL;
    }

    public String getRecipeOnlyIngredientsAsString(int position){
        ArrayList<IngredientPojo> ingredientPojoArrayList = getRecipeIngredients(position);
        String ingredientTest=ingredientPojoArrayList.get(position).getIngredient();
        Timber.i("getRecipeOnlyIngredientsAsString= "+ingredientTest);
        StringBuilder recipeIngredientsSB=new StringBuilder();
//        recipeIngredientsSB.append("Ingredients");
//        recipeIngredientsSB.append("\n");
        for (int i = 0; i < ingredientPojoArrayList.size(); i++) {
            String ingredient = new Gson().getInstance().toJson(ingredientPojoArrayList.get(i).getIngredient());
            recipeIngredientsSB.append(ingredient);
            recipeIngredientsSB.append("\n");
        }
        String ingredientsString=recipeIngredientsSB.toString();
        Timber.i("getRecipeOnlyIngredientsAsString ingredientString= "+ingredientsString);
        return ingredientsString;
    }
    /*
    @param recipe position
    @return step pojo
     */

    //returns all one recipe steps based off of recipe position
    public ArrayList<StepPojo> getRecipeStepsPojo(int position){
        String recipeString = getRecipe(position);
        ArrayList<StepPojo> recipeStepsAL = new ArrayList<>();
        ArrayList<RecipePojo> recipePojoArrayList = new ArrayList<RecipePojo>();
        recipePojoArrayList.add(mGson.fromJson(recipeString, RecipePojo.class));
        RecipePojo recipePojo = recipePojoArrayList.get(0);
        List<StepPojo> recipePojoIngredients=recipePojo.getSteps();
        recipeStepsAL.addAll(recipePojoIngredients);
        return recipeStepsAL;
    }


    public StepPojo getStep(int recipePosition, int stepPosition){
        ArrayList<StepPojo> recipeSteps=getRecipeStepsPojo(recipePosition);
        StepPojo stepPojo =recipeSteps.get(stepPosition);
        return stepPojo;
    }




    final public class Gson {

        public com.google.gson.Gson getInstance() {
            if (mGson == null) {
                mGson = new GsonBuilder().create();
            }
            return mGson;
        }
    }
}
