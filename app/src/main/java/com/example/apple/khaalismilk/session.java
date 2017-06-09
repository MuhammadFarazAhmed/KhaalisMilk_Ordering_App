package com.example.apple.khaalismilk;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashMap;

/**
 * Created by apple on 11/10/16.
 */

public class session {

    SharedPreferences prefs ;

    SharedPreferences.Editor editor;

    Context _context;

    private static final String PREF_NAME = "Myprefs";


    private static final String IS_LOGIN = "IsLoggedIn";


    public static final String KEY_ID = "id";


    public static final String KEY_NAME = "name";


    public static final String KEY_PASSWORD = "password";


    public static final String KEY_FIRST_NAME = "firstname";



    public session(Context context)
    {
        this._context = context;
        prefs = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        editor = prefs.edit();

    }



    public void createLoginSession(String username , String password ,String id)
    {
        editor.putBoolean(IS_LOGIN,true);

        editor.putString(KEY_NAME,username);

        editor.putString(KEY_PASSWORD,password);

        editor.putString(KEY_ID,id);

        editor.commit();
    }


    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, prefs.getString(KEY_NAME, null));

        // user email id
        user.put(KEY_PASSWORD, prefs.getString(KEY_PASSWORD, null));

        user.put(KEY_ID, prefs.getString(KEY_ID,null));

        user.put(KEY_FIRST_NAME, prefs.getString(KEY_FIRST_NAME,null));


        // return user
        return user;
    }

    public boolean checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){

            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);

            return true;
        }
        return false;
    }

    public void logoutUser(){
        // Clearing all data from Shared Preferences

        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);



        // Staring Login Activity
        _context.startActivity(i);


    }

    public boolean isLoggedIn(){
        return prefs.getBoolean(IS_LOGIN, false);
    }
}
