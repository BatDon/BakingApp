package com.example.bakingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.BackButtonPressed;
import com.example.bakingapp.Fragments.ExoplayerFragment;
import com.example.bakingapp.R;
import com.example.bakingapp.ViewModels.ExoplayerViewModel;
import com.example.bakingapp.ViewModels.ExoplayerViewModelFactory;

import java.util.List;
import java.util.Timer;

import timber.log.Timber;

import static com.example.bakingapp.Constants.EXOPLAYER_FRAGMENT;
import static com.example.bakingapp.Constants.RECIPE_POSITION;
import static com.example.bakingapp.Constants.RECIPE_STEP_POSITION_PREFERENCE_FILE;
import static com.example.bakingapp.Constants.STEP_POSITION;

//public class ExoplayerActivity extends AppCompatActivity implements BackButtonPressed {
//called when NOT tablet
//public class ExoplayerActivity extends AppCompatActivity implements ExoplayerFragment.NextButtonClicked
//            , ExoplayerFragment.PreviousButtonClicked{
public class ExoplayerActivity extends AppCompatActivity{
    int i=0;

    int recipePosition=0;

    boolean isTablet=true;

    ExoplayerViewModel exoplayerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exoplayer_activity_layout);

        Intent intent=getIntent();
        recipePosition=intent.getIntExtra(RECIPE_POSITION,0);


        setUpExoplayerViewModel();

//        ExoplayerViewModelFactory exoplayerViewModelFactory=new ExoplayerViewModelFactory(this.getApplication());
//        exoplayerViewModel = ViewModelProviders.of(this, exoplayerViewModelFactory).get(ExoplayerViewModel.class);

//        if(savedInstanceState==null) {
//            initializeExoPlayer();
//        }

        //Have to initialize exoplayer fragment every time in order to set listeners in fragment
        initializeExoPlayer();
    }



    public void setUpExoplayerViewModel(){
        ExoplayerViewModelFactory exoplayerViewModelFactory=new ExoplayerViewModelFactory(getApplication());
//        exoplayerViewModel = new ViewModelProvider(this).get(exoplayerViewModelFactory);
        exoplayerViewModel = ViewModelProviders.of(this, exoplayerViewModelFactory).get(ExoplayerViewModel.class);
        exoplayerViewModel.setStepNumber(0);
        exoplayerViewModel.getStepNumber();



//        exoplayerViewModel.setStepNumber(0);


        Observer<Integer> recipeStepObserver = new Observer<Integer>() {
            int i = 0;

            @Override
            public void onChanged(@Nullable final Integer stepNumber) {
                //this.stepPosition=stepPosition;
                String stepNumberAsString=exoplayerViewModel.getStepNumber().getValue().toString();
                Timber.i("stepNumberAsString= "+stepNumberAsString);

                Timber.i("onChanged in setupExoplayerViewModel called stepNumber= "+stepNumber);


                if(isTablet){

                    Timber.i("setUpExoplayerViewModel called then initializeExoPlayer method called");
                    initializeExoPlayer();
                    //update Exoplayer Fragment
                }
                else{

                    Intent exoPlayerActivityIntent=new Intent(ExoplayerActivity.this, ExoplayerActivity.class);
                    exoPlayerActivityIntent.putExtra(RECIPE_POSITION, recipePosition);
                    //exoPlayerActivityIntent.putExtra()
                    startActivity(exoPlayerActivityIntent);
                    //start new activity with exoplayer fragment full screen

                }

                Timber.i("viewmodel onChanged called position= %s", stepNumber);
            }
        };

        exoplayerViewModel.getStepNumber().observe(this, recipeStepObserver);
    }

    public void initializeExoPlayer(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(fragmentManager.getBackStackEntryCount()==0) {
//            FragmentManager fragmentManager = getSupportFragmentManager();
            ExoplayerFragment exoplayerFragment = new ExoplayerFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.frame_layout_exoPlayer, exoplayerFragment)
                    .addToBackStack(EXOPLAYER_FRAGMENT)
                    .commit();
        }
        else{
            ExoplayerFragment exoplayerFragment = new ExoplayerFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_layout_exoPlayer, exoplayerFragment)
                    .addToBackStack(EXOPLAYER_FRAGMENT)
                    .commit();
        }

    }


