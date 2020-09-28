package com.example.bakingapp.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.bakingapp.JSONUtility;
import com.example.bakingapp.Pojos.StepPojo;
import com.example.bakingapp.R;
import com.example.bakingapp.RecipeIngredientsSteps;
import com.example.bakingapp.ViewModels.ExoplayerViewModel;
import com.example.bakingapp.ViewModels.ExoplayerViewModelFactory;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import timber.log.Timber;

import static com.example.bakingapp.Constants.RECIPE_POSITION;
import static com.example.bakingapp.Constants.RECIPE_STEP_POSITION_PREFERENCE_FILE;
import static com.example.bakingapp.Constants.STEP_CURRENT_VIDEO_POSITION;
import static com.example.bakingapp.Constants.STEP_PLAY_WHEN_READY;
import static com.example.bakingapp.Constants.STEP_PLAY_WINDOW_INDEX;
import static com.example.bakingapp.Constants.STEP_POSITION;

//public class ExoplayerFragment extends Fragment implements BackButtonPressed {
public class ExoplayerFragment extends Fragment {

    PlayerView exoPlayerView;
    TextView stepDescription;
    ImageView imageViewPlaceholder;
    Button previousButton;
    Button nextButton;

    SimpleExoPlayer simpleExoPlayer;

    JSONUtility jsonUtility;

    StepPojo stepPojo;
    String shortDescriptionString;
    String descriptionString;
    Uri stepVideoUri;
    boolean stepPlayWhenReady = true;
    //allows to seek to position in video
    long exoPlayerCurrentPositionIndex;

    int exoPlayerWindowIndex;

    int recipePosition;
    int stepPosition;

    ExoplayerViewModel exoplayerViewModel;

    View rootView;


    NextButtonClicked nextButtonClickedCallback;

    public void setOnNextButtonClickedListener(NextButtonClicked nextButtonClickedCallback) {
        this.nextButtonClickedCallback = nextButtonClickedCallback;
    }

    public interface NextButtonClicked {
        public void nextButtonClicked(int currentStep);
    }


    public ExoplayerFragment() {
    }

    //PreviousButtonClicked previousButtonClickedCallback;

//    public void setOnPreviousButtonClickedListener(PreviousButtonClicked previousButtonClickedCallback){
//        this.previousButtonClickedCallback=previousButtonClickedCallback;
//    }

//    public interface PreviousButtonClicked{
//        public void previousButtonClicked(int currentStep);
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRetainInstance(true);

        ExoplayerViewModelFactory exoplayerViewModelFactory=new ExoplayerViewModelFactory(getActivity().getApplication());
        exoplayerViewModel = ViewModelProviders.of(getActivity(), exoplayerViewModelFactory).get(ExoplayerViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Timber.i("ExoPlayerFragment onCreateView called");
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.exoplayer_fragment, container, false);

        setUpViews();

        setUpPreviousButton();
        setUpNextButton();

