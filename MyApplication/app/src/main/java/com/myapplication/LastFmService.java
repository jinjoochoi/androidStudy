package com.myapplication;


import com.myapplication.Model.ResultTrackResponse;
import com.myapplication.Model.TopTrackResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by choijinjoo on 2016. 3. 12..
 */
public interface LastFmService {
    @GET("2.0/")
    Call<TopTrackResponse> getTopTracks(@QueryMap Map<String,String> track);
    @GET("2.0/")
    Call<ResultTrackResponse> getResultTracks(@QueryMap Map<String,String> track);
}
