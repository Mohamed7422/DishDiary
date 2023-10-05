package com.example.dishdiary.ui.favourite_compomemts.view;

import androidx.lifecycle.LiveData;

import com.example.dishdiary.data.model.dto.MealsItemDTO;

import java.util.List;

public interface IFavouriteMeals {

void getFavouriteMeals(LiveData<List<MealsItemDTO>> favouriteMeals);
}
