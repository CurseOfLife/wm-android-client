package com.example.wm_android_client.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.wm_android_client.R;

public class SessionManager {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private static String USER_TOKEN = "user_token";

    public SessionManager(Context context)
    {
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public void setLogin(boolean login) {
        editor.putBoolean("KEY_LOGIN", login);
        editor.commit();
    }
    public boolean getLogin(){
        return sharedPreferences.getBoolean("KEY_LOGIN", false);
    }

    public void setUsername(String username)
    {
        editor.putString("KEY_USERNAME", username);
        editor.commit();
    }
    public String getUsername(){
        return sharedPreferences.getString("KEY_USERNAME", "");
    }

    public void saveAuthToken(String token) {
        editor.putString(USER_TOKEN, token);
        editor.commit();
    }

    public String fetchAuthToken() {
        return this.sharedPreferences.getString(USER_TOKEN, null);
    }

}
