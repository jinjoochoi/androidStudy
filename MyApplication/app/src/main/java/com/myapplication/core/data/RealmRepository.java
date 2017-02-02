package com.myapplication.core.data;

import java.util.List;

import io.realm.RealmQuery;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Func1;

/**
 * Created by Zherebtsov Alexandr on 07.01.2016.
 */
public interface RealmRepository {
    <T> Observable<T> get(Class clazz, Func1<RealmQuery, RealmQuery> predicate);
    <T> Observable<T> getAll(Class clazz);
    <T extends Object> Observable<T> storeObject(Class clazz, Class<T> realmObject);
    <T extends Object> Observable<List<T>> storeObjects(Class clazz, List<Class<T>> realmObjects);
    <T> Observable<T> update(Class clazz, Action0 action);
}