package com.example.bakingapp.RetrofitRequester;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bakingapp.Api.RecipesApi;
import com.example.bakingapp.Pojos.RecipePojo;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class RetrofitRequester extends AppCompatActivity {

    public static final String TAG=RetrofitRequester.class.getSimpleName();

    public interface OnRetrofitListener {
        public void onRetrofitFinished(List<RecipePojo> recipes);
    }
    private OnRetrofitListener onRetrofitListener;


    public void requestRecipes(final OnRetrofitListener onRetrofitListener) {

        Timber.i("requestRecipes called");

        this.onRetrofitListener = onRetrofitListener;


        RecipesApi recipesApi = RetrofitClient.getRetrofitInstance().create(RecipesApi.class);
        Call<List<RecipePojo>> call=recipesApi.getAllRecipes();

        call.enqueue(new Callback<List<RecipePojo>>() {
            @Override
            public void onResponse(Call<List<RecipePojo>> call, Response<List<RecipePojo>> response) {
                Timber.i("response as String= " + response.toString());
                if(response.body()==null){
                    Timber.i("responseBody equals null");
                }
                Timber.i("responseBodySize= " + response.body().size());

                List<RecipePojo> recipesPojoList= (List<RecipePojo>) response.body();
                Timber.i("Name= " + recipesPojoList.get(0).getName());

                if (onRetrofitListener != null){
                    Timber.i("RetrofitListener has a value");
                    onRetrofitListener.onRetrofitFinished(recipesPojoList);
                }
                else{
                    Timber.i("RetrofitListener equals null");
                }
            }

            @Override
            public void onFailure(Call<List<RecipePojo>> call, Throwable t) {
                Timber.e("R.string.problem_retrieving_data");

                if (t instanceof IOException) {
                    Timber.i("network failure retry requesting data");
                }
                else {
                    Timber.i("conversion error unable to convert data");
                }
            }
        });
    }

}