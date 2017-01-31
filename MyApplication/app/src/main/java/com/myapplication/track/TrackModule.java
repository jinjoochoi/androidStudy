package com.myapplication.track;

import android.support.annotation.NonNull;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Mathpresso9 on 2017-01-31.
 */

public class TrackModule implements TrackComponent {

    private static TrackPresenter presenter;

    @NonNull
    @Override
    public TrackPresenter getPresenter() {
        if (presenter == null) {
            presenter = new TrackPresenter(trackNetworkManager(), AndroidSchedulers.mainThread());
        }
        return presenter;
    }


    private static TrackNetworkManager trackNetworkManager() {
        return new TrackNetworkManager(lastFmService(), Schedulers.io());
    }

    private static LastFmService lastFmService() {
        final Retrofit retrofit = new Retrofit.Builder().baseUrl("http://ws.audioscrobbler.com/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(LastFmService.class);
    }
}
