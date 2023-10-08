package com.example.dishdiary.ui.splash_components.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dishdiary.MainActivity;
import com.example.dishdiary.R;
import com.example.dishdiary.data.Repository.RepoImpl;
import com.example.dishdiary.data.local.LocalDataBaseImpl;
import com.example.dishdiary.data.model.authDTO.AuthSharedPref;
import com.example.dishdiary.data.remote.Api_Manager;
import com.example.dishdiary.data.remote.authentication_remote.FireBaseManager;
import com.example.dishdiary.ui.splash_components.splash_presenter.SplashPresenter;


public class SplashFragment extends Fragment implements ISplashView {

      SplashPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_splash, container, false);

        presenter = new SplashPresenter(RepoImpl.getInstance(Api_Manager.getInstance(), LocalDataBaseImpl.getInstance(getContext()), AuthSharedPref.getInstance(getContext()),
                FireBaseManager.getInstance()),this);

        new Handler().postDelayed(() -> {
            if (getLogState()){
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }else {
                Navigation.findNavController(view).navigate(R.id.logInFragment);
               // getActivity().finish();
            }

        },3000);

        return view;
    }

    @Override
    public boolean getLogState() {
        return presenter.getLogState();
    }
}