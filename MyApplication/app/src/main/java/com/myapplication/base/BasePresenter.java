package com.myapplication.base;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Mathpresso9 on 2017-01-31.
 */

public class BasePresenter<V extends PresenterView> {
    private final CompositeSubscription compositeSubscription = new CompositeSubscription();
    private V view;

    @CallSuper
    public void onViewAttached(@NonNull final V view) {
        if (this.view != null) {
            throw new IllegalStateException("View " + this.view + " is already attached. Cannot attach " + view);
        }
        this.view = view;
    }

    @CallSuper
    public void onViewDetached() {
        if (view == null) {
            throw new IllegalStateException("View is already detached");
        }
        view = null;
        compositeSubscription.clear();
    }

    @CallSuper
    protected final void addToAutoUnsubscribe(@NonNull final Subscription subscription) {
        compositeSubscription.add(subscription);
    }

}
