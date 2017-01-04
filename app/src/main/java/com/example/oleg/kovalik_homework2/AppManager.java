package com.example.oleg.kovalik_homework2;

import android.app.Application;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import com.example.oleg.kovalik_homework2.model.AppData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleg on 04.01.2017.
 */
public class AppManager {
    private int appOnTopCount = 0;
    private int maxOnTop;
    private PackageManager pm;
    private SaveManager saveManager;
    private Application app;
    private String[] appOnTop;
    private List<AppData> appList;

    public AppManager(Application app) {
        this.app = app;
        pm = app.getPackageManager();
        saveManager = ((CustomApplication) app).getSaveManager();
        maxOnTop = saveManager.getMaxOnTop();
        initAppList();
    }

    public String[] getAppOnTop() {
        return appOnTop;
    }

    public ApplicationInfo getAppInfo(String packageName) {
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return applicationInfo;
    }

    public String getAppName(String packageName) {
        return getAppInfo(packageName).loadLabel(pm).toString();
    }

    public Drawable getAppIcon(String packageName) {
        return getAppInfo(packageName).loadIcon(pm);
    }

    public List<AppData> getAppList() {
        return appList;
    }


    public void initAppList() {
        appOnTop = saveManager.getAppOnTop();
        appList = new ArrayList<>();
        for (ApplicationInfo applicationInfo : pm.getInstalledApplications(PackageManager.GET_META_DATA)) {
            appList.add(new AppData(applicationInfo.loadLabel(pm).toString(), applicationInfo.loadIcon(pm), applicationInfo.packageName));
            appList.get(appList.size() - 1).setAppOnTop(isAppOnTop(appOnTop, appList.get(appList.size() - 1).getAppPackage()));
        }

        appOnTopCount = calculateOnTopCount();
    }


    public void startApp(String appPackage) {
        if (!appPackage.equals(app.getResources().getString(R.string.cellName))) {
            startApp(pm.getLaunchIntentForPackage(appPackage));
        }

    }

    public void startApp(Intent launchIntent) {
        if (launchIntent != null) {
            app.startActivity(launchIntent);
        }
    }

    public void startApp(int id) {
        startApp(appOnTop[id]);
    }

    public void addNewAppOnTop(String newApp) {
        for (int i = 0; i < appOnTop.length; i++) {
            if (appOnTop[i].equals(app.getResources().getString(R.string.cellName))) {
                saveManager.saveAppOnTop(newApp, i);
                appOnTop[i] = newApp;
                break;
            }
        }
    }

    public void removeApp(String remove) {
        for (int i = 0; i < appOnTop.length; i++) {
            if (appOnTop[i].equals(remove)) {
                appOnTop[i] = app.getResources().getString(R.string.cellName);
                saveManager.saveAppOnTop(appOnTop[i], i);
                break;
            }
        }
    }


    public void removeApp(int i) {

        removeAppOnTopFromList(appOnTop[i]);
        appOnTop[i] = app.getResources().getString(R.string.cellName);
        saveManager.saveAppOnTop(appOnTop[i], i);

    }

    public void removeAppOnTopFromList(String buf) {
        for (int i = 0; i < appList.size(); i++) {
            if (appList.get(i).getAppPackage().equals(buf)) {
                appList.get(i).setAppOnTop(false);
            }
        }
    }

    public void swapApp(int id1, int id2) {
        String buf;
        buf = appOnTop[id1];
        appOnTop[id1] = appOnTop[id2];
        appOnTop[id2] = buf;

        saveManager.saveAppOnTop(appOnTop[id1], id1);
        saveManager.saveAppOnTop(appOnTop[id2], id2);
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

    public int getAppOnTopCount() {
        return appOnTopCount;
    }

    public void setAppOnTopCount(int appOnTopCount) {
        this.appOnTopCount = appOnTopCount;
    }

    public void increaseOnTopCount() {
        appOnTopCount++;
    }

    public void decreaseOnTopCount() {
        appOnTopCount--;
    }

    public int calculateOnTopCount() {
        int j = 0;
        for (int i = 0; i < appOnTop.length; i++) {

            if (!appOnTop[i].equals(app.getResources().getString(R.string.cellName))) {
                j++;
            }
        }
        return j;
    }

    public int getMaxOnTop() {
        return maxOnTop;
    }

    public boolean isAppOnTopFull() {
        return !(appOnTopCount < maxOnTop);
    }
}
