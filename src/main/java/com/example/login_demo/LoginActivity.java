package com.example.login_demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


import com.example.login_demo.interceptor.ReceivedCookiesInterceptor;
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

public class LoginActivity extends AppCompatActivity {


    @BindView(R.id.login_username)
    EditText login_Username;
    @BindView(R.id.login_password)
    EditText login_Password;
    @BindView(R.id.button)
    Button button;

    Retrofit retrofit;

    UserServiceAPI service;

    @BindView(R.id.checkBox)
    CheckBox checkBox;

    boolean flag;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


    }

    public void login(View view) {

        String username = login_Username.getText().toString().trim();
        String password = login_Password.getText().toString().trim();


        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "no empty value allow", Toast.LENGTH_SHORT).show();
        } else {

            if (checkBox.isChecked()) {
                flag = true;
            } else {
                flag = false;
            }

            OkHttpClient.Builder okhttpbuilder = new OkHttpClient.Builder();

            okhttpbuilder.addInterceptor(new ReceivedCookiesInterceptor(this));


            retrofit = new Retrofit.Builder()
                    .baseUrl(UserServiceAPI.BASE_URL)
                    .client(okhttpbuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service = retrofit.create(UserServiceAPI.class);


            sendLoginRequest(username,password,flag,this);


        }
    }

    public void forgetPassword(View view){
        Intent intent = new Intent();
        intent.setClass(this,forgetPasswordActivity.class);
        startActivity(intent);

    }


    private void sendLoginRequest(String username, String password, boolean flag, Context context){

        Call<register> bodycall = service.Login(username, password,flag);
        bodycall.enqueue(new Callback<register>() {
            @Override
            public void onResponse(Call<register> call, Response<register> response) {

                int code = response.code();
                //System.out.println(code);
                try {
                    register register  = response.body();


                    if(register.getCode()==200){
                        login_Username.setText("");
                        login_Password.setText("");

                        System.out.println(register.getMsg());

                     Intent intent = new Intent();
                     intent.setClass(context,UserProfileActivity.class);
                     startActivity(intent);


                    }else{

                        System.out.println(register.getMsg());
                    }



                    Toast.makeText(LoginActivity.this,register.getMsg(), Toast.LENGTH_SHORT);
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
