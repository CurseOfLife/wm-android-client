package com.example.wm_android_client.viewmodels;

import android.app.Application;


import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.wm_android_client.repositories.LoginRepository;
import com.example.wm_android_client.requests.LoginRequest;


public class LoginViewModel extends AndroidViewModel {

    private LoginRepository loginRepository;

    public LoginViewModel(Application application) {
        super(application);
        this.loginRepository = new LoginRepository(application);
    }

    //login vm
    public MutableLiveData<String> login(LoginRequest loginRequest){
        return loginRepository.login(loginRequest);
    }

}
