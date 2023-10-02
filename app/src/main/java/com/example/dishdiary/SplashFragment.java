package com.example.dishdiary;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SplashFragment extends Fragment {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
          new Handler().postDelayed(new Runnable() {
              @Override
              public void run() {
                  NavController   navController = Navigation.findNavController(view);
                  navController.navigate(R.id.action_splashFragment_to_logInFragment);

              }
          },3000);

        return view;
    }
}