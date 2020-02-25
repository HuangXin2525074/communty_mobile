package com.example.login_demo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.login_demo.interceptor.AddCookiesInterceptor;
import com.example.login_demo.model.register;
import com.example.login_demo.service.UserServiceAPI;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SettingActivity extends AppCompatActivity {


    Retrofit retrofit;

    UserServiceAPI service;
    @BindView(R.id.input_oldPasswpord)
    EditText inputOldPasswpord;
    @BindView(R.id.input_newPassword)
    EditText inputNewPassword;
    @BindView(R.id.input_confirmPassword)
    EditText inputConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initInfo();
    }

    private void initInfo() {

        OkHttpClient.Builder okhttpbuilder = new OkHttpClient.Builder();

        okhttpbuilder.addInterceptor(new AddCookiesInterceptor(this));


        retrofit = new Retrofit.Builder()
                .baseUrl(UserServiceAPI.BASE_URL)
                .client(okhttpbuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(UserServiceAPI.class);


    }

    public void update(View view) {

     String oldPassword= inputOldPasswpord.getText().toString().trim();
     String newPassword= inputNewPassword.getText().toString().trim();
     String confirmPassword= inputConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(oldPassword) || TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(SettingActivity.this, "no empty value allow", Toast.LENGTH_SHORT).show();
        } else {
            sendUpdatePasswordRequest(oldPassword, newPassword, confirmPassword);


        }


    }

    public void back(View view) {
        Intent intent = new Intent();
        intent.setClass(this, UserProfileActivity.class);
        startActivity(intent);

    }

    private void sendUpdatePasswordRequest(String oldPassword, String newPassword, String confirmPassword) {

        Call<register> bodycall = service.updatePassword(oldPassword, newPassword, confirmPassword);
        bodycall.enqueue(new Callback<register>() {
            @Override
            public void onResponse(Call<register> call, Response<register> response) {

                int code = response.code();
                System.out.println(code);
                try {
                    register result = response.body();

                    if (result.getCode() == 200) {
                        System.out.println(result.getMsg());
                    } else {
                        System.out.println(result.getMsg());
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<register> call, Throwable t) {
                System.out.println(t);
            }
        });
    }


}
