package com.example.dishdiary.data.Repository;

import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;

import com.example.dishdiary.data.local.LocalDB;
import com.example.dishdiary.data.model.authDTO.AuthenticationPoJo;
import com.example.dishdiary.data.model.authDTO.ISharedPref;
import com.example.dishdiary.data.model.dto.MealPlanDTO;
import com.example.dishdiary.data.model.dto.MealsItemDTO;
import com.example.dishdiary.data.remote.FilterNetworkDelegate;
import com.example.dishdiary.data.remote.NetworkDelegate;
import com.example.dishdiary.data.remote.RemoteSource;
import com.example.dishdiary.data.remote.authentication_remote.FirebaseSource;
import com.example.dishdiary.data.remote.authentication_remote.IFirebaseDelegate;

import java.util.List;

public class RepoImpl implements Repo {
    RemoteSource remoteSource;
    LocalDB localDB;
    ISharedPref sharedPref;
    FirebaseSource fbSource;



    private static RepoImpl instance =null;

    private RepoImpl(RemoteSource remoteSource, LocalDB localDB,ISharedPref sharedPref,FirebaseSource fbSource) {
     this.remoteSource = remoteSource;
     this.localDB = localDB;
     this.sharedPref = sharedPref;
     this.fbSource = fbSource;

    }

    public static synchronized RepoImpl getInstance(RemoteSource remoteSource, LocalDB localDB, ISharedPref sharedPref,
                                                    FirebaseSource fbSource){
        if (instance==null){
               instance = new RepoImpl(remoteSource,localDB,sharedPref,fbSource);
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
    public void filterByFirstLetter(String fLetter, FilterNetworkDelegate callback) {
    remoteSource.filterByFirstLetter(fLetter,callback);
    }

    @Override
    public void filterByName(String mealName, FilterNetworkDelegate callback) {
    remoteSource.filterByName(mealName,callback);
    }

    @Override
    public void getMealByID(int mealId, FilterNetworkDelegate callback) {
    remoteSource.getMealByID(mealId,callback);
    }

    @Override
    public void filterByCountry(String query, FilterNetworkDelegate callback) {
    remoteSource.filterByCountry(query,callback);
    }

    @Override
    public void filterByIngredient(String query, FilterNetworkDelegate callback) {
    remoteSource.filterByIngredient(query,callback);
    }

    @Override
    public void filterByCategory(String query, FilterNetworkDelegate callback) {
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
    public LiveData<List<MealsItemDTO>> getCashedMealsList() {
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


    @Override
    public void setLoginState(Boolean isLogged) {
        sharedPref.setLoginStatus(isLogged);
    }

    @Override
    public boolean getLoginState() {
        return sharedPref.getLoginState();
    }

    @Override
    public void logIn(AuthenticationPoJo authPojo, IFirebaseDelegate firebaseDelegate) {
      fbSource.logIn(authPojo,firebaseDelegate);
    }

    @Override
    public void signUp(AuthenticationPoJo authPojo, IFirebaseDelegate firebaseDelegate) {
      fbSource.signUp(authPojo,firebaseDelegate);
    }

    @Override
    public void uploadMealsList(String userEmail, List<MealPlanDTO> mealsPlanList, List<MealsItemDTO> mealsItemsList, IFirebaseDelegate firebaseDelegate) {
       fbSource.uploadMealsList(userEmail,mealsPlanList,mealsItemsList,firebaseDelegate);
    }

    @Override
    public void downloadMealsList(String userEmail, IFirebaseDelegate firebaseDelegate) {
       fbSource.downloadMeals(userEmail,firebaseDelegate);
    }
}
