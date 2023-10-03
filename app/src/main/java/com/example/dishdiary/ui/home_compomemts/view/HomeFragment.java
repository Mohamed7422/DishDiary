package com.example.dishdiary.ui.home_compomemts.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dishdiary.R;
import com.example.dishdiary.data.Repository.RepoImpl;
import com.example.dishdiary.data.local.LocalDataBaseImpl;
import com.example.dishdiary.data.model.dto.CategoryDTO;
import com.example.dishdiary.data.model.dto.CountryDTO;
import com.example.dishdiary.data.model.dto.MealsItemDTO;
import com.example.dishdiary.data.remote.Api_Manager;
import com.example.dishdiary.ui.MealDetailActivity;
import com.example.dishdiary.ui.favourite_compomemts.FavouriteMealsFragment;
import com.example.dishdiary.ui.home_compomemts.presenter.HomePresenter;
import com.example.dishdiary.ui.home_compomemts.presenter.IHomePresenter;
import com.example.dishdiary.ui.search_compomemts.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment  implements IHomeFragment ,OnItemClickListener {
    private final String TAG = "HOME Fragment Tag";

    RecyclerView categoryRecyclerView;
    RecyclerView countryRecyclerView;
    CategoryRecyclerAdapter categoryRecyclerAdapter;
    CountryRecyclerAdapter countryRecyclerAdapter;
    LinearLayoutManager categoryLayoutManager;
    LinearLayoutManager countryLayoutManager;

    ImageView dailyMealImg;
    TextView dailyMealName;
    ImageView addToFavouriteBtn;

    CardView cardDailyItem;


     IHomePresenter homePresenter;

    boolean clicked = false;


//create constructor if necessary

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        homePresenter = new HomePresenter(RepoImpl.getInstance(Api_Manager.getInstance(),
                LocalDataBaseImpl.getInstance(getContext())),this);


        homePresenter.getDailyMeal();
        homePresenter.getCategories();
        homePresenter.getCountries();

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initViews(view);
        setListeners();


        return view;
    }

    private void setListeners() {

    }

    private void initViews(View view) {


        dailyMealImg = view.findViewById(R.id.dailyImage);
        dailyMealName = view.findViewById(R.id.daily_meal_name);
        addToFavouriteBtn = view.findViewById(R.id.addToFavouriteBtn);

        categoryRecyclerView = view.findViewById(R.id.category_RV);

        cardDailyItem = view.findViewById(R.id.card_daily_item);

        //Recycler Initiation

        categoryRecyclerAdapter = new CategoryRecyclerAdapter(getContext(),new ArrayList<>(),this);
        categoryLayoutManager  = new LinearLayoutManager(getContext());
        categoryLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(categoryLayoutManager);

        countryRecyclerView = view.findViewById(R.id.country_RV);
        countryRecyclerAdapter = new CountryRecyclerAdapter(getContext(),new ArrayList<>(),this);
        countryLayoutManager  = new LinearLayoutManager(getContext());
        countryLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        countryRecyclerView.setLayoutManager(countryLayoutManager);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


    @Override
    public void getDailyMeal(MealsItemDTO mealsItemDTO) {

        Log.i(TAG,"Daily Meal " + mealsItemDTO.getStrArea());

         dailyMealName.setText(mealsItemDTO.getMealName());
         Glide.with(this)
                .load(mealsItemDTO.getStrMealThumb())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(dailyMealImg);

        cardDailyItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //open activity Detail
                startActivity(new Intent(getActivity(), MealDetailActivity.class));
            }
        });


        //implement add to favourite meal button
        addToFavouriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add the item to the database
                if (!clicked){
                    homePresenter.insertMeal(mealsItemDTO);
                    addToFavouriteBtn.setImageResource(R.drawable.baseline_favorite);
                    Toast.makeText(requireContext(),"Added To favourite",Toast.LENGTH_SHORT).show();
                    clicked = true;
                }else {
                    homePresenter.deleteMeal(mealsItemDTO);
                    addToFavouriteBtn.setImageResource(R.drawable.baseline_favorite_border);
                    Toast.makeText(requireContext(),"Removed From favourite",Toast.LENGTH_SHORT).show();
                   clicked = false;
                }

            }
        });

        //implement add to plan button



    }



    @Override
    public void getCategoriesList(List<CategoryDTO> categories) {
        Log.i(TAG,"categories " + categories.toString());
        //send the list to the adapter
        categoryRecyclerAdapter.setCategoriesList(categories);
        categoryRecyclerView.setAdapter(categoryRecyclerAdapter);

    }

    @Override
    public void getCountriesList(List<CountryDTO> countries) {
        Log.i(TAG,"countries " + countries.get(0).getStrArea().toString());

        countryRecyclerAdapter.setCountriesList(countries);
        countryRecyclerView.setAdapter(countryRecyclerAdapter);
    }

    @Override
    public void appendConnectionError(String errorMessage) {
        Log.i(TAG,"error " + errorMessage);
    }

    @Override
    public void onItemClick() {

    }
}