package com.example.dishdiary.data.network;


import android.util.Log;

import androidx.annotation.NonNull;


import com.example.dishdiary.Constants;
import com.example.dishdiary.data.model.MealResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api_Manager implements RemoteSource {
    private static final String TAG ="Api Client";
    private static Api_Manager instance = null;
    ApiService apiService;
    private Api_Manager(){
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        apiService = retrofit.create(ApiService.class);
    }

    public static Api_Manager getInstance(){
        if(instance == null){
            return new Api_Manager();
        }
        return instance;
    }

    @Override
    public void makeRandomMealsCall(NetworkDelegate callback) {

        Call<MealResponse> call= apiService.getRandomMeal();
        call.enqueue(new Callback<MealResponse>() {

            @Override
            public void onResponse(@NonNull Call<MealResponse> call, @NonNull Response<MealResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                   //return the random item which is the first item
                    callback.onSuccess(response.body().getMeals().get(0));
                    Log.i(TAG, "OnResponse :" + response.body().getMeals());
                }
            }
            @Override
            public void onFailure(@NonNull Call<MealResponse> call, Throwable t) {
                callback.onFailure(t.getLocalizedMessage());
            }
        });
    }





}
