package ru.majestic.yandextranslateapp;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

/**
 * Created by arkadiy.zakharov on 30.03.2017.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ActiveAndroid.initialize(this);
    }
}
