package com.myapplication.track;

import android.support.annotation.NonNull;
import android.util.Log;

import com.jakewharton.rxrelay.PublishRelay;
import com.myapplication.Common.Results;
import com.myapplication.track.api.LastFmApi;
import com.myapplication.track.api.Model.TopTrackResponse;
import com.myapplication.track.api.Model.Track;

import java.util.HashMap;
import java.util.List;

import retrofit2.adapter.rxjava.Result;
import rx.Observable;
import rx.Scheduler;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by mathpresso on 2016-10-16.
 */
public class TrackNetworkManager {
    private final PublishRelay<Void> refreshRelay = PublishRelay.create();

    private final PublishRelay<LoadingState> loadingStateRelay = PublishRelay.create();
    private final PublishRelay<List<Track>> trackListRelay = PublishRelay.create();

    private final CompositeSubscription subscription = new CompositeSubscription();

    private final LastFmApi lastFmApi;
    private final Scheduler ioScheduler;

    public TrackNetworkManager(@NonNull final LastFmApi lastFmApi, @NonNull final Scheduler ioScheduler) {
        this.lastFmApi = lastFmApi;
        this.ioScheduler = ioScheduler;
    }

    @NonNull Observable<LoadingState> onLoadingStateChanged(){ return loadingStateRelay;}
    @NonNull PublishRelay<List<Track>> onDataChanged(){
        return trackListRelay;
    }

    void setup(){
        HashMap<String, String> track = new HashMap<>();
        track.put("method", "chart.gettoptracks");
        track.put("format", "json");
        track.put("api_key", "76b686c47907e60b569a191afeb561da");

        final Observable<Result<TopTrackResponse>> result = refreshRelay
                .doOnNext(ignored -> loadingStateRelay.call(LoadingState.LOADING))
                .flatMap(ignored -> lastFmApi.getTopTracks(track).subscribeOn(ioScheduler))
                .share();

        subscription.add(result.filter(Results.isSuccessful())
                .map(listResult -> listResult.response().body().getTracks().getTrackList())
                .doOnNext(trackListRelay::call)
                .subscribe(ignored -> loadingStateRelay.call(LoadingState.IDLE),
                        throwable -> Log.e("TrackNetworkManager","Failed to retrieve latest top track list",throwable)));
        subscription.add(result.filter());

    }
    void refresh(){
        refreshRelay.call(null);
    }
    void teardown() {
        subscription.clear();
    }
}
