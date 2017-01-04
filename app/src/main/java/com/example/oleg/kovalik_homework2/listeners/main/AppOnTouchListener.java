package com.example.oleg.kovalik_homework2.listeners.main;

import android.content.ClipData;
import android.view.MotionEvent;
import android.view.View;
import com.example.oleg.kovalik_homework2.model.ViewHolderCell;
import com.example.oleg.kovalik_homework2.ui.MainActivity;

/**
 * Created by Oleg on 04.01.2017.
 */
public class AppOnTouchListener implements View.OnTouchListener {
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            System.out.println("Start Drug from " + ((ViewHolderCell) ((View) view.getParent()).getTag()).id + " " + ((ViewHolderCell) ((View) view.getParent()).getTag()).viewHolderItem.textView.getText());
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(data, shadowBuilder, view, 0);
            view.setVisibility(View.INVISIBLE);
            ((MainActivity) view.getContext()).addDelPanel();
            return true;
        } else {
            return false;
        }
    }
}
