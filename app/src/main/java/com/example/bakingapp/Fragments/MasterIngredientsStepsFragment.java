package com.example.bakingapp.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.BackButtonPressed;
import com.example.bakingapp.Adapters.IngredientLinearAdapter;
import com.example.bakingapp.Adapters.StepLinearAdapter;
import com.example.bakingapp.JSONUtility;
import com.example.bakingapp.Pojos.IngredientPojo;
import com.example.bakingapp.Pojos.StepPojo;
import com.example.bakingapp.R;

import java.util.ArrayList;
import java.util.Objects;

import timber.log.Timber;

import static android.content.Context.MODE_PRIVATE;
import static com.example.bakingapp.Constants.MASTER_INGREDIENTS_STEPS_FRAGMENT;
import static com.example.bakingapp.Constants.RECIPE_POSITION;
import static com.example.bakingapp.Constants.RECIPE_PREFERENCE_FILE;

public class MasterIngredientsStepsFragment extends Fragment implements BackButtonPressed {

        View ingredientsStepsView;
        RecyclerView ingredientsLinearRecyclerView;
        RecyclerView stepsLinearRecyclerView;
        IngredientLinearAdapter ingredientLinearAdapter;
        StepLinearAdapter stepLinearAdapter;

        StepLinearAdapter.OnRecipeStepListener stepCallback;

        int recipePosition;

        public MasterIngredientsStepsFragment() {

        }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if(getActivity().getIntent()!=null){
            Timber.i("intent exists");
        }

        //recipePosition= Objects.requireNonNull(getActivity().getIntent().getExtras()).getInt("RECIPE_POSITION",0);
        SharedPreferences sharedPreferences=getActivity().getApplicationContext().getSharedPreferences(RECIPE_PREFERENCE_FILE, MODE_PRIVATE);
        recipePosition=sharedPreferences.getInt(RECIPE_POSITION,0);
    }




    // OnImageClickListener interface, calls a method in the host activity named onImageSelected
        public interface OnRecipeTitleListener{
            void onRecipeTitleClick(int position);
        }


        @Override
        public void onAttach(Context context) {
            super.onAttach(context);

            try {
                stepCallback = (StepLinearAdapter.OnRecipeStepListener) context;
                //recipeCallback = (com.example.bakingapp.Fragments.RecipleNameGridListFragment.OnRecipeTitleListener) context;
            } catch (ClassCastException e) {
                throw new ClassCastException(context.toString()+ getString(R.string.implement_StepLinearAdapter_OnRecipeStepListener_ERROR));
            }
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            int recipePosition=-1;

            //rootview is grid recyclerview
            //View rootView = inflater.inflate(R.layout.recipe_grid_item, container, false);

            ingredientsStepsView = inflater.inflate(R.layout.fragment_ingredient_step_linear_list, container, false);
//            ingredientsStepsView = inflater.inflate(R.layout.fragment_ingredient_step_linear_list, container, false);
            ingredientsLinearRecyclerView=ingredientsStepsView.findViewById(R.id.linearRecyclerViewIngredients);
            stepsLinearRecyclerView=ingredientsStepsView.findViewById(R.id.linearRecyclerViewSteps);


//            TextView tv=ingredientsStepsView.findViewById(R.id.textview);
//            tv.setText("Something new is here");

            if (getArguments() != null) {
                recipePosition = getArguments().getInt(RECIPE_POSITION);
            }

            if(recipePosition!= -1) {

                setUpIngredientsAdapter(recipePosition);
                setUpStepsAdapter(recipePosition);
            }

            //gridview is each individual box of data in grid recyclerview list
            // Get a reference to the GridView in the fragment_master_list xml layout file
//            TextView recipeTitleTV = linearRecyclerView.findViewById(R.id.recipe_grid_item);



            return ingredientsStepsView;
        }


        public void setUpIngredientsAdapter(int position){

            ingredientsLinearRecyclerView.setHasFixedSize(true);

            LinearLayoutManager linearLayoutManager =
                    new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

            ingredientsLinearRecyclerView.setLayoutManager(linearLayoutManager);

            ArrayList<IngredientPojo> ingredientPojoArrayList= JSONUtility.createJSONUtilityInstance().getRecipeIngredients(position);

            ingredientLinearAdapter=new IngredientLinearAdapter(getActivity(), ingredientPojoArrayList);

            ingredientsLinearRecyclerView.setAdapter(ingredientLinearAdapter);
            Timber.i("setUpIngredientsAdapter finished");

        }

        public void setUpStepsAdapter(int position) {
            stepsLinearRecyclerView.setHasFixedSize(true);

            LinearLayoutManager stepsLinearLayoutManager =
                    new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

            stepsLinearRecyclerView.setLayoutManager(stepsLinearLayoutManager);

            ArrayList<StepPojo> stepPojoArrayList= JSONUtility.createJSONUtilityInstance().getRecipeStepsPojo(position);

            Timber.i("stepPojoAL size= %s", stepPojoArrayList.size());
            Timber.i(stepPojoArrayList.toString());
            Timber.i(stepPojoArrayList.get(0).getDescription());
            Timber.i(stepPojoArrayList.get(1).getDescription());
            Timber.i(stepPojoArrayList.get(2).getDescription());

            stepLinearAdapter=new StepLinearAdapter(getActivity(), stepPojoArrayList, stepCallback);

            stepsLinearRecyclerView.setAdapter(stepLinearAdapter);
            Timber.i("setUpStepsAdapter finished");

        }

        @Override
        public void backButtonPressed() {
            FragmentManager fragmentManager = getActivity()
                    .getSupportFragmentManager();
            fragmentManager.popBackStack (MASTER_INGREDIENTS_STEPS_FRAGMENT, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }

}
