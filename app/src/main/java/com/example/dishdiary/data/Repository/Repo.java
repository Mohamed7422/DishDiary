package com.example.dishdiary.data.Repository;

import androidx.lifecycle.LiveData;

import com.example.dishdiary.data.model.dto.MealPlanDTO;
import com.example.dishdiary.data.model.dto.MealsItemDTO;
import com.example.dishdiary.data.remote.NetworkDelegate;

import java.util.List;

public interface Repo {

    void getDailyMeal(NetworkDelegate callback);
    void getCategories(NetworkDelegate callback);
    void getCountries(NetworkDelegate callback);
    void getIngredients(NetworkDelegate callback);
    /*****************************************/
    void filterByFirstLetter(String fLetter,NetworkDelegate callback);
    void filterByName(String mealName,NetworkDelegate callback);
    void getMealByID(int mealId,NetworkDelegate callback);
    void filterByCountry(String query,NetworkDelegate callback);
    void filterByIngredient(String query,NetworkDelegate callback);
    void filterByCategory(String query,NetworkDelegate callback);


    /***********************************************/

    void insertMeal(MealsItemDTO meal);

    void deleteMeal(MealsItemDTO meal);

    LiveData<List<MealsItemDTO>> getAllMeals();

    void insertFavMealList(List<MealsItemDTO> favMeals);

    void clearAllFavoriteMeals();

    void insertPlanMeal(MealPlanDTO planMeal);

    void deletePlanMeal(MealPlanDTO planMeal);

    void insertPlanMealList(List<MealPlanDTO> planMeals);

    LiveData<List<MealPlanDTO>> getAllPlanMeals();

    LiveData<List<MealPlanDTO>> getAllPlanMealsByDay(String day);

    void clearAllPlanMeals();

    LiveData<Boolean> checkIfExist(String mealId);
}
