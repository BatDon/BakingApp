package com.example.bakingapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.Pojos.IngredientPojo;
import com.example.bakingapp.R;

import java.util.ArrayList;


public class IngredientLinearAdapter extends RecyclerView.Adapter<IngredientLinearAdapter.IngredientsViewHolder> {

    ArrayList<IngredientPojo> ingredientPojos;
    private LayoutInflater mInflater;

    Context context;


    public IngredientLinearAdapter(Context context, ArrayList<IngredientPojo> ingredientPojos){
        this.context=context;
        this.mInflater = LayoutInflater.from(context);
        this.ingredientPojos=ingredientPojos;
    }


    @NonNull
    @Override
    public IngredientLinearAdapter.IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recipe_ingredient_item, parent, false);
        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, final int position) {
        IngredientPojo ingredientPojo=ingredientPojos.get(position);
        String quantity=ingredientPojo.getQuantity().toString();
        String measure=ingredientPojo.getMeasure();
        String ingredient=ingredientPojo.getIngredient();

        holder.quantityTv.setText(quantity);
        holder.measureTv.setText(measure);
        holder.ingredientTv.setText(ingredient);
    }


    public static class IngredientsViewHolder extends RecyclerView.ViewHolder{
        TextView quantityTv;
        TextView measureTv;
        TextView ingredientTv;

        public IngredientsViewHolder(View itemView){
            super(itemView);
            quantityTv=itemView.findViewById(R.id.quantity);
            measureTv=itemView.findViewById(R.id.measure);
            ingredientTv=itemView.findViewById(R.id.ingredient);
        }

    }


    @Override
    public int getItemCount() {
        if(ingredientPojos!=null) {
            int size= ingredientPojos.size();
            return size;
        }
        return 0;
    }
}