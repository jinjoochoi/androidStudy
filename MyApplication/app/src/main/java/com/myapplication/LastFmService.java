package com.myapplication;


import com.myapplication.Model.ResultTrackResponse;
import com.myapplication.Model.TopTrackResponse;

import java.util.Map;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by choijinjoo on 2016. 3. 12..
 */
public interface LastFmService {
    @GET("2.0/")
    Observable<Response<TopTrackResponse>> getTopTracks(@QueryMap Map<String,String> track);
    @GET("2.0/")
    Observable<Response<ResultTrackResponse>> getResultTracks(@QueryMap Map<String,String> track);
}
