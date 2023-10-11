package com.example.dishdiary.data.remote;

import com.example.dishdiary.data.model.CategoriesResponse;
import com.example.dishdiary.data.model.CountriesResponse;
import com.example.dishdiary.data.model.IngredientResponse;
import com.example.dishdiary.data.model.MealResponse;
import com.example.dishdiary.data.model.dto.MealPlanDTO;

import io.reactivex.rxjava3.core.Observable;

public interface RemoteSource {
    //make retrofit builder
    Observable<MealResponse> getDailyMeal();
    Observable<CategoriesResponse> getCategories();
    Observable<CountriesResponse> getCountries();
    Observable<IngredientResponse> getIngredients();
    /*****************************************/
    void filterByFirstLetter(String fLetter,FilterNetworkDelegate callback);
    void filterByName(String mealName,FilterNetworkDelegate callback);
    void getMealByID(int mealId,FilterNetworkDelegate callback);
    void filterByCountry(String query,FilterNetworkDelegate callback);
    void filterByIngredient(String query,FilterNetworkDelegate callback);
    void filterByCategory(String query,FilterNetworkDelegate callback);
}
