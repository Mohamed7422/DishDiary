package com.example.dishdiary.ui.favourite_compomemts.favourite_presenter;

import com.example.dishdiary.data.Repository.Repo;
import com.example.dishdiary.ui.favourite_compomemts.view.IFavouriteMeals;

public class Presenter implements IPresenter{


    Repo repo;
    IFavouriteMeals view;

    public Presenter(Repo repo, IFavouriteMeals view) {
        this.repo = repo;
        this.view = view;
    }

    @Override
    public void getFavouriteMeals() {
      view.getFavouriteMeals(repo.getAllMeals());
    }
}
