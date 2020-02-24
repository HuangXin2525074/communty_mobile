package com.example.login_demo.interceptor;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.login_demo.MainActivity;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.prefs.Preferences;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Interceptor;
import okhttp3.Response;


public class ReceivedCookiesInterceptor implements Interceptor {


    private Context context;

    public ReceivedCookiesInterceptor( Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        //这里获取请求返回的cookie
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {

            HashSet<String> cookies = new HashSet<>();
            for(String header: originalResponse.headers("Set-Cookie"))
            {
              String[] ticket  =header.split(";");

//              int begin = value[0].indexOf("=");
//              int end = value[0].length();
//              String ticket= value[0].substring(begin+1,end);

                cookies.add(ticket[0]);

            }


            SharedPreferences sharedPreferences = context.getSharedPreferences("cookieData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet("cookie", cookies);



            editor.commit();
        }

        return originalResponse;
    }


}
