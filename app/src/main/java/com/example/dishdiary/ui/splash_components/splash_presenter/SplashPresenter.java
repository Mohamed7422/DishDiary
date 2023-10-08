package com.example.dishdiary.ui.splash_components.splash_presenter;

import com.example.dishdiary.data.Repository.Repo;
import com.example.dishdiary.ui.splash_components.view.ISplashView;

public class SplashPresenter {


    Repo repo;
    ISplashView view;

    public SplashPresenter(Repo repo, ISplashView view) {
        this.repo = repo;
        this.view = view;
    }

    public boolean getLogState(){
        return repo.getLoginState();
    }
}
