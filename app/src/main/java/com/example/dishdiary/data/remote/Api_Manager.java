package com.example.dishdiary.data.remote;


import android.util.Log;

import androidx.annotation.NonNull;


import com.example.dishdiary.Constants;
import com.example.dishdiary.data.model.CategoriesResponse;
import com.example.dishdiary.data.model.CountriesResponse;
import com.example.dishdiary.data.model.IngredientResponse;
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
                    callback.onMealCallSuccess(response.body().getMeals().get(0));
                    Log.i(TAG, "OnResponse :" + response.body().getMeals());
                }
            }
            @Override
            public void onFailure(@NonNull Call<MealResponse> call, Throwable t) {
                callback.onMealCallFailure(t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void getCategories(NetworkDelegate callback) {
      apiService.getCategories().enqueue(new Callback<CategoriesResponse>() {
          @Override
          public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
            if (response.isSuccessful()) {
                assert response.body() != null;
                callback.onCategoryCallSuccess(response.body().getCategoryList());
            }
            }

          @Override
          public void onFailure(Call<CategoriesResponse> call, Throwable t) {
                 callback.onCategoryCallFailure(t.getLocalizedMessage());
          }
      });
   }

    @Override
    public void getCountries(NetworkDelegate callback) {
            apiService.getCountries().enqueue(new Callback<CountriesResponse>() {
                @Override
                public void onResponse(Call<CountriesResponse> call, Response<CountriesResponse> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        callback.onCountryCallSuccess(response.body().getCountries());
                    }
                }

                @Override
                public void onFailure(Call<CountriesResponse> call, Throwable t) {
                       callback.onCountryCallFailure(t.getLocalizedMessage());
                }
            });
    }

    @Override
    public void getIngredients(NetworkDelegate callback) {
               apiService.getIngredients().enqueue(new Callback<IngredientResponse>() {
                   @Override
                   public void onResponse(Call<IngredientResponse> call, Response<IngredientResponse> response) {
                          if (response.isSuccessful()){
                              assert response.body() != null;
                              callback.onIngredientCallSuccess(response.body().getMeals());

                          }
                   }
                   @Override
                   public void onFailure(Call<IngredientResponse> call, Throwable t) {
                        callback.onIngredientCallFailure(t.getLocalizedMessage());
                   }
               });
    }

    @Override
    public void filterByFirstLetter(String fLetterQuery,NetworkDelegate callback) {
           apiService.searchByFirstLetter(fLetterQuery).enqueue(new Callback<MealResponse>() {
               @Override
               public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        callback.onFilterSuccess(response.body().getMeals());
                    }
                    }

               @Override
               public void onFailure(Call<MealResponse> call, Throwable t) {
                   Log.i(TAG,"onFailure: from api manager");
               }

           });

    }

    @Override
    public void filterByName(String mealName,NetworkDelegate callback) {
      apiService.getMealsByName(mealName).enqueue(new Callback<MealResponse>() {
          @Override
          public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
              if (response.isSuccessful()) {
                  assert response.body() != null;
                  callback.onFilterSuccess(response.body().getMeals());
              }
          }

          @Override
          public void onFailure(Call<MealResponse> call, Throwable t) {
              callback.onFilterFailure(t.getLocalizedMessage());
          }
      });
    }

    @Override
    public void getMealByID(int mealId,NetworkDelegate callback) {
        apiService.getMealInfo(mealId).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful()){

                    assert response.body() != null;
                    callback.onFilterSuccess(response.body().getMeals());

                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                callback.onFilterFailure(t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void filterByCountry(String query, NetworkDelegate callback) {
           apiService.getMealByCountry(query).enqueue(new Callback<MealResponse>() {
               @Override
               public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                   if (response.isSuccessful()){
                       callback.onFilterSuccess(response.body().getMeals());
                   }
               }

               @Override
               public void onFailure(Call<MealResponse> call, Throwable t) {
                     callback.onFilterFailure(t.getLocalizedMessage());
               }
           });
    }

    @Override
    public void filterByIngredient(String query, NetworkDelegate callback) {
        apiService.getMealByIngredient(query).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful()){
                    callback.onFilterSuccess(response.body().getMeals());
                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                callback.onFilterFailure(t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void filterByCategory(String query, NetworkDelegate callback) {
               apiService.getMealByCategory(query).enqueue(new Callback<MealResponse>() {
                   @Override
                   public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                       if (response.isSuccessful()){
                           callback.onFilterSuccess(response.body().getMeals());
                       }
                   }

                   @Override
                   public void onFailure(Call<MealResponse> call, Throwable t) {
                       callback.onFilterFailure(t.getLocalizedMessage());

                   }
               });
    }


}
