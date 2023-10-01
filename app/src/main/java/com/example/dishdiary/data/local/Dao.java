package com.example.dishdiary.data.local;


import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.dishdiary.data.model.dto.MealPlanDTO;
import com.example.dishdiary.data.model.dto.MealsItemDTO;

import java.util.List;

public interface Dao {

    @Query("SELECT * FROM meals")
    LiveData<List<MealsItemDTO>> getAllMeals();

    @Query("DELETE FROM meals")
    void clearAllMeals();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMeal(MealsItemDTO meal);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertFavMealList(List<MealsItemDTO> favMeals);



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPlanMeal(MealPlanDTO planMeal);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertPlanMealList(List<MealPlanDTO> planMeals);

    @Query("SELECT 1 FROM meals WHERE idMeal = :mealId")
    LiveData<Boolean> checkIfExist(String mealId);
    @Delete
    void deleteMeal(MealsItemDTO meal);

    @Delete
    void deletePlanMeal(MealPlanDTO planMeal);

    @Query("DELETE FROM mealPlan")
    void clearAllPlanMeals();

    @Query("SELECT * FROM mealPlan WHERE day = :day")
    LiveData<List<MealPlanDTO>> getAllPlanMealsByDay(String day);

    @Query("SELECT * FROM mealPlan")
    LiveData<List<MealPlanDTO>> getAllPlanMeals();

}
