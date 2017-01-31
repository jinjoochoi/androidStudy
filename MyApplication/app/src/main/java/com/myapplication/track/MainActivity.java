package com.myapplication.track;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jakewharton.rxrelay.PublishRelay;
import com.myapplication.Model.Track;
import com.myapplication.R;
import com.myapplication.base.BaseActivity;
import com.myapplication.base.BasePresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

public class MainActivity extends BaseActivity<TrackPresenter.View, TrackComponent> implements TrackPresenter.View {
    private final PublishRelay<Void> refreshRelay = PublishRelay.create();
    private final PublishRelay<Track> trackClickRelay = PublishRelay.create();

    @BindView(R.id.topTrackList)
    RecyclerView topTrackRecyclerView;
    @BindView(R.id.resultTrackList)
    RecyclerView resultTrackRecyclerView;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    TrackPresenter presenter;
    ToptrackAdapter toptrackAdapter;
    ResultTrackAdapter resultTrackAdapter;

    @Override
    protected void onViewCreated(@Nullable Bundle savedInstanceState) {
        super.onViewCreated(savedInstanceState);
        ButterKnife.bind(this);

        toptrackAdapter = new ToptrackAdapter(this, null, trackClickRelay);
        topTrackRecyclerView.setAdapter(toptrackAdapter);
        LinearLayoutManager mLayoutManager = new
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        topTrackRecyclerView.setLayoutManager(mLayoutManager);

        resultTrackAdapter = new ResultTrackAdapter(this);
        resultTrackRecyclerView.setAdapter(resultTrackAdapter);
        LinearLayoutManager mLayoutManager2 = new
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        resultTrackRecyclerView.setLayoutManager(mLayoutManager2);

        refreshRelay.call(null);
    }


    public boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }

//    @OnClick(R.id.searchbutton)
//    public void searchButtonClicked(){
//        if(!isEmpty(editText)) {
//            resultTrackAdapter.clearItem();
//            HashMap<String, String> track = new HashMap<>();
//            track.put("method", "track.search");
//            track.put("format", "json");
//            track.put("api_key", "76b686c47907e60b569a191afeb561da");
//            track.put("track",editText.getText().toString());
//            AppController.getInstance().getLastFmService().getResultTracks(track).
//                    enqueue(new Callback<ResultTrackResponse>() {
//                        @Override
//                        public void onResponse(Call<ResultTrackResponse> call, Response<ResultTrackResponse> response) {
//                            if(response.isSuccess()){
//                                for(Track2 item : response.body().getResults().getTrackMatches().getTrack()){
//                                    resultTrackAdapter.addItem(item);
//                                }
//                                resultTrackAdapter.notifyDataSetChanged();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<ResultTrackResponse> call, Throwable t) {
//
//                        }
//                    });
//        }
//
//    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected TrackComponent createComponent() {
        return new TrackModule();
    }

    @Override
    protected void inject(@NonNull TrackComponent component) {
        this.presenter = component.getPresenter();
    }

    @NonNull
    @Override
    protected BasePresenter<TrackPresenter.View> getPresenter() {
        return presenter;
    }

    @NonNull
    @Override
    protected TrackPresenter.View getPresenterView() {
        return this;
    }

    @NonNull
    @Override
    public Observable<Void> onRefreshAction() {
        return refreshRelay;
    }

    @NonNull
    @Override
    public Observable<Track> onTrackClicked() {
        return trackClickRelay;
    }

    @Override
    public void setTracks(@NonNull List<Track> tracks) {
        toptrackAdapter.clear();
        toptrackAdapter.add(tracks);
    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showIncrementalError() {

    }

    @Override
    public void showLoading() {
        progressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressbar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showIncrementalLoading() {

    }

    @Override
    public void hideIncrementalLoading() {

    }

    @Override
    public void goToTrack(@NonNull Track track) {
        Toast.makeText(this, track.getName(), Toast.LENGTH_SHORT).show();
    }

}
