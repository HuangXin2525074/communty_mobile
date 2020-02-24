package com.example.login_demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.login_demo.interceptor.AddCookiesInterceptor;
import com.example.login_demo.model.UserAPI;
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

public class UserProfileActivity extends AppCompatActivity {

    @BindView(R.id.db_username)
    TextView dbUsername;
    @BindView(R.id.user_username)
    TextView userUsername;

    Retrofit retrofit;

    UserServiceAPI service;
    @BindView(R.id.bt_logout)
    Button btLogout;
    @BindView(R.id.db_email)
    TextView dbEmail;
    @BindView(R.id.user_email)
    TextView userEmail;
    @BindView(R.id.db_createTime)
    TextView dbCreateTime;
    @BindView(R.id.user_createTime)
    TextView userCreateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
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

        sendUserRequest();

    }

    private void sendUserRequest() {

        Call<UserAPI> bodycall = service.getUser();
        bodycall.enqueue(new Callback<UserAPI>() {
            @Override
            public void onResponse(Call<UserAPI> call, Response<UserAPI> response) {

                int code = response.code();
                System.out.println(code);
                try {

                    UserAPI result = response.body();
                    userUsername.setText(result.getUsername());
                    userEmail.setText(result.getEmail());
                    userCreateTime.setText(result.getCreateTime());


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<UserAPI> call, Throwable t) {
                System.out.println(t);
            }
        });
    }

    public void logout(View view) {

        sendUserLogoutRequest(this);


    }

    public void setting(View view){


    }

    private void sendUserLogoutRequest(Context context) {

        Call<register> bodycall = service.Userlogout();
        bodycall.enqueue(new Callback<register>() {
            @Override
            public void onResponse(Call<register> call, Response<register> response) {

                int code = response.code();
                System.out.println(code);
                try {
                    register result = response.body();

                    if (result.getCode() == 200) {
                        System.out.println(result.getMsg());

                        Intent intent = new Intent();
                        intent.setClass(context, LoginActivity.class);
                        startActivity(intent);

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
