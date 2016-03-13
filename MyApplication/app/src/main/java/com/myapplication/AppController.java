package com.myapplication;

import android.support.multidex.MultiDexApplication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by choijinjoo on 2016. 3. 12..
 */
public class AppController extends MultiDexApplication{
    private static AppController mInstance;
    private Retrofit mRetrofit;


    public static synchronized AppController getInstance() {
        return mInstance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
    public Retrofit getmRetrofit(){
        if(mRetrofit == null){
            mRetrofit = new Retrofit.Builder()
                    .baseUrl("http://ws.audioscrobbler.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return mRetrofit;
    }
    public LastFmService getLastFmService(){
        return getmRetrofit().create(LastFmService.class);
    }



}
