package com.example.oleg.kovalik_homework2;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleg on 19.12.2016.
 */
public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.ViewHolder> implements Filterable {

    PackageManager pm;
    private List<ApplicationInfo> appList;
    private ArrayList<ApplicationInfo> filteredList;
    private List<ApplicationInfo> originalList = new ArrayList<>();
    Context context;

    private ListFilter filter = new ListFilter();


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        public ImageView image;

        public ViewHolder(View v) {
            super(v);
            text = (TextView) v.findViewById(R.id.app_item_text);
            image = (ImageView) v.findViewById(R.id.app_item_image);
        }

    }


    private class ListFilter extends Filter {

        @Override
        protected FilterResults performFiltering(final CharSequence constraint) {
            appList.clear();
            filteredList.clear();
            FilterResults filterResults = new FilterResults();

            if (constraint != null || constraint.length() != 0) {
                for (ApplicationInfo appI : originalList) {
                    if (appI.loadLabel(pm).toString().toLowerCase().contains(constraint.toString().toLowerCase())) {
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
            appList.addAll(((List<ApplicationInfo>) results.values));
            notifyDataSetChanged();
        }
    }


    public AppListAdapter(Context context, PackageManager pm, List<ApplicationInfo> appList) {
        this.context = context;
        this.appList = appList;
        this.pm = pm;
        originalList = new ArrayList<>(appList);
        filteredList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.app_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.text.setText(appList.get(position).loadLabel(pm).toString());
        holder.image.setImageDrawable(appList.get(position).loadIcon(pm));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startApp(appList.get(position));
            }
        });
    }

    public void startApp(ApplicationInfo appInfo) {
        Intent launchIntent = pm.getLaunchIntentForPackage(appInfo.packageName);
        if (launchIntent != null) {
            context.startActivity(launchIntent);
        }
    }

    @Override
    public int getItemCount() {
        return appList.size();
    }


    @Override
    public Filter getFilter() {
        return filter;
    }


}
