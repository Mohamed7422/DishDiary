package com.example.dishdiary.ui.favourite_compomemts.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dishdiary.R;
import com.example.dishdiary.data.Repository.RepoImpl;
import com.example.dishdiary.data.local.LocalDataBaseImpl;
import com.example.dishdiary.data.model.authDTO.AuthSharedPref;
import com.example.dishdiary.data.model.authDTO.ISharedPref;
import com.example.dishdiary.data.model.dto.MealsItemDTO;
import com.example.dishdiary.data.remote.Api_Manager;
import com.example.dishdiary.data.remote.authentication_remote.FireBaseManager;
import com.example.dishdiary.ui.favourite_compomemts.favourite_presenter.IPresenter;
import com.example.dishdiary.ui.favourite_compomemts.favourite_presenter.Presenter;
import com.example.dishdiary.ui.meal_details_components.view.MealDetailActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class FavouriteMealsFragment extends Fragment implements IFavouriteMeals
        , Favourite_recycler_adapter.OnFavouriteItemClickListener,
        Favourite_recycler_adapter.OnDeleterFavMealClickListener {
     RecyclerView favouriteMealsRecyclerView;
     Favourite_recycler_adapter adapter;
     List<MealsItemDTO> favouriteMeals;
     IPresenter presenter;
     TextView signInRequiredBtn,noItemFound;



    public static boolean mealsListRetrievedInd = false;

    public FavouriteMealsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite_meals, container, false);
        initViews(view);


        presenter = new Presenter(RepoImpl.getInstance(Api_Manager.getInstance(), LocalDataBaseImpl.getInstance(requireContext()),
                AuthSharedPref.getInstance(requireContext()), FireBaseManager.getInstance()),this);
        presenter.getFavouriteMeals();
        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
             if (!mealsListRetrievedInd){

                 presenter.downloadMeals(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                   mealsListRetrievedInd = true;
             }

        } else {
            //show Text of Sign up required

            signInRequiredBtn.setVisibility(View.VISIBLE);
            noItemFound.setVisibility(View.GONE);

        }

        return view;
    }

    private void initViews(View view) {
        favouriteMeals = new ArrayList<>();
        favouriteMealsRecyclerView = view.findViewById(R.id.favourite_meals_RV);
        adapter = new Favourite_recycler_adapter(getContext(),favouriteMeals,this,this);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        favouriteMealsRecyclerView.setLayoutManager(layoutManager);
        favouriteMealsRecyclerView.setAdapter(adapter);
        signInRequiredBtn = view.findViewById(R.id.signInRequiredBtn);
        noItemFound = view.findViewById(R.id.noItemFound);
    }

    @Override
    public void getFavouriteMeals(LiveData<List<MealsItemDTO>> favouriteMeals) {
              favouriteMeals.observe(this, mealsItemDTOList -> {
                  //handle no item found
                  if (!mealsItemDTOList.isEmpty()){
                      noItemFound.setVisibility(View.GONE);
                      adapter.setFavouriteItemsList(mealsItemDTOList);
                      System.out.println("mealsItemDTOList "+mealsItemDTOList.size());
                  }else {
                      noItemFound.setVisibility(View.VISIBLE);
                      adapter.setFavouriteItemsList(mealsItemDTOList);
                  }

              });
    }

    @Override
    public void onFavouriteItemClick(MealsItemDTO mealsItemDTO) {
       //navigate to details Activity with the meal item
        Intent intent = new Intent(getActivity(), MealDetailActivity.class);
        intent.putExtra("mealItem",mealsItemDTO);
        startActivity(intent);
    }

    @Override
    public void onDeleteMealClick(MealsItemDTO mealsItemDTO) {
        presenter.deleteFromFavMeals(mealsItemDTO);
    }
}