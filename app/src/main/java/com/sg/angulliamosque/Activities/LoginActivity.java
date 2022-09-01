package com.sg.angulliamosque.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.installations.FirebaseInstallations;
import com.google.firebase.messaging.FirebaseMessaging;
import com.sg.angulliamosque.R;
import com.sg.angulliamosque.RetrofitClient;
import com.sg.angulliamosque.databinding.LoginActivityMainBinding;
import com.sg.angulliamosque.models.LoginRequest;
import com.sg.angulliamosque.models.LoginResponse;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private LoginActivityMainBinding binding;
    SharedPreferences sharedPref;
    String deviceToken;

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.login_activity_main);

        binding.setLifecycleOwner(this);

        FirebaseApp.initializeApp(this);
        FirebaseInstallations.getInstance().getToken(true);

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful()) {
                    deviceToken = task.getResult();
                    Log.d("TAG", "onComplete: " + deviceToken);
                }
            }
        });


        sharedPref = getSharedPreferences("sharedPrefs", MODE_PRIVATE);

        String name = sharedPref.getString("name", "");
        String password = sharedPref.getString("password", "");
        String devicetoken = sharedPref.getString("deviceToken", "");

        if (!name.equals("") && !password.equals("") && !devicetoken.equals("")) {
            loginMethod(name, password, devicetoken);

        } else {
            binding.mainLayout.setVisibility(View.VISIBLE);
        }

        checkPermissionAndOpenCamera();

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(binding.username.getText()) && !TextUtils.isEmpty(binding.password.getText())) {
                    loginMethod(binding.username.getText().toString(), binding.password.getText().toString(), deviceToken);
                } else {
                    Toast.makeText(LoginActivity.this, "please fill all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loginMethod(String name, String password, String dToken) {

        ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Logging ..");
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);

        progressDialog.show();
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setDeviceToken(deviceToken);
        loginRequest.setPassword(password);
        loginRequest.setUsername(name);
        Call<LoginResponse> call = RetrofitClient.getInstance().getapi().loginUser(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.code() == 200) {
                    LoginResponse loginResponse = (LoginResponse) response.body();

                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.clear();
                    editor.putString("token", loginResponse.getToken());
                    editor.putString("role", loginResponse.getRole());
                    editor.putString("username", loginResponse.getUsername());
                    editor.putString("deviceToken", loginResponse.getDeviceToken());

                    editor.putString("name", name);
                    editor.putString("password", password);
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, OptionActivity.class);
                    startActivity(intent);
                    finish();
                    progressDialog.dismiss();

                } else if (response.code() == 401) {
                    progressDialog.dismiss();
                    binding.mainLayout.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginActivity.this, "Invalid user", Toast.LENGTH_SHORT).show();

                } else if (response.code() == 202) {
                    progressDialog.dismiss();
                    binding.mainLayout.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginActivity.this, "Invalid user/password", Toast.LENGTH_SHORT).show();

                } else {
                    progressDialog.dismiss();
                    binding.mainLayout.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginActivity.this, "error :" + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();

            }
        });
    }

    public void checkPermissionAndOpenCamera() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 5);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 5) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }

        }
    }
}