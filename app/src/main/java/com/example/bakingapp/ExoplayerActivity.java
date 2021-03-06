package com.example.bakingapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.bakingapp.Fragments.ExoplayerFragment;
import com.example.bakingapp.ViewModels.ExoplayerViewModel;
import com.example.bakingapp.ViewModels.ExoplayerViewModelFactory;


import timber.log.Timber;

import static com.example.bakingapp.Constants.EXOPLAYER_FRAGMENT;
import static com.example.bakingapp.Constants.RECIPE_POSITION;

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
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

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
//            fragmentManager.popBackStack ();
//            super.onBackPressed();
//        }
//    }

}
