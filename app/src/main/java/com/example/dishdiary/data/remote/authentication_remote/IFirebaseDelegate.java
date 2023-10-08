package com.example.dishdiary.data.remote.authentication_remote;

import com.example.dishdiary.data.model.dto.MealPlanDTO;
import com.example.dishdiary.data.model.dto.MealsItemDTO;

import java.util.List;

public interface IFirebaseDelegate {
    void onSuccess();
    void onFailure(String errorMsg);
    void onDownloadSuccess( List<MealsItemDTO> mealsList,List<MealPlanDTO> mealsPlanList);
}
