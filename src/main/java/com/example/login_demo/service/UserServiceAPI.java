package com.example.login_demo.service;

import com.example.login_demo.entity.User;
import com.example.login_demo.model.UserAPI;
import com.example.login_demo.model.register;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserServiceAPI {

  String BASE_URL="http://192.168.1.121:8080";

    @FormUrlEncoded
    @POST("/Community/register")
    Call<register> Regsiter(@Field("username")String username, @Field("password") String password, @Field("email")String email);

    @FormUrlEncoded
    @POST("/Community/login")
    Call<register> Login(@Field("username")String username,@Field("password") String password,@Field("rememberMe")boolean rememberMe);

    @FormUrlEncoded
    @POST("/Community/sendCode")
    Call<register> sendCode(@Field("email")String email);

    @FormUrlEncoded
    @POST("/Community/forgetPassword")
    Call<register> forgetPassword(@Field("email") String email, @Field("code")String code, @Field("newPassword")String newPassword);



    @GET("/Community/getUser")
    Call<UserAPI> getUser();

    @GET("/Community/logout")
    Call<register> Userlogout();



}