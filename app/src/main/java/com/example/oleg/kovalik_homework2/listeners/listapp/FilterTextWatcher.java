package com.example.oleg.kovalik_homework2.listeners.listapp;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Filter;

/**
 * Created by Oleg on 03.01.2017.
 */
public class FilterTextWatcher implements TextWatcher {
    private Filter filter;

    public FilterTextWatcher(Filter filter) {
        this.filter = filter;
    }

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
}

