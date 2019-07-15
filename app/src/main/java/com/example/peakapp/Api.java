package com.example.peakapp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Api {

    @POST(".")
    Call<User> createUser(@Body User user);

}
