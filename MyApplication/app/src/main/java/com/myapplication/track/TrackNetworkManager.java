package com.myapplication.track;

import android.support.annotation.NonNull;
import android.util.Log;

import com.jakewharton.rxrelay.PublishRelay;
import com.myapplication.Model.LoadingState;
import com.myapplication.Model.TopTrackResponse;
import com.myapplication.Model.Track;
import com.myapplication.base.Funcs;
import com.myapplication.base.Results;

import java.util.HashMap;
import java.util.List;

import retrofit2.adapter.rxjava.Result;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Mathpresso9 on 2017-01-31.
 */

public class TrackNetworkManager {
    private final PublishRelay<Void> refreshRelay = PublishRelay.create();

    private final PublishRelay<LoadingState> loadingStateRelay = PublishRelay.create();
    private final PublishRelay<List<Track>> tracksRelay = PublishRelay.create();

    private final CompositeSubscription subscription = new CompositeSubscription();

    private final LastFmService lastFmService;
    private final Scheduler ioScheduler;


    public TrackNetworkManager(LastFmService lastFmService, Scheduler ioScheduler) {
        this.lastFmService = lastFmService;
        this.ioScheduler = ioScheduler;
    }

    @NonNull
    Observable<LoadingState> onLoadingStateChanged() {
        return loadingStateRelay;
    }

    @NonNull
    PublishRelay<List<Track>> onDataChanged() {
        return tracksRelay;
    }

    void setup() {
        final Observable<Result<TopTrackResponse>> result = refreshRelay
                .doOnNext(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        loadingStateRelay.call(LoadingState.LOADING);
                    }
                })
                .flatMap(new Func1<Void, Observable<Result<TopTrackResponse>>>() {
                    @Override
                    public Observable<Result<TopTrackResponse>> call(Void aVoid) {
                        HashMap<String, String> track = new HashMap<>();
                        track.put("method", "chart.gettoptracks");
                        track.put("format", "json");
                        track.put("api_key", "76b686c47907e60b569a191afeb561da");

                        return lastFmService.getTopTracks(track).subscribeOn(ioScheduler);
                    }
                });

        subscription.add(result.filter(Results.isSuccessful())
                .map(new Func1<Result<TopTrackResponse>, TopTrackResponse>() {
                    @Override
                    public TopTrackResponse call(Result<TopTrackResponse> topTrackResponseResult) {
                        return topTrackResponseResult.response().body();
                    }
                }).doOnNext(new Action1<TopTrackResponse>() {
                    @Override
                    public void call(TopTrackResponse topTrackResponse) {
                        tracksRelay.call(topTrackResponse.getTracks().getTrackList());
                    }
                }).subscribe(new Subscriber<TopTrackResponse>() {
                    @Override
                    public void onCompleted() {
                        loadingStateRelay.call(LoadingState.IDLE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TrackNetworkManager","onError");
                    }

                    @Override
                    public void onNext(TopTrackResponse topTrackResponse) {
                        Log.d("TrackNetworkManager","onNext");
                        loadingStateRelay.call(LoadingState.IDLE);

                    }
                }));

        subscription.add(result.filter(Funcs.not(Results.isSuccessful()))
                .subscribe(new Subscriber<Result<TopTrackResponse>>() {
                    @Override
                    public void onCompleted() {
                        loadingStateRelay.call(LoadingState.ERROR);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TrackNetworkManager","onError");

                    }

                    @Override
                    public void onNext(Result<TopTrackResponse> topTrackResponseResult) {
                        Log.d("TrackNetworkManager","onNext");

                    }
                }));
    }


    void refresh() {
        refreshRelay.call(null);
    }

    void teardown() {
        subscription.clear();
    }
}
