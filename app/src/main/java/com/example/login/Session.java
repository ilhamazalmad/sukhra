package com.example.login;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public Session(Context context)
    {
        sharedPreferences=context.getSharedPreferences("AppKey",0);
        editor=sharedPreferences.edit();
        editor.apply();
    }

    public void setLogin(Boolean login)
    {
        editor.putBoolean("KEY_LOGIN",login);
        editor.commit();
    }

    public boolean getLogin(){
        return sharedPreferences.getBoolean("KEY_LOGIN",false);
    }

    public void setClient(String client)
    {
        editor.putString("KEY_USERNAME",client);

        editor.commit();
    }

    public String getClient(){
        return sharedPreferences.getString("KEY_USERNAME","");

    }
}
