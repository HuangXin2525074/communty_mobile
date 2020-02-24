package com.example.login_demo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.login_demo.model.register;
import com.example.login_demo.service.UserServiceAPI;


import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.checkBox)
    CheckBox checkBox;
    @BindView(R.id.button)
    Button button;

    Retrofit retrofit;

    UserServiceAPI service;

    @BindView(R.id.et_email)
    EditText etEmail;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);



        retrofit = new Retrofit.Builder()
                .baseUrl(UserServiceAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(UserServiceAPI.class);


    }



    public void SignUp(View view) {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(MainActivity.this, "no empty value allow", Toast.LENGTH_SHORT).show();
        } else {

            sendRegisterRequest(username,password,email);


        }

    }

    public void login1(View view) {
        Intent intent = new Intent();

        //intent.setClassName("com.example.login_demo","com.example.login_demo.LoginActivity");

        intent.setClass(this,LoginActivity.class);
        startActivity(intent);

    }

    private void sendRegisterRequest(String username,String password, String email){

        Call<register> bodyCall = service.Regsiter(username, password, email);
        bodyCall.enqueue(new Callback<register>() {
            @Override
            public void onResponse(Call<register> call, Response<register> response) {
                int code = response.code();
                System.out.println(code);
                try {
                    String msg = response.body().getMsg();
                    System.out.println(msg);
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT);
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
