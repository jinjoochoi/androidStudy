package com.okhttp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.okhttp.Model.ResultTrackResponse;
import com.okhttp.Model.TopTrackResponse;
import com.okhttp.Model.Track;
import com.okhttp.Model.Track2;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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
        AppController.getInstance().doGetTopLyricsCallback().enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {

                    ObjectMapper objectMapper = new ObjectMapper();
                    final TopTrackResponse topTrackResponse = objectMapper.readValue(response.body().string(), TopTrackResponse.class);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (Track item : topTrackResponse.getTracks().getTrackList()) {
                                toptrackAdapter.addItem(item);
                            }
                            toptrackAdapter.notifyDataSetChanged();
                        }
                    });


                }
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
            AppController.getInstance().doGetSearchLyricsCallback(
                    editText.getText().toString()).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    if (response.isSuccessful()) {

                        ObjectMapper objectMapper = new ObjectMapper();
                        final ResultTrackResponse resultTrackResponse = objectMapper.readValue(response.body().string(),ResultTrackResponse.class);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (Track2 item : resultTrackResponse.getResults().getTrackMatches().getTrack()) {
                                    resultTrackAdapter.addItem(item);
                                }
                                resultTrackAdapter.notifyDataSetChanged();

                            }
                        });



                    }
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
