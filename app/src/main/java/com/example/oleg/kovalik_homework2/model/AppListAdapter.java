package com.example.oleg.kovalik_homework2.model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.oleg.kovalik_homework2.AppManager;
import com.example.oleg.kovalik_homework2.R;
import com.example.oleg.kovalik_homework2.listeners.listapp.OnAppCheckClickListener;
import com.example.oleg.kovalik_homework2.listeners.listapp.OnAppItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleg on 19.12.2016.
 */
public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.ViewHolder> implements Filterable {


    private List<AppData> appList;
    private ArrayList<AppData> filteredList;
    private List<AppData> originalList = new ArrayList<>();
    private int layoutId;
    private ListFilter filter = new ListFilter();
    AppManager appManager;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        public ImageView image;
        public CheckBox appOnTop;

        public ViewHolder(View v) {
            super(v);
            text = (TextView) v.findViewById(R.id.app_item_text);
            image = (ImageView) v.findViewById(R.id.app_item_image);
            appOnTop = (CheckBox) v.findViewById(R.id.app_on_top);

        }

    }


    private class ListFilter extends Filter {

        @Override
        protected FilterResults performFiltering(final CharSequence constraint) {
            appList.clear();
            filteredList.clear();
            FilterResults filterResults = new FilterResults();

            if (constraint != null || constraint.length() != 0) {
                for (AppData appI : originalList) {
                    if (appI.getAppName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredList.add(appI);
                    }
                }
            }

            filterResults.values = filteredList;
            filterResults.count = filteredList.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            appList.addAll(((List<AppData>) results.values));
            notifyDataSetChanged();
        }
    }


    public AppListAdapter(AppManager appManager) {
        this.appManager = appManager;
        appManager.initAppList();
        this.appList = appManager.getAppList();
        originalList = new ArrayList<>(appList);
        filteredList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(layoutId, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.text.setText(appList.get(position).getAppName());
        holder.image.setImageDrawable(appList.get(position).getAppIcon());
        holder.appOnTop.setChecked(appList.get(position).isAppOnTop());
        if (appManager.isAppOnTopFull()) {
            holder.appOnTop.setEnabled(holder.appOnTop.isChecked());
        } else {
            holder.appOnTop.setEnabled(true);
        }
        holder.appOnTop.setOnClickListener(new OnAppCheckClickListener(appManager, this, position));
        holder.itemView.setOnClickListener(new OnAppItemClickListener(appManager, position));
    }


    @Override
    public int getItemCount() {
        return appList.size();
    }


    @Override
    public Filter getFilter() {
        return filter;
    }


    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;


    }
}
