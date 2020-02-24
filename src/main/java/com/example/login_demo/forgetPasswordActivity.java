package com.example.login_demo;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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

public class forgetPasswordActivity extends AppCompatActivity {

    @BindView(R.id.ed_email)
    EditText edEmail;
    @BindView(R.id.editText2)
    EditText editText2;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.editText3)
    EditText editText3;
    @BindView(R.id.reset_password)
    Button resetPassword;

    Retrofit retrofit;

    UserServiceAPI service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);

        retrofit = new Retrofit.Builder()
                .baseUrl(UserServiceAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(UserServiceAPI.class);
    }


    public void resetpassword(View view) {
        String email = edEmail.getText().toString().trim();
        String Code =  editText2.getText().toString().trim();
        String newPassword= editText3.getText().toString().trim();


        if(TextUtils.isEmpty(email)||TextUtils.isEmpty(Code)||TextUtils.isEmpty(newPassword)){
            Toast.makeText(forgetPasswordActivity.this, "no empty value allow", Toast.LENGTH_SHORT);
        }else{


            sendNetworkRequest(email,Code,newPassword);



        }


    }

    public void getCode(View view) {
        String email = edEmail.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(forgetPasswordActivity.this, "no empty value allow", Toast.LENGTH_SHORT);
        }else{


            sendCodeRequest(email);


        }


    }

    private void sendNetworkRequest(String email,String Code, String newPassword){


        Call<register> bodycall = service.forgetPassword(email,Code,newPassword);
        bodycall.enqueue(new Callback<register>() {
            @Override
            public void onResponse(Call<register> call, Response<register> response) {

                int code = response.code();
                System.out.println(code);
                try {
                    String msg = response.body().getMsg();
                    System.out.println(msg);
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

    private void sendCodeRequest(String email){

        Call<register> bodycall = service.sendCode(email);
        bodycall.enqueue(new Callback<register>() {
            @Override
            public void onResponse(Call<register> call, Response<register> response) {

                int code = response.code();
                System.out.println(code);
                try {
                    String msg = response.body().getMsg();
                    System.out.println(msg);
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
