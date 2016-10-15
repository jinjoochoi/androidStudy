package com.myapplication;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.myapplication.Model.Image;
import com.myapplication.Model.ResultTrackResponse;
import com.myapplication.Model.TopTrackResponse;
import com.myapplication.Model.Track;
import com.myapplication.Model.Track2;
import com.myapplication.Realm.RealmArtist;
import com.myapplication.Realm.RealmCount;
import com.myapplication.Realm.RealmImage;
import com.myapplication.Realm.RealmTrack;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    @Bind(R.id.topTrackList)
    RecyclerView topTrackRecyclerView;
    @Bind(R.id.resultTrackList)
    RecyclerView resultTrackRecyclerView;
    @Bind(R.id.editText)
    EditText editText;
    @Bind(R.id.txtvCount)
    TextView txtvCount;

    ToptrackAdapter toptrackAdapter;
    ResultTrackAdapter resultTrackAdapter;

    private RealmCount realmCount;
    private RealmChangeListener<RealmCount> mListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mListener = new RealmChangeListener<RealmCount>() {
            @Override
            public void onChange(RealmCount count) {
                txtvCount.setText(String.valueOf(count.getCount()));
            }
        };

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realmCount = realm.where(RealmCount.class).findFirst();
                if (realmCount == null) {
                    realmCount = realm.createObject(RealmCount.class, 1);
                    realmCount.setCount(0);
                }
                txtvCount.setText(String.valueOf(realmCount.getCount()));
                realmCount.addChangeListener(mListener);
            }
        });


        toptrackAdapter = new ToptrackAdapter(this);
        topTrackRecyclerView.setAdapter(toptrackAdapter);
        LinearLayoutManager mLayoutManager = new
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        topTrackRecyclerView.setLayoutManager(mLayoutManager);


        resultTrackAdapter = new ResultTrackAdapter(this);
        resultTrackRecyclerView.setAdapter(resultTrackAdapter);
        LinearLayoutManager mLayoutManager2 = new
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        resultTrackRecyclerView.setLayoutManager(mLayoutManager2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        toptrackAdapter.clearItem();
        HashMap<String, String> track = new HashMap<>();
        track.put("method", "chart.gettoptracks");
        track.put("format", "json");
        track.put("api_key", "76b686c47907e60b569a191afeb561da");
        AppController.getInstance().getLastFmService().getTopTracks(track)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<Response<TopTrackResponse>, ArrayList<Track>>() {
                    @Override
                    public ArrayList<Track> call(Response<TopTrackResponse> topTrackResponseResponse) {
                        return writeTrackToRealm(topTrackResponseResponse);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<Track>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ArrayList<Track> tracks) {
                        for (Track track : tracks) {
                            toptrackAdapter.addItem(track);
                        }
                        toptrackAdapter.notifyDataSetChanged();

                    }
                });
    }


    //TODO response.isSuccessfull을 어느시점에 두는게 좋을 지 ?

//    public ArrayList<Track> writeToRealm(final Response<? extends TrackResponse> response) {
//        Realm realm = Realm.getDefaultInstance();
//        response.body().getTrackList();
//        final ArrayList<Track> trackList = response.body().getTrackList();
//
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                deleteInRealmImage(realm);
//
//                for(Track track : trackList) {
//                    RealmTrack realmTrack = findInRealm(realm, track.getUrl());
//                    if (realmTrack == null) {
//                        realmTrack = realm.createObject(RealmTrack.class, track.getUrl());
//                    }
//                    realmTrack.setName(track.getName());
//
//                    RealmArtist realmArtist = realm.createObject(RealmArtist.class);
//                    realmArtist.setName(track.getArtist().getName());
//
//                    realmTrack.setArtist(realmArtist);
//
//                    RealmList<RealmImage> realmImages = realmTrack.getImage();
//                    for (Image image : track.getImage()) {
//                        RealmImage realmImage = realm.createObject(RealmImage.class);
//                        realmImage.setSize(image.getSize());
//                        realmImage.setText(image.getText());
//                        realmImages.add(realmImage);
//                    }
//                    realmTrack.setImage(realmImages);
//                }
//
//            }
//        });
//        return trackList;
//
//    }

    //TODO writeTrackToRealm, writeTrack2ToRealm 합치기
    public ArrayList<Track2> writeTrack2ToRealm(final Response<ResultTrackResponse> response) {

        final ArrayList<Track2> trackList = response.body().getResults().getTrackMatches().getTrack();

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                deleteInRealmImage(realm);

                for (Track2 track : trackList) {
                    RealmTrack realmTrack = findInRealm(realm, track.getUrl());
                    if (realmTrack == null) {
                        realmTrack = realm.createObject(RealmTrack.class, track.getUrl());
                    }
                    realmTrack.setName(track.getName());

                    RealmArtist realmArtist = realm.createObject(RealmArtist.class);
                    realmArtist.setName(track.getArtist());

                    realmTrack.setArtist(realmArtist);

                    RealmList<RealmImage> realmImages = realmTrack.getImage();
                    for (Image image : track.getImage()) {
                        RealmImage realmImage = realm.createObject(RealmImage.class);
                        realmImage.setSize(image.getSize());
                        realmImage.setText(image.getText());
                        realmImages.add(realmImage);
                    }
                    realmTrack.setImage(realmImages);
                }

            }
        });
        return trackList;

    }

    public ArrayList<Track> writeTrackToRealm(final Response<TopTrackResponse> response) {
        final ArrayList<Track> trackList = response.body().getTracks().getTrackList();

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                deleteInRealmImage(realm);

                for (Track track : trackList) {
                    RealmTrack realmTrack = findInRealm(realm, track.getUrl());
                    if (realmTrack == null) {
                        realmTrack = realm.createObject(RealmTrack.class, track.getUrl());
                    }
                    realmTrack.setName(track.getName());

                    RealmArtist realmArtist = realm.createObject(RealmArtist.class);
                    realmArtist.setName(track.getArtist().getName());

                    realmTrack.setArtist(realmArtist);

                    RealmList<RealmImage> realmImages = realmTrack.getImage();
                    for (Image image : track.getImage()) {
                        RealmImage realmImage = realm.createObject(RealmImage.class);
                        realmImage.setSize(image.getSize());
                        realmImage.setText(image.getText());
                        realmImages.add(realmImage);
                    }
                    realmTrack.setImage(realmImages);
                }

            }
        });
        return trackList;

    }

    //    public RealmTrack readFromTrack(String url){