        // Check if there is any state saved
        if (savedInstanceState != null) {

            showExoplayer();

            Timber.i("ExoPlayerFragment savedInstance state does not equal null");

            recipePosition = savedInstanceState.getInt(RECIPE_POSITION);
            stepPosition = savedInstanceState.getInt(STEP_POSITION);

            JSONUtility jsonUtility = JSONUtility.createJSONUtilityInstance();
            stepPojo = jsonUtility.getStep(recipePosition, stepPosition);

            if(stepPojo.getShortDescription().isEmpty()){
                shortDescriptionString="";
            }
            else{
                shortDescriptionString=stepPojo.getShortDescription();
            }

            if(stepPojo.getDescription().isEmpty()){
                descriptionString="";
            }
            else{
                descriptionString=stepPojo.getDescription();
            }

            stepVideoUri = Uri.parse(stepPojo.getVideoURL());

//            mStep = savedInstanceState.getParcelable(STEP_SINGLE);
            stepPlayWhenReady = savedInstanceState.getBoolean(STEP_PLAY_WHEN_READY);
            exoPlayerCurrentPositionIndex = savedInstanceState.getLong(STEP_CURRENT_VIDEO_POSITION);
            exoPlayerWindowIndex = savedInstanceState.getInt(STEP_PLAY_WINDOW_INDEX);
//            mVideoUri = Uri.parse(savedInstanceState.getString(STEP_URI));
        }
        // If there is no saved state getArguments from CookingActivity
        else {
            showExoplayer();

            Timber.i("ExoplayerFragment savedInstanceState equals null");
           // if (getArguments() != null) {
            SharedPreferences recipeStepsPositionSharedPreferences = this.getActivity().getSharedPreferences(RECIPE_STEP_POSITION_PREFERENCE_FILE, Context.MODE_PRIVATE);
            recipePosition=recipeStepsPositionSharedPreferences.getInt(RECIPE_POSITION, 0);
            stepPosition=recipeStepsPositionSharedPreferences.getInt(STEP_POSITION,0);
            stepPlayWhenReady=true;

            jsonUtility = JSONUtility.createJSONUtilityInstance();
            stepPojo = jsonUtility.getStep(recipePosition, stepPosition);


//            SharedPreferences positionSharedPreferences=this.getApplicationContext().getSharedPreferences(RECIPE_STEP_POSITION_PREFERENCE_FILE, MODE_PRIVATE);
//        SharedPreferences.Editor editor = positionSharedPreferences.edit();
//        editor.putInt(RECIPE_POSITION, position);
//        editor.putInt(STEP_POSITION, 0);
//        editor.apply();

//                Intent intent = getActivity().getIntent();
//                if(intent.hasExtra(RECIPE_POSITION)&& intent.hasExtra(STEP_POSITION)){
//                    recipePosition=intent.getIntExtra(RECIPE_POSITION,0);
//                    stepPosition=intent.getIntExtra(STEP_POSITION,0);
//                }

                if(stepPojo.getShortDescription().isEmpty()){
                    shortDescriptionString="";
                }
                else{
                    shortDescriptionString=stepPojo.getShortDescription();
                }

                if(stepPojo.getDescription().isEmpty()){
                    descriptionString="";
                }
                else{
                    descriptionString=stepPojo.getDescription();
                }

                stepVideoUri = Uri.parse(stepPojo.getVideoURL());
                Timber.i("stepVideoUri= "+stepVideoUri);

                exoPlayerCurrentPositionIndex=0;

                //showExoplayer();


//                // Get arguments
//                mStep = getArguments().getParcelable(ConstantsUtil.STEP_SINGLE);


                // If has no video
                if (stepVideoUri.toString().equals("")){
                    exoPlayerView.setUseController(false);
                    showPlaceholder();
                    exoPlayerView.setUseArtwork(true);
                 }
                else{
                    showExoplayer();
                    exoPlayerView.setUseController(true);
                    exoPlayerView.setUseArtwork(false);
                }

                if(previousButton!=null && nextButton!=null){
                    
                }

                    //showPlaceholder();


        }


//        stepTitleTv.setText(stepPojo.getShortDescription());
//        stepDescription.setText(stepPojo.getDescription());


        return rootView;
    }



    public void setUpViews(){
//        stepTitleTv=rootView.findViewById(R.id.step_title);
        exoPlayerView=rootView.findViewById(R.id.exoplayer_video_view);
        stepDescription=rootView.findViewById(R.id.step_description_tv);
        imageViewPlaceholder=rootView.findViewById(R.id.image_placeholder);
        previousButton=rootView.findViewById(R.id.previous_step_button);
        nextButton=rootView.findViewById(R.id.next_step_button);
    }

    public void setUpPreviousButton(){
        if(previousButton!=null){
            previousButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //previousButtonClickedCallback.previousButtonClicked(stepPosition);
                    //exoplayerViewModel.setStepNumber(stepPosition);
                    exoplayerViewModel.decreaseStep();
                }
            });
        }
    }

    public void setUpNextButton(){
        if(nextButton!=null){
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Timber.i("setUpNextButton called stepPosition= "+stepPosition);
                    //nextButtonClickedCallback.nextButtonClicked(stepPosition);
                    //exoplayerViewModel.setStepNumber(stepPosition);
                    exoplayerViewModel.increaseStep();
                }
            });
        }
    }


    // Initialize exoplayer
//    public void createExoPlayer(Uri videoUri){
    public void createExoPlayer(){
        if (simpleExoPlayer == null) {
//            stepTitleTv.setText(stepPojo.getShortDescription());
            if(stepDescription!=null){
                stepDescription.setText(stepPojo.getDescription());
            }

            Timber.i("simpleExoPlayer equals null");
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(),
                    new DefaultTrackSelector(),
                    new DefaultLoadControl());
            exoPlayerView.setPlayer(simpleExoPlayer);

            //Uri mediaUri = stepVideoUri;

//            if(stepVideoUri.toString().isEmpty())
//            Timber.i("stepVideoUri is an empty string");
//            Timber.i("stepVideoThumbnail is an empty string");

            Timber.i("createExoPlayer called stepVideoUri= "+stepVideoUri);

            if (!stepVideoUri.toString().equals("")){


                Timber.i("stepVideoUri= %s", stepVideoUri);
                DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), getString(R.string.app_name)));
                MediaSource videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(stepVideoUri);

                simpleExoPlayer.prepare(videoSource);
                simpleExoPlayer.setPlayWhenReady(stepPlayWhenReady);
                showExoplayer();
