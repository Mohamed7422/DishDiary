package com.example.dishdiary.ui.favourite_compomemts.favourite_presenter;

import com.example.dishdiary.data.model.dto.MealPlanDTO;
import com.example.dishdiary.data.model.dto.MealsItemDTO;

public interface IPresenter {

    void getFavouriteMeals();
    public void downloadMeals(String userEmail);
    void deleteFromFavMeals(MealsItemDTO mealsItemDTO);
}
