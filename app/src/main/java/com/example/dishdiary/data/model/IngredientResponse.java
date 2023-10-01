package com.example.dishdiary.data.model;

import com.example.dishdiary.data.model.dto.IngredientDTO;

import java.util.List;

public class IngredientResponse {


    private List<IngredientDTO> meals;

    public List<IngredientDTO> getMeals() {
        return meals;
    }

    public void setMeals(List<IngredientDTO> meals) {
        this.meals = meals;
    }
}
