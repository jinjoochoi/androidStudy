package com.myapplication.track;


import com.myapplication.Model.ResultTrackResponse;
import com.myapplication.Model.TopTrackResponse;

import java.util.Map;

import retrofit2.adapter.rxjava.Result;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by choijinjoo on 2016. 3. 12..
 */
public interface LastFmService {
    @GET("2.0/")
    Observable<Result<TopTrackResponse>> getTopTracks(@QueryMap Map<String, String> track);

    @GET("2.0/")
    Observable<Result<ResultTrackResponse>> getResultTracks(@QueryMap Map<String, String> track);
}
