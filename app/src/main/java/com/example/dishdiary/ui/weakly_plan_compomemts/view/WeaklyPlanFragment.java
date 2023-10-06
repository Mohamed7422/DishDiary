package com.example.dishdiary.ui.weakly_plan_compomemts.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dishdiary.R;
import com.example.dishdiary.data.Repository.RepoImpl;
import com.example.dishdiary.data.local.LocalDataBaseImpl;
import com.example.dishdiary.data.model.dto.MealPlanDTO;
import com.example.dishdiary.data.model.dto.MealsItemDTO;
import com.example.dishdiary.data.remote.Api_Manager;
import com.example.dishdiary.ui.meal_details_components.view.MealDetailActivity;
import com.example.dishdiary.ui.weakly_plan_compomemts.presenter.IWeakPlanPresenter;
import com.example.dishdiary.ui.weakly_plan_compomemts.presenter.WeakPlanPresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class WeaklyPlanFragment extends Fragment implements IWeakPlan, WeakDaysRecyclerAdapter.OnDayCardClickListener,
        WeakDishesRecyclerAdapter.OnWeakDishClickListener {


    IWeakPlanPresenter presenter;
    RecyclerView daysRecyclerView;
    RecyclerView dishesRecyclerView;
    WeakDaysRecyclerAdapter daysAdapter;
    WeakDishesRecyclerAdapter dishesAdapter;

    List<String> days = Arrays.asList("Saturday","Sunday","Monday","Tuesday","Wednesday","Thursday",
            "Friday");

    List<MealPlanDTO> mealPlanList =new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weakly_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new WeakPlanPresenter(this, RepoImpl.getInstance(Api_Manager.getInstance(),
                LocalDataBaseImpl.getInstance(getContext())));
        initView(view);
        getMealsByDay("Saturday");
    }

    private void initView(View view) {
        daysRecyclerView = view.findViewById(R.id.weakDaysRV);
        dishesRecyclerView = view.findViewById(R.id.weakDishesRV);
        daysAdapter = new WeakDaysRecyclerAdapter(getContext(),days,this);
        daysRecyclerView.setAdapter(daysAdapter);
        dishesAdapter = new WeakDishesRecyclerAdapter(getContext(),mealPlanList,this);
        dishesRecyclerView.setAdapter(dishesAdapter);
    }

    @Override
    public void getMealsByDay(String day) {
        presenter.getMealsByDay(day).observe(getViewLifecycleOwner(),
                mealPlanDTOS -> dishesAdapter.setMealPlanList(mealPlanDTOS));
    }

    @Override
    public void onDayCardClick(String day) {
            getMealsByDay(day);
    }

    @Override
    public void onWeakDishClick(MealPlanDTO mealPlanDTO) {
        Intent intent = new Intent(getActivity(), MealDetailActivity.class);
        MealsItemDTO mealsItemDTO = new MealsItemDTO(mealPlanDTO.getIdMeal(), mealPlanDTO.getMealName(), mealPlanDTO.getStrDrinkAlternate()
                , mealPlanDTO.getStrCategory(), mealPlanDTO.getStrArea(), mealPlanDTO.getStrInstructions()
                , mealPlanDTO.getStrMealThumb(), mealPlanDTO.getStrTags(), mealPlanDTO.getStrYoutube()
                , mealPlanDTO.getStrIngredient1(), mealPlanDTO.getStrIngredient2(), mealPlanDTO.getStrIngredient3()
                , mealPlanDTO.getStrIngredient4(), mealPlanDTO.getStrIngredient5(), mealPlanDTO.getStrIngredient6()
                , mealPlanDTO.getStrIngredient7(), mealPlanDTO.getStrIngredient8(), mealPlanDTO.getStrIngredient9()
                , mealPlanDTO.getStrIngredient10(), mealPlanDTO.getStrIngredient11(), mealPlanDTO.getStrIngredient12()
                , mealPlanDTO.getStrIngredient13(), mealPlanDTO.getStrIngredient14(), mealPlanDTO.getStrIngredient15()
                , mealPlanDTO.getStrIngredient16(), mealPlanDTO.getStrIngredient17(), mealPlanDTO.getStrIngredient18()
                , mealPlanDTO.getStrIngredient19(), mealPlanDTO.getStrIngredient20(), mealPlanDTO.getStrMeasure1()
                , mealPlanDTO.getStrMeasure2(), mealPlanDTO.getStrMeasure3(), mealPlanDTO.getStrMeasure4()
                , mealPlanDTO.getStrMeasure5(), mealPlanDTO.getStrMeasure6(), mealPlanDTO.getStrMeasure7()
                , mealPlanDTO.getStrMeasure8(), mealPlanDTO.getStrMeasure9(), mealPlanDTO.getStrMeasure10()
                , mealPlanDTO.getStrMeasure11(), mealPlanDTO.getStrMeasure12(), mealPlanDTO.getStrMeasure13()
                , mealPlanDTO.getStrMeasure14(), mealPlanDTO.getStrMeasure15(), mealPlanDTO.getStrMeasure16()
                , mealPlanDTO.getStrMeasure17(), mealPlanDTO.getStrMeasure18(), mealPlanDTO.getStrMeasure19()
                , mealPlanDTO.getStrMeasure20(), mealPlanDTO.getStrSource(), mealPlanDTO.getStrImageSource(), mealPlanDTO.getStrCreativeCommonsConfirmed()
                , mealPlanDTO.getDateModified());

        intent.putExtra("mealItem", mealsItemDTO);
        startActivity(intent);
    }
}