package com.example.dishdiary.ui.home_compomemts.presenter;

import androidx.lifecycle.LiveData;

import com.example.dishdiary.data.Repository.Repo;
import com.example.dishdiary.data.model.CategoriesResponse;
import com.example.dishdiary.data.model.CountriesResponse;
import com.example.dishdiary.data.model.MealResponse;
import com.example.dishdiary.data.model.dto.CategoryDTO;
import com.example.dishdiary.data.model.dto.CountryDTO;
import com.example.dishdiary.data.model.dto.IngredientDTO;
import com.example.dishdiary.data.model.dto.MealsItemDTO;
import com.example.dishdiary.data.remote.NetworkDelegate;
import com.example.dishdiary.ui.home_compomemts.view.IHomeFragment;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenter implements IHomePresenter , NetworkDelegate {

    Repo repo;

    IHomeFragment homeFragment;
    public HomePresenter(Repo repo,IHomeFragment homeFragment) {
        this.repo = repo;
        this.homeFragment = homeFragment;
    }

    @Override
    public void getDailyMeal() {
        Observable<MealResponse> dailyMeals = repo.getDailyMeal();
        dailyMeals.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mealResponse -> homeFragment.getDailyMeal(mealResponse.getMeals().get(0)),
                        error ->  homeFragment.appendConnectionError(error.getLocalizedMessage()));

    }

    @Override
    public void getCategories( ) {
        Observable<CategoriesResponse> categories = repo.getCategories();
        categories.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        categoriesResponse -> homeFragment.getCategoriesList(categoriesResponse.getCategoryList()),
                        error ->  homeFragment.appendConnectionError(error.getLocalizedMessage()));
    }

    @Override
    public void getCountries( ) {
        Observable<CountriesResponse> countries =    repo.getCountries();
        countries.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        countriesResponse ->
                                homeFragment.getCountriesList(countriesResponse.getCountries()),
                        error ->{
                           homeFragment.appendConnectionError(error.getLocalizedMessage());
                        });
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
       // homeFragment.getDailyMeal(dailyMeal);
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
