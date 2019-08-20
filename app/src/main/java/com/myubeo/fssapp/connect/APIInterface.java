package com.myubeo.fssapp.connect;

import com.myubeo.fssapp.model.login.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIInterface {

//    @FormUrlEncoded
//    @POST("json.php")
//    Call<List<User>>  Login(@Field("jsonrpc") String jsonrpc,
//                            @Field("method") String method,
//                            @Field("params[]") List<String> params,
//                            @Field("id") String id);

    @POST("core/json.php")
    Call<User> Login(@Body User user);
}
