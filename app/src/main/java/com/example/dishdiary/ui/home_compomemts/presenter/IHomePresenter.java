package com.example.dishdiary.ui.home_compomemts.presenter;

import androidx.lifecycle.LiveData;

import com.example.dishdiary.data.model.dto.MealsItemDTO;
import com.example.dishdiary.data.remote.NetworkDelegate;

public interface IHomePresenter {

    //get Random Meal
    void getDailyMeal();
    //get Category List
    void getCategories();
    //get Countries list
    void getCountries();

    void insertMeal(MealsItemDTO meal);

    void deleteMeal(MealsItemDTO meal);
    LiveData<Boolean> checkIfExist(String mealId);



}
