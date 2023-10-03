package com.example.dishdiary.ui.home_compomemts.view;

import com.example.dishdiary.data.model.dto.CategoryDTO;
import com.example.dishdiary.data.model.dto.CountryDTO;
import com.example.dishdiary.data.model.dto.MealsItemDTO;

import java.util.List;

public interface IHomeFragment {

    void getDailyMeal(MealsItemDTO mealsItemDTO);

    void getCategoriesList(List<CategoryDTO> categories);
    void getCountriesList(List<CountryDTO> countries);
    void appendConnectionError(String errorMessage);


}
