package com.myapplication.track.data;

import com.myapplication.Model.RealmTrack;
import com.myapplication.core.data.RealmRepository;
import com.myapplication.core.data.RealmHelper;
import com.myapplication.track.RetrofitHelper;
import com.myapplication.utils.SystemUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Mathpresso9 on 2017-02-02.
 */

public class TrackDataService {
    private RetrofitHelper mRetrofitHelper;
    private RealmHelper mRealmHelper;

    public TrackDataService(RetrofitHelper retrofitHelper, RealmHelper realmHelper) {
        this.mRealmHelper = realmHelper;
        this.mRetrofitHelper = retrofitHelper;
    }

    public Observable<List<RealmTrack>> getTopTracks(final boolean forceUpdate, Map queryMap) {
        final Observable<List<RealmTrack>> apiObservable = mRetrofitHelper.getLastFmApiClient().getTopTracks(queryMap);
        final Observable<List<RealmTrack>> dbObservable = mRealmHelper.getAll(RealmTrack.class);

        return SystemUtil.isNetworkConnected()
                .map(new Func1<Boolean, Observable<List<RealmTrack>>>() {
                    @Override
                    public Observable<List<RealmTrack>> call(Boolean aBoolean) {
                        return aBoolean ? apiObservable : dbObservable;
                    }
                }).map(new Func1<Observable<List<RealmTrack>>, List<RealmTrack>>() {
                    @Override
                    public List<RealmTrack> call(Observable<List<RealmTrack>> listObservable) {
                        return null;
                    }
                })
