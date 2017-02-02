package com.myapplication.track.data;


import com.myapplication.Model.ResultTrackResponse;
import com.myapplication.Model.TopTrackResponse;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by choijinjoo on 2016. 3. 12..
 */
public interface LastFmApi {
    String HOST = "http://ws.audioscrobbler.com/";

    @GET("2.0/")
    Observable<TopTrackResponse> getTopTracks(@QueryMap Map<String, String> track);

    @GET("2.0/")
    Observable<ResultTrackResponse> getResultTracks(@QueryMap Map<String, String> track);
}
