package com.example.oleg.kovalik_homework2.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.example.oleg.kovalik_homework2.AppManager;
import com.example.oleg.kovalik_homework2.CustomApplication;
import com.example.oleg.kovalik_homework2.SaveManager;

/**
 * Created by Oleg on 27.12.2016.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private SaveManager saveManager;
    private AppManager appManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomApplication app = (CustomApplication) getApplication();
        saveManager = app.getSaveManager();
        appManager = app.getAppManager();
    }

    public SaveManager getSaveManager() {
        return saveManager;
    }

    public AppManager getAppManager() {
        return appManager;
    }
}

