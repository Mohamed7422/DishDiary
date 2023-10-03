package com.example.dishdiary.data.model;

import com.example.dishdiary.data.model.dto.IngredientDTO;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientResponse {


    @SerializedName("meals")
    private List<IngredientDTO> meals;

    public List<IngredientDTO> getMeals() {
        return meals;
    }

    public void setMeals(List<IngredientDTO> meals) {
        this.meals = meals;
    }
}
