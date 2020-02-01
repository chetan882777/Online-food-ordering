package com.example.github.util;

import android.content.SharedPreferences;

import javax.inject.Inject;

public class SharedPrefUtil {

    @Inject
    static SharedPreferences preferences;

    public static void saveCredentials(String contact){
        SharedPreferences.Editor editor = preferences.edit().putString(Constants.SHARED_PREF_AUTH_CREDENTIAL, contact);
        editor.apply();
    }
    public String getCredentials(){
        return preferences.getString(Constants.SHARED_PREF_AUTH_CREDENTIAL, Constants.SHARED_PREF_AUTH_CREDENTIAL_DEF);
    }

    public static void saveType(String intentMessageAuthTypeUser) {
        SharedPreferences.Editor editor = preferences.edit().putString(Constants.SHARED_PREF_TYPE, intentMessageAuthTypeUser);
        editor.apply();
    }
    public String getType(){
        return preferences.getString(Constants.SHARED_PREF_TYPE, Constants.SHARED_PREF_TYPE_DEF);
    }
}
