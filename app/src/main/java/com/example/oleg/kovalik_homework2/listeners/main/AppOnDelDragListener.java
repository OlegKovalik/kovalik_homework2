package com.example.oleg.kovalik_homework2.listeners.main;

import android.support.v4.content.ContextCompat;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import com.example.oleg.kovalik_homework2.AppManager;
import com.example.oleg.kovalik_homework2.R;
import com.example.oleg.kovalik_homework2.model.ViewHolderCell;
import com.example.oleg.kovalik_homework2.ui.MainActivity;

/**
 * Created by Oleg on 01.01.2017.
 */
public class AppOnDelDragListener implements View.OnDragListener {
    private AppManager appManager;

    public AppOnDelDragListener(AppManager appManager) {
        this.appManager = appManager;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        int action = event.getAction();
        View viewItem = (View) event.getLocalState();
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                // do nothing
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                v.setBackgroundColor(v.getResources().getColor(R.color.colorDelPanelActive));
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                v.setBackgroundColor(v.getResources().getColor(R.color.colorDelPanelNotActive));
                break;
            case DragEvent.ACTION_DROP:
                // Dropped, reassign View to ViewGroup
                ViewGroup from = (ViewGroup) viewItem.getParent();
                appManager.removeApp(((ViewHolderCell) from.getTag()).id);
                appManager.decreaseOnTopCount();
                ((ViewHolderCell) from.getTag()).viewHolderItem.textView.setText(v.getResources().getString(R.string.cellName));
                ((ViewHolderCell) from.getTag()).viewHolderItem.imageView.setImageDrawable(ContextCompat.getDrawable(v.getContext(), R.mipmap.ic_launcher));
                ((MainActivity) v.getContext()).delDelPanel();
                viewItem.setVisibility(View.VISIBLE);
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                v.setBackgroundColor(v.getResources().getColor(R.color.colorDelPanelNotActive));
            default:
                break;
        }
        return true;
    }
}
