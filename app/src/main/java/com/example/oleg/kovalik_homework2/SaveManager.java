package com.example.oleg.kovalik_homework2;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Oleg on 27.12.2016.
 */
public class SaveManager {
    private static final String MAXONTOP_KEY = "MAXONTOP";
    private static final String APPONTOP_KEY = "APPONTOP";


    private final int MAXAPPONTOP = 12;
    private final String APPONTOP = "Add App";

    private SharedPreferences sp;

    public SaveManager(Application app) {
        sp = PreferenceManager.getDefaultSharedPreferences(app);
    }

    public int getMaxOnTop() {
        return sp.getInt(MAXONTOP_KEY, MAXAPPONTOP);
    }

    public String[] getAppOnTop() {
        String[] appOnTop = new String[MAXAPPONTOP];
        for (int i = 0; i < appOnTop.length; i++) {
            appOnTop[i] = getAppOnTop(i);
        }
        return appOnTop;
    }

    public String getAppOnTop(int i) {
        return sp.getString(APPONTOP_KEY + i, APPONTOP);
    }

    public void saveAppOnTop(String[] appOnTop) {
        SharedPreferences.Editor e = sp.edit();

        for (int i = 0; i < appOnTop.length; i++) {
            e.putString(APPONTOP_KEY + i, appOnTop[i]);
        }
        e.apply();
    }

    public void saveAppOnTop(String app, int i) {
        SharedPreferences.Editor e = sp.edit();

        e.putString(APPONTOP_KEY + i, app);
        e.apply();
    }

    public String[] addNewApp(String[] appOnTop, String newApp) {
        for (int i = 0; i < appOnTop.length; i++) {
            if (appOnTop[i].equals(APPONTOP)) {
                saveAppOnTop(newApp, i);
                appOnTop[i] = newApp;
                break;
            }
        }
        return appOnTop;
    }

    public String[] removeApp(String[] appOnTop, String remove) {
        for (int i = 0; i < appOnTop.length; i++) {
            if (appOnTop[i].equals(remove)) {
                saveAppOnTop(APPONTOP, i);
                appOnTop[i] = APPONTOP;
                break;
            }
        }
        return appOnTop;
    }


    public String[] removeApp(String[] appOnTop, int i) {
        saveAppOnTop(APPONTOP, i);
        appOnTop[i] = APPONTOP;
        return appOnTop;
    }

    public String[] swapApp(String[] appOnTop, int id1, int id2) {
        String buf;
        buf = appOnTop[id1];
        appOnTop[id1] = appOnTop[id2];
        appOnTop[id2] = buf;

        saveAppOnTop(appOnTop[id1], id1);
        saveAppOnTop(appOnTop[id2], id2);

        return appOnTop;
    }

    public int getCount(String[] appOnTop) {
        int j = 0;
        for (int i = 0; i < appOnTop.length; i++) {

            if (!appOnTop[i].equals(APPONTOP)) {
                j++;
            }
        }
        return j;
    }

    public boolean isAppOnTop(String[] appOnTop, String app) {
        boolean flag = false;
        for (int i = 0; i < appOnTop.length; i++) {
            if (appOnTop[i].equals(app)) {
                flag = true;
                break;
            }
        }
        return flag;
    }
}
