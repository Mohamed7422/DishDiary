package com.example.dishdiary.data.model.authDTO;

import android.content.Context;
import android.content.SharedPreferences;

public class AuthSharedPref implements ISharedPref{

    private static  AuthSharedPref authSharedPref = null;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private final String SharedPrefName = "authState";
    private final String IS_LOGGED = "is_logged";

    private AuthSharedPref(Context context) {
    sharedPreferences = context.getSharedPreferences(SharedPrefName,Context.MODE_PRIVATE);
        editor  = sharedPreferences.edit();
    }

    public static synchronized AuthSharedPref getInstance(Context context){
        if (authSharedPref ==null){
            authSharedPref = new AuthSharedPref(context);
        }
        return authSharedPref;
    }
    @Override
    public void setLoginStatus(Boolean isLogged) {
        editor.putBoolean(IS_LOGGED,isLogged);
        editor.commit();
    }

    @Override
    public boolean getLoginState() {
        return sharedPreferences.getBoolean(IS_LOGGED,false);
    }
}
