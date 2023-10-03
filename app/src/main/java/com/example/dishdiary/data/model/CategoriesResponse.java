package com.example.dishdiary.data.model;

import com.example.dishdiary.data.model.dto.CategoryDTO;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoriesResponse {

    @SerializedName("categories")
    private List<CategoryDTO> categoryList;
    public List<CategoryDTO> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryDTO> categoryList) {
        this.categoryList = categoryList;
    }



}
