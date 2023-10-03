package com.example.dishdiary.data.model;

import com.example.dishdiary.data.model.dto.MealsItemDTO;

import java.util.List;

public class MealResponse {


    private List<MealsItemDTO> meals;

    public List<MealsItemDTO> getMeals() {
        return meals;
    }

    public void setMeals(List<MealsItemDTO> meals) {
        this.meals = meals;
    }
}
