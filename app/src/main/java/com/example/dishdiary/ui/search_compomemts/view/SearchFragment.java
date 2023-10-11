package com.example.dishdiary.ui.search_compomemts.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dishdiary.Constants;
import com.example.dishdiary.R;
import com.example.dishdiary.data.Repository.Repo;
import com.example.dishdiary.data.Repository.RepoImpl;
import com.example.dishdiary.data.local.LocalDataBaseImpl;
import com.example.dishdiary.data.model.CategoriesResponse;
import com.example.dishdiary.data.model.authDTO.AuthSharedPref;
import com.example.dishdiary.data.model.dto.CategoryDTO;
import com.example.dishdiary.data.model.dto.CountryDTO;
import com.example.dishdiary.data.model.dto.IngredientDTO;
import com.example.dishdiary.data.model.dto.MealsItemDTO;
import com.example.dishdiary.data.remote.Api_Manager;
import com.example.dishdiary.data.remote.authentication_remote.FireBaseManager;
import com.example.dishdiary.ui.home_compomemts.view.CountryRecyclerAdapter;
import com.example.dishdiary.ui.meal_details_components.view.MealDetailActivity;
import com.example.dishdiary.ui.search_compomemts.presenter.SearchPresenter;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;


public class SearchFragment extends Fragment implements ISearchFragment, SearchRecyclerAdapter.OnMealItemClickListener,
        SearchRecyclerAdapter.OnCountryItemClickListener,SearchRecyclerAdapter.OnIngredientItemClickListener,
        SearchRecyclerAdapter.OnCategoryItemClickListener{

    SearchPresenter searchPresenter;
    SearchView searchView;
    ChipGroup chipGroup;

    RecyclerView mealsRecyclerView;

    SearchRecyclerAdapter mealsSearchAdapter,categorySearchAdapter
                          ,countrySearchAdapter,ingredientsSearchAdapter;

    List<MealsItemDTO> meals = new ArrayList<>();
    List<CountryDTO> countries = new ArrayList<>();
    List<IngredientDTO> ingredients = new ArrayList<>();
    List<CategoryDTO> categories = new ArrayList<>();

    Disposable ingredientSearch,ingredientSearchTwo;

    List<IngredientDTO> searchIngredients = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        searchPresenter = new SearchPresenter(RepoImpl.getInstance(Api_Manager.getInstance(), LocalDataBaseImpl.getInstance(requireContext()),
                AuthSharedPref.getInstance(requireContext()), FireBaseManager.getInstance()),this);

        initViews(view);
        setListeners();

        /*
        * get the clicked item from home fragment
        * and swap the recycler based on that
        *  */

        int state = -1;
        String filter="";
        Bundle arguments = getArguments();
        if (arguments != null){
            filter = arguments.getString("filter");
            //System.out.println("Filtered comes from home"+ filter);
            state = arguments.getInt("state");
            if (!filter.isEmpty()){
                if (state == Constants.CATEGORIES)
                {
                    mealsRecyclerView.swapAdapter(mealsSearchAdapter,true);
                    searchPresenter.filterByCategory(filter);


                } else if (state == Constants.COUNTRIES) {
                    mealsRecyclerView.swapAdapter(mealsSearchAdapter,true);
                    searchPresenter.filterByCountry(filter);


                }
            }
        }


        return view;
    }



    private void initViews(View view) {

        searchView = view.findViewById(R.id.search_id);
        chipGroup  = view.findViewById(R.id.categoryGroup);

        mealsRecyclerView = view.findViewById(R.id.search_fragment_RV);

       mealsSearchAdapter = new SearchRecyclerAdapter(getContext(),meals,countries,categories,ingredients,this,this,this,this, Constants.MEALS);
       countrySearchAdapter = new SearchRecyclerAdapter(getContext(),meals,countries,categories,ingredients,this,this,this,this, Constants.COUNTRIES);
       ingredientsSearchAdapter = new SearchRecyclerAdapter(getContext(),meals,countries,categories,ingredients,this,this,this,this, Constants.INGREDIENTS);
       categorySearchAdapter = new SearchRecyclerAdapter(getContext(),meals,countries,categories,ingredients,this,this,this,this, Constants.CATEGORIES);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),3);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mealsRecyclerView.setLayoutManager(layoutManager);
        mealsRecyclerView.setAdapter(mealsSearchAdapter);

    }


    private void setListeners() {
        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, int checkedId) {
                Chip chip = group.findViewById(checkedId);
                if (chip!= null){

                    if (checkedId == R.id.country_id){
                        if (ingredientSearch != null){
                            if (ingredientSearchTwo != null){
                                ingredientSearchTwo.dispose();
                                ingredientSearchTwo = null;
                            }
                            ingredientSearch.dispose();
                            ingredientSearch = null;

                            bindSearch();

                        }
                        mealsRecyclerView.swapAdapter(countrySearchAdapter,true);
                        searchPresenter.getCountries();
                    } else if (checkedId == R.id.ingredient_Id) {
                        mealsRecyclerView.swapAdapter(ingredientsSearchAdapter,true);
                        searchPresenter.getIngredients();
                        ingredientSearch = Observable.create(new ObservableOnSubscribe<String>() {
                                    @Override
                                    public void subscribe(ObservableEmitter<String> emit) {
                                        searchView.setOnQueryTextListener(new  SearchView.OnQueryTextListener() {
                                            @Override
                                            public boolean onQueryTextSubmit(String query) {
                                                return false;
                                            }

                                            @Override
                                            public boolean onQueryTextChange(String newText) {
                                                emit.onNext(newText);
                                                return true;
                                            }
                                        });
                                    }
                                }).debounce(1, TimeUnit.SECONDS)
                                .subscribe(rule -> {
                                    ingredientSearchTwo = Observable.fromIterable(ingredients)
                                            .subscribeOn(AndroidSchedulers.mainThread())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .filter(s -> s.getStrIngredient().toLowerCase().contains(rule.toLowerCase()))
                                            .subscribe(s -> {
                                                searchIngredients.add(s);
                                                ingredientsSearchAdapter.setIngredientList(searchIngredients);
                                            });
                                    searchIngredients.clear();
                                });
                    }else if (checkedId == R.id.category_id) {

                        if (ingredientSearch != null){
                            if (ingredientSearchTwo != null){
                                ingredientSearchTwo.dispose();
                                ingredientSearchTwo = null;
                            }
                            ingredientSearch.dispose();
                            ingredientSearch = null;

                            bindSearch();

                        }
                        mealsRecyclerView.swapAdapter(categorySearchAdapter,true);
                        searchPresenter.getCategories();

                    }

                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (Objects.equals(query,"")){
                    return false;
                }
                searchPresenter.searchByName(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (Objects.equals(newText,"")){
                    return false;
                }
                mealsRecyclerView.swapAdapter(mealsSearchAdapter,true);
                searchPresenter.searchByLetter(newText);
                return true;
            }
        });

    }

    private void bindSearch(){
        searchView.setOnQueryTextListener(new  SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (Objects.equals(query, "")) {
                    return false;
                }
                searchPresenter.searchByName(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (Objects.equals(newText, "")) {
                    return false;
                }
                mealsRecyclerView.swapAdapter(mealsSearchAdapter, true);
                searchPresenter.searchByLetter(newText);
                return true;
            }
        });
    }

    @Override
    public void appendSearchResult(List<MealsItemDTO> mealsItemDTOList) {
         meals = mealsItemDTOList;
        mealsSearchAdapter.setMealsItemsList(meals);
        System.out.println("Meals from Search"+ mealsItemDTOList.size());
    }

    @Override
    public void appendCategoriesResult(List<CategoryDTO> categoryDTOList) {
         categories = categoryDTOList;
         categorySearchAdapter.setCategoriesList(categories);
        System.out.println("categoryDTOList from Search"+ categoryDTOList.size());
    }

    @Override
    public void appendCountriesResult(List<CountryDTO> countryDTOList) {
          countries = countryDTOList;
           countrySearchAdapter.setCountriesList(countries);
        System.out.println("countryDTOList from Search"+ countryDTOList.get(1));
    }

    @Override
    public void appendIngredientResult(List<IngredientDTO> ingredientDTOList) {
         ingredients = ingredientDTOList;
        ingredientsSearchAdapter.setIngredientList(ingredients);
    }

    @Override
    public void appendErrorMsg(String msg) {
        Toast.makeText(getContext(), "Check Your Internet Connection!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void appendDetails(List<MealsItemDTO> mealsItemDTOList) {
          //open details page activity when click
        Intent intent = new Intent(getActivity(), MealDetailActivity.class);
        intent.putExtra("mealItem",mealsItemDTOList.get(0));
        startActivity(intent);
    }

    @Override
    public void onCountryClickListener(CountryDTO country) {
        mealsRecyclerView.swapAdapter(mealsSearchAdapter,true);
        searchPresenter.filterByCountry(country.getStrArea());
    }

    @Override
    public void onIngredientClickListener(IngredientDTO ingredient) {
        mealsRecyclerView.swapAdapter(mealsSearchAdapter,true);
        searchPresenter.filterByIngredient(ingredient.getStrIngredient());
    }

    @Override
    public void onCategoryClickListener(CategoryDTO category) {
        mealsRecyclerView.swapAdapter(mealsSearchAdapter,true);
       searchPresenter.filterByCategory(category.getStrCategory());
    }

    @Override
    public void onMealItemClickListener(MealsItemDTO mealsItemDTO) {

        searchPresenter.getMealById(Integer.parseInt(mealsItemDTO.getIdMeal()));

    }
}