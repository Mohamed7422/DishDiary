package com.example.dishdiary.ui.weakly_plan_compomemts.presenter;

import androidx.lifecycle.LiveData;

import com.example.dishdiary.data.Repository.Repo;
import com.example.dishdiary.data.model.dto.MealPlanDTO;
import com.example.dishdiary.data.model.dto.MealsItemDTO;
import com.example.dishdiary.data.remote.authentication_remote.IFirebaseDelegate;
import com.example.dishdiary.ui.weakly_plan_compomemts.view.IWeakPlan;

import java.util.List;

public class WeakPlanPresenter implements IWeakPlanPresenter, IFirebaseDelegate {

    IWeakPlan view;
    Repo repo;

    public WeakPlanPresenter(IWeakPlan view, Repo repo) {
        this.view = view;
        this.repo = repo;

    }


    @Override
    public LiveData<List<MealPlanDTO>> getMealsByDay(String day) {
        return repo.getAllPlanMealsByDay(day) ;
    }

    @Override
    public void downloadPlanMeals(String userEmail) {
        repo.downloadMealsList(userEmail,this);
    }

    @Override
    public void deleteFromPlanMeals(MealPlanDTO mealPlanDTO) {
        repo.deletePlanMeal(mealPlanDTO);
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure(String errorMsg) {

    }

    @Override
    public void onDownloadSuccess( List<MealsItemDTO> mealsList,List<MealPlanDTO> mealsPlanList) {
       repo.insertPlanMealList(mealsPlanList);
       getMealsByDay("Saturday");
    }
}
