package com.example.dishdiary.data.Repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.dishdiary.data.local.LocalDB;
import com.example.dishdiary.data.model.dto.MealPlanDTO;
import com.example.dishdiary.data.model.dto.MealsItemDTO;
import com.example.dishdiary.data.remote.NetworkDelegate;
import com.example.dishdiary.data.remote.RemoteSource;

import java.util.List;

public class RepoImpl implements Repo {
    RemoteSource remoteSource;
    LocalDB localDB;


    private static RepoImpl instance =null;

    private RepoImpl(RemoteSource remoteSource, LocalDB localDB) {
     this.remoteSource = remoteSource;
     this.localDB = localDB;

    }

    public static synchronized RepoImpl getInstance(RemoteSource remoteSource, LocalDB localDB ){
        if (instance==null){
               instance = new RepoImpl(remoteSource,localDB);
        }
        return instance;

    }


    @Override
    public void getDailyMeal(NetworkDelegate callback) {
        remoteSource.getDailyMeal(callback);
    }

    @Override
    public void getCategories(NetworkDelegate callback) {
      remoteSource.getCategories(callback);

    }

    @Override
    public void getCountries(NetworkDelegate callback) {
     remoteSource.getCountries(callback);
    }

    @Override
    public void getIngredients(NetworkDelegate callback) {
     remoteSource.getIngredients(callback);
    }

    @Override
    public void filterByFirstLetter(String fLetter, NetworkDelegate callback) {
    remoteSource.filterByFirstLetter(fLetter,callback);
    }

    @Override
    public void filterByName(String mealName, NetworkDelegate callback) {
    remoteSource.filterByName(mealName,callback);
    }

    @Override
    public void getMealByID(int mealId, NetworkDelegate callback) {
    remoteSource.getMealByID(mealId,callback);
    }

    @Override
    public void filterByCountry(String query, NetworkDelegate callback) {
    remoteSource.filterByCountry(query,callback);
    }

    @Override
    public void filterByIngredient(String query, NetworkDelegate callback) {
    remoteSource.filterByIngredient(query,callback);
    }

    @Override
    public void filterByCategory(String query, NetworkDelegate callback) {
    remoteSource.filterByCategory(query,callback);
    }

    @Override
    public void insertMeal(MealsItemDTO meal) {
    localDB.insertMeal(meal);
    }

    @Override
    public void deleteMeal(MealsItemDTO meal) {
    localDB.deleteMeal(meal);
    }

    @Override
    public LiveData<List<MealsItemDTO>> getAllMeals() {
        return localDB.getAllMeals();
    }

    @Override
    public void insertFavMealList(List<MealsItemDTO> favMeals) {
      localDB.insertFavMealList(favMeals);
    }

    @Override
    public void clearAllFavoriteMeals() {
     localDB.clearAllFavoriteMeals();
    }

    @Override
    public void insertPlanMeal(MealPlanDTO planMeal) {
        localDB.insertPlanMeal(planMeal);
    }

    @Override
    public void deletePlanMeal(MealPlanDTO planMeal) {
       localDB.deletePlanMeal(planMeal);
    }

    @Override
    public void insertPlanMealList(List<MealPlanDTO> planMeals) {
       localDB.insertPlanMealList(planMeals);
    }

    @Override
    public LiveData<List<MealPlanDTO>> getAllPlanMeals() {
        return localDB.getAllPlanMeals();
    }

    @Override
    public LiveData<List<MealPlanDTO>> getAllPlanMealsByDay(String day) {
        return localDB.getAllPlanMealsByDay(day);
    }

    @Override
    public void clearAllPlanMeals() {
         localDB.clearAllPlanMeals();
    }

    @Override
    public LiveData<Boolean> checkIfExist(String mealId) {
        return localDB.checkIfExist(mealId);
    }
}
