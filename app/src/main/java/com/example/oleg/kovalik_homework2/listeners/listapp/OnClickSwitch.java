package com.example.oleg.kovalik_homework2.listeners.listapp;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.example.oleg.kovalik_homework2.R;
import com.example.oleg.kovalik_homework2.model.AppListAdapter;

/**
 * Created by Oleg on 03.01.2017.
 */
public class OnClickSwitch implements View.OnClickListener {
    private RecyclerView recyclerView;

    public OnClickSwitch(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Override
    public void onClick(View view) {
        RecyclerView.LayoutManager layoutManager = null;
        if (view.getId() == R.id.list_button) {
            ((AppListAdapter) recyclerView.getAdapter()).setLayoutId(R.layout.app_item_list);
            layoutManager = new LinearLayoutManager(view.getContext());
        } else {
            ((AppListAdapter) recyclerView.getAdapter()).setLayoutId(R.layout.app_item_grid);
            layoutManager = new GridLayoutManager(view.getContext(), 3);
        }

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.getRecycledViewPool().clear();
    }
}
