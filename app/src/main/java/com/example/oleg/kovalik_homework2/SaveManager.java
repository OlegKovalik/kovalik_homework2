package com.example.oleg.kovalik_homework2;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Oleg on 27.12.2016.
 */
public class SaveManager {
    private static final String MAX_ON_TOP_KEY = "MAX_ON_TOP";
    private static final String APP_ON_TOP_KEY = "APP_ON_TOP";


    private int MAX_APP_ON_TOP;
    private String APP_ON_TOP;

    private SharedPreferences sp;

    public SaveManager(Application app) {
        sp = PreferenceManager.getDefaultSharedPreferences(app);
        APP_ON_TOP = app.getResources().getString(R.string.cellName);
        MAX_APP_ON_TOP = app.getResources().getInteger(R.integer.defaultMaxAppOnTop);
    }

    public int getMaxOnTop() {
        return sp.getInt(APP_ON_TOP_KEY, MAX_APP_ON_TOP);
    }

    public String[] getAppOnTop() {
        String[] appOnTop = new String[MAX_APP_ON_TOP];
        for (int i = 0; i < appOnTop.length; i++) {
            appOnTop[i] = getAppOnTop(i);
        }
        return appOnTop;
    }

    public String getAppOnTop(int i) {
        return sp.getString(APP_ON_TOP_KEY + i, APP_ON_TOP);
    }

    public void saveAppOnTop(String[] appOnTop) {
        SharedPreferences.Editor e = sp.edit();

        for (int i = 0; i < appOnTop.length; i++) {
            e.putString(APP_ON_TOP_KEY + i, appOnTop[i]);
        }
        e.apply();
    }

    public void saveAppOnTop(String app, int i) {
        SharedPreferences.Editor e = sp.edit();

        e.putString(APP_ON_TOP_KEY + i, app);
        e.apply();
    }


}
