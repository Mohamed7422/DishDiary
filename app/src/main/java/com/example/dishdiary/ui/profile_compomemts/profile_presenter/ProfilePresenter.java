package com.example.dishdiary.ui.profile_compomemts.profile_presenter;

import androidx.lifecycle.LiveData;

import com.example.dishdiary.data.Repository.Repo;
import com.example.dishdiary.data.model.dto.MealPlanDTO;
import com.example.dishdiary.data.model.dto.MealsItemDTO;
import com.example.dishdiary.data.remote.authentication_remote.IFirebaseDelegate;
import com.example.dishdiary.ui.profile_compomemts.view.IProfileV;

import java.util.List;

public class ProfilePresenter implements IFirebaseDelegate {



    Repo repo;
    IProfileV view;
    public ProfilePresenter(Repo repo, IProfileV view) {
        this.repo = repo;
        this.view = view;
    }

   private int signOutDetector = -1;


    public LiveData<List<MealsItemDTO>> getCashedFavMeals() {
        return repo.getCashedMealsList();
    }
    public LiveData<List<MealPlanDTO>> getCashedMealsPlanned() {
        return repo.getAllPlanMeals();
    }

    public void uploadMeals(String userEmail, List<MealPlanDTO> mealPlanDTOList, List<MealsItemDTO> mealsItemDTOS , int signOut) {
        signOutDetector= signOut;
        repo.uploadMealsList(userEmail, mealPlanDTOList, mealsItemDTOS, this);
    }

    public void clearLocalDatabase(){
        repo.clearAllFavoriteMeals();
        repo.clearAllPlanMeals();
    }

    @Override
    public void onSuccess() {
        if (signOutDetector ==1){
            clearLocalDatabase();
            view.signOut();
        } else if (signOutDetector ==0) {
            view.appendErrorMsg("Uploaded Successfully");

        }
    }

    @Override
    public void onFailure(String errorMsg) {
        view.appendErrorMsg(errorMsg);
    }

    @Override
    public void onDownloadSuccess(List<MealsItemDTO> mealsList,List<MealPlanDTO> mealsPlanList) {
           //not here
    }
}
