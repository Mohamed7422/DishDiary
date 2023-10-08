package com.example.dishdiary.ui.profile_compomemts.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dishdiary.MainActivity;
import com.example.dishdiary.R;
import com.example.dishdiary.data.Repository.RepoImpl;
import com.example.dishdiary.data.local.LocalDataBaseImpl;
import com.example.dishdiary.data.model.authDTO.AuthSharedPref;
import com.example.dishdiary.data.model.dto.MealPlanDTO;
import com.example.dishdiary.data.model.dto.MealsItemDTO;
import com.example.dishdiary.data.remote.Api_Manager;
import com.example.dishdiary.data.remote.authentication_remote.FireBaseManager;
import com.example.dishdiary.ui.SplashActivity;
import com.example.dishdiary.ui.favourite_compomemts.view.FavouriteMealsFragment;
import com.example.dishdiary.ui.home_compomemts.view.HomeFragment;
import com.example.dishdiary.ui.meal_details_components.view.MealDetailActivity;
import com.example.dishdiary.ui.profile_compomemts.profile_presenter.ProfilePresenter;
import com.example.dishdiary.ui.weakly_plan_compomemts.view.WeaklyPlanFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;


public class ProfileFragment extends Fragment implements IProfileV{
    de.hdodenhof.circleimageview.CircleImageView userImg;
    TextView user_name,favouriteMeals_id,weak_Plan,singInRequiredTV;

    Button logOutBtn,backup;
    ProfilePresenter presenter;
    List<MealsItemDTO> mealsItemList;
    List<MealPlanDTO> mealsPlanList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi(view);

        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            presenter =  new ProfilePresenter(RepoImpl.getInstance(Api_Manager.getInstance(),
                    LocalDataBaseImpl.getInstance(getContext()),
                    AuthSharedPref.getInstance(getContext()), FireBaseManager.getInstance()),this);

            presenter.getCashedFavMeals().observe(getViewLifecycleOwner(), new Observer<List<MealsItemDTO>>() {
                @Override
                public void onChanged(List<MealsItemDTO> mealsItemDTOS) {
                    mealsItemList = mealsItemDTOS;
                }
            });

            presenter.getCashedMealsPlanned().observe(getViewLifecycleOwner(),
                    new Observer<List<MealPlanDTO>>() {
                @Override
                public void onChanged(List<MealPlanDTO> mealPlanDTOList) {
                    mealsPlanList = mealPlanDTOList;
                }
            });
           // logOutBtn.setVisibility(View.GONE);
            user_name.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

            favouriteMeals_id.setOnClickListener(v ->{
                Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_favouriteMealsFragment);
            });

            weak_Plan.setOnClickListener(v ->{
                Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_weaklyPlanFragment);
            });

            logOutBtn.setOnClickListener(v ->{
                presenter.uploadMeals(
                        FirebaseAuth.getInstance().getCurrentUser().getEmail(),mealsPlanList,
                        mealsItemList,1
                );
            });

            backup.setOnClickListener(v -> {

                presenter.uploadMeals(FirebaseAuth.getInstance().getCurrentUser().getEmail(), mealsPlanList, mealsItemList , 0);
            });

        }else {
            userImg.setVisibility(View.GONE);
            user_name.setVisibility(View.GONE);
            favouriteMeals_id.setVisibility(View.GONE);
            logOutBtn.setVisibility(View.GONE);
            weak_Plan.setVisibility(View.GONE);
            backup.setVisibility(View.GONE);
            singInRequiredTV.setVisibility(View.VISIBLE);


        }

    }

    private void initUi(View view) {
        userImg = view.findViewById(R.id.user_Img);
        user_name =view.findViewById(R.id.user_name);
        favouriteMeals_id =view.findViewById(R.id.favouriteMeals_id);
        weak_Plan =view.findViewById(R.id.weak_Plan);
        logOutBtn =view.findViewById(R.id.log_out_id);
        backup = view.findViewById(R.id.backup);
        singInRequiredTV = view.findViewById(R.id.signInRequired);
    }

    @Override
    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        AuthSharedPref.getInstance(getContext()).setLoginStatus(false);
        //handle fragment visibility
        HomeFragment.dailyMealIndicator = false;
        MealDetailActivity.mealDetailIndicator = false;
        FavouriteMealsFragment.mealsListRetrievedInd = false;
        WeaklyPlanFragment.mealsPlanListRetrievedInd = false;
        startActivity(new Intent(getActivity(), SplashActivity.class));
        getActivity().finish();

    }

    @Override
    public void appendErrorMsg(String errorMsg) {
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();

    }
}