package com.example.dishdiary.ui.search_compomemts.presenter;



import com.example.dishdiary.data.Repository.Repo;
import com.example.dishdiary.data.model.CategoriesResponse;
import com.example.dishdiary.data.model.CountriesResponse;
import com.example.dishdiary.data.model.IngredientResponse;
import com.example.dishdiary.data.model.dto.CategoryDTO;
import com.example.dishdiary.data.model.dto.CountryDTO;
import com.example.dishdiary.data.model.dto.IngredientDTO;
import com.example.dishdiary.data.model.dto.MealsItemDTO;
import com.example.dishdiary.data.remote.FilterNetworkDelegate;
import com.example.dishdiary.data.remote.NetworkDelegate;
import com.example.dishdiary.ui.search_compomemts.view.ISearchFragment;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenter implements FilterNetworkDelegate , NetworkDelegate {

  Repo repo;
  ISearchFragment view;

    public SearchPresenter(Repo repo, ISearchFragment view) {
        this.repo = repo;
        this.view = view;
    }

    public void getCategories() {
        Observable<CategoriesResponse> categories = repo.getCategories();
        categories.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(categoriesResponse -> view.appendCategoriesResult(categoriesResponse.getCategoryList()),
                        error -> System.out.println(error+"onSearchPresenter"));
    }

    public void getCountries() {
      Observable<CountriesResponse>  countries =repo.getCountries();
      countries.subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(
                       countriesResponse -> view.appendCountriesResult(countriesResponse.getCountries())
               ,error -> System.out.println(error+"onSearchPresenter"));
    }

    public void getIngredients() {
        Observable<IngredientResponse> ingredients =   repo.getIngredients();
        ingredients.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ingredientResponse -> view.appendIngredientResult(ingredientResponse.getMeals())
                );
    }

    public void filterByCategory(String filterQuery) {
        repo.filterByCategory(filterQuery, this);
    }

    public void filterByCountry(String filterQuery) {
        repo.filterByCountry(filterQuery, this);
    }

    public void filterByIngredient(String filterQuery) {
        repo.filterByIngredient(filterQuery, this);
    }

    public void searchByLetter(String filterQuery) {
        repo.filterByFirstLetter(filterQuery, this);
    }

    public void searchByName(String filterQuery) {
        repo.filterByName(filterQuery, this);
    }

    public void getMealById(int mealId) {
        repo.getMealByID(mealId, this);
    }



    @Override
    public void onFilterSuccess(List<MealsItemDTO> meals) {
         view.appendSearchResult(meals);
    }

    @Override
    public void onFilterFailure(String errorMsg) {
    view.appendErrorMsg(errorMsg);
    }

    @Override
    public void onGetMealByIdSuccess(List<MealsItemDTO> mealsItemDTOList) {
       view.appendDetails(mealsItemDTOList);
    }

    @Override
    public void onMealCallSuccess(MealsItemDTO dailyMeal) {
      ///
    }

    @Override
    public void onMealCallFailure(String errorMsg) {

    }

    @Override
    public void onCategoryCallSuccess(List<CategoryDTO> categoriesList) {
//         view.appendCategoriesResult(categoriesList);
    }

    @Override
    public void onCategoryCallFailure(String errorMsg) {
//        view.appendErrorMsg(errorMsg);
    }

    @Override
    public void onCountryCallSuccess(List<CountryDTO> countriesList) {
//      view.appendCountriesResult(countriesList);
    }

    @Override
    public void onCountryCallFailure(String errorMsg) {
//        view.appendErrorMsg(errorMsg);
    }

    @Override
    public void onIngredientCallSuccess(List<IngredientDTO> ingredientsList) {
      view.appendIngredientResult(ingredientsList);
    }

    @Override
    public void onIngredientCallFailure(String errorMsg) {
        view.appendErrorMsg(errorMsg);
    }
}
