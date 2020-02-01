package com.example.github.util;

import com.example.github.ui.auth.AuthActivity;

public class Constants {

    public static final String BASE_URL = "https://api.github.com/";

    public static final int CONNECTION_TIMEOUT = 10; // 10seconds
    public static final int READ_TIMEOUT = 10; // 10seconds
    public static final int WRITE_TIMEOUT = 10; // 10seconds

    public static final String DATABASE_NAME = "github_database";



    public static final String FIREBASE_SUCCESS = "FIREBASE_SUCCESS";
    public static final String FIREBASE_FAILED = "FIREBASE_FAILED";



    public static final String SHARED_PREF_AUTH_CREDENTIAL = "SHARED_PREF_AUTH_CREDENTIAL";
    public static final String SHARED_PREF_TYPE = "SHARED_PREF_TYPE";
    public static final String SHARED_PREF_AUTH_CREDENTIAL_DEF = "No User Found";
    public static final String SHARED_PREF_TYPE_DEF = AuthActivity.INTENT_MESSAGE_AUTH_TYPE_USER;
}
