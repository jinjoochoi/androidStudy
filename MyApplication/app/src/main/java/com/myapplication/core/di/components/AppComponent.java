package com.myapplication.core.di.components;

import android.app.Application;
import android.content.Context;

import com.myapplication.core.data.RealmRepository;
import com.myapplication.core.di.module.AppModule;
import com.myapplication.core.di.scope.PerApplication;

import dagger.Component;
import io.realm.Realm;

/**
 * Created by Zherebtsov Alexandr on 07.01.2016.
 */
@PerApplication
@Component(
        modules = {AppModule.class}
)
public interface AppComponent {
    void inject(Application app);
    Context context();
    Realm realm();
    RealmRepository realmAccessLayer();
}
