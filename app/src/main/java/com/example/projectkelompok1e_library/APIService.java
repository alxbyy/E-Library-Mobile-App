package com.example.projectkelompok1e_library;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {
    @FormUrlEncoded
    @POST("register.php")
    Call<ResponseBody> registerUser(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseBody> loginUser(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("update.php")
    Call<ResponseBody> updateUser(
            @Field("old_username") String oldUsername,
            @Field("new_username") String newUsername,
            @Field("email") String email,
            @Field("password") String password
    );
}
