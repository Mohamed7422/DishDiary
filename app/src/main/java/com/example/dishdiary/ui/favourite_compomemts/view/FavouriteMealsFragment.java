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

import com.example.dishdiary.R;
import com.example.dishdiary.data.Repository.RepoImpl;
import com.example.dishdiary.data.local.LocalDataBaseImpl;
import com.example.dishdiary.data.model.dto.MealsItemDTO;
import com.example.dishdiary.data.remote.Api_Manager;
import com.example.dishdiary.ui.favourite_compomemts.favourite_presenter.IPresenter;
import com.example.dishdiary.ui.favourite_compomemts.favourite_presenter.Presenter;
import com.example.dishdiary.ui.meal_details_components.view.MealDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class FavouriteMealsFragment extends Fragment implements IFavouriteMeals , Favourite_recycler_adapter.OnFavouriteItemClickListener {
     RecyclerView favouriteMealsRecyclerView;
     Favourite_recycler_adapter adapter;
     List<MealsItemDTO> favouriteMeals;
     IPresenter presenter;

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

        presenter = new Presenter(RepoImpl.getInstance(Api_Manager.getInstance(), LocalDataBaseImpl.getInstance(requireContext())),this);
        presenter.getFavouriteMeals();

        return view;
    }

    private void initViews(View view) {
        favouriteMeals = new ArrayList<>();
        favouriteMealsRecyclerView = view.findViewById(R.id.favourite_meals_RV);
        adapter = new Favourite_recycler_adapter(getContext(),favouriteMeals,this);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        favouriteMealsRecyclerView.setLayoutManager(layoutManager);
        favouriteMealsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void getFavouriteMeals(LiveData<List<MealsItemDTO>> favouriteMeals) {
              favouriteMeals.observe(this, mealsItemDTOList -> {
                    adapter.setFavouriteItemsList(mealsItemDTOList);
                  System.out.println("mealsItemDTOList "+mealsItemDTOList.size());

              });
    }

    @Override
    public void onFavouriteItemClick(MealsItemDTO mealsItemDTO) {
       //navigate to details Activity with the meal item
        Intent intent = new Intent(getActivity(), MealDetailActivity.class);
        intent.putExtra("mealItem",mealsItemDTO);
        startActivity(intent);
    }
}