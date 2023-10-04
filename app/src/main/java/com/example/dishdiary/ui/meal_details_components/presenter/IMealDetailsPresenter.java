package com.example.dishdiary.ui.meal_details_components.presenter;

import androidx.lifecycle.LiveData;

import com.example.dishdiary.data.model.dto.MealPlanDTO;
import com.example.dishdiary.data.model.dto.MealsItemDTO;

public interface IMealDetailsPresenter {
    void addMealToFavourite(MealsItemDTO mealsItemDTO);
    void removeMealFromFavourite(MealsItemDTO mealsItemDTO);

    public LiveData<Boolean> checkExistence(String mealId);

    void addToWeakPlan(MealPlanDTO mealPlanDTO);

}
