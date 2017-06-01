package com.sau.campusclick.rest;

import com.sau.campusclick.model.Picture;
import com.sau.campusclick.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by saurabh on 2017-04-13.
 */

public interface ApiInterface {

    @GET("login.php")
    Call<User> loginUser(@Query("username") String username);

    @FormUrlEncoded
    @POST("signup.php")
    Call<User> signUpUser(@Field("username") String username);

    @GET("getpicsbyuser.php")
    Call<List<Picture>> getPicsByUser(@Query("id") int id);

    @GET("getpictures.php")
    Call<List<Picture>> getAllPictures();
}
