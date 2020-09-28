package com.example.bakingapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.BackButtonPressed;
import com.example.bakingapp.Adapters.StepLinearAdapter;
import com.example.bakingapp.Fragments.ExoplayerFragment;
import com.example.bakingapp.Fragments.MasterIngredientsStepsFragment;
import com.example.bakingapp.ViewModels.ExoplayerViewModel;
import com.example.bakingapp.ViewModels.ExoplayerViewModelFactory;
import com.example.bakingapp.ViewModels.RecipeViewModel;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

import static com.example.bakingapp.Constants.EXOPLAYER_FRAGMENT;
import static com.example.bakingapp.Constants.EXOPLAYER_FRAGMENT_STACK_COUNT;
import static com.example.bakingapp.Constants.IS_TABLET;
import static com.example.bakingapp.Constants.MASTER_INGREDIENTS_STEPS_FRAGMENT;
import static com.example.bakingapp.Constants.RECIPE_NAME_FRAGMENT;
import static com.example.bakingapp.Constants.RECIPE_POSITION;
import static com.example.bakingapp.Constants.RECIPE_STEP_POSITION_PREFERENCE_FILE;
import static com.example.bakingapp.Constants.STEP_POSITION;

//public class RecipeIngredientsSteps extends AppCompatActivity implements StepLinearAdapter.OnRecipeStepListener,
//        BackButtonPressed{
//public class RecipeIngredientsSteps extends AppCompatActivity implements StepLinearAdapter.OnRecipeStepListener
//        , ExoplayerFragment.NextButtonClicked, ExoplayerFragment.PreviousButtonClicked{
public class RecipeIngredientsSteps extends AppCompatActivity implements StepLinearAdapter.OnRecipeStepListener{

    RecipeViewModel recipeViewModel;
    int recipePosition;
    int stepPosition;
    boolean isTablet;
    Bundle savedInstanceState;
    FragmentManager fragmentManager;

    MasterIngredientsStepsFragment masterIngredientsStepsFragment;
    int exoPlayerFragmentCount=0;

    ExoplayerViewModel exoplayerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState=savedInstanceState;
        setContentView(R.layout.recipe_ingredients_steps);

        setUpExoplayerViewModel();

        Timber.i("onCreate called in Activity");

        if (findViewById(R.id.frame_layout_exoPlayer) != null) {
            isTablet=true;
            Toast.makeText(this, "recipe_ingredients_steps tablet view", Toast.LENGTH_SHORT).show();
        }
        else{
            isTablet=false;
        }

        Intent intent = getIntent();

        recipePosition = intent.getIntExtra(RECIPE_POSITION, 0);
        //isTablet = intent.getBooleanExtra(IS_TABLET, false);

        //setUpViewModel();
        if(savedInstanceState==null) {
            Timber.i("savedInstanceState equals null");
            setUpFragments();
        }
        else{
            Timber.i("savedInstanceState isn't equal to null");

        }

    }

    public void setUpExoplayerViewModel(){
        ExoplayerViewModelFactory exoplayerViewModelFactory=new ExoplayerViewModelFactory(getApplication());
        exoplayerViewModel = ViewModelProviders.of(this, exoplayerViewModelFactory).get(ExoplayerViewModel.class);

//        exoplayerViewModel.setStepNumber(0);

        Observer<Integer> recipeStepObserver = new Observer<Integer>() {
            int i = 0;

            @Override
            public void onChanged(@Nullable final Integer stepNumber) {
                i++;
                //this.stepPosition=stepPosition;

                Timber.i("onChanged in setupExoplayerViewModel called stepNumber= "+stepNumber);
                stepPosition=stepNumber;

                if(isTablet){

                    Timber.i("setUpExoplayerViewModel called then initializeExoPlayer method called");
                    initializeExoPlayer();
                    //update Exoplayer Fragment
                }
                else{

                    if(i>1) {
                        Intent exoPlayerActivityIntent = new Intent(RecipeIngredientsSteps.this, ExoplayerActivity.class);
                        exoPlayerActivityIntent.putExtra(RECIPE_POSITION, recipePosition);
                        //exoPlayerActivityIntent.putExtra()
                        startActivity(exoPlayerActivityIntent);
                        //start new activity with exoplayer fragment full screen
                    }

                }

                Timber.i("viewmodel onChanged called position= %s", stepPosition);
            }
        };

        exoplayerViewModel.getStepNumber().observe(this, recipeStepObserver);
    }


    public void setUpFragments() {
//        if(savedInstanceState==null) {


            Timber.i("setUpFragments called");

            Timber.i("setUpFragments recipe position"+recipePosition);
            Timber.i("setUpFragments isTablet= "+Boolean.toString(isTablet));


            //if(isTablet){

            Bundle recipeBundle = new Bundle();
            recipeBundle.putInt(RECIPE_POSITION, recipePosition);
            recipeBundle.putBoolean(IS_TABLET, isTablet);

            masterIngredientsStepsFragment = new MasterIngredientsStepsFragment();
            masterIngredientsStepsFragment.setArguments(recipeBundle);

            Timber.i("setUpFragments before beginning fragment transaction");
            fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_layout_list, masterIngredientsStepsFragment)
