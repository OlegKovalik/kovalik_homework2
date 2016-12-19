package com.example.oleg.kovalik_homework2;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;

import java.util.List;

public class NewAppActivity extends AppCompatActivity {

    private RecyclerView appRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private Filter filter;

    private static final String TAG = "Test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_app);


        appRecyclerView = (RecyclerView) findViewById(R.id.app_view);
        appRecyclerView.setHasFixedSize(true);
        onClickList(null);

        PackageManager pm = this.getPackageManager();
        List<ApplicationInfo> appList = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        AppListAdapter appListAdapter = new AppListAdapter(this, pm, appList);
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


//        Intent launchIntent = pm.getLaunchIntentForPackage(appList.get(0).packageName);
//        if (launchIntent != null) {
//            startActivity(launchIntent);
//        }

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
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        appRecyclerView.setLayoutManager(layoutManager);
    }

    public void onClickList(View view) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        appRecyclerView.setLayoutManager(layoutManager);
    }
}
