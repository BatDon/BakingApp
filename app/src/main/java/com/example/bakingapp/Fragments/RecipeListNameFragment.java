package com.example.bakingapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.BackButtonPressed;
import com.example.bakingapp.Adapters.RecipeNameAdapter;
import com.example.bakingapp.JSONUtility;
import com.example.bakingapp.R;

import java.util.ArrayList;

import timber.log.Timber;

import static com.example.bakingapp.Constants.MASTER_INGREDIENTS_STEPS_FRAGMENT;
import static com.example.bakingapp.Constants.RECIPE_LIST_NAME_FRAGMENT;


//public class RecipeListNameFragment extends Fragment implements BackButtonPressed {
public class RecipeListNameFragment extends Fragment{

    // Inflates the GridView of all AndroidMe images

    // Define a new interface OnImageClickListener that triggers a callback in the host activity
    RecipeNameAdapter.OnRecipeTitleListener recipeCallback;

    RecyclerView listRecyclerView;

    public RecipeListNameFragment() {
        Timber.i("RecipeListNameFragment constructor called");

    }

    // OnImageClickListener interface, calls a method in the host activity named onImageSelected
//    public interface OnRecipeTitleListener{
//        void onRecipeTitleClick(int position);
//    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            recipeCallback = (RecipeNameAdapter.OnRecipeTitleListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + getString(R.string.implement_OnRecipeTitleClickListener_Error));
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //rootview is grid recyclerview
        //View rootView = inflater.inflate(R.layout.recipe_grid_item, container, false);

        listRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_recipe_grid_list, container, false);
        setUpGridAdapter();

        //gridview is each individual box of data in grid recyclerview list
        // Get a reference to the GridView in the fragment_master_list xml layout file
        TextView recipeTitleTV = listRecyclerView.findViewById(R.id.recipe_grid_item);

//        gridRecyclerView.setHasFixedSize(true);
//        int numberOfColumns = 3;
//
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), numberOfColumns, RecyclerView.VERTICAL, false);
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                return 1;
//            }
//        });

        return listRecyclerView;
    }

    public void setUpGridAdapter() {

        listRecyclerView.setHasFixedSize(true);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        listRecyclerView.setLayoutManager(linearLayoutManager);

        JSONUtility jsonUtility = JSONUtility.createJSONUtilityInstance();
        ArrayList<String> recipeNames = jsonUtility.getRecipeNames();
        if (recipeNames.size() > 0) {
            RecipeNameAdapter recipeNameAdapter = new RecipeNameAdapter(getActivity(), recipeNames, recipeCallback);
            listRecyclerView.setAdapter(recipeNameAdapter);
            getActivity().findViewById(R.id.loading_progress_bar).setVisibility(View.INVISIBLE);
            getActivity().findViewById(R.id.list_fragment).setVisibility(View.VISIBLE);
        }
    }

//        @Override
//        public void backButtonPressed() {
//            FragmentManager fragmentManager = getActivity()
//                    .getSupportFragmentManager();
//            Timber.i("recipeListNameFragment backStackfragmentManger count="+fragmentManager.getBackStackEntryCount());
//            fragmentManager.popBackStack (RECIPE_LIST_NAME_FRAGMENT, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//        }


//        Result[] resultArray = new Result[resultList.size()];
//        resultList.toArray(resultArray);
//        if (resultArray.length == 0) {
//            Log.i(TAG, "no results found");
//        } else {
//            Log.i(TAG, "resultArray length= " + resultArray.length + "");
//        }
//        Log.i(TAG, Integer.toString(resultArray.length));
//        gridAdapter = new GridAdapter(this, resultArray, this);
//        //completeAdapter = new CompleteAdapter(this, this, resultArray, GRID_RECYCLER_VIEW);
//        gridRecyclerView.setAdapter(gridAdapter);
////        gridRecyclerView.setAdapter(completeAdapter);
//
//        progressBar.setVisibility(View.INVISIBLE);
//        gridRecyclerView.setVisibility(View.VISIBLE);
    }

//    @Override
//    public void onRecipeTitleClick(int position){
//        Toast.makeText(getActivity(), "position= "+position, Toast.LENGTH_SHORT).show();
////        Intent detailIntent=new Intent(this, MovieDetails.class);
////        detailIntent.putExtra(MOVIE_POSITION, position);
////        startActivity(detailIntent);
//    }


//        gridRecyclerView.setLayoutManager(gridLayoutManager);
//        Result[] resultArray = new Result[resultList.size()];
//        resultList.toArray(resultArray);
//        if (resultArray.length == 0) {
//            Log.i(TAG, "no results found");
//        } else {
//            Log.i(TAG, "resultArray length= " + resultArray.length + "");
//        }
//        Log.i(TAG, Integer.toString(resultArray.length));
//        gridAdapter = new GridAdapter(this, resultArray, this);
//        //completeAdapter = new CompleteAdapter(this, this, resultArray, GRID_RECYCLER_VIEW);
//        gridRecyclerView.setAdapter(gridAdapter);
////        gridRecyclerView.setAdapter(completeAdapter);
//
//        progressBar.setVisibility(View.INVISIBLE);
//        gridRecyclerView.setVisibility(View.VISIBLE);
//    }
//}
