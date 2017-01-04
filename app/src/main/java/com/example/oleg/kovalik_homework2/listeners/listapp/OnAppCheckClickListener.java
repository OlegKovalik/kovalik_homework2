package com.example.oleg.kovalik_homework2.listeners.listapp;

import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;
import com.example.oleg.kovalik_homework2.AppManager;
import com.example.oleg.kovalik_homework2.model.AppListAdapter;

/**
 * Created by Oleg on 27.12.2016.
 */
public class OnAppCheckClickListener implements View.OnClickListener {
    private AppManager appManager;
    private int position;
    private AppListAdapter adapter;

    public OnAppCheckClickListener(AppManager appManager, AppListAdapter adapter, int position) {
        this.appManager = appManager;
        this.adapter = adapter;
        this.position = position;
    }

    @Override
    public void onClick(View view) {
        CheckBox appOnTop = (CheckBox) view;
        if (appOnTop.isChecked()) {
            appManager.increaseOnTopCount();
            appManager.getAppList().get(position).setAppOnTop(true);
            appManager.addNewAppOnTop(appManager.getAppList().get(position).getAppPackage());
            Toast.makeText(view.getContext(), "Application '" + appManager.getAppList().get(position).getAppName() + "' added to favourited", Toast.LENGTH_SHORT).show();

            if (appManager.isAppOnTopFull()) {
                adapter.notifyDataSetChanged();
                Toast.makeText(view.getContext(), "Main Activity is full", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (appManager.isAppOnTopFull()) {
                adapter.notifyDataSetChanged();
            }
            appManager.decreaseOnTopCount();
            appManager.getAppList().get(position).setAppOnTop(false);
            appManager.removeApp(appManager.getAppList().get(position).getAppPackage());
            Toast.makeText(view.getContext(), "Application '" + appManager.getAppList().get(position).getAppName() + "' remove from favourited", Toast.LENGTH_SHORT).show();
        }

        System.out.println(appManager.getAppOnTopCount());
    }


}
