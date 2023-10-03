package com.example.dishdiary.ui.log_in_compomemts;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dishdiary.MainActivity;
import com.example.dishdiary.R;
import com.example.dishdiary.ui.SplashActivity;

public class LogInFragment extends Fragment {

    Button logIn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);
        logIn = view.findViewById(R.id.Log_In_Btn);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent = new Intent(getActivity(),MainActivity.class);
                 startActivity(intent);
            }
        });
        return view;
    }
}