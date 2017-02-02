package com.myapplication.core.data;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmQuery;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.BehaviorSubject;

/**
 * Created by Zherebtsov Alexandr on 07.01.2016.
 */
public class RealmQueryableCollection {

    List<RealmQueryable> queryables;

    public RealmQueryableCollection() {
        queryables = new ArrayList<>();
    }

    public List<RealmQueryable> getQuerables(final Class clazz) {
        final List<RealmQueryable> filtered = new ArrayList<>();
        Observable.from(queryables)
                .filter(new Func1<RealmQueryable, Boolean>() {
                    @Override
                    public Boolean call(RealmQueryable realmQueryable) {
                        return realmQueryable.getClazz().equals(clazz);
                    }
                }).subscribe(new Action1<RealmQueryable>() {
            @Override
            public void call(RealmQueryable realmQueryable) {
                filtered.add(realmQueryable);
            }
        });
        return filtered;
    }

    public void add(Class clazz, Func1<RealmQuery, RealmQuery> predicate, BehaviorSubject subject) {
        RealmQueryable realmQuerable = new RealmQueryable(clazz, predicate, subject);
        queryables.add(realmQuerable);
    }

    public void clear() {
        queryables.clear();
    }
}
