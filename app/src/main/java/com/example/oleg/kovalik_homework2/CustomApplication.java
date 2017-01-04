package com.example.oleg.kovalik_homework2;

import android.app.Application;

/**
 * Created by Oleg on 27.12.2016.
 */
public class CustomApplication extends Application {
    private SaveManager saveManager;
    private AppManager appManager;

    @Override
    public void onCreate() {
        super.onCreate();
        saveManager = new SaveManager(this);
        appManager = new AppManager(this);

    }

    public SaveManager getSaveManager() {
        return saveManager;
    }

    public AppManager getAppManager() {
        return appManager;
    }
}


