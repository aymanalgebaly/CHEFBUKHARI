package com.compubase.chefbukhari.helpers;

import android.app.Application;
import android.content.SharedPreferences;

import com.yariksoffice.lingver.Lingver;

import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmConfig extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // The default Realm file is "default.realm" in Context.getFilesDir();
        // we'll change it to "myrealm.realm"
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("myRealm").deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);


        SharedPreferences preferences = getSharedPreferences("lan",MODE_PRIVATE);
        Lingver.init(this, Objects.requireNonNull(preferences.getString("lan", "en")));
    }
}
