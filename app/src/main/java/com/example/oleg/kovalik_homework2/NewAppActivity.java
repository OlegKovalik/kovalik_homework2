package com.example.oleg.kovalik_homework2;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class NewAppActivity extends BaseActivity {

    private RecyclerView appRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private Filter filter;
    private AppListAdapter appListAdapter;
    private PackageManager pm;
    private List<AppData> appList;
    HashSet<String> appOnTop = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_app);

        getAppList();


        OnAppItemClickListener onAppItemClickListener = new OnAppItemClickListener() {
            @Override
            public void onAppItemClick(String appPackage) {
                Intent launchIntent = pm.getLaunchIntentForPackage(appPackage);
                if (launchIntent != null) {
                    startActivity(launchIntent);
                }
            }
        };
        OnCheckClickListener onCheckClickListener = new OnCheckClickListener() {
            @Override
            public void addAppOnTop(AppData appData) {
                appOnTop.add(appData.getAppPackage());
                getSaveManager().saveAppOnTop(appOnTop);

            }

            @Override
            public void removeAppOnTop(AppData appData) {
                appOnTop.remove(appData.getAppPackage());
                getSaveManager().saveAppOnTop(appOnTop);
            }
        };

        appListAdapter = new AppListAdapter(appList, getSaveManager().getMaxOnTop(), onAppItemClickListener, onCheckClickListener);
        appRecyclerView = (RecyclerView) findViewById(R.id.app_view);
        appRecyclerView.setHasFixedSize(true);
        onClickList(null);


        filter = appListAdapter.getFilter();
        appRecyclerView.setAdapter(appListAdapter);

        EditText appFilter = (EditText) findViewById(R.id.app_filter);
        appFilter.addTextChangedListener(textWatcher);

        Button listButton = (Button) findViewById(R.id.list_button);
        Button gridButton = (Button) findViewById(R.id.grid_button);

        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickList(view);
            }
        });

        gridButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickGrid(view);
            }
        });


    }

    public void getAppList() {

        appOnTop = getSaveManager().getAppOnTop();

        appList = new ArrayList<>();
        pm = this.getPackageManager();
        List<ApplicationInfo> applicationInfoList = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo applicationInfo : applicationInfoList) {
            appList.add(new AppData(applicationInfo.loadLabel(pm).toString(), applicationInfo.loadIcon(pm), applicationInfo.packageName));
            appList.get(appList.size() - 1).setAppOnTop(appOnTop.contains(appList.get(appList.size() - 1).getAppPackage()));
        }

        AppData.setAppOnTopCount(appOnTop.size());
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            filter.filter(editable);
        }
    };


    public void onClickGrid(View view) {
        appListAdapter.setLayoutId(R.layout.app_item_grid);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        appRecyclerView.setLayoutManager(layoutManager);
        appRecyclerView.getRecycledViewPool().clear();


    }

    public void onClickList(View view) {
        appListAdapter.setLayoutId(R.layout.app_item_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        appRecyclerView.setLayoutManager(layoutManager);
        appRecyclerView.getRecycledViewPool().clear();

    }


}
