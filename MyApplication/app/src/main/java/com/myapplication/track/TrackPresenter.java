package com.myapplication.track;

import android.database.Observable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.myapplication.Common.base.BasePresenter;
import com.myapplication.Common.base.PresenterView;
import com.myapplication.track.api.Model.Track;

import java.util.List;

import rx.Scheduler;

/**
 * Created by mathpresso on 2016-10-16.
 */
public class TrackPresenter extends BasePresenter<TrackPresenter.View> {
    private final TrackNetworkManager manager;
    private final Scheduler scheduler;

    public TrackPresenter(@NonNull  final TrackNetworkManager manager, @NonNull final Scheduler scheduler) {
        this.manager = manager;
        this.scheduler = scheduler;
    }

    @Override
    public void onViewAttached(@NonNull final View view) {
        super.onViewAttached(view);
        manager.setup();

    }

    @Override
    public void onViewDetached() {
        super.onViewDetached();
    }

    interface View extends PresenterView{
        @NonNull Observable<Void> onRefreshAction();
        @NonNull Observable<Track> onTrackClicked();

        void setTopTrackList(@NonNull final List<Track> trackList);

        void showEmpty();
        void showError();

        void showLoading();
        void hideLoading();

        void goToTrack(@NonNull final Track track);
    }
    @AutoValue abstract static class LoadingStateWithData{
        static LoadingStateWithData create(@NonNull final LoadingState loadingState,
                                           @Nullable final  List<Track> trackList){
            return AutoValue_TrendingPresenter_LoadingStateWithData(loadingState,trackList);
        }
        abstract LoadingState loadingState();

        @Nullable abstract List<Track> topTrackList();

    }
}
