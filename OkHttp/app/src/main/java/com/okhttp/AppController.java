package com.okhttp;

import android.app.Application;

import com.google.gson.Gson;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;


/**
 * Created by choijinjoo on 2016. 3. 12..
 */
public class AppController extends Application {
    private static String BASE_URL = "http://ws.audioscrobbler.com/2.0/";
    private static AppController mInstance;
    private OkHttpClient okHttpClient;
    private Gson gson = new Gson();



    public static synchronized AppController getInstance() {
        return mInstance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public Gson getGson() {
        return gson;
    }

    public Call doGetCallback (String parameter){
        okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(BASE_URL + parameter)
                .build();
        return okHttpClient.newCall(request);

    }




}
