package com.example.dishdiary.ui.meal_details_components.presenter;

import androidx.lifecycle.LiveData;

import com.example.dishdiary.data.Repository.Repo;
import com.example.dishdiary.data.model.dto.MealPlanDTO;
import com.example.dishdiary.data.model.dto.MealsItemDTO;


public class MealsDetailsPresenter implements IMealDetailsPresenter{


    private  Repo repo;


    public MealsDetailsPresenter(Repo repo) {
        this.repo = repo;

    }


    @Override
    public void addMealToFavourite(MealsItemDTO mealsItemDTO) {
        repo.insertMeal(mealsItemDTO);

    }

    @Override
    public void removeMealFromFavourite(MealsItemDTO mealsItemDTO) {
        repo.deleteMeal(mealsItemDTO);

    }

    @Override
    public LiveData<Boolean> checkExistence(String mealId) {
        return repo.checkIfExist(mealId);
    }

    @Override
    public void addToWeakPlan(MealPlanDTO mealPlanDTO) {
        repo.insertPlanMeal(mealPlanDTO);
    }
}
