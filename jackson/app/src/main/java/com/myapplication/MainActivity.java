package com.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.current)
    TextView textView;

    DateTime currentDateTime;
    DateTime dateTime;
    Current current;
    String str_current;
    ObjectMapper objectMapper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        current = null;
        objectMapper = new ObjectMapper();
        currentDateTime = new DateTime();
        textView.setText(getCurrent(currentDateTime));


        Log.d("current",currentDateTime.toString());
    }

    public String getCurrent(DateTime currentDateTime){

        str_current = "{\"year\":" + Integer.toString(currentDateTime.getYear()) +
                ",\"month\":" + Integer.toString(currentDateTime.getMonthOfYear()) +
                ",\"date\":" + Integer.toString(currentDateTime.getDayOfMonth()) +
                ",\"hour\":" + Integer.toString(currentDateTime.getHourOfDay()) +
                ",\"minute\":" + Integer.toString(currentDateTime.getMinuteOfHour())+
                ",\"second\":" + Integer.toString(currentDateTime.getSecondOfMinute())+"}";

        try {
            current = objectMapper.readValue(str_current, Current.class);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return current.getYear() + "/" + current.getMonth() + "/" +
                current.getDate() + "/" + current.getHour() + ":" + current.getMinute()+
                ":"+current.getSecond();
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
