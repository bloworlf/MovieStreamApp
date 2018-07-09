package com.example.mathurinbloworlf.moviestreamapp.manager;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by Mathurin Bloworlf on 21/04/2017.
 */

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String KEY_LASTNAME = "lastname";
    private static final String KEY_FIRSTNAME = "firstname";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ID = "id";
    private static final String KEY_LINK = "profile_link";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;

    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public boolean userLogin(int id, String lastname, String firstname, String username, String email, String profile_link)
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, id);
        editor.putString(KEY_LASTNAME, lastname);
        editor.putString(KEY_FIRSTNAME, firstname);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_LINK, profile_link);

        editor.apply();
        return true;
    }

    public boolean isLoggedIn()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_USERNAME, null) != null)
        {
            return true;
        }
        return false;
    }

    public boolean LogOut()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public String getUsername()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    public String getFullName(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_FIRSTNAME, null) + " " + sharedPreferences.getString(KEY_LASTNAME, null);
        //return sharedPreferences.getString(KEY_LASTNAME +" "+ KEY_FIRSTNAME, null);
    }

    public String getProfileLink()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_LINK, null);
    }
}
