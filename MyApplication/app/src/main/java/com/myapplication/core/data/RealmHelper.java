package com.myapplication.core.data;

import android.content.Context;

import com.myapplication.Model.RealmTrack;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.BehaviorSubject;

/**
 * Created by Zherebtsov Alexandr on 07.01.2016.
 */
public class RealmHelper {

    private static final String DB_NAME = "myRealm";

    RealmQueryableCollection realmQueryCollection;
    Realm realm;

    public RealmHelper(Context context) {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .schemaVersion(1)
                .name(DB_NAME)
                .deleteRealmIfMigrationNeeded()
                .build();

        this.realm = Realm.getInstance(realmConfiguration);
        this.realmQueryCollection = new RealmQueryableCollection();
    }


    public <T> Observable<T> get(Class clazz, Func1<RealmQuery, RealmQuery> predicate) {
        BehaviorSubject<T> behaviorSubject = BehaviorSubject.create((T) getInner(clazz, predicate));
        realmQueryCollection.add(clazz, predicate, behaviorSubject);
        return behaviorSubject;
    }


    public <T> Observable<T> getAll(Class clazz) {
        BehaviorSubject<T> behaviorSubject = BehaviorSubject.create((T) getInner(clazz));
        realmQueryCollection.add(clazz, null, behaviorSubject);

        return behaviorSubject;
    }

    public <T extends RealmObject> RealmResults<T> getInner(Class clazz, Func1<RealmQuery, RealmQuery> predicate) {
        RealmQuery query = getRealm().where(clazz);
        if (predicate != null)
            query = predicate.call(query);
        return query.findAllAsync();
    }

    public <T extends RealmObject> RealmResults<T> getInner(Class clazz) {
        RealmQuery query = getRealm().where(clazz);
        return query.findAllAsync();
    }

    public RealmTrack storeObject(Class clazz, RealmTrack realmObject) {
        RealmTrack result;
        getRealm().beginTransaction();
        result = getRealm().copyToRealmOrUpdate(realmObject);
        getRealm().commitTransaction();
        notifyObservers(clazz);
        return result;
    }


    public Observable<List<RealmTrack>> storeObjects(Class clazz, List<RealmTrack> realmObjects) {
        List<RealmTrack> results;
        getRealm().beginTransaction();
        results = getRealm().copyToRealmOrUpdate(realmObjects);
        getRealm().commitTransaction();
        notifyObservers(clazz);
        return Observable.just(results);
    }


    public <T> Observable<T> update(final Class clazz, final Action0 action) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                getRealm().beginTransaction();
                action.call();
            }
        }).doOnNext(new Action1<T>() {
            @Override
            public void call(T t) {
                getRealm().commitTransaction();
                notifyObservers(clazz);
            }
        });
    }

    private void notifyObservers(final Class clazz) {
        Observable.from(realmQueryCollection.getQuerables(clazz))
                .subscribe(new Action1<RealmQueryable>() {
                    @Override
                    public void call(RealmQueryable realmQueryable) {
                        if (!realmQueryable.getSubject().hasObservers()) {
                            realmQueryCollection.queryables.remove(realmQueryable);
                        } else {
                            RealmResults realmResults = getInner(clazz, realmQueryable.getPredicate());
                            realmResults.load();
                            realmQueryable.getSubject().onNext(realmResults);
                        }
                    }
                });
    }

    private Realm getRealm() {
        return realm;
    }
}
