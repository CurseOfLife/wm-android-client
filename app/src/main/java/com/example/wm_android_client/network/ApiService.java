package com.example.wm_android_client.network;

import com.example.wm_android_client.requests.LoginRequest;
import com.example.wm_android_client.responses.LoginResponse;
import com.example.wm_android_client.responses.MeasuringPointResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    //https://medium.com/@ryan.samarajeewa/connect-your-android-app-to-a-local-asp-net-core-app-without-the-headaches-725dfb16e061

    @POST("api/account/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    //needs to have a token in header
    //check if can get with token or id needed

    @GET("api/measuringpoint/user/{username}")
    Call<MeasuringPointResponse> getMeasuringPoints(@Path("username") String username);

    @GET("api/readingstatus")
    Call<MeasuringPointResponse> getReadingStatuses(@Path("username") String username);

}