//                exoPlayerView.setVisibility(View.VISIBLE);
//                imageViewPlaceholder.setVisibility(View.INVISIBLE);
            }

            //createExoPlayer(stepVideoUri);
            //showExoplayer();

        }

//        else{
////            exoPlayerView.setVisibility(View.GONE);
//            showPlaceholder();
//        }

//         else if (stepVideoUri.toString().isEmpty()) {
//            exoPlayerView.setVisibility(View.GONE);
//        }
//         else{
//            exoPlayerView.setPlayer(simpleExoPlayer);
//
//            Uri mediaUri = stepVideoUri;
//            Timber.i("mediaUri= %s", mediaUri);
//            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), getString(R.string.app_name)));
//            MediaSource videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(mediaUri);
//
//            simpleExoPlayer.setPlayWhenReady(stepPlayWhenReady);
//
//            simpleExoPlayer.prepare(videoSource);
//            exoPlayerView.setVisibility(View.VISIBLE);
//            imageViewPlaceholder.setVisibility(View.INVISIBLE);
//        }
    }


//    public void createExoPlayer(Uri videoUri){
//
//        stepTitleTv.setText(shortDescriptionString);
//        stepDescription.setText(descriptionString);
//
//
//        if(simpleExoPlayer == null){
//            Timber.i("simpleExoPlayer equals null");
//
//            if(getActivity()!=null){
//                Timber.i("getActivity does not equal null");
//            }
//
//            simpleExoPlayer=  ExoPlayerFactory.newSimpleInstance(getActivity(),
//                    new DefaultTrackSelector(),
//                    new DefaultLoadControl());
//
//            // Bind exoPlayer to view.
//            exoPlayerView.setPlayer(simpleExoPlayer);
//
//            // Prepare the MediaSource.
//            String userAgent = Util.getUserAgent(getActivity(), getString(R.string.app_name));
//            MediaSource mediaSource = new ExtractorMediaSource(stepVideoUri,
//                    new DefaultDataSourceFactory(getActivity(), userAgent),
//                    new DefaultExtractorsFactory(),
//                    null,
//                    null);
//
//            //make sure the position is a valid time unit
//            if (exoPlayerCurrentPositionIndex != C.TIME_UNSET) {
//                simpleExoPlayer.seekTo(exoPlayerCurrentPositionIndex);
//            }
//            // Prepare the player with the source.
//            simpleExoPlayer.prepare(mediaSource);
//            simpleExoPlayer.setPlayWhenReady(stepPlayWhenReady);
//        }
//    }

    // Release player
    private void releasePlayer() {
        if (simpleExoPlayer != null) {
            saveVideoStartPosition();
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            createExoPlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || simpleExoPlayer == null) {
            createExoPlayer();
        }
        if(simpleExoPlayer != null){
            simpleExoPlayer.setPlayWhenReady(stepPlayWhenReady);
            simpleExoPlayer.seekTo(exoPlayerCurrentPositionIndex);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(simpleExoPlayer != null){
            saveVideoStartPosition();
            if (Util.SDK_INT <= 23) {
                releasePlayer();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(simpleExoPlayer != null){
            saveVideoStartPosition();
            if (Util.SDK_INT > 23) {
                releasePlayer();
            }
        }
    }

    public void showExoplayer(){
        imageViewPlaceholder.setVisibility(View.GONE);
        exoPlayerView.setVisibility(View.VISIBLE);
    }

    public void showPlaceholder(){
        imageViewPlaceholder.setVisibility(View.VISIBLE);
        exoPlayerView.setVisibility(View.GONE);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        saveVideoStartPosition();
        outState.putInt(RECIPE_POSITION, recipePosition);
        outState.putInt(STEP_POSITION, stepPosition);

//        outState.putString(STEP_URI, step.getVideoURL());
//        outState.putParcelable(STEP_SINGLE, step);
        outState.putLong(STEP_CURRENT_VIDEO_POSITION, exoPlayerCurrentPositionIndex);
        outState.putBoolean(STEP_PLAY_WHEN_READY, stepPlayWhenReady);
    }

    private void saveVideoStartPosition() {
        if (simpleExoPlayer != null) {
            stepPlayWhenReady = simpleExoPlayer.getPlayWhenReady();
            exoPlayerWindowIndex = simpleExoPlayer.getCurrentWindowIndex();
            exoPlayerCurrentPositionIndex = simpleExoPlayer.getCurrentPosition();
        }
    }

//    @Override
//    public void backButtonPressed() {
//        FragmentManager fragmentManager = getActivity()
//                .getSupportFragmentManager();
//        Timber.i("exoplayer backStackfragmentManger count="+fragmentManager.getBackStackEntryCount());
//        fragmentManager.popBackStack (EXOPLAYER_FRAGMENT, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//    }

}