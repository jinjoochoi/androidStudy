package com.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.myapplication.Realm.RealmCount;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmChangeListener;

public class NewActivity extends BaseActivity {
    @Bind(R.id.txtvCount)
    TextView txtvCount;
    @Bind(R.id.btnPlus)
    Button btnPlus;
    @Bind(R.id.btnMinus)
    Button btnMinus;

    ToptrackAdapter toptrackAdapter;
    ResultTrackAdapter resultTrackAdapter;

    private RealmCount realmCount;
    private RealmChangeListener<RealmCount> mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
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
                if(realmCount != null) {
                    txtvCount.setText(String.valueOf(realmCount.getCount()));
                }
                realmCount.addChangeListener(mListener);


            }
        });


        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCount(Integer.valueOf(txtvCount.getText().toString()) + 1);
            }
        });
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCount(Integer.valueOf(txtvCount.getText().toString()) - 1);

            }
        });

    }
    private void updateCount(final int n){
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmCount realmCount = mRealm.where(RealmCount.class).findFirst();
                realmCount.setCount(n);
            }
        });

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        realmCount.removeChangeListener(mListener);

    }
}