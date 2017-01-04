package com.example.oleg.kovalik_homework2.listeners.listapp;

import android.view.View;
import com.example.oleg.kovalik_homework2.AppManager;

/**
 * Created by Oleg on 21.12.2016.
 */
public class OnAppItemClickListener implements View.OnClickListener {
    private AppManager appManager;
    private int position;


    public OnAppItemClickListener(AppManager appManager, int position) {
        this.appManager = appManager;
        this.position = position;
    }

    @Override
    public void onClick(View view) {
        appManager.startApp(appManager.getAppList().get(position).getAppPackage());
    }

}