//                    .addToBackStack(MASTER_INGREDIENTS_STEPS_FRAGMENT)
                    .commit();


//        }
//        else{
//            fragmentManager.beginTransaction()
//                    .replace(R.id.frame_layout_list, masterIngredientsStepsFragment)
//                    .addToBackStack(MASTER_INGREDIENTS_STEPS_FRAGMENT)
//                    .commit();
//        }

        if(isTablet){
            initializeExoPlayer();
        }
    }

    @Override
    public void onStepClick(int stepPosition) {
        Timber.i("onStepClick method called position= %s", stepPosition);

        SharedPreferences positionSharedPreferences=this.getApplicationContext().getSharedPreferences(RECIPE_STEP_POSITION_PREFERENCE_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = positionSharedPreferences.edit();
        //editor.putInt(RECIPE_POSITION, 0);
        editor.putInt(STEP_POSITION, stepPosition);
        editor.apply();

//        exoplayerViewModel.setStepNumber(stepPosition);


        if(isTablet){
            //Need to find recipe position


            initializeExoPlayer();

            //maintain in same fragment start exoplayer fragment here
        }
        else{
            //initializeExoPlayer();

            new ExoplayerActivity();
            Intent exoPlayerActivityIntent=new Intent(this, ExoplayerActivity.class);
            exoPlayerActivityIntent.putExtra(RECIPE_POSITION, recipePosition);
            //exoPlayerActivityIntent.putExtra()
            startActivity(exoPlayerActivityIntent);
            //start new activity
        }

    }

    public void initializeExoPlayer() {
        Timber.i("initializeExoPlayer called");

//        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager = getSupportFragmentManager();
        if(fragmentManager.getBackStackEntryCount()==0){
            Timber.i("initializeExoPlayer called fragment back stack equals 0");
            ExoplayerFragment exoplayerFragment = new ExoplayerFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.frame_layout_exoPlayer, exoplayerFragment)
                    .addToBackStack(EXOPLAYER_FRAGMENT)
                    .commit();

        }
        else {
            ExoplayerFragment exoplayerFragment = new ExoplayerFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_layout_exoPlayer, exoplayerFragment)
                    .addToBackStack(EXOPLAYER_FRAGMENT)
                    .commit();
        }
    }
//        SharedPreferences positionSharedPreferences=this.getApplicationContext().getSharedPreferences(RECIPE_STEP_POSITION_PREFERENCE_FILE, MODE_PRIVATE);
//
//
//        SharedPreferences.Editor editor = positionSharedPreferences.edit();
//        //editor.putInt(RECIPE_POSITION, 0);
//        editor.putInt(EXOPLAYER_FRAGMENT_STACK_COUNT, 0);
//        editor.apply();
//        exoPlayerFragmentCount++;

//        if(savedInstanceState==null) {
//
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            ExoplayerFragment exoplayerFragment = new ExoplayerFragment(this);
//            fragmentManager.beginTransaction()
//                    .replace(R.id.frame_layout_exoPlayer, exoplayerFragment)
////                    .addToBackStack(EXOPLAYER_FRAGMENT)
//                    .commit();
//        }
//        else{
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            ExoplayerFragment exoplayerFragment = new ExoplayerFragment(this);
//            fragmentManager.beginTransaction()
//                    .replace(R.id.frame_layout_exoPlayer, exoplayerFragment)
////                    .addToBackStack(EXOPLAYER_FRAGMENT)
//                    .commit();
//        }



//    @Override
//    public void onAttachFragment(Fragment fragment) {
//        if (fragment instanceof ExoplayerFragment) {
//            ExoplayerFragment exoplayerFragment = (ExoplayerFragment) fragment;
//            exoplayerFragment.setOnNextButtonClickedListener(this);
//        }
//    }

//only called on tablet


