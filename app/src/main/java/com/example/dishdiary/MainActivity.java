package com.example.dishdiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    NavController navController;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.button_navigation_bar);

        navController = Navigation.findNavController(this,R.id.nav_host);

        bottomNavigationView.setOnItemSelectedListener(item -> {
          int id = item.getItemId();
            if (id == R.id.homeItem) {
                navController.navigate(R.id.homeFragment);
            } else if (id == R.id.searchItem) {
                navController.navigate(R.id.searchFragment);
            } else if (id == R.id.favouriteItem) {
                navController.navigate(R.id.favouriteMealsFragment);
            } else if (id == R.id.weak_calender_mealsItem) {
                navController.navigate(R.id.weaklyPlanFragment);
            } else if (id == R.id.profileItem) {
                navController.navigate(R.id.profileFragment);
            }
            return true;
        });


        
    }


}


