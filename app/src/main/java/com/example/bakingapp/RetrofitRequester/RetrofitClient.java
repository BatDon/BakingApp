package com.example.bakingapp.RetrofitRequester;

import com.example.bakingapp.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;

    private static OkHttpClient loggingInterceptor=createLoggingInteceptor();

    public static Retrofit getRetrofitInstance() {
        Gson gson = new GsonBuilder().serializeNulls().create();
        GsonConverterFactory gsonConverterFactory=GsonConverterFactory.create(gson);

        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .client(loggingInterceptor)
                    .build();
        }
        return retrofit;
    }

    public static OkHttpClient createLoggingInteceptor(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
        return client;
    };
}
