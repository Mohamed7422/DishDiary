package com.example.dishdiary.ui.log_in_compomemts.log_in_Presenter;

import com.example.dishdiary.data.Repository.Repo;
import com.example.dishdiary.data.model.authDTO.AuthenticationPoJo;
import com.example.dishdiary.data.model.dto.MealPlanDTO;
import com.example.dishdiary.data.model.dto.MealsItemDTO;
import com.example.dishdiary.data.remote.authentication_remote.IFirebaseDelegate;
import com.example.dishdiary.ui.log_in_compomemts.view.ILogIn;

import java.util.List;

public class LogInPresenter implements IFirebaseDelegate {


    Repo repo;
    ILogIn view;
    public LogInPresenter(Repo repo, ILogIn view) {
        this.repo = repo;
        this.view = view;
    }

    public void logIn(AuthenticationPoJo authPojo){
        repo.logIn(authPojo,this);
    }


    @Override
    public void onSuccess() {
        view.onLogInSuccess();
        repo.setLoginState(true);
    }

    @Override
    public void onFailure(String errorMsg) {
       view.onLogInFailure(errorMsg);
    }

    @Override
    public void onDownloadSuccess( List<MealsItemDTO> mealsList,List<MealPlanDTO> mealsPlanList) {
          //handle in another view to show data
    }
}
