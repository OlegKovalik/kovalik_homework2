package com.example.oleg.kovalik_homework2.listeners.main;

import android.graphics.drawable.Drawable;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.example.oleg.kovalik_homework2.AppManager;
import com.example.oleg.kovalik_homework2.R;
import com.example.oleg.kovalik_homework2.model.ViewHolderCell;
import com.example.oleg.kovalik_homework2.model.ViewHolderItem;
import com.example.oleg.kovalik_homework2.ui.MainActivity;

/**
 * Created by Oleg on 01.01.2017.
 */
public class AppOnTopDragListener implements View.OnDragListener {

    private AppManager appManager;

    public AppOnTopDragListener(AppManager appManager) {
        this.appManager = appManager;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        Drawable enterShape = v.getResources().getDrawable(R.drawable.shape_droptarget);
        Drawable normalShape = v.getResources().getDrawable(R.drawable.shape);

        int action = event.getAction();
        View viewItem = (View) event.getLocalState();
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                // do nothing
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                v.setBackgroundDrawable(enterShape);
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                v.setBackgroundDrawable(normalShape);
                break;
            case DragEvent.ACTION_DROP:
                // Dropped, reassign View to ViewGroup
                LinearLayout to = (LinearLayout) v;
                ViewGroup from = (ViewGroup) viewItem.getParent();

                if (((ViewHolderCell) to.getTag()).viewHolderItem.textView.getText().toString().equals(v.getResources().getString(R.string.cellName)) && ((ViewHolderCell) to.getTag()).id != ((ViewHolderCell) from.getTag()).id) {

                    View viewItemTo = to.getChildAt(0);

                    ViewHolderItem viewHolderFrom = ((ViewHolderCell) from.getTag()).viewHolderItem;
                    ((ViewHolderCell) from.getTag()).viewHolderItem = ((ViewHolderCell) to.getTag()).viewHolderItem;
                    ((ViewHolderCell) to.getTag()).viewHolderItem = viewHolderFrom;
                    appManager.swapApp(((ViewHolderCell) from.getTag()).id, ((ViewHolderCell) to.getTag()).id);

                    from.removeView(viewItem);
                    to.removeView(viewItemTo);

                    to.addView(viewItem);
                    from.addView(viewItemTo);

                }
                viewItem.setVisibility(View.VISIBLE);
                ((MainActivity) v.getContext()).delDelPanel();
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                v.setBackgroundDrawable(normalShape);
                viewItem.setVisibility(View.VISIBLE);
                ((MainActivity) v.getContext()).delDelPanel();
            default:
                break;
        }
        return true;
    }
}