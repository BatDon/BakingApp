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

public class RetrofitRequester extends AppCompatActivity {

    public static final String TAG=RetrofitRequester.class.getSimpleName();

    public interface OnRetrofitListener {
        public void onRetrofitFinished(List<RecipePojo> recipes);
    }
    private OnRetrofitListener onRetrofitListener;


    public void requestRecipes(final OnRetrofitListener onRetrofitListener) {

        Log.i(TAG,"requestRecipes called");

        this.onRetrofitListener = onRetrofitListener;


        RecipesApi recipesApi = RetrofitClient.getRetrofitInstance().create(RecipesApi.class);
        Call<List<RecipePojo>> call=recipesApi.getAllRecipes();

        call.enqueue(new Callback<List<RecipePojo>>() {
            @Override
            public void onResponse(Call<List<RecipePojo>> call, Response<List<RecipePojo>> response) {
                Log.i(TAG,"anything");

                Log.i(TAG, "response as String= "+response.toString());
                if(response.body()==null){
                    Log.i(TAG,"responseBody equals null");
                }
                Log.i(TAG,"responseBodySize= "+response.body().size());

                List<RecipePojo> recipesPojoList= (List<RecipePojo>) response.body();
                Log.i(TAG,"Name= "+recipesPojoList.get(0).getName());


//                Log.i(TAG,"retrieved data successfully");

//                String recipeName=recipesPojoList.get(0).getName();
//                Log.i(TAG,"recipeName= "+recipeName);
//                Toast.makeText(RetrofitRequester.this, recipeName, Toast.LENGTH_SHORT).show();
//                List<RecipesPojo> recipesPojoList=generateDataList(response.body());
                if (onRetrofitListener != null){
                    Log.i(TAG,"RetrofitListener has a value");
                    onRetrofitListener.onRetrofitFinished(recipesPojoList);
                }
                else{
                    Log.i(TAG,"RetrofitListener equals null");
                }
            }

            @Override
            public void onFailure(Call<List<RecipePojo>> call, Throwable t) {
                Log.e(TAG,"R.string.problem_retrieving_data");

                if (t instanceof IOException) {
                    Log.i(TAG,"this is an actual network failure :( inform the user and possibly retry");
                  //  Toast.makeText(RetrofitRequester.this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                    // logging probably not necessary
                }
                else {
                    Log.i(TAG,"conversion issue! big problems :(");
//                    Toast.makeText(RetrofitRequester.this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                    // todo log to some central bug tracking service
                }
//                Toast.makeText(RetrofitRequester.this, R.string.problem_retrieving_data, Toast.LENGTH_SHORT).show();
            }
        });
    }

//    public List<Result> generateDataList(MoviePojo moviePojoList) {
//        List<Result> resultList;
//        if (moviePojoList == null) {
//            //           Toast.makeText(this, R.string.parsing_problem, Toast.LENGTH_SHORT).show();
//            resultList=null;
//        } else {
//            resultList = moviePojoList.getResults();
//        }
//        return resultList;
//    }

}