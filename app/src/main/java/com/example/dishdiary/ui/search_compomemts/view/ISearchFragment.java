package com.example.dishdiary.ui.search_compomemts.view;

import com.example.dishdiary.data.model.dto.CategoryDTO;
import com.example.dishdiary.data.model.dto.CountryDTO;
import com.example.dishdiary.data.model.dto.IngredientDTO;
import com.example.dishdiary.data.model.dto.MealsItemDTO;

import java.util.List;

public interface ISearchFragment {

   void appendSearchResult(List<MealsItemDTO> mealsItemDTOList);
   void appendCategoriesResult(List<CategoryDTO> categoryDTOList);
   void appendCountriesResult(List<CountryDTO> countryDTOList);
   void appendIngredientResult(List<IngredientDTO> ingredientDTOList);

    void appendErrorMsg(String msg);
    void appendDetails(List<MealsItemDTO> mealsItemDTOList);

}
