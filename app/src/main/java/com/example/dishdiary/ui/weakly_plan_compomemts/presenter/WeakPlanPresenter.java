package com.example.dishdiary.ui.weakly_plan_compomemts.presenter;

import androidx.lifecycle.LiveData;

import com.example.dishdiary.data.Repository.Repo;
import com.example.dishdiary.data.model.dto.MealPlanDTO;
import com.example.dishdiary.ui.weakly_plan_compomemts.view.IWeakPlan;

import java.util.List;

public class WeakPlanPresenter implements IWeakPlanPresenter {

    IWeakPlan view;
    Repo repo;

    public WeakPlanPresenter(IWeakPlan view, Repo repo) {
        this.view = view;
        this.repo = repo;

    }


    @Override
    public LiveData<List<MealPlanDTO>> getMealsByDay(String day) {
        return repo.getAllPlanMealsByDay(day) ;
    }
}
