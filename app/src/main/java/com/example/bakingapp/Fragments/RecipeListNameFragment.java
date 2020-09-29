package com.example.bakingapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.Adapters.RecipeNameAdapter;
import com.example.bakingapp.JSONUtility;
import com.example.bakingapp.R;

import java.util.ArrayList;

import timber.log.Timber;


//public class RecipeListNameFragment extends Fragment implements BackButtonPressed {
public class RecipeListNameFragment extends Fragment {

    RecipeNameAdapter.OnRecipeTitleListener recipeCallback;

    RecyclerView listRecyclerView;

    public RecipeListNameFragment() {
        Timber.i("RecipeListNameFragment constructor called");

    }

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

        listRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_recipe_grid_list, container, false);
        setUpGridAdapter();

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
}

