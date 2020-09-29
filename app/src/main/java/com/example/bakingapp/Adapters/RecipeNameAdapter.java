package com.example.bakingapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.R;

import java.util.ArrayList;

public class RecipeNameAdapter extends RecyclerView.Adapter<RecipeNameAdapter.RecipeTitleViewHolder> {

    ArrayList<String> recipeList;
    private LayoutInflater mInflater;
    private OnRecipeTitleListener onRecipeTitleListener;


    Context context;

    public interface OnRecipeTitleListener{
        void onRecipeTitleClick(int position);
    }

    public RecipeNameAdapter(Context context, ArrayList<String> recipeList, OnRecipeTitleListener onRecipeTitleListener){
        this.context=context;
        this.mInflater = LayoutInflater.from(context);
        this.recipeList=recipeList;
        this.onRecipeTitleListener=onRecipeTitleListener;
    }


    @NonNull
    @Override
    public RecipeNameAdapter.RecipeTitleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recipe_grid_item, parent, false);
        return new RecipeTitleViewHolder(view, onRecipeTitleListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeTitleViewHolder holder, final int position) {
        String recipeTitle=recipeList.get(position);
        holder.recipeTitleTv.setText(recipeTitle);
    }


    public static class RecipeTitleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView recipeTitleTv;
        OnRecipeTitleListener onRecipeTitleListener;
        public RecipeTitleViewHolder(View itemView, OnRecipeTitleListener onRecipeTitleListener){
            super(itemView);
            recipeTitleTv=itemView.findViewById(R.id.recipe_grid_item);
            this.onRecipeTitleListener=onRecipeTitleListener;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (onRecipeTitleListener != null){
                onRecipeTitleListener.onRecipeTitleClick(getAdapterPosition());
            }
        }
    }


    @Override
    public int getItemCount() {
        if(recipeList!=null) {
            int size= recipeList.size();
            return size;
        }
        return 0;
    }
}
