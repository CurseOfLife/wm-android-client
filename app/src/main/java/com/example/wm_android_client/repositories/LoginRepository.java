package com.example.wm_android_client.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.wm_android_client.database.WaterMeterDb;
import com.example.wm_android_client.network.ApiClient;
import com.example.wm_android_client.network.ApiService;
import com.example.wm_android_client.requests.LoginRequest;
import com.example.wm_android_client.responses.LoginResponse;
import com.example.wm_android_client.utilities.SessionManager;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {

    private SessionManager sessionManager;
    private ApiService apiService;
    private WaterMeterDb db;

    public LoginRepository(Application application) {
        apiService = ApiClient.getRetrofit(application).create(ApiService.class);
        db = WaterMeterDb.getInstance(application);
        sessionManager = new SessionManager(application);
    }

    //check if login true to move to other scren
    //check how to do it with completable
    public MutableLiveData<String> login(LoginRequest loginRequest) {

        final MutableLiveData<String> code = new MutableLiveData<>();

        apiService.login(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.isSuccessful()) {
                    sessionManager.saveAuthToken(response.body().getAuthToken());
                    sessionManager.setLogin(true);
                    sessionManager.setUsername(loginRequest.getEmail());
                }
                code.postValue(String.valueOf(response.code()));

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

                code.postValue(t.getMessage());
            }
        });
        return code;

    }

}
