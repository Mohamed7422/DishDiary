package com.example.dishdiary.data.model.authDTO;

import com.example.dishdiary.data.model.dto.MealPlanDTO;
import com.example.dishdiary.data.model.dto.MealsItemDTO;

import java.util.List;

public class UploadMealsDTO {


    private List<MealPlanDTO> planDTOList;
    private List<MealsItemDTO> mealsItemDTOList;
    public UploadMealsDTO(List<MealPlanDTO> planDTOList, List<MealsItemDTO> mealsItemDTOList) {
        this.planDTOList = planDTOList;
        this.mealsItemDTOList = mealsItemDTOList;
    }

    public UploadMealsDTO() {
    }

    public List<MealPlanDTO> getPlanDTOList() {
        return planDTOList;
    }

    public void setPlanDTOList(List<MealPlanDTO> planDTOList) {
        this.planDTOList = planDTOList;
    }

    public List<MealsItemDTO> getMealsItemDTOList() {
        return mealsItemDTOList;
    }

    public void setMealsItemDTOList(List<MealsItemDTO> mealsItemDTOList) {
        this.mealsItemDTOList = mealsItemDTOList;
    }




}
