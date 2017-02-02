package com.myapplication.track;

import com.myapplication.track.data.LastFmApi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mathpresso9 on 2017-02-02.
 */

public class RetrofitHelper {
    public final static RetrofitHelper INSTANCE = new RetrofitHelper();
    private static LastFmApi lastFmApi = null;

    public RetrofitHelper() {
        init();
    }

    public static RetrofitHelper getInstance() {
        return INSTANCE;
    }

    private void init() {
        initOkHttp();
        lastFmApi = getApiService(lastFmApi.HOST, LastFmApi.class);
    }

    private static void initOkHttp() {
        // init OkHttp
    }

    private <T> T getApiService(String baseUrl, Class<T> clz) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(clz);
    }

    public LastFmApi getLastFmApiClient() {
        return lastFmApi;
    }
}
