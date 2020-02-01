package com.example.github.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import javax.inject.Inject;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SharedPrefUtil {

    private SharedPreferences preferences;

    public SharedPrefUtil(Activity a){
            preferences = a.getSharedPreferences("restaurantPrefs", Context.MODE_PRIVATE);

    }

    public void saveCredentials(String contact){
        SharedPreferences.Editor editor = preferences.edit().putString(Constants.SHARED_PREF_AUTH_CREDENTIAL, contact);
        editor.apply();
    }
    public String getCredentials(){
        if(preferences != null) {
            if (preferences.contains(Constants.SHARED_PREF_AUTH_CREDENTIAL)) {
                return preferences.getString(Constants.SHARED_PREF_AUTH_CREDENTIAL, Constants.SHARED_PREF_AUTH_CREDENTIAL_DEF);
            } else {
                return null;
            }
        }else{
            Log.d(TAG, "getCredentials: prefrences object null");
            return null;
        }
    }

    public void saveType(String intentMessageAuthTypeUser) {
        SharedPreferences.Editor editor = preferences.edit().putString(Constants.SHARED_PREF_TYPE, intentMessageAuthTypeUser);
        editor.apply();
    }
    public String getType(){
        if(preferences != null) {
            if (preferences.contains(Constants.SHARED_PREF_TYPE)) {
                return preferences.getString(Constants.SHARED_PREF_TYPE, Constants.SHARED_PREF_TYPE_DEF);
            } else {
                return null;
            }
        }else{
            Log.d(TAG, "getType: preferences null");
            return null;
        }
    }
}
