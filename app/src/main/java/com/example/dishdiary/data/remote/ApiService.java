package com.example.dishdiary.data.remote;


import com.example.dishdiary.data.model.CategoriesResponse;
import com.example.dishdiary.data.model.CountriesResponse;
import com.example.dishdiary.data.model.IngredientResponse;
import com.example.dishdiary.data.model.MealResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("random.php")
    Observable<MealResponse> getRandomMeal();

    @GET("categories.php")
    Observable<CategoriesResponse> getCategories();

    @GET("list.php?a=list")
    Observable<CountriesResponse> getCountries();
    @GET("list.php?i=list")
    Observable<IngredientResponse> getIngredients();

    @GET("search.php")
    Call<MealResponse> searchByFirstLetter(@Query("f")String fLetter);

    @GET("search.php")
    Call<MealResponse> getMealsByName(@Query("s") String mealName);

    @GET("lookup.php")
    Call<MealResponse> getMealInfo(@Query("i") int id);

    @GET("filter.php")
    Call<MealResponse> getMealByCountry(@Query("a")String country);
    @GET("filter.php")
    Call<MealResponse> getMealByIngredient(@Query("i")String ingredient);

    @GET("filter.php")
    Call<MealResponse> getMealByCategory(@Query("c")String category);



}
