package com.example.bakingapp.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.Pojos.StepPojo;
import com.example.bakingapp.R;

import java.util.ArrayList;

import timber.log.Timber;

public class StepLinearAdapter extends RecyclerView.Adapter<StepLinearAdapter.StepsViewHolder> {

    ArrayList<StepPojo> stepsList;
    private LayoutInflater mInflater;
    private OnRecipeStepListener onRecipeStepListener;


    Context mContext;

    public interface OnRecipeStepListener{
        void onStepClick(int position);
    }

    public StepLinearAdapter(Context context, ArrayList<StepPojo> stepsList, OnRecipeStepListener onRecipeStepListener){
        Timber.i("StepLinearAdapter called");
        this.mContext=context;
        this.mInflater = LayoutInflater.from(context);
        this.stepsList = stepsList;
        this.onRecipeStepListener=onRecipeStepListener;
    }


    @NonNull
    @Override
    public StepLinearAdapter.StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Timber.i("onCreateViewHolder called");
        View view = mInflater.inflate(R.layout.recipe_step_item, parent, false);
        return new StepsViewHolder(view, onRecipeStepListener);
    }


    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, final int position) {
        StepPojo stepPojo= stepsList.get(position);
        holder.stepShortDescriptionTv.setText(stepPojo.getShortDescription());
        holder.stepLongDescriptionTv.setText(stepPojo.getDescription());
        Timber.i("stepPojo description%s", stepPojo.getDescription());
    }


    public static class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView stepShortDescriptionTv;
        TextView stepLongDescriptionTv;
        OnRecipeStepListener onRecipeStepListener;
        public StepsViewHolder(View itemView, OnRecipeStepListener onRecipeStepListener){
            super(itemView);
            stepShortDescriptionTv =itemView.findViewById(R.id.recipe_step_item_short_description);
            stepLongDescriptionTv=itemView.findViewById(R.id.recipe_step_item_description);
            this.onRecipeStepListener=onRecipeStepListener;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (onRecipeStepListener != null){
                onRecipeStepListener.onStepClick(getAdapterPosition());
            }
        }
    }


    @Override
    public int getItemCount() {
        if(stepsList !=null) {
            int size= stepsList.size();
            return size;
        }
        return 0;
    }
}
