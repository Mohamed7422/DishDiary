package com.example.dishdiary.data.remote;



import com.example.dishdiary.data.model.dto.CategoryDTO;
import com.example.dishdiary.data.model.dto.CountryDTO;
import com.example.dishdiary.data.model.dto.IngredientDTO;
import com.example.dishdiary.data.model.dto.MealsItemDTO;

import java.util.List;

public interface NetworkDelegate {

     void onMealCallSuccess(MealsItemDTO dailyMeal);
     void onMealCallFailure(String errorMsg);

     void onCategoryCallSuccess(List<CategoryDTO> categoriesList);
     void onCategoryCallFailure(String errorMsg);

     void onCountryCallSuccess(List<CountryDTO> countriesList);
     void onCountryCallFailure(String errorMsg);

     void onIngredientCallSuccess(List<IngredientDTO> ingredientsList);
     void onIngredientCallFailure(String errorMsg);

         //*******************************************//


}
