package com.example.dishdiary.data.remote.authentication_remote;

import com.example.dishdiary.data.model.authDTO.AuthenticationPoJo;
import com.example.dishdiary.data.model.dto.MealPlanDTO;
import com.example.dishdiary.data.model.dto.MealsItemDTO;

import java.util.List;

public interface FirebaseSource {
    void logIn(AuthenticationPoJo authPojo,IFirebaseDelegate firebaseDelegate);
    void signUp(AuthenticationPoJo authPojo,IFirebaseDelegate firebaseDelegate);

    void uploadMealsList(String userEmail, List<MealPlanDTO> mealsPlanList, List<MealsItemDTO> mealsList,
                     IFirebaseDelegate firebaseDelegate);

    void downloadMeals(String userEmail, IFirebaseDelegate firebaseDelegate);
}
