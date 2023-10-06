package com.example.dishdiary.ui.weakly_plan_compomemts.presenter;

import androidx.lifecycle.LiveData;

import com.example.dishdiary.data.model.dto.MealPlanDTO;

import java.util.List;

public interface IWeakPlanPresenter {

    LiveData<List<MealPlanDTO>> getMealsByDay(String day);


}
