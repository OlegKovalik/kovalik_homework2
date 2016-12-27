package com.example.oleg.kovalik_homework2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Oleg on 27.12.2016.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private SaveManager saveManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomApplication app = (CustomApplication) getApplication();
        saveManager = app.getSaveManager();
    }

    public SaveManager getSaveManager() {
        return saveManager;
    }
}