//        Realm realm = Realm.getDefaultInstance();
//        RealmTrack realmTrack = findInRealm(realm,url);
//
//    }
    private boolean deleteInRealmImage(Realm realm) {
        return realm.where(RealmImage.class).findAll().deleteAllFromRealm();
    }

    private RealmTrack findInRealm(Realm realm, String url) {
        return realm.where(RealmTrack.class).equalTo("url", url).findFirst();
    }

    public boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }

    @OnClick(R.id.searchbutton)
    public void searchButtonClicked() {
        if (!isEmpty(editText)) {
            resultTrackAdapter.clearItem();
            HashMap<String, String> track = new HashMap<>();
            track.put("method", "track.search");
            track.put("format", "json");
            track.put("api_key", "76b686c47907e60b569a191afeb561da");
            track.put("track", editText.getText().toString());
            AppController.getInstance().getLastFmService().getResultTracks(track)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(new Func1<Response<ResultTrackResponse>, ArrayList<Track2>>() {
                        @Override
                        public ArrayList<Track2> call(Response<ResultTrackResponse> resultTrackResponseResponse) {
                            return writeTrack2ToRealm(resultTrackResponseResponse);
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<ArrayList<Track2>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onNext(ArrayList<Track2> tracks) {
                            for (Track2 track : tracks) {
                                resultTrackAdapter.addItem(track);
                            }
                            resultTrackAdapter.notifyDataSetChanged();
                        }
                    });

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realmCount.removeChangeListener(mListener);

    }
}

//    AppController.getInstance().getLastFmService().getTopTracks(track)
//    .subscribeOn(Schedulers.io())
//            .observeOn(Schedulers.computation())
//            .flatMap(new Func1<Response<TopTrackResponse>, Observable<Track>>() {
//        @Override
//        public Observable<Track> call(Response<TopTrackResponse> topTrackResponseResponse) {
//            return Observable.from(topTrackResponseResponse.body().getTracks().getTrackList());
//        }
//    })
//            .map(new Func1<Track, String>() {
//        @Override
//        public String call(Track track) {
//            return writeToRealm(track);
//        }
//    })
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(new Action1<Object>() {
//        @Override
//        public void call(Object o) {
//
//        }
//    });
//    AppController.getInstance().getLastFmService().getTopTracks(track).
//    enqueue(new Callback<TopTrackResponse>() {
//        @Override
//        public void onResponse(Call<TopTrackResponse> call, Response<TopTrackResponse> response) {
//            Log.d("thread", "***" + (Looper.myLooper() == Looper.getMainLooper()));
//
//            if (response.isSuccess()) {
//                for (Track item : response.body().getTracks().getTrackList()) {
//                    toptrackAdapter.addItem(item);
//                }
//                toptrackAdapter.notifyDataSetChanged();
//            }
//        }
//
//        @Override
//        public void onFailure(Call<TopTrackResponse> call, Throwable t) {
//
//        }
//    });


//    public String writeToRealm(final Track track) {
//
//        Realm realm = Realm.getDefaultInstance();
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                RealmTrack realmTrack = findInRealm(realm, track.getUrl());
//                if(realmTrack == null){
//                    realmTrack = realm.createObject(RealmTrack.class,track.getUrl());
//                }
//                realmTrack.setName(track.getName());
//                realmTrack.setArtist(track.getArtist());
//
//                RealmList<RealmImage> realmImages =  new RealmList<RealmImage>();
//                for(Image image : track.getImage()){
//                    realmImages.add(new RealmImage(image.getText(),image.getSize()));
//                }
//                realmTrack.setImage(realmImages);
//
//            }
//        });
//        return track.getUrl();
//
//    }
//}
