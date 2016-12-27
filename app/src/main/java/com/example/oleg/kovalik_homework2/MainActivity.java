package com.example.oleg.kovalik_homework2;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayout;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

//import android.widget.GridLayout;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private ArrayList<View> appCell = new ArrayList<>();
    private int maxAppOnTop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton dialButton = (ImageButton) findViewById(R.id.dial_button);
        ImageButton smsButton = (ImageButton) findViewById(R.id.sms_button);
        Button appButton = (Button) findViewById(R.id.app_button);

        dialButton.setOnClickListener(this);
        smsButton.setOnClickListener(this);
        appButton.setOnClickListener(this);

        maxAppOnTop = getSaveManager().getMaxOnTop();


        try {
            showData();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void showData() throws PackageManager.NameNotFoundException {

        appCell.clear();
        GridLayout gridLayout = (GridLayout) findViewById(R.id.grid_layuot);


        for (int i = 0; i < maxAppOnTop; i++) {
            View view = getLayoutInflater().inflate(R.layout.data_cell,gridLayout,false);
            view.setTag(new ViewHolder(view.findViewById(R.id.app_on_top_text), view.findViewById(R.id.app_on_top_image)));
            appCell.add(view);
            gridLayout.addView(view);
        }


    }

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
                intent = new Intent(this, NewAppActivity.class);
                break;

        }
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private class ViewHolder {
        public TextView textView;
        public ImageView imageView;

        public ViewHolder(View textView, View imageView) {
            this.textView = (TextView) textView;
            this.imageView = (ImageView) imageView;
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        Iterator dataIterator = ((HashSet<String>) getSaveManager().getAppOnTop()).iterator();
        PackageManager pm = this.getPackageManager();
        ApplicationInfo applicationInfo = null;

        for (int i = 0; i < maxAppOnTop; i++) {
            View view = appCell.get(i);

            if (dataIterator.hasNext()) {
                try {
                    applicationInfo = getPackageManager().getApplicationInfo((String) dataIterator.next(), PackageManager.GET_META_DATA);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                ((ViewHolder) view.getTag()).textView.setText(applicationInfo.loadLabel(pm).toString());
                ((ViewHolder) view.getTag()).imageView.setImageDrawable(null);
                ((ViewHolder) view.getTag()).imageView.setImageDrawable(applicationInfo.loadIcon(pm));

            } else {
                ((ViewHolder) view.getTag()).textView.setText(getResources().getString(R.string.cellName));
                ((ViewHolder) view.getTag()).imageView.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_launcher));
            }


        }

    }
}
