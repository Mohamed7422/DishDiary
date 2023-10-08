package com.example.dishdiary.ui.signup_compomemts.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dishdiary.R;
import com.example.dishdiary.data.Repository.RepoImpl;
import com.example.dishdiary.data.local.LocalDataBaseImpl;
import com.example.dishdiary.data.model.authDTO.AuthSharedPref;
import com.example.dishdiary.data.model.authDTO.AuthenticationPoJo;
import com.example.dishdiary.data.remote.Api_Manager;
import com.example.dishdiary.data.remote.authentication_remote.FireBaseManager;
import com.example.dishdiary.ui.signup_compomemts.sign_up_presenter.SignUpPresenter;
import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Pattern;


public class SignUpFragment extends Fragment implements ISignUp {

    EditText name,userEmail,userPassword;
    TextView signIn;
    Button register;
    ImageView backBtn;
    SignUpPresenter presenter;
    AuthenticationPoJo authPojo = new AuthenticationPoJo();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        presenter  = new SignUpPresenter(RepoImpl.getInstance(Api_Manager.getInstance(), LocalDataBaseImpl.getInstance(getContext()),
                AuthSharedPref.getInstance(getContext()), FireBaseManager.getInstance()),this);

        initViews(view);



        register.setOnClickListener(item ->{
            String validateResult = inputsValidation();
               if (!validateResult.isEmpty()){
                   //Show Toast
                //   Toast.makeText(getContext(), validateResult, Toast.LENGTH_SHORT).show();
                   Snackbar snackbar = Snackbar.make(getView(),validateResult,Snackbar.LENGTH_SHORT);
                   snackbar.setAction("CANCEL", new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           snackbar.dismiss();
                       }
                   });

                   snackbar.show();
               }
               else {
                   //make the registration using firebase
                   presenter.signUp(authPojo);
               }
        });
        signIn.setOnClickListener(v -> {
            Navigation.findNavController(getView()).navigate(R.id.action_signUpFragment_to_logInFragment);
        });

        backBtn.setOnClickListener(v ->{
            Navigation.findNavController(getView()).navigate(R.id.action_signUpFragment_to_logInFragment);
        });
        return view;
    }

    private String inputsValidation() {
        String resultMsg = "";
        String userEmailRegex= "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(userEmailRegex);
        Pattern passwordPattern = Pattern.compile(".*[a-zA-Z]+.*");
        authPojo.setUserName(name.getText().toString());
        authPojo.setUserEmail(userEmail.getText().toString().trim());
        authPojo.setPassword(userPassword.getText().toString().trim());
        if (authPojo.getUserEmail().isEmpty()){
            resultMsg  = getString(R.string.provide_all_required_fields);
        } else if (!pattern.matcher(authPojo.getUserEmail()).matches())
        {
            resultMsg = "Provide a valid email!";
        }

        if (authPojo.getUserName().isEmpty()) {
            resultMsg = "Provide Your Name.";
        }


        if (authPojo.getPassword().isEmpty()){
            resultMsg = getString(R.string.provide_all_required_fields);
        }
        if (authPojo.getPassword().length() < 8){
            resultMsg = "Password length should be > 8.";
        }
        if (!passwordPattern.matcher(authPojo.getPassword()).matches()){
            resultMsg = "Provide a valid password!";
        }

        return resultMsg;
    }

    private void initViews(View view) {

        name =view.findViewById(R.id.first_name_edit_txt);
        userEmail = view.findViewById(R.id.email_edit_text);
        userPassword = view.findViewById(R.id.password_edit_text);
        signIn = view.findViewById(R.id.signIn_TV);
        register = view.findViewById(R.id.register_btn);
        backBtn = view.findViewById(R.id.backBtn);

    }


    @Override
    public void onSignUpSuccess() {

     Navigation.findNavController(getView()).navigate(R.id.action_signUpFragment_to_logInFragment);


    }

    @Override
    public void onSignupFailure(String errorMsg) {
     //Show a text of the error
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }


}