package com.example.oleg.kovalik_homework2;

import android.content.pm.PackageManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleg on 19.12.2016.
 */
public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.ViewHolder> implements Filterable {

    PackageManager pm;
    private List<AppData> appList;
    private ArrayList<AppData> filteredList;
    private List<AppData> originalList = new ArrayList<>();
    private int layoutId;
    private OnAppItemClickListener onAppItemClickListener;
    private ListFilter filter = new ListFilter();
    private boolean appOnTopFull = false;
    private int maxOnTop;
    private OnCheckClickListener onCheckClickListener;


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


    public AppListAdapter(List<AppData> appList, int maxOnTop, OnAppItemClickListener onAppItemClickListener, OnCheckClickListener onCheckClickListener) {
        this.appList = appList;
        this.pm = pm;
        originalList = new ArrayList<>(appList);
        filteredList = new ArrayList<>();
        this.onAppItemClickListener = onAppItemClickListener;
        this.onCheckClickListener = onCheckClickListener;
        this.maxOnTop = maxOnTop;
        this.appOnTopFull = (AppData.getAppOnTopCount() >= maxOnTop);
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
        if (appOnTopFull) {
            holder.appOnTop.setEnabled(holder.appOnTop.isChecked());
        } else {
            holder.appOnTop.setEnabled(true);
        }
        holder.appOnTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.appOnTop.isChecked()) {
                    AppData.increaseOnTopCount();
                    appList.get(position).setAppOnTop(true);
                    onCheckClickListener.addAppOnTop(appList.get(position));
                    Toast.makeText(view.getContext(), "Application '" + appList.get(position).getAppName() + "' added to favourited", Toast.LENGTH_SHORT).show();

                    if (AppData.getAppOnTopCount() == maxOnTop) {
                        appOnTopFull = true;
                        notifyDataSetChanged();
                        Toast.makeText(view.getContext(), "Main Activity is full", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (AppData.getAppOnTopCount() == maxOnTop) {
                        appOnTopFull = false;
                        notifyDataSetChanged();
                    }
                    AppData.decreaseOnTopCount();
                    appList.get(position).setAppOnTop(false);
                    onCheckClickListener.removeAppOnTop(appList.get(position));
                    Toast.makeText(view.getContext(), "Application '" + appList.get(position).getAppName() + "' remove from favourited", Toast.LENGTH_SHORT).show();
                }


                System.out.println(AppData.getAppOnTopCount());
            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAppItemClickListener.onAppItemClick(appList.get(position).getAppPackage());
            }
        });

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
