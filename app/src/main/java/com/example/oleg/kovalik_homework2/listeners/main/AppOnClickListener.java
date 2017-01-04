package com.example.oleg.kovalik_homework2.listeners.main;

import android.view.View;
import com.example.oleg.kovalik_homework2.AppManager;
import com.example.oleg.kovalik_homework2.model.ViewHolderCell;

/**
 * Created by Oleg on 04.01.2017.
 */
public class AppOnClickListener implements View.OnClickListener {
    private AppManager appManager;

    public AppOnClickListener(AppManager appManager) {
        this.appManager = appManager;
    }

    @Override
    public void onClick(View view) {
        appManager.startApp(((ViewHolderCell) view.getTag()).id);
    }

}
