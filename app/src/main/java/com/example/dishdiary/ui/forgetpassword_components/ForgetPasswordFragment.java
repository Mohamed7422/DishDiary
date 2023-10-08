package com.example.dishdiary.ui.forgetpassword_components;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.dishdiary.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;


public class ForgetPasswordFragment extends Fragment {
    ImageView reset_backBtn;
    Button reset_btn;
      EditText email;
      ProgressBar reset_ProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forget_password, container, false);
        initViews(view);

        reset_btn.setOnClickListener(item ->{
            reset_ProgressBar.setVisibility(View.VISIBLE);
            String userEmail = email.getText().toString().trim();

            if (userEmail.equals("")){
                Snackbar snackbar = Snackbar.make(view,"Email is Empty!",Snackbar.LENGTH_SHORT);
                snackbar.show();
                reset_ProgressBar.setVisibility(View.GONE);
            }else {
                FirebaseAuth.getInstance().sendPasswordResetEmail(userEmail)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                   Navigation.findNavController(view).navigate(R.id.action_forgetPasswordFragment_to_logInFragment);

                                }else {
                                    Snackbar snackbar = Snackbar.make(view,"Provide a valid email!",Snackbar.LENGTH_SHORT);
                                    snackbar.show();
                                }
                                reset_ProgressBar.setVisibility(View.GONE);
                            }
                        });
            }

        });

        reset_backBtn.setOnClickListener(item ->{
                 Navigation.findNavController(view).navigate(R.id.action_forgetPasswordFragment_to_logInFragment);
        });




        return view;
    }

    private void initViews(View view) {
        reset_btn  = view.findViewById(R.id.reset_btn);
        reset_backBtn  = view.findViewById(R.id.reset_backBtn);
        email  = view.findViewById(R.id.forget_email_edit_text);
        reset_ProgressBar= view.findViewById(R.id.reset_ProgressBar);
    }
}