package com.myapplication.core.di.module;

import android.app.Application;
import android.content.Context;

import com.myapplication.core.data.RealmRepository;
import com.myapplication.core.data.RealmHelper;
import com.myapplication.core.di.scope.PerApplication;

import javax.inject.Provider;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

/**
 * Created by Zherebtsov Alexandr on 07.01.2016.
 */
@Module(includes = {RealmModule.class})
public class AppModule {

    protected final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @PerApplication
    @Provides
    public Context provideContext() {
        return application.getApplicationContext();
    }

    @PerApplication
    @Provides
    RealmRepository provideRealmLocalService(Provider<Realm> realmProvider) {
        return new RealmHelper(realmProvider);
    }
}