//    @Override
//    public void previousButtonClicked(int currentStep) {
//        Timber.i("previousButtonClicked called currentStep= " + currentStep);
//        SharedPreferences positionSharedPreferences=this.getApplicationContext().getSharedPreferences(RECIPE_STEP_POSITION_PREFERENCE_FILE, MODE_PRIVATE);
//        SharedPreferences.Editor editor = positionSharedPreferences.edit();
//        //editor.putInt(RECIPE_POSITION, 0);
//        if(currentStep==0){
//            editor.putInt(STEP_POSITION, currentStep);
//        }
//        else{
//            editor.putInt(STEP_POSITION, currentStep-1);
//        }
//        editor.apply();

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        ExoplayerFragment exoplayerFragment = new ExoplayerFragment(this, this);
//        fragmentManager.beginTransaction()
//                .replace(R.id.frame_layout_exoPlayer, exoplayerFragment)
////                    .addToBackStack(EXOPLAYER_FRAGMENT)
//                .commit();

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager = getSupportFragmentManager();
//        Timber.i("fragment back stack= "+fragmentManager.getBackStackEntryCount());
//        if(fragmentManager.getBackStackEntryCount()==0 || savedInstanceState==null){
//            Timber.i("pb fragment back stack equals zero or savedInstanceState equals null");
//            ExoplayerFragment exoplayerFragment = new ExoplayerFragment(this, this);
//            fragmentManager.beginTransaction()
//                    .add(R.id.frame_layout_exoPlayer, exoplayerFragment)
//                    .addToBackStack(EXOPLAYER_FRAGMENT)
//                    .commit();
//        }
//        else {
//            ExoplayerFragment exoplayerFragment = new ExoplayerFragment(this, this);
//            fragmentManager.beginTransaction()
//                    .replace(R.id.frame_layout_exoPlayer, exoplayerFragment)
//                    .addToBackStack(EXOPLAYER_FRAGMENT)
//                    .commit();
//        }
//    }

//    @Override
//    public void nextButtonClicked(int currentStep) {
//        Timber.i("nextButtonClicked called currentStep= " + currentStep);
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

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager = getSupportFragmentManager();
//        Timber.i("fragment back stack before pop= "+fragmentManager.getBackStackEntryCount());
//        if(fragmentManager.getBackStackEntryCount()>0){
//            fragmentManager.popBackStack();
//            fragmentManager.popBackStack();
//        }
//        Timber.i("fragment back stack after pop= "+fragmentManager.getBackStackEntryCount());
//        if(fragmentManager.getBackStackEntryCount()==0 || savedInstanceState==null){
//            Timber.i("nb fragment back stack equals zero or savedInstanceState equals null");
//            ExoplayerFragment exoplayerFragment = new ExoplayerFragment(this, this);
//            fragmentManager.beginTransaction()
//                    .add(R.id.frame_layout_exoPlayer, exoplayerFragment)
//                    .addToBackStack(EXOPLAYER_FRAGMENT)
//                    .commit();
//        }
//        else {
//
//            ExoplayerFragment exoplayerFragment = new ExoplayerFragment(this, this);
//            fragmentManager.beginTransaction()
//                    .replace(R.id.frame_layout_exoPlayer, exoplayerFragment)
//                    .addToBackStack(EXOPLAYER_FRAGMENT)
//                    .commit();
//        }
//    }

//    @Override
//    public void onBackPressed() {
//        FragmentManager fragmentManager = this.getSupportFragmentManager();
//        int fragmentStack=fragmentManager.getBackStackEntryCount();
//
//        if(fragmentStack==0){
//            super.onBackPressed();
//        }
//        else{
//            Timber.i("exoplayer backStackfragmentManger count="+fragmentManager.getBackStackEntryCount());
//            if(isTablet){
//                fragmentManager.popBackStack();
//                fragmentManager.popBackStack();
//                super.onBackPressed();
//            }
//            else {
//                fragmentManager.popBackStack();
//                super.onBackPressed();
//            }
//        }
//
//    }



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
}


//            fragmentManager.beginTransaction()
//                    .replace(R.id.list_fragment, masterIngredientsStepsFragment)
//                    .addToBackStack(MASTER_INGREDIENTS_STEPS_FRAGMENT)
//                    .commit();

//        fragmentManager.beginTransaction()
//                .replace(R.id.list_fragment, masterIngredientsStepsFragment)
//                .addToBackStack(MASTER_INGREDIENTS_STEPS_FRAGMENT)
//                .commit();



//    public void setUpViewModelOnChanged(){
//
//        Observer<List<Result>> observer=new Observer<List<Result>>() {
//            int i=0;
//            @Override
//            public void onChanged(@Nullable final List<Result> movies) {
//                i=movies.size();
//                // Update the cached copy of the words in the adapter.
//                Log.i("MainActivity","onChanged triggered");
//                if(i>0){
//                    resultList=movies;
//                    setUpGridAdapter();
//                }
////                i++;
////                //mainViewModel.requestMovies();
////                resultList=movies;
////                if(mainViewModel.getAllMovies().getValue()!=null){
////                    mainViewModel.requestMovies();
////                }
//            }
//        };
//
//        mainActivityViewModel.getAllMovies().observe(this,observer);
//    }
//}