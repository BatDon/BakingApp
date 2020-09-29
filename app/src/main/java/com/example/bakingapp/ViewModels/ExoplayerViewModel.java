package com.example.bakingapp.ViewModels;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bakingapp.JSONUtility;

import timber.log.Timber;

import static android.content.Context.MODE_PRIVATE;
import static com.example.bakingapp.Constants.RECIPE_POSITION;
import static com.example.bakingapp.Constants.RECIPE_STEP_POSITION_PREFERENCE_FILE;
import static com.example.bakingapp.Constants.STEP_POSITION;

public class ExoplayerViewModel extends AndroidViewModel {

    public MutableLiveData<Integer> stepNumberLiveData=new MutableLiveData<Integer>(){};
//public MutableLiveData<Integer> stepNumberLiveData;
    public int stepNumber;
    public int recipeNumber;
    Context context;



    public ExoplayerViewModel(@NonNull Application application) {
        super(application);
        context=application;
        stepNumber=0;

        stepNumberLiveData.setValue(0);

    }

    public MutableLiveData<Integer> getStepNumber() {
        if (stepNumberLiveData == null) {
            stepNumberLiveData = new MutableLiveData<Integer>();
            stepNumberLiveData.setValue(0);
        }
        return stepNumberLiveData;
    }

    public void setStepNumber(int stepNumber) {
        if(stepNumberLiveData==null){
            stepNumberLiveData=new MutableLiveData<Integer>();
            stepNumberLiveData.setValue(0);
        }
        Timber.i("setStepNumber called");
        this.stepNumberLiveData.setValue(stepNumber);
        Timber.i("setStepNumber called stepNumberLiveData= "+this.stepNumberLiveData.getValue().toString());
    }

    public void setRecipeNumber(int recipeNumber){
        this.recipeNumber=recipeNumber;
    }

    public void decreaseStep(){
        Timber.i("previousButtonClicked called currentStep= " + stepNumber);
        SharedPreferences positionSharedPreferences=context.getSharedPreferences(RECIPE_STEP_POSITION_PREFERENCE_FILE, MODE_PRIVATE);
        stepNumber =positionSharedPreferences.getInt(STEP_POSITION,0);
        SharedPreferences.Editor editor = positionSharedPreferences.edit();
        //editor.putInt(RECIPE_POSITION, 0);
        if(stepNumber==0){
            editor.putInt(STEP_POSITION, stepNumber);
        }
        else{
            editor.putInt(STEP_POSITION, stepNumber-1);
        }
        editor.apply();

        setStepNumber(stepNumber-1);
    }

    public void increaseStep(){

        JSONUtility jsonUtility=JSONUtility.createJSONUtilityInstance();
        int stepSize=jsonUtility.getRecipeStepsPojo(recipeNumber).size();

        SharedPreferences positionSharedPreferences = getApplication().getSharedPreferences(RECIPE_STEP_POSITION_PREFERENCE_FILE, MODE_PRIVATE);
        stepNumber =positionSharedPreferences.getInt(STEP_POSITION,0);

        Timber.i("stepNumber= "+stepNumber);
        SharedPreferences.Editor editor = positionSharedPreferences.edit();

        if(stepNumber<stepSize-1) {
            Timber.i("nextButtonClicked called currentStep<stepSize-1");

            editor.putInt(STEP_POSITION, stepNumber + 1);
            editor.apply();
            setStepNumber(stepNumber+1);
        }
        //go to first step if reached end
        else if(stepSize==stepSize-1){
            Timber.i("nextButtonClicked called currentStep==stepSize-1");
            editor.putInt(STEP_POSITION, 0);
            editor.apply();
            setStepNumber(0);
        }
    }
}
