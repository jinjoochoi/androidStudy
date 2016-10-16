package com.myapplication.track;

import com.myapplication.track.api.LastFmApi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mathpresso on 2016-10-16.
 */
public class TrackModule {
    private static TrackPresenter presenter;

    static TrackPresenter trackPresenter(){
        if (presenter == null){
            presenter = new TrackPresenter(trackNetworkManager(), AndroidSchedulers.mainThread());
        }
        return presenter;
    }

    private static TrackNetworkManager trackNetworkManager(){
        return new TrackNetworkManager(lastFmApi(), Schedulers.io());
    }

    private static LastFmApi lastFmApi(){
        final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ws.audioscrobbler.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

        return retrofit.create(LastFmApi.class);
    }
}
