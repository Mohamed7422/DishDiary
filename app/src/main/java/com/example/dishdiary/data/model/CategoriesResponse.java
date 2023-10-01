package com.example.dishdiary.data.model;

import com.example.dishdiary.data.model.dto.CategoryDTO;

import java.util.List;

public class CategoriesResponse {

    public List<CategoryDTO> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryDTO> categoryList) {
        this.categoryList = categoryList;
    }

    private List<CategoryDTO>categoryList;

}
