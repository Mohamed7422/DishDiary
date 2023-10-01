package com.example.dishdiary.data.local;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.dishdiary.data.model.dto.MealPlanDTO;
import com.example.dishdiary.data.model.dto.MealsItemDTO;

import java.util.List;

public class LocalDataBaseImpl implements LocalDB{


    private Dao dao;

    private static LocalDataBaseImpl instance = null;
    private LocalDataBaseImpl(Context context) {
         dao = DatabaseApp.getInstance(context.getApplicationContext()).dao();
    }

    public static synchronized LocalDataBaseImpl getInstance(Context context){
        if (instance == null){
            instance = new LocalDataBaseImpl(context);
        }
        return instance;
    }

    @Override
    public void insertMeal(MealsItemDTO meal) {
        new Thread(
                () ->dao.insertMeal(meal)
        ).start();
    }

    @Override
    public void deleteMeal(MealsItemDTO meal) {
             new Thread(
                     () -> dao.deleteMeal(meal)
             ).start();
    }

    @Override
    public LiveData<List<MealsItemDTO>> getAllMeals() {
        return dao.getAllMeals();
    }

    @Override
    public void insertFavMealList(List<MealsItemDTO> favMeals) {
           new Thread(
                   () -> dao.insertFavMealList(favMeals)
           ).start();
    }

    @Override
    public void clearAllFavoriteMeals() {
        new Thread(
                () -> dao.clearAllMeals()
        ).start();
    }

    @Override
    public void insertPlanMeal(MealPlanDTO planMeal) {
        new Thread(
                () -> dao.insertPlanMeal(planMeal)
        ).start();
    }

    @Override
    public void deletePlanMeal(MealPlanDTO planMeal) {
        new Thread(
                () -> dao.deletePlanMeal(planMeal)
        ).start();
    }

    @Override
    public void insertPlanMealList(List<MealPlanDTO> planMeals) {
        new Thread(
                () -> dao.insertPlanMealList(planMeals)
        ).start();
    }

    @Override
    public LiveData<List<MealPlanDTO>> getAllPlanMeals() {
        return dao.getAllPlanMeals();
    }

    @Override
    public LiveData<List<MealPlanDTO>> getAllPlanMealsByDay(String day) {
        return dao.getAllPlanMealsByDay(day);
    }

    @Override
    public void clearAllPlanMeals() {
        new Thread(
                () -> dao.clearAllPlanMeals()
        ).start();
    }

    @Override
    public LiveData<Boolean> checkIfExist(String mealId) {
        return dao.checkIfExist(mealId);
    }
}
