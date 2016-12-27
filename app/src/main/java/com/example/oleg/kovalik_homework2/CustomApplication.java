package com.example.oleg.kovalik_homework2;

import android.app.Application;

/**
 * Created by Oleg on 27.12.2016.
 */
public class CustomApplication extends Application {
    private SaveManager saveManager;

    @Override
    public void onCreate() {
        super.onCreate();
        saveManager = new SaveManager(this);
    }

    public SaveManager getSaveManager() {
        return saveManager;
    }
}


