package com.example.bakingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.test.espresso.IdlingResource;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bakingapp.Adapters.RecipeNameAdapter;
import com.example.bakingapp.Adapters.StepLinearAdapter;
import com.example.bakingapp.Fragments.MasterIngredientsStepsFragment;
import com.example.bakingapp.Fragments.RecipeListNameFragment;
import com.example.bakingapp.Fragments.RecipleNameGridListFragment;
import com.example.bakingapp.Pojos.RecipePojo;
import com.example.bakingapp.ViewModels.MainActivityViewModel;
import com.example.bakingapp.ViewModels.MainActivityViewModelFactory;
import com.example.bakingapp.Widget.BakingWidgetService;

import java.util.List;

import timber.log.Timber;

import static com.example.bakingapp.Constants.ACTION_INTENT_OPEN_RECIPE;
import static com.example.bakingapp.Constants.RECIPE_POSITION;
import static com.example.bakingapp.Constants.RECIPE_STEP_POSITION_PREFERENCE_FILE;
import static com.example.bakingapp.Constants.STEP_POSITION;

//import com.example.bakingapp.Repositories.RecipeRepository;

//import com.example.bakingapp.Fragments.RecipleNameGridListFragment;


//public class MainActivity extends AppCompatActivity implements RecipleNameGridListFragment.OnRecipeTitleClickListener {
//public class MainActivity extends AppCompatActivity implements RetrofitRequester.OnRetrofitListener{
//public class MainActivity extends AppCompatActivity implements RecipeNameAdapter.OnRecipeTitleListener,
//        StepLinearAdapter.OnRecipeStepListener, BackButtonPressed {
public class MainActivity extends AppCompatActivity implements RecipeNameAdapter.OnRecipeTitleListener,
        StepLinearAdapter.OnRecipeStepListener,  NetworkDelayer.NetworkCallback {

    public static final String TAG=MainActivity.class.getSimpleName();

    private MainActivityViewModel mainActivityViewModel;

    private boolean gridViewTablet;

    private List<RecipePojo>recipeList;

    ProgressBar progressBar;
    View recipesFragment;
    RecipleNameGridListFragment recipleNameGridListFragment;
    RecipeListNameFragment recipeListNameFragment;
    MasterIngredientsStepsFragment masterIngredientsStepsFragment;
   // ExoplayerFragment exoplayerFragment exoplayerFragment;

    SharedPreferences positionSharedPreferences;
    Bundle savedInstanceState;

    // Idling resource only used for testing
    @Nullable private BasicIdlingResource idlingResourceForTesting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState=savedInstanceState;
        setContentView(R.layout.activity_main);

        positionSharedPreferences=this.getApplicationContext().getSharedPreferences(RECIPE_STEP_POSITION_PREFERENCE_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = positionSharedPreferences.edit();
        editor.putInt(RECIPE_POSITION, 0);
        editor.putInt(STEP_POSITION, 0);
        editor.apply();

        //initializing Timber for project
        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }

        //new RetrofitRequester().requestRecipes(this);

        progressBar=findViewById(R.id.loading_progress_bar);
        progressBar.setVisibility(View.VISIBLE);

      //  RecipeRepository recipeRepository= RecipeRepository.getInstance(getApplication());
        MainActivityViewModelFactory mainActivityViewModelFactory= new MainActivityViewModelFactory(getApplication());

//        recipesFragment=findViewById(R.id.list_fragment);


        mainActivityViewModel = ViewModelProviders.of(this, mainActivityViewModelFactory).get(MainActivityViewModel.class);
        setUpViewModelOnChanged();

        if(mainActivityViewModel.recipesPojoList!=null){
            Timber.i("initializeFragments called");
            initializeFragments();
        }


        //setUpViewRecipeStepsViewModel();

//        initializeFragments();
    }


    public void setUpViewModelOnChanged(){
        Observer<List<RecipePojo>> observer=new Observer<List<RecipePojo>>() {
            int i=0;
            @Override
            public void onChanged(@Nullable final List<RecipePojo> recipes) {
//                i=recipes.size();
                // Update the cached copy of the words in the adapter.
                Log.i("MainActivity","onChanged triggered");
                if(recipes!=null){
                    recipeList=recipes;
                    Log.i(TAG, "recipes received in onchanged");
                    JSONUtility jsonUtility= JSONUtility.createJSONUtilityInstance();
                    jsonUtility.storeRecipeDataInSharedPreferences(MainActivity.this, recipeList);

                    if(recipleNameGridListFragment==null){
                        if(savedInstanceState == null) {
                            Timber.i("initializeFragments method called");
                            initializeFragments();
                        }
                    }
                    //setUpGridAdapter();
                }
            }
        };

        mainActivityViewModel.getAllRecipes().observe(this,observer);
    }


    public void initializeFragments() {
        if (findViewById(R.id.grid_fragment) != null) {
            // This LinearLayout will only initially exist in the two-pane tablet case
            gridViewTablet = true;

//            recipesFragment=findViewById(R.id.grid_list_fragment);

            FragmentManager fragmentManager = getSupportFragmentManager();

            recipleNameGridListFragment = new RecipleNameGridListFragment();
            // Add the fragment to its container using a transaction
            fragmentManager.beginTransaction()
                    .add(R.id.grid_fragment, recipleNameGridListFragment)
//                    .addToBackStack(RECIPE_NAME_FRAGMENT)
                    .commit();
        }
        else{
            gridViewTablet=false;

            FragmentManager fragmentManager = getSupportFragmentManager();

            recipeListNameFragment = new RecipeListNameFragment();
            // Add the fragment to its container using a transaction
            fragmentManager.beginTransaction()
                    .add(R.id.list_fragment, recipeListNameFragment)
                    //.addToBackStack(RECIPE_NAME_FRAGMENT)
                    .commit();
        }
    }

    //Only used for testing idling resource
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (idlingResourceForTesting == null) {
            idlingResourceForTesting = new BasicIdlingResource();
        }
        return idlingResourceForTesting;
    }


    @Override
    public void onRecipeTitleClick(int position) {

    //for testing
        NetworkDelayer.processMessage("testingIdling", this, idlingResourceForTesting);

        Toast.makeText(this, "position= "+position, Toast.LENGTH_SHORT).show();
        Timber.i("onRecipeTitleClick called");
        Timber.i("recipe position= %s", position);

        SharedPreferences.Editor editor = positionSharedPreferences.edit();
        editor.putInt(RECIPE_POSITION, position);
        editor.putInt(STEP_POSITION, 0);
        editor.apply();



        //Updating widget
        Intent widgetIntent=new Intent(this, BakingWidgetService.class);
        widgetIntent.setAction(ACTION_INTENT_OPEN_RECIPE);
        widgetIntent.putExtra(RECIPE_POSITION, position);
//        BakingWidgetService.enqueueRecipeWork(this, widgetIntent);
        BakingWidgetService.startActionUpdateRecipe(this, position);


        //sending data to RecipeIngredientsSteps
        Intent intent=new Intent(MainActivity.this, RecipeIngredientsSteps.class);
        intent.putExtra(RECIPE_POSITION, position);
        startActivity(intent);

    }

    @Override
    public void onStepClick(int position) {
        Toast.makeText(this, "step clicked position= "+position, Toast.LENGTH_SHORT).show();
    }


    //Testing to make sure list_fragment is shown
    @Override
    public void finished(String text) {
        Toast.makeText(this, "finished callback called", Toast.LENGTH_SHORT).show();


//        FragmentManager fragmentManager = getSupportFragmentManager();
//
//        recipeListNameFragment = new RecipeListNameFragment();
//        // Add the fragment to its container using a transaction
//        fragmentManager.beginTransaction()
//                .add(R.id.list_fragment, recipeListNameFragment)
//                //.addToBackStack(RECIPE_NAME_FRAGMENT)
//                .commit();
    }
}




