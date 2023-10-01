package com.example.dishdiary.data.network;


import com.example.dishdiary.data.model.CategoriesResponse;
import com.example.dishdiary.data.model.CountriesResponse;
import com.example.dishdiary.data.model.IngredientResponse;
import com.example.dishdiary.data.model.MealResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("random.php")
    Call<MealResponse> getRandomMeal();

    @GET("categories.php")
    Call<CategoriesResponse> getCategories();

    @GET("list.php?a=list")
    Call<CountriesResponse> getCountries();
    @GET("list.php?i=list")
    Call<IngredientResponse> getIngredients();

    @GET("search.php")
    Call<MealResponse> searchByFirstLetter(@Query("f")String fLetter);

    @GET("search.php")
    Call<MealResponse> getMealsByName(@Query("s") String mealName);

    @GET("lookup.php")
    Call<MealResponse> getMealInfo(@Query("i") String id);

    @GET("filter.php")
    Call<MealResponse> getMealByCountry(@Query("a")String country);
    @GET("filter.php")
    Call<MealResponse> getMealByIngredient(@Query("i")String ingredient);

    @GET("filter.php")
    Call<MealResponse> getMealByCategory(@Query("c")String category);



}
