package com.example.oleg.kovalik_homework2;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;

/**
 * Created by Oleg on 27.12.2016.
 */
public class SaveManager {
    private static final String MAXONTOP_KEY = "MAXONTOP";
    private static final String APPONTOP_KEY = "APPONTOP";

    private final int MAXAPPONTOP = 12;

    private SharedPreferences sp;

    public SaveManager(Application app) {
        sp = PreferenceManager.getDefaultSharedPreferences(app);
    }

    public int getMaxOnTop() {
        return sp.getInt(MAXONTOP_KEY, MAXAPPONTOP);
    }

    public HashSet<String> getAppOnTop() {
        return (HashSet<String>) sp.getStringSet(APPONTOP_KEY, new HashSet<String>());
    }

    public void saveAppOnTop(HashSet<String> appOnTop) {
        SharedPreferences.Editor e = sp.edit();
        e.clear();
        e.putStringSet(APPONTOP_KEY, appOnTop);
        e.apply();
    }
}
