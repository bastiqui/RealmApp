package com.example.realm1;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //Iniciem el realm
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("realm.realm").build();
        Realm.setDefaultConfiguration(config);
    }
}
