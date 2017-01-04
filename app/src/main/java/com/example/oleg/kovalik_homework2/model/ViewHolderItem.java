package com.example.oleg.kovalik_homework2.model;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Oleg on 01.01.2017.
 */
public class ViewHolderItem {
    public TextView textView;
    public ImageView imageView;
    public LinearLayout container;


    public ViewHolderItem(View textView, View imageView, View contrainer) {
        this.textView = (TextView) textView;
        this.imageView = (ImageView) imageView;
        this.container = (LinearLayout) contrainer;


    }
}