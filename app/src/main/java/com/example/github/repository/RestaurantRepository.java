package com.example.github.repository;


import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

public class RestaurantRepository {

    public static final String NOTE_TITLE_NULL = "Note title cannot be null";
    public static final String INVALID_NOTE_ID = "Invalid id. Can't delete note";
    public static final String DELETE_SUCCESS = "Delete success";
    public static final String DELETE_FAILURE = "Delete failure";
    public static final String UPDATE_SUCCESS = "Update success";
    public static final String UPDATE_FAILURE = "Update failure";
    public static final String INSERT_SUCCESS = "Insert success";
    public static final String INSERT_FAILURE = "Insert failure";

    private int timeDelay = 0;
    private TimeUnit timeUnit = TimeUnit.SECONDS;

    private static final String TAG = "RestaurantRepository";



    @Inject
    public RestaurantRepository( ) {
    }




}