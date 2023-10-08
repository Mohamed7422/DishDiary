package com.example.dishdiary.ui.log_in_compomemts.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dishdiary.MainActivity;
import com.example.dishdiary.R;
import com.example.dishdiary.data.Repository.RepoImpl;
import com.example.dishdiary.data.local.LocalDataBaseImpl;
import com.example.dishdiary.data.model.authDTO.AuthSharedPref;
import com.example.dishdiary.data.model.authDTO.AuthenticationPoJo;
import com.example.dishdiary.data.remote.Api_Manager;
import com.example.dishdiary.data.remote.authentication_remote.FireBaseManager;
import com.example.dishdiary.ui.SplashActivity;
import com.example.dishdiary.ui.home_compomemts.view.HomeFragment;
import com.example.dishdiary.ui.log_in_compomemts.log_in_Presenter.LogInPresenter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.OAuthCredential;
import com.google.firebase.auth.OAuthProvider;

import java.util.regex.Pattern;

public class LogInFragment extends Fragment implements ILogIn {

    private   final int REQ_ONE_TAP_Google = 0;
    private GoogleSignInClient googleSignInClient;
    TextInputEditText email_edit_text;
    EditText password_edit_text;
    TextView forget_password_tv, create_new_account, continueAsGuest;
    Button logIn, gmailBtn;

    LogInPresenter presenter;
    ProgressBar loginProgress;

    Intent intent;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);
        presenter = new LogInPresenter(RepoImpl.getInstance(Api_Manager.getInstance(), LocalDataBaseImpl.getInstance(getContext()), AuthSharedPref.getInstance(getContext()), FireBaseManager.getInstance()), this);
        initViews(view);

        //handle reset here

        logIn.setOnClickListener(v -> {
            loginProgress.setVisibility(View.VISIBLE);
            String valString = userInputValidation();
            if (valString.isEmpty()) {
                AuthenticationPoJo authPojo = new AuthenticationPoJo(email_edit_text.getText().toString(), password_edit_text.getText().toString());
                presenter.logIn(authPojo);
            } else {
                loginProgress.setVisibility(View.GONE);
                Snackbar snackbar = Snackbar.make(getView(),valString,Snackbar.LENGTH_SHORT);
                snackbar.setAction("CANCEL", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                });

                snackbar.show();
            }


        });

        create_new_account.setOnClickListener(item -> {
            //navigate to signup
            Navigation.findNavController(view).navigate(R.id.action_logInFragment_to_signUpFragment);
        });

        gmailBtn.setOnClickListener(v -> {signInWithGoogle();});

        continueAsGuest.setOnClickListener(item -> {
            Intent navigateToHomeIntent = new Intent(getActivity(), MainActivity.class);
            startActivity(navigateToHomeIntent);
        });
        forget_password_tv.setOnClickListener(item -> {
                //Navigate to forget password screen
            Navigation.findNavController(getView()).navigate(R.id.action_logInFragment_to_forgetPasswordFragment);

        });

        intent = new Intent(getActivity(), MainActivity.class);
        return view;
    }

    private void signInWithGoogle() {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(getActivity(), googleSignInOptions);

        //make sure user signedOut
        googleSignInClient.signOut().addOnCompleteListener(task -> {
            startActivityForResult(googleSignInClient.getSignInIntent(), REQ_ONE_TAP_Google);
        });

        Intent signInGoogleIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInGoogleIntent, REQ_ONE_TAP_Google);
        //init firebaseAuth
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_ONE_TAP_Google) {
            Task<GoogleSignInAccount> signedInAccountFromIntent = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount result = signedInAccountFromIntent.getResult(ApiException.class);
                //auth with firebase
                authWithGoogleByFb(result);

            } catch (ApiException e) {
                System.out.println("ApiException "+e.getLocalizedMessage());
            }

        }
    }

    private void authWithGoogleByFb(GoogleSignInAccount result) {
        AuthCredential credential = GoogleAuthProvider.getCredential(result.getIdToken(), null);
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    AuthSharedPref authSharedPref = AuthSharedPref.getInstance(getContext());
                    authSharedPref.setLoginStatus(true);
                    Intent navigateToHomeIntent = new Intent(getActivity(), MainActivity.class);
                    startActivity(navigateToHomeIntent);
                    getActivity().finish();
                } else {
                    //google signed fail
                    System.out.println("google signed fail");
                }
            }
        });
    }

    private String userInputValidation() {
        String valStringRes = "";
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");
        String userEmail = email_edit_text.getText().toString();
        String password = password_edit_text.getText().toString();
        if (userEmail.isEmpty()) {
            valStringRes = getString(R.string.provide_all_required_fields);
        } else if (!pattern.matcher(userEmail).matches()) {
            valStringRes = "Provide a valid email!";
        }

        if (password.isEmpty()) {
            valStringRes = getString(R.string.provide_all_required_fields);
        }
        return valStringRes;
    }

    private void initViews(View view) {
        logIn = view.findViewById(R.id.Log_In_Btn);
        email_edit_text = view.findViewById(R.id.email_edit_text);
        password_edit_text = view.findViewById(R.id.password_edit_text);
        forget_password_tv = view.findViewById(R.id.forget_password_tv);
        create_new_account = view.findViewById(R.id.create_new_account);
        continueAsGuest = view.findViewById(R.id.continueAsGuest);
        gmailBtn = view.findViewById(R.id.gmailBtn);
        loginProgress = view.findViewById(R.id.login_progress);
    }

    @Override
    public void onLogInSuccess() {
        //navigate to home
        loginProgress.setVisibility(View.GONE);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onLogInFailure(String errorMsg) {
        //show textView with the error
        loginProgress.setVisibility(View.GONE);
        System.out.println("onLogInFailure " + errorMsg);
        Snackbar snackbar = Snackbar.make(getView(), R.string.invalid_credentials_check_email_password,Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Ok", item -> snackbar.dismiss());
        snackbar.show();
    }
}