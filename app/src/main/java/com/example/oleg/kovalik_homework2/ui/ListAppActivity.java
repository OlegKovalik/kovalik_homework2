package com.example.oleg.kovalik_homework2.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import com.example.oleg.kovalik_homework2.R;
import com.example.oleg.kovalik_homework2.listeners.listapp.FilterTextWatcher;
import com.example.oleg.kovalik_homework2.listeners.listapp.OnClickSwitch;
import com.example.oleg.kovalik_homework2.model.AppListAdapter;

public class ListAppActivity extends BaseActivity {

    private RecyclerView appRecyclerView;
    private Filter filter;
    private AppListAdapter appListAdapter;
    private OnClickSwitch onClickSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);


        appListAdapter = new AppListAdapter(getAppManager());

        appRecyclerView = (RecyclerView) findViewById(R.id.app_view);
        appRecyclerView.setHasFixedSize(true);


        filter = appListAdapter.getFilter();
        appRecyclerView.setAdapter(appListAdapter);

        EditText appFilter = (EditText) findViewById(R.id.app_filter);
        appFilter.addTextChangedListener(new FilterTextWatcher(filter));

        Button listButton = (Button) findViewById(R.id.list_button);
        Button gridButton = (Button) findViewById(R.id.grid_button);
        onClickSwitch = new OnClickSwitch(appRecyclerView);

        listButton.setOnClickListener(onClickSwitch);
        gridButton.setOnClickListener(onClickSwitch);

        onClickSwitch.onClick(listButton);

    }


}
