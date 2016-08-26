package com.iyad.sultan.notes;

import android.app.Application;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Administrator on 8/24/2016.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // The Realm file will be located in Context.getFilesDir() with name "default.realm"
        RealmConfiguration config = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(config);

    }
}
