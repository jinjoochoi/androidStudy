package com.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.myapplication.Model.ResultTrackResponse;
import com.myapplication.Model.TopTrackResponse;
import com.myapplication.Model.Track;
import com.myapplication.Model.Track2;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.topTrackList)
    RecyclerView topTrackRecyclerView;
    @Bind(R.id.resultTrackList)
    RecyclerView resultTrackRecyclerView;
    @Bind(R.id.editText)
    EditText editText;


    ToptrackAdapter toptrackAdapter;
    ResultTrackAdapter resultTrackAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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
        AppController.getInstance().getLastFmService().getTopTracks(track).
                enqueue(new Callback<TopTrackResponse>() {
                    @Override
                    public void onResponse(Call<TopTrackResponse> call, Response<TopTrackResponse> response) {
                        if (response.isSuccess()) {
                            for (Track item : response.body().getTracks().getTrackList()) {
                                toptrackAdapter.addItem(item);
                            }
                            toptrackAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<TopTrackResponse> call, Throwable t) {

                    }
                });
    }
    public boolean isEmpty(EditText editText){
        return editText.getText().toString().trim().length() == 0;
    }

    @OnClick(R.id.searchbutton)
    public void searchButtonClicked(){
        if(!isEmpty(editText)) {
            resultTrackAdapter.clearItem();
            HashMap<String, String> track = new HashMap<>();
            track.put("method", "track.search");
            track.put("format", "json");
            track.put("api_key", "76b686c47907e60b569a191afeb561da");
            track.put("track",editText.getText().toString());
            AppController.getInstance().getLastFmService().getResultTracks(track).
                    enqueue(new Callback<ResultTrackResponse>() {
                        @Override
                        public void onResponse(Call<ResultTrackResponse> call, Response<ResultTrackResponse> response) {
                            if(response.isSuccess()){
                                for(Track2 item : response.body().getResults().getTrackMatches().getTrack()){
                                    resultTrackAdapter.addItem(item);
                                }
                                resultTrackAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResultTrackResponse> call, Throwable t) {

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
}