//    @Override
//    public void backButtonPressed() {
//        tellFragments();
//        super.onBackPressed();
//    }
//
//    private void tellFragments(){
//        List<Fragment> fragments = getSupportFragmentManager().getFragments();
//        for(Fragment fragment : fragments){
//            if(fragment instanceof BackButtonPressed)
//                ((BackButtonPressed)fragment).backButtonPressed();
//        }
//    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        int fragmentStack=fragmentManager.getBackStackEntryCount();

        if(fragmentStack==0){
            super.onBackPressed();
        }
        else{
            Timber.i("exoplayer backStackfragmentManger count="+fragmentManager.getBackStackEntryCount());
            fragmentManager.popBackStack ();
            super.onBackPressed();
        }
    }


//    @Override
//    public void previousButtonClicked(int currentStep){
//        Timber.i("nextButtonClicked called currentStep= "+currentStep);
//        SharedPreferences positionSharedPreferences=this.getApplicationContext().getSharedPreferences(RECIPE_STEP_POSITION_PREFERENCE_FILE, MODE_PRIVATE);
//        SharedPreferences.Editor editor = positionSharedPreferences.edit();
//        //editor.putInt(RECIPE_POSITION, 0);
//        if(currentStep==0){
//            editor.putInt(STEP_POSITION, currentStep);
//        }
//        else{
//            editor.putInt(STEP_POSITION, currentStep-1);
//        }
//
//        editor.apply();
//
//        initializeExoPlayer();
//        //initializeReplaceExoPlayer();
//    }
//
//    //need to make sure doesn't go over amount of steps
//
//    @Override
//    public void nextButtonClicked(int currentStep) {
//        Timber.i("nextButtonClicked called currentStep= "+currentStep);
//
//        JSONUtility jsonUtility=JSONUtility.createJSONUtilityInstance();
//        int stepSize=jsonUtility.getRecipeStepsPojo(recipePosition).size();
//
//        if(currentStep<stepSize-1) {
//            Timber.i("nextButtonClicked called currentStep<stepSize-1");
//
//            SharedPreferences positionSharedPreferences = this.getApplicationContext().getSharedPreferences(RECIPE_STEP_POSITION_PREFERENCE_FILE, MODE_PRIVATE);
//            SharedPreferences.Editor editor = positionSharedPreferences.edit();
//            //editor.putInt(RECIPE_POSITION, 0);
//            editor.putInt(STEP_POSITION, currentStep + 1);
//            editor.apply();
//        }
//        //go to first step if reached end
//        else if(currentStep==stepSize-1){
//            Timber.i("nextButtonClicked called currentStep==stepSize-1");
//            SharedPreferences positionSharedPreferences = this.getApplicationContext().getSharedPreferences(RECIPE_STEP_POSITION_PREFERENCE_FILE, MODE_PRIVATE);
//            SharedPreferences.Editor editor = positionSharedPreferences.edit();
//            //editor.putInt(RECIPE_POSITION, 0);
//            editor.putInt(STEP_POSITION, 0);
//            editor.apply();
//        }

//        SharedPreferences positionSharedPreferences=this.getApplicationContext().getSharedPreferences(RECIPE_STEP_POSITION_PREFERENCE_FILE, MODE_PRIVATE);
//        SharedPreferences.Editor editor = positionSharedPreferences.edit();
//        //editor.putInt(RECIPE_POSITION, 0);
//        editor.putInt(STEP_POSITION, currentStep+1);
//        editor.apply();

        //initializeExoPlayer();
        //initializeReplaceExoPlayer();
    //}

//    public void initializeReplaceExoPlayer(){
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        if(fragmentManager==null){
//            Timber.i("initializeReplaceExoPlayer called fragmentManager equals null");
//        }
//        ExoplayerFragment exoplayerFragment = new ExoplayerFragment(this, this);
//        if(fragmentManager.getBackStackEntryCount()==0) {
//            fragmentManager.beginTransaction()
//                    .add(R.id.frame_layout_exoPlayer, exoplayerFragment)
//                    .addToBackStack(EXOPLAYER_FRAGMENT)
//                    .commit();
//        }
//        else{
//            fragmentManager.beginTransaction()
//                    .replace(R.id.frame_layout_exoPlayer, exoplayerFragment)
//                    .addToBackStack(EXOPLAYER_FRAGMENT)
//                    .commit();
//        }
//    }
}
