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
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.BackButtonPressed;
import com.example.bakingapp.Adapters.RecipeNameAdapter;
import com.example.bakingapp.Adapters.StepLinearAdapter;
import com.example.bakingapp.Fragments.ExoplayerFragment;
import com.example.bakingapp.Fragments.MasterIngredientsStepsFragment;
import com.example.bakingapp.Fragments.RecipeListNameFragment;
import com.example.bakingapp.Fragments.RecipleNameGridListFragment;
import com.example.bakingapp.Pojos.RecipePojo;
import com.example.bakingapp.ViewModels.RecipeViewModel;
import com.example.bakingapp.Widget.BakingWidgetService;

import java.util.List;

import timber.log.Timber;

import static com.example.bakingapp.Constants.ACTION_INTENT_OPEN_RECIPE;
import static com.example.bakingapp.Constants.IS_TABLET;
import static com.example.bakingapp.Constants.MASTER_INGREDIENTS_STEPS_FRAGMENT;
import static com.example.bakingapp.Constants.RECIPE_POSITION;
import static com.example.bakingapp.Constants.RECIPE_NAME_FRAGMENT;
import static com.example.bakingapp.Constants.RECIPE_STEP_POSITION_PREFERENCE_FILE;
import static com.example.bakingapp.Constants.STEP_POSITION;

//import com.example.bakingapp.Repositories.RecipeRepository;

//import com.example.bakingapp.Fragments.RecipleNameGridListFragment;


//public class MainActivity extends AppCompatActivity implements RecipleNameGridListFragment.OnRecipeTitleClickListener {
//public class MainActivity extends AppCompatActivity implements RetrofitRequester.OnRetrofitListener{
//public class MainActivity extends AppCompatActivity implements RecipeNameAdapter.OnRecipeTitleListener,
//        StepLinearAdapter.OnRecipeStepListener, BackButtonPressed {
public class MainActivity extends AppCompatActivity implements RecipeNameAdapter.OnRecipeTitleListener,
        StepLinearAdapter.OnRecipeStepListener {

    public static final String TAG=MainActivity.class.getSimpleName();

    private MainActivityViewModel mainActivityViewModel;
    RecipeViewModel recipeViewModel;

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


//    public void setUpViewRecipeStepsViewModel() {
//        recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
//
//        Observer<Integer> recipeNameObserver = new Observer<Integer>() {
//            int i = 0;
//
//            @Override
//            public void onChanged(@Nullable final Integer recipePosition) {
//
//                Timber.i("viewmodel onChanged called position= %s", recipePosition);
//                // Update the cached copy of the words in the adapter.
////                Log.i("MainActivity", "onChanged triggered");
////                if (i > 0) {
////                    resultList = movies;
////                    setUpGridAdapter();
////                }
////                i++;
////                //mainViewModel.requestMovies();
////                resultList=movies;
////                if(mainViewModel.getAllMovies().getValue()!=null){
////                    mainViewModel.requestMovies();
////                }
//            }
//        };
//    }


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



//
//    @Override
//    public void onRecipeSelected(){
//        int l=2;
//    }





    //linearRecyclerView for phones

//     loadingCircle=findViewById(R.id.progressBar);
//        userRecyclerView=findViewById(R.id.recyclerView);
//
//        Log.i(TAG,"setUpViews called");
//
//        showLoading();
//
//
//        LinearLayoutManager linearLayoutManager =
//                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//
//        userRecyclerView.setLayoutManager(linearLayoutManager);
//        userRecyclerView.setHasFixedSize(true);
//
//
////        userFavoritesAdapter = new FavoritesAdapter(this, this);
//        completeAdapter=new CompleteAdapter(this,this,null, LIST_RECYCLER_VIEW);
//
//
////        userRecyclerView.setAdapter(userFavoritesAdapter);
//        userRecyclerView.setAdapter(completeAdapter);


    public void showFragment(){

    }

    public void showLoading(){

    }

    @Override
    public void onRecipeTitleClick(int position) {
        Toast.makeText(this, "position= "+position, Toast.LENGTH_SHORT).show();
        Timber.i("onRecipeTitleClick called");
        Timber.i("recipe position= %s", position);

        SharedPreferences.Editor editor = positionSharedPreferences.edit();
        editor.putInt(RECIPE_POSITION, position);
        editor.putInt(STEP_POSITION, 0);
        editor.apply();
        //recipeViewModel.setRecipeNumberLiveData(position);


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


//        SharedPreferences positionSharedPreferences=this.getApplicationContext().getSharedPreferences(RECIPE_STEP_POSITION_PREFERENCE_FILE, MODE_PRIVATE);
//        SharedPreferences.Editor editor = positionSharedPreferences.edit();
//        editor.putInt(RECIPE_POSITION, position);
//        editor.putInt(STEP_POSITION, 0);
//        editor.apply();


//        Bundle recipeBundle = new Bundle();
//        recipeBundle.putInt(RECIPE_POSITION, position);
//        recipeBundle.putBoolean(IS_TABLET, gridViewTablet);
//
//        FragmentManager fragmentManager = getSupportFragmentManager();
//
//        masterIngredientsStepsFragment = new MasterIngredientsStepsFragment();
//        masterIngredientsStepsFragment.setArguments(recipeBundle);
        // Add the fragment to its container using a transaction
//        fragmentManager.beginTransaction()
//                .replace(R.id.list_fragment, masterIngredientsStepsFragment)
//                .commit();
//        fragmentManager.beginTransaction()
//                .replace(R.id.list_fragment, masterIngredientsStepsFragment)
//                .addToBackStack(MASTER_INGREDIENTS_STEPS_FRAGMENT)
//                .commit();





//        Bundle exoPlayerBundle = new Bundle();
//        exoPlayerBundle.putInt(RECIPE_POSITION, position);
//        exoPlayerBundle.putInt(STEP_POSITION, 0);
//
//        exoplayerFragment = new ExoplayerFragment();
//        exoplayerFragment.setArguments(exoPlayerBundle);
//
//        fragmentManager.beginTransaction()
//                .add(fragment_ingredient_step_)
    }

    @Override
    public void onStepClick(int position) {
        Toast.makeText(this, "step clicked position= "+position, Toast.LENGTH_SHORT).show();
    }


}

