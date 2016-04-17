package com.okhttp;

import android.app.Application;


import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;


/**
 * Created by choijinjoo on 2016. 3. 12..
 */
public class AppController extends Application {
    private static String BASE_URL = "http://ws.audioscrobbler.com/2.0/";
    private static String API_KEY = "76b686c47907e60b569a191afeb561da";
    private static AppController mInstance;
    private OkHttpClient okHttpClient;


    public static synchronized AppController getInstance() {
        return mInstance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;



    }


    public Call doGetTopLyricsCallback (){
        HttpUrl.Builder urlBuilder= HttpUrl.parse(BASE_URL).newBuilder();
        urlBuilder.addQueryParameter("format","json");
        urlBuilder.addQueryParameter("api_key",API_KEY);
        urlBuilder.addQueryParameter("method","chart.gettoptracks");
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url).build();
        okHttpClient = new OkHttpClient();

        return okHttpClient.newCall(request);

    }
    public Call doGetSearchLyricsCallback (String track){
        HttpUrl.Builder urlBuilder= HttpUrl.parse(BASE_URL).newBuilder();
        urlBuilder.addQueryParameter("format","json");
        urlBuilder.addQueryParameter("api_key",API_KEY);
        urlBuilder.addQueryParameter("method","track.search");
        urlBuilder.addQueryParameter("track",track);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url).build();
        okHttpClient = new OkHttpClient();

        return okHttpClient.newCall(request);

    }




}
