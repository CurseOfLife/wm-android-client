package com.example.wm_android_client.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import android.widget.Toast;

import com.example.wm_android_client.Components.ProgressBarDialog;
import com.example.wm_android_client.R;
import com.example.wm_android_client.databinding.ActivityLoginBinding;
import com.example.wm_android_client.requests.LoginRequest;
import com.example.wm_android_client.utilities.VariousTools;
import com.example.wm_android_client.viewmodels.LoginViewModel;


import org.reactivestreams.Subscription;

import java.util.Objects;


public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;
    private Subscription sub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);
        binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
        loginViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(LoginViewModel.class);
        // binding.setLifecycleOwner(this);
        //binding.setLoginViewModel(loginViewModel);

        components();

    }

    private void components() {


        binding.btnSignIn.setOnClickListener(v -> {
            login();
        });
    }

    private void login() {

        final ProgressBarDialog dialog = new ProgressBarDialog(this);

        //toggleloading
        LoginRequest request = new LoginRequest(Objects.requireNonNull(binding.email.getText()).toString().trim(),
                Objects.requireNonNull(binding.password.getText()).toString().trim());

        if (TextUtils.isEmpty(Objects.requireNonNull(request.getEmail()))) {
            binding.parentEmail.setError("Enter an E-Mail Address");
            binding.parentEmail.requestFocus();

        } else if (!request.isEmailValid()) {
            binding.parentEmail.setError("Enter a Valid E-mail Address");
            binding.parentEmail.requestFocus();
        } else if (TextUtils.isEmpty((Objects.requireNonNull(request.getPassword())))) {
            binding.parentPass.setError("Enter a Password");
            binding.parentPass.requestFocus();
        } else if (!request.isPasswordLengthGreaterThan5()) {
            binding.parentPass.setError("Enter at least a 6 digit password");
            binding.parentPass.requestFocus();
        } else {
            dialog.show();
            //check if there is internet
            //if there is we can send the request
            //if not we have to ask for internet

            if (VariousTools.isNetworkAvailable(getApplication())) {
                loginViewModel.login(request).observe(this, s -> {

                    switch (s) {
                        case "202":
                            //accepted
                            dialog.dismiss();
                            Toast.makeText(this, "Accepted", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(this, MainActivity.class));
                            break;
                        case "400":
                            //bad request
                            dialog.dismiss();
                            Toast.makeText(this, "Bad Request", Toast.LENGTH_SHORT).show();
                            break;
                        case "401":
                            //unauthorized
                            dialog.dismiss();
                            Toast.makeText(this, "Unauthorized", Toast.LENGTH_SHORT).show();
                            break;
                        case "500":
                            //something went wrong on server while trying
                            dialog.dismiss();
                            Toast.makeText(this, "Problem on server", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            dialog.dismiss();
                            Toast.makeText(this, "error" + " " + s, Toast.LENGTH_SHORT).show();
                    }

                });
            } else {
                dialog.dismiss();
                Toast.makeText(this, "No Internet connection try again later", Toast.LENGTH_SHORT).show();
            }
        }

    }

}