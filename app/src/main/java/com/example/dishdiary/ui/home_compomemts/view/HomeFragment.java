package com.example.dishdiary.ui.home_compomemts.view;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dishdiary.Constants;
import com.example.dishdiary.R;
import com.example.dishdiary.data.Repository.RepoImpl;
import com.example.dishdiary.data.local.LocalDataBaseImpl;
import com.example.dishdiary.data.model.authDTO.AuthSharedPref;
import com.example.dishdiary.data.model.dto.CategoryDTO;
import com.example.dishdiary.data.model.dto.CountryDTO;
import com.example.dishdiary.data.model.dto.MealsItemDTO;
import com.example.dishdiary.data.remote.Api_Manager;
import com.example.dishdiary.data.remote.authentication_remote.FireBaseManager;
import com.example.dishdiary.ui.SplashActivity;
import com.example.dishdiary.ui.meal_details_components.view.MealDetailActivity;
import com.example.dishdiary.ui.home_compomemts.presenter.HomePresenter;
import com.example.dishdiary.ui.home_compomemts.presenter.IHomePresenter;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;


import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment  implements IHomeFragment , CategoryRecyclerAdapter.OnCategoryItemClickListener,
        CountryRecyclerAdapter.OnCountryItemClickListener {
    private final String TAG = "HOME Fragment Tag";

    RecyclerView categoryRecyclerView;
    RecyclerView countryRecyclerView;
    CategoryRecyclerAdapter categoryRecyclerAdapter;
    CountryRecyclerAdapter countryRecyclerAdapter;
    LinearLayoutManager categoryLayoutManager;
    LinearLayoutManager countryLayoutManager;


    ImageView dailyMealImg;
    TextView dailyMealName;
    TextView daily_meal,category,country;
    ImageView addToFavouriteBtn;
    ImageView no_connection_img;

    CardView cardDailyItem;
    ShimmerFrameLayout shimmerDailyMeal,category_shimmer_container,country_shimmer_container;



     IHomePresenter homePresenter;
    public static boolean dailyMealIndicator = false;

    boolean clicked = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        homePresenter = new HomePresenter(RepoImpl.getInstance(Api_Manager.getInstance(requireContext()), LocalDataBaseImpl.getInstance(requireContext()),
                AuthSharedPref.getInstance(requireContext()), FireBaseManager.getInstance()),this);


        homePresenter.getDailyMeal();
        homePresenter.getCategories();
        homePresenter.getCountries();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initViews(view);

        return view;
    }



    private void initViews(View view) {


        dailyMealImg = view.findViewById(R.id.dailyImage);
        dailyMealName = view.findViewById(R.id.daily_meal_name);
        addToFavouriteBtn = view.findViewById(R.id.addToFavouriteBtn);

        categoryRecyclerView = view.findViewById(R.id.category_RV);

        cardDailyItem = view.findViewById(R.id.card_daily_item);

        no_connection_img = view.findViewById(R.id.no_connection_img);
        daily_meal = view.findViewById(R.id.daily_meal);
        category = view.findViewById(R.id.category);
        country = view.findViewById(R.id.country);
        shimmerDailyMeal = view.findViewById(R.id.shimmerDailyMeal);
        category_shimmer_container = view.findViewById(R.id.category_shimmer_container);
        country_shimmer_container = view.findViewById(R.id.country_shimmer_container);


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

        no_connection_img.setVisibility(View.GONE);
        appendViews();

        Log.i(TAG,"Daily Meal " + mealsItemDTO.getStrArea());
        shimmerDailyMeal.setVisibility(View.GONE);

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
                    //send the object to meal details activity
                    Intent intent = new Intent(getActivity(),MealDetailActivity.class);
                    intent.putExtra("mealItem",mealsItemDTO);
                    startActivity(intent);
                }
            });


            //implement add to favourite meal button
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            if (!dailyMealIndicator){
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
                dailyMealIndicator =true;
            }


        }else {
               addToFavouriteBtn.setOnClickListener(item ->{
                   Snackbar snackbar  = Snackbar.make(getView(),"You have to Log In",Snackbar.LENGTH_SHORT);
                   snackbar.setAction(R.string.log_in, new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           Intent intent = new Intent(getActivity(), SplashActivity.class);
                           startActivity(intent);
                       }
                   });

                   snackbar.show();
               });
        }

    }



    @Override
    public void getCategoriesList(List<CategoryDTO> categories) {
        no_connection_img.setVisibility(View.GONE);
        appendViews();
        Log.i(TAG,"categories " + categories.toString());
        //shimmerFrameLayout.setVisibility(View.GONE);
        category_shimmer_container.setVisibility(View.GONE);
        //send the list to the adapter
        categoryRecyclerAdapter.setCategoriesList(categories);
        categoryRecyclerView.setAdapter(categoryRecyclerAdapter);

    }

    @Override
    public void getCountriesList(List<CountryDTO> countries) {
        no_connection_img.setVisibility(View.GONE);
        appendViews();
        country_shimmer_container.setVisibility(View.GONE);

        Log.i(TAG,"countries " + countries.get(0).getStrArea().toString());

        countryRecyclerAdapter.setCountriesList(countries);
        countryRecyclerView.setAdapter(countryRecyclerAdapter);
    }

    @Override
    public void appendConnectionError(String errorMessage) {
        Log.i(TAG,"error " + errorMessage);
        hideViews();
        no_connection_img.setVisibility(View.VISIBLE);
    }

    private void hideViews() {
        cardDailyItem.setVisibility(View.GONE);
        categoryRecyclerView.setVisibility(View.GONE);
        countryRecyclerView.setVisibility(View.GONE);
        daily_meal.setVisibility(View.GONE);
        category.setVisibility(View.GONE);
        country.setVisibility(View.GONE);
    }
    private void appendViews() {
        cardDailyItem.setVisibility(View.VISIBLE);
        categoryRecyclerView.setVisibility(View.VISIBLE);
        countryRecyclerView.setVisibility(View.VISIBLE);
        daily_meal.setVisibility(View.VISIBLE);
        category.setVisibility(View.VISIBLE);
        country.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCategoryItemClickListener(CategoryDTO categoryDTO) {
       Bundle bundle = new Bundle();
       bundle.putInt("state", Constants.CATEGORIES);
       bundle.putString("filter",categoryDTO.getStrCategory());
        Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_searchFragment2
                ,bundle);
    }

    @Override
    public void onCountryItemClickListener(CountryDTO countryDTO) {
        Bundle bundle = new Bundle();
        bundle.putInt("state",Constants.COUNTRIES);
        bundle.putString("filter",countryDTO.getStrArea());
        Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_searchFragment2,
                bundle);
    }
}