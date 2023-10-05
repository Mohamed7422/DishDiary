package com.example.dishdiary.data.remote;

import com.example.dishdiary.data.model.dto.MealsItemDTO;

import java.util.List;

public interface FilterNetworkDelegate {

    //Delegates for the filter search
    void onFilterSuccess(List<MealsItemDTO> meals);
    void onFilterFailure(String errorMsg);
    void onGetMealByIdSuccess(List<MealsItemDTO> mealsItemDTOList);
}