//    @Override
//    public void onRetrofitFinished(List<RecipePojo> recipes) {
//        Toast.makeText(this, "recipesSize= "+recipes.size(), Toast.LENGTH_SHORT).show();
//    }


//      Grid RecyclerView tablet

//       public void setUpGridAdapter(){
//        gridRecyclerView.setHasFixedSize(true);
//        int numberOfColumns = 2;
//
//        GridLayoutManager gridLayoutManager=new GridLayoutManager(this, numberOfColumns, RecyclerView.VERTICAL,false);
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                return 1;
//            }
//        });
//        gridRecyclerView.setLayoutManager(gridLayoutManager);
//        Result[] resultArray=new Result[resultList.size()];
//        resultList.toArray(resultArray);
//        if(resultArray.length==0){
//            Log.i(TAG,"no results found");
//        }
//        else{
//            Log.i(TAG,"resultArray length= "+resultArray.length+"");
//        }
//        Log.i(TAG,Integer.toString(resultArray.length));
//        gridAdapter = new GridAdapter(this, resultArray, this);
//        //completeAdapter = new CompleteAdapter(this, this, resultArray, GRID_RECYCLER_VIEW);
//        gridRecyclerView.setAdapter(gridAdapter);
////        gridRecyclerView.setAdapter(completeAdapter);
//
//        progressBar.setVisibility(View.INVISIBLE);
//        gridRecyclerView.setVisibility(View.VISIBLE);
//
//        if(resultArray.length>0) {
//            ArrayList<Result> movieArrayList = new ArrayList<Result>(Arrays.asList(resultArray));
//
//            mainActivityViewModel.writeToFile(movieArrayList, this);
//        }
//
//        Log.i(TAG,"end of setUpAdapter");
//    }













//        try {
//            String result = "{\"someKey\":\"someValue\"}";
//            String jsonString = "{\"someKey\":\"\"}";
//            JSONObject jObject = new JSONObject(jsonString);
//            String objectString=jObject.getString("someKey");
////            String objectAsString=object.toString();
//            Log.i(TAG,"objectString= "+objectString);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

//        JSON.parse("");



//    }

//    public void requestData(View v){
//        RetrofitRequester retrofitRequester=new RetrofitRequester();
//        retrofitRequester.requestRecipes(this);
//    }

//    @Override
//    public void onRetrofitFinished(List<RecipesPojo> recipes) {
//        Log.i(TAG, "onRetrofitFinished");
//        Toast.makeText(this, recipes.size()+" recipes", Toast.LENGTH_SHORT).show();
////        recipes.size();
//    }


