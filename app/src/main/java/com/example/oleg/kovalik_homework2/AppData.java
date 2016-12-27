package com.example.oleg.kovalik_homework2;

import android.graphics.drawable.Drawable;

/**
 * Created by Oleg on 25.12.2016.
 */
public class AppData {
    private static int appOnTopCount = 0;

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

    public static int getAppOnTopCount() {
        return appOnTopCount;
    }

    public static void setAppOnTopCount(int appOnTopCount) {
        AppData.appOnTopCount = appOnTopCount;
    }

    public static void increaseOnTopCount() {
        appOnTopCount++;
    }

    public static void decreaseOnTopCount() {
        appOnTopCount--;
    }


}
