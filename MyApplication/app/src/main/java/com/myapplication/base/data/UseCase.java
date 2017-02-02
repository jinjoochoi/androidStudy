package com.myapplication.base.data;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by Zherebtsov Alexandr on 13.02.2016.
 */
public abstract class UseCase {

    protected Subscription subscription = Subscriptions.empty();

    protected abstract Observable getUseCaseObservable();

    protected abstract void execute (Subscriber UseCaseSubscriber);

    public void unsubscribe() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

}
