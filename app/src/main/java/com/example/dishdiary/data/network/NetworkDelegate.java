package com.example.dishdiary.data.network;



import com.example.dishdiary.data.model.dto.MealsItemDTO;
  public interface NetworkDelegate {

     void onSuccess(MealsItemDTO dailyMeal);
     void onFailure(String errorMsg);
}
