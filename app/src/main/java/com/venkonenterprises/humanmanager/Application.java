package com.venkonenterprises.humanmanager;

import androidx.multidex.MultiDexApplication;

import leakcanary.AppWatcher;

public class Application extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        if(BuildConfig.DEBUG)
            AppWatcher.INSTANCE.setConfig(AppWatcher.INSTANCE.getConfig().copy(true, true, true, true, 10000));

    }

}