package com.myapplication.core.di.module;

import android.app.Application;

import com.myapplication.core.di.scope.PerApplication;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Zherebtsov Alexandr on 07.01.2016.
 */

@Module
public class RealmModule {
    Application application;

    public RealmModule(Application application) {
        this.application = application;
        initRealm();
    }

    @PerApplication
    @Provides
    Realm provideRealm() {
        return Realm.getDefaultInstance();
    }

    private void initRealm() {
        Realm.init(application.getApplicationContext());
        RealmConfiguration realmConfiguration
                = new RealmConfiguration.Builder()
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
