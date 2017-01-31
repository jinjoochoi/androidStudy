package com.myapplication.track;

import android.support.annotation.NonNull;

import com.myapplication.base.BaseComponent;

/**
 * Created by Mathpresso9 on 2017-01-31.
 */

 interface TrackComponent extends BaseComponent {
    @NonNull
    TrackPresenter getPresenter();
}
