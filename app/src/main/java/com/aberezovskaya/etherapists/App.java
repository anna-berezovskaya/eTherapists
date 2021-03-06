package com.aberezovskaya.etherapists;

import android.app.Application;

import com.aberezovskaya.etherapists.utils.ObservableCache;

/**
 * main app class
 * provides app instance
 * and holds observable cache instance
 */
public class App extends Application {

    private static App sInstance = null;
    private ObservableCache mObservableCache = new ObservableCache();


    public static App instance() {

        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public ObservableCache getObservablesCache() {
        return mObservableCache;
    }
}
