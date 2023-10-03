package com.example.dishdiary.ui.home_compomemts.presenter;

import androidx.lifecycle.LiveData;

import com.example.dishdiary.data.Repository.Repo;
import com.example.dishdiary.data.model.dto.CategoryDTO;
import com.example.dishdiary.data.model.dto.CountryDTO;
import com.example.dishdiary.data.model.dto.IngredientDTO;
import com.example.dishdiary.data.model.dto.MealsItemDTO;
import com.example.dishdiary.data.remote.NetworkDelegate;
import com.example.dishdiary.ui.home_compomemts.view.IHomeFragment;

import java.util.List;

public class HomePresenter implements IHomePresenter , NetworkDelegate {

    Repo repo;

    IHomeFragment homeFragment;
    public HomePresenter(Repo repo,IHomeFragment homeFragment) {
        this.repo = repo;
        this.homeFragment = homeFragment;
    }

    @Override
    public void getDailyMeal( ) {
     repo.getDailyMeal(this);

    }

    @Override
    public void getCategories( ) {
     repo.getCategories(this);
    }

    @Override
    public void getCountries( ) {
     repo.getCountries(this);
    }

    @Override
    public void insertMeal(MealsItemDTO meal) {
       repo.insertMeal(meal);
    }

    @Override
    public void deleteMeal(MealsItemDTO meal) {
        repo.deleteMeal(meal);
    }

    @Override
    public LiveData<Boolean> checkIfExist(String mealId) {
        return repo.checkIfExist(mealId);
    }

    /*******************************************************************/

    @Override
    public void onMealCallSuccess(MealsItemDTO dailyMeal) {
        System.out.println("daily Meal"+dailyMeal.getMealName());
        //get Data and send it to the view
        homeFragment.getDailyMeal(dailyMeal);
    }

    @Override
    public void onMealCallFailure(String errorMsg) {
        homeFragment.appendConnectionError(errorMsg);
    }

    @Override
    public void onCategoryCallSuccess(List<CategoryDTO> categoriesList) {
       homeFragment.getCategoriesList(categoriesList);
    }

    @Override
    public void onCategoryCallFailure(String errorMsg) {
       homeFragment.appendConnectionError(errorMsg);
    }

    @Override
    public void onCountryCallSuccess(List<CountryDTO> countriesList) {
    homeFragment.getCountriesList(countriesList);
    }

    @Override
    public void onCountryCallFailure(String errorMsg) {
    homeFragment.appendConnectionError(errorMsg);
    }

    @Override
    public void onIngredientCallSuccess(List<IngredientDTO> ingredientsList) {

    }

    @Override
    public void onIngredientCallFailure(String errorMsg) {

    }




}
