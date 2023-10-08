package com.example.dishdiary.ui.signup_compomemts.sign_up_presenter;

import com.example.dishdiary.data.Repository.Repo;
import com.example.dishdiary.data.model.authDTO.AuthenticationPoJo;
import com.example.dishdiary.data.model.dto.MealPlanDTO;
import com.example.dishdiary.data.model.dto.MealsItemDTO;
import com.example.dishdiary.data.remote.authentication_remote.IFirebaseDelegate;
import com.example.dishdiary.ui.signup_compomemts.view.ISignUp;

import java.util.List;

public class SignUpPresenter implements IFirebaseDelegate {



    Repo repo;
    ISignUp view;

    public SignUpPresenter(Repo repo, ISignUp view) {
        this.repo = repo;
        this.view = view;
    }

    public void signUp(AuthenticationPoJo authPojo){
        repo.signUp(authPojo,this);
    }

    @Override
    public void onSuccess() {
        view.onSignUpSuccess();
        repo.setLoginState(true);
    }

    @Override
    public void onFailure(String errorMsg) {
         view.onSignupFailure(errorMsg);
    }

    @Override
    public void onDownloadSuccess( List<MealsItemDTO> mealsList,List<MealPlanDTO> mealsPlanList) {
       //handled in required views
    }
}
