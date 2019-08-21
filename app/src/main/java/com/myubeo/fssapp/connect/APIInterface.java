package com.myubeo.fssapp.connect;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIInterface {

    @Headers({
            "Accept: application/json, text/javascript, */*; q=0.01",
            "Content-Type: application/json"
    })

    @POST("core/json.php")
    Call<JsonObject> postRawJSON(@Body JsonObject locationPost);
}
