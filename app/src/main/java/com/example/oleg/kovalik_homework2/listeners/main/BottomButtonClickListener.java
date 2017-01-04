package com.example.oleg.kovalik_homework2.listeners.main;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import com.example.oleg.kovalik_homework2.R;
import com.example.oleg.kovalik_homework2.ui.ListAppActivity;

/**
 * Created by Oleg on 04.01.2017.
 */
public class BottomButtonClickListener implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        Intent intent = null;

        switch (view.getId()) {
            case R.id.dial_button:
                intent = new Intent(Intent.ACTION_DIAL);
                break;
            case R.id.sms_button:
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("sms:"));
                break;
            case R.id.app_button:
                intent = new Intent(view.getContext(), ListAppActivity.class);
                break;

        }
        view.getContext().startActivity(intent);
    }
}
