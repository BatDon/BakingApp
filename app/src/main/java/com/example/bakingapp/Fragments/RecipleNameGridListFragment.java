package com.example.bakingapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.Adapters.RecipeNameAdapter;
import com.example.bakingapp.JSONUtility;
import com.example.bakingapp.R;

import java.util.ArrayList;

//public class RecipleNameGridListFragment extends Fragment implements BackButtonPressed {
public class RecipleNameGridListFragment extends Fragment{

    // Define a new interface OnImageClickListener that triggers a callback in the host activity
    RecipeNameAdapter.OnRecipeTitleListener recipeCallback;

    RecyclerView gridRecyclerView;

    public RecipleNameGridListFragment() {

    }


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

        gridRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_recipe_grid_list, container, false);
        setUpGridAdapter();

        return gridRecyclerView;
    }

    public void setUpGridAdapter(){

        gridRecyclerView.setHasFixedSize(true);
        int numberOfColumns = 2;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), numberOfColumns, RecyclerView.VERTICAL, false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });

        gridRecyclerView.setLayoutManager(gridLayoutManager);
        JSONUtility jsonUtility= JSONUtility.createJSONUtilityInstance();
        ArrayList<String> recipeNames= jsonUtility.getRecipeNames();
        if(recipeNames.size()>0) {
            RecipeNameAdapter recipeNameAdapter =new RecipeNameAdapter(getActivity(), recipeNames, recipeCallback);
            gridRecyclerView.setAdapter(recipeNameAdapter);
            getActivity().findViewById(R.id.loading_progress_bar).setVisibility(View.INVISIBLE);
        }

    }
}


