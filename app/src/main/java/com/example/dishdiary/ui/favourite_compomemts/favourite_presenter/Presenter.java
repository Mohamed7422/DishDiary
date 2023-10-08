package com.example.dishdiary.ui.favourite_compomemts.favourite_presenter;

import com.example.dishdiary.data.Repository.Repo;
import com.example.dishdiary.data.model.dto.MealPlanDTO;
import com.example.dishdiary.data.model.dto.MealsItemDTO;
import com.example.dishdiary.data.remote.authentication_remote.IFirebaseDelegate;
import com.example.dishdiary.ui.favourite_compomemts.view.IFavouriteMeals;

import java.util.List;

public class Presenter implements IPresenter, IFirebaseDelegate {


    Repo repo;
    IFavouriteMeals view;

    public Presenter(Repo repo, IFavouriteMeals view) {
        this.repo = repo;
        this.view = view;
    }

    @Override
    public void getFavouriteMeals() {
      view.getFavouriteMeals(repo.getAllMeals());
    }

    public void downloadMeals(String userEmail){
        repo.downloadMealsList(userEmail , this);
    }

    @Override
    public void deleteFromFavMeals(MealsItemDTO mealsItemDTO) {
        repo.deleteMeal(mealsItemDTO);
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure(String errorMsg) {

    }

    @Override
    public void onDownloadSuccess( List<MealsItemDTO> mealsList,List<MealPlanDTO> mealsPlanList) {
         repo.insertFavMealList(mealsList);
         getFavouriteMeals();
    }
}
