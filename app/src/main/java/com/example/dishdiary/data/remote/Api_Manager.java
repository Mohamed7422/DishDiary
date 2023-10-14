package com.example.dishdiary.data.remote;


import android.content.Context;
import android.util.Log;




import com.example.dishdiary.Constants;
import com.example.dishdiary.data.model.CategoriesResponse;
import com.example.dishdiary.data.model.CountriesResponse;
import com.example.dishdiary.data.model.IngredientResponse;
import com.example.dishdiary.data.model.MealResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Observable;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api_Manager implements RemoteSource {
    private static final String TAG ="Api Client";
    private static Api_Manager instance = null;
    ApiService apiService;
    Context context;
    private Api_Manager(Context context){
        this.context = context;
        File cashDirectory = new File(context.getCacheDir(),"offline_cash_directory");
        Cache cash =  new Cache(cashDirectory,10*1024*1024);

        OkHttpClient okHttp = new OkHttpClient.Builder()
                .cache(cash)
                .addNetworkInterceptor(provideCacheInterceptor())
                .addInterceptor(provideOfflineCacheInterceptor())
                .build();

        Gson gson = new GsonBuilder().setLenient().create();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(okHttp)
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    private static Interceptor provideOfflineCacheInterceptor() {

        return chain -> {

            try {
                return chain.proceed(chain.request());
            }catch (Exception e){

                 CacheControl cacheControl = new CacheControl.Builder()
                         .onlyIfCached()
                         .maxStale(1,TimeUnit.DAYS)
                         .build();

                Request offlineRequest = chain.request().newBuilder()
                         .cacheControl(cacheControl)
                         .build();
                return chain.proceed(offlineRequest);
            }


        };
    }

    private static Interceptor provideCacheInterceptor() {
        return      new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {

                okhttp3.Response response = chain.proceed(chain.request());

                 CacheControl cacheControl = new  CacheControl.Builder()
                         .maxAge(4,TimeUnit.SECONDS)
                         .maxStale(10,TimeUnit.SECONDS).build();

                return response.newBuilder()
                        .header("Cache-Controller",cacheControl.toString()).build();
            }
        };
    }

    public static Api_Manager getInstance(Context context){
        if(instance == null){
            return new Api_Manager(context);
        }
        return instance;
    }

    @Override
    public Observable<MealResponse> getDailyMeal() {
        return apiService.getRandomMeal();
    }

    @Override
    public Observable<CategoriesResponse> getCategories() {
         return  apiService.getCategories();
   }

    @Override
    public Observable<CountriesResponse> getCountries() {
           return apiService.getCountries();
    }

    @Override
    public Observable<IngredientResponse> getIngredients() {
             return  apiService.getIngredients();
    }

    @Override
    public void filterByFirstLetter(String fLetterQuery,FilterNetworkDelegate callback) {
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
    public void filterByName(String mealName,FilterNetworkDelegate callback) {
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
              Log.i(TAG,"onFailure: from api manager");
          }
      });
    }

    @Override
    public void getMealByID(int mealId,FilterNetworkDelegate callback) {
        apiService.getMealInfo(mealId).enqueue(new Callback<MealResponse>() {
            @Override
            public void onResponse(Call<MealResponse> call, Response<MealResponse> response) {
                if (response.isSuccessful()){

                    callback.onGetMealByIdSuccess(response.body().getMeals());

                }
            }

            @Override
            public void onFailure(Call<MealResponse> call, Throwable t) {
                callback.onFilterFailure(t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void filterByCountry(String query, FilterNetworkDelegate callback) {
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
    public void filterByIngredient(String query, FilterNetworkDelegate callback) {
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
    public void filterByCategory(String query, FilterNetworkDelegate callback) {
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
