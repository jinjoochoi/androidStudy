package com.myapplication.track;

import android.support.annotation.NonNull;
import android.util.Log;

import com.myapplication.Model.LoadingState;
import com.myapplication.Model.RealmTrack;
import com.myapplication.base.BasePresenter;
import com.myapplication.base.PresenterView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func2;

/**
 * Created by Mathpresso9 on 2017-01-31.
 */

public class TrackPresenter extends BasePresenter<TrackPresenter.View> {
    private final TrackNetworkManager trackNetworkManager;
    private final Scheduler uiScheduler;

    public TrackPresenter(TrackNetworkManager trackNetworkManager, Scheduler uiScheduler) {
        this.trackNetworkManager = trackNetworkManager;
        this.uiScheduler = uiScheduler;
    }

    @Override
    public void onViewAttached(final @NonNull View view) {
        super.onViewAttached(view);
        trackNetworkManager.setup();

        addToAutoUnsubscribe(Observable.combineLatest(trackNetworkManager.onLoadingStateChanged(),
                trackNetworkManager.onDataChanged().startWith(new ArrayList<RealmTrack>()),
                new Func2<LoadingState, List<RealmTrack>, LoadingStateWithData>() {
                    @Override
                    public LoadingStateWithData call(LoadingState loadingState, List<RealmTrack> tracks) {
                        return new LoadingStateWithData(loadingState, tracks);
                    }
                })
                .observeOn(uiScheduler)
                .subscribe(new Subscriber<LoadingStateWithData>() {
                    @Override
                    public void onCompleted() {
                        Log.d("TrackPresenter", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TrackPresenter", "onError");

                    }

                    @Override
                    public void onNext(LoadingStateWithData loadingStateWithData) {
                        final LoadingState loadingState = loadingStateWithData.loadingState;
                        final List<RealmTrack> data = loadingStateWithData.getTracks();

                        if (loadingState == LoadingState.LOADING) {
                            if (data == null) {
                                view.showLoading();
                            } else {
                                view.showIncrementalLoading();
                            }
                        } else {
                            view.hideLoading();
                            view.hideIncrementalLoading();
                            if (loadingState == LoadingState.IDLE) {
                                if (data == null) {
                                    view.showEmpty();
                                } else {
                                    view.setTracks(data);
                                }
                            } else if(loadingState == LoadingState.ERROR){
                                if(data == null){
                                    view.showError();
                                }else{
                                    view.showIncrementalError();
                                }
                            }
                        }
                    }
                }));

        addToAutoUnsubscribe(view.onRefreshAction().
                startWith(getVoidObservable())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
                        Log.d("TrackPresenter", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TrackPresenter", "onError");


                    }

                    @Override
                    public void onNext(Void aVoid) {
                        Log.d("onNext", "onNext");
                        trackNetworkManager.refresh().call(null);

                    }
                }));
        addToAutoUnsubscribe(view.onTrackClicked().subscribe(new Action1<RealmTrack>() {
            @Override
            public void call(RealmTrack track) {
                view.goToTrack(track);
            }
        }));

    }

    private Observable<Void> getVoidObservable() {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                subscriber.onNext(null);
            }
        });
    }

    @Override
    public void onViewDetached() {
        super.onViewDetached();
    }

    interface View extends PresenterView {
        @NonNull
        Observable<Void> onRefreshAction();

        @NonNull
        Observable<RealmTrack> onTrackClicked();

        void setTracks(@NonNull final List<RealmTrack> tracks);

        void showEmpty();

        void showError();

        void showIncrementalError();

        void showLoading();

        void hideLoading();

        void showIncrementalLoading();

        void hideIncrementalLoading();

        void goToTrack(@NonNull final RealmTrack track);
    }

    static class LoadingStateWithData {
        LoadingState loadingState;
        List<RealmTrack> tracks;


        public LoadingStateWithData(@NonNull LoadingState loadingState, List<RealmTrack> tracks) {
            this.loadingState = loadingState;
            this.tracks = tracks;
        }

        static Func2<LoadingState, List<RealmTrack>, LoadingStateWithData> create(LoadingState LoadingState, List<RealmTrack> tracks) {
            return new Func2<LoadingState, List<RealmTrack>, LoadingStateWithData>() {
                @Override
                public LoadingStateWithData call(LoadingState loadingState, List<RealmTrack> tracks) {
                    return new LoadingStateWithData(loadingState, tracks);
                }
            };
        }

        public LoadingState getLoadingState() {
            return loadingState;
        }

        public List<RealmTrack> getTracks() {
            return tracks;
        }
    }
}