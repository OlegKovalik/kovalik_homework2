package com.example.oleg.kovalik_homework2.ui;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayout;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import com.example.oleg.kovalik_homework2.R;
import com.example.oleg.kovalik_homework2.listeners.main.*;
import com.example.oleg.kovalik_homework2.model.ViewHolderCell;
import com.example.oleg.kovalik_homework2.model.ViewHolderItem;

import java.util.ArrayList;


public class MainActivity extends BaseActivity {
    private ArrayList<View> appCell = new ArrayList<>();
    private int maxAppOnTop;
    private String[] appOnTop;
    private ViewGroup mainView;
    private View delPanel;
    private GridLayout gridLayout;
    private PackageManager pm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton dialButton = (ImageButton) findViewById(R.id.dial_button);
        ImageButton smsButton = (ImageButton) findViewById(R.id.sms_button);
        Button appButton = (Button) findViewById(R.id.app_button);

        BottomButtonClickListener bottomButtonClickListener = new BottomButtonClickListener();
        dialButton.setOnClickListener(bottomButtonClickListener);
        smsButton.setOnClickListener(bottomButtonClickListener);
        appButton.setOnClickListener(bottomButtonClickListener);

        mainView = (ViewGroup) findViewById(R.id.main_layout);
        delPanel = getLayoutInflater().inflate(R.layout.del_panel, mainView, false);
        delPanel.setOnDragListener(new AppOnDelDragListener(getAppManager()));

        maxAppOnTop = getAppManager().getMaxOnTop();

        showAppOnTop();


    }

    public void showAppOnTop() {

        gridLayout = (GridLayout) findViewById(R.id.grid_layuot);

        for (int i = 0; i < maxAppOnTop; i++) {
            View view = getLayoutInflater().inflate(R.layout.data_cell, gridLayout, false);
            view.setTag(new ViewHolderCell(new ViewHolderItem(view.findViewById(R.id.app_on_top_text), view.findViewById(R.id.app_on_top_image), view.findViewById(R.id.app_container)), i));
            ((ViewHolderCell) view.getTag()).viewHolderItem.container.setOnTouchListener(new AppOnTouchListener());
            ((ViewHolderCell) view.getTag()).viewHolderItem.container.setOnClickListener(new AppOnClickListener(getAppManager()));
            view.setOnDragListener(new AppOnTopDragListener(getAppManager()));
            gridLayout.addView(view);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        appOnTop = getAppManager().getAppOnTop();

        for (int i = 0; i < maxAppOnTop; i++) {
            //View view = appCell.get(i);
            View view = gridLayout.getChildAt(i);

            if (!appOnTop[i].equals(getResources().getString(R.string.cellName))) {
                ((ViewHolderCell) view.getTag()).viewHolderItem.textView.setText(getAppManager().getAppName(appOnTop[i]));
                ((ViewHolderCell) view.getTag()).viewHolderItem.imageView.setImageDrawable(null);
                ((ViewHolderCell) view.getTag()).viewHolderItem.imageView.setImageDrawable(getAppManager().getAppIcon(appOnTop[i]));
            } else {
                ((ViewHolderCell) view.getTag()).viewHolderItem.textView.setText(getResources().getString(R.string.cellName));
                ((ViewHolderCell) view.getTag()).viewHolderItem.imageView.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_launcher));
            }
        }

    }

    public void addDelPanel() {
        mainView.addView(delPanel);
        gridLayout.invalidate();
    }

    public void delDelPanel() {
        mainView.removeView(delPanel);
    }
}


