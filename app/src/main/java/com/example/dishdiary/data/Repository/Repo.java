package com.example.dishdiary.data.Repository;

import androidx.lifecycle.LiveData;

import com.example.dishdiary.data.model.authDTO.AuthenticationPoJo;
import com.example.dishdiary.data.model.dto.MealPlanDTO;
import com.example.dishdiary.data.model.dto.MealsItemDTO;
import com.example.dishdiary.data.remote.FilterNetworkDelegate;
import com.example.dishdiary.data.remote.NetworkDelegate;
import com.example.dishdiary.data.remote.authentication_remote.IFirebaseDelegate;

import java.util.List;

public interface Repo {

    void getDailyMeal(NetworkDelegate callback);
    void getCategories(NetworkDelegate callback);
    void getCountries(NetworkDelegate callback);
    void getIngredients(NetworkDelegate callback);
    /*****************************************/
    void filterByFirstLetter(String fLetter, FilterNetworkDelegate callback);
    void filterByName(String mealName,FilterNetworkDelegate callback);
    void getMealByID(int mealId,FilterNetworkDelegate callback);
    void filterByCountry(String query,FilterNetworkDelegate callback);
    void filterByIngredient(String query,FilterNetworkDelegate callback);
    void filterByCategory(String query,FilterNetworkDelegate callback);


    /***********************************************/

    void insertMeal(MealsItemDTO meal);

    void deleteMeal(MealsItemDTO meal);

    LiveData<List<MealsItemDTO>> getAllMeals();
    LiveData<List<MealsItemDTO>> getCashedMealsList();


    void insertFavMealList(List<MealsItemDTO> favMeals);

    void clearAllFavoriteMeals();

    void insertPlanMeal(MealPlanDTO planMeal);

    void deletePlanMeal(MealPlanDTO planMeal);

    void insertPlanMealList(List<MealPlanDTO> planMeals);

    LiveData<List<MealPlanDTO>> getAllPlanMeals();

    LiveData<List<MealPlanDTO>> getAllPlanMealsByDay(String day);

    void clearAllPlanMeals();

    LiveData<Boolean> checkIfExist(String mealId);

    /***************************************/
    //AUTHENTICATION AND FIREBASE CLOUD
    void setLoginState(Boolean isLogged);
    boolean getLoginState();

    void logIn(AuthenticationPoJo authPojo, IFirebaseDelegate firebaseDelegate);
    void signUp(AuthenticationPoJo authPojo,IFirebaseDelegate firebaseDelegate);
    void uploadMealsList(String userEmail,List<MealPlanDTO> mealsPlanList,List<MealsItemDTO> mealsItemsList,
                         IFirebaseDelegate firebaseDelegate);

    void downloadMealsList(String userEmail,IFirebaseDelegate firebaseDelegate);
}
