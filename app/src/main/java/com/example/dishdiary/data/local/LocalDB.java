package com.example.dishdiary.data.local;

import androidx.lifecycle.LiveData;

import com.example.dishdiary.data.model.dto.MealPlanDTO;
import com.example.dishdiary.data.model.dto.MealsItemDTO;

import java.util.List;

public interface LocalDB {

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
