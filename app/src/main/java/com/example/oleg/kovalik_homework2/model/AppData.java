package com.example.oleg.kovalik_homework2.model;

import android.graphics.drawable.Drawable;

/**
 * Created by Oleg on 25.12.2016.
 */
public class AppData {

    private String appName;
    private Drawable appIcon;
    private String appPackage;
    private boolean appOnTop = false;


    public AppData(String appName, Drawable appIcon, String appPackage) {
        this.appName = appName;
        this.appIcon = appIcon;
        this.appPackage = appPackage;
        appOnTop = false;
    }


    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String getAppPackage() {
        return appPackage;
    }

    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }

    public boolean isAppOnTop() {
        return appOnTop;
    }

    public void setAppOnTop(boolean appOnTop) {
        this.appOnTop = appOnTop;
    }


}
