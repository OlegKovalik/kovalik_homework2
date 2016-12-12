package com.example.oleg.kovalik_homework2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;

import static android.view.View.inflate;

public class MainActivity extends Activity implements View.OnClickListener {


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

        showData();


    }

    public void showData() {

        final GridLayout gridLayout = (GridLayout) findViewById(R.id.grid_layuot);

        for (int i = 0; i < getResources().getInteger(R.integer.gridColumnCount) * getResources().getInteger(R.integer.gridRowCount); i++) {
            View v = inflate(this, R.layout.data_cell, null);
            GridLayout.LayoutParams params = (GridLayout.LayoutParams) new GridLayout.LayoutParams();
            params.width = 200;
            params.height = 200;
            v.setLayoutParams(params);
            gridLayout.addView(v);
        }

        gridLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                int width = (view.getWidth()) / getResources().getInteger(R.integer.gridColumnCount);
                int height = (view.getHeight()) / getResources().getInteger(R.integer.gridRowCount);
                int margin = (int) getResources().getDimension(R.dimen.dataButtonMargin);
                for (int j = 0; j < getResources().getInteger(R.integer.gridColumnCount) * getResources().getInteger(R.integer.gridRowCount); j++) {
                    gridLayout.getChildAt(j).layout(width * (j % getResources().getInteger(R.integer.gridColumnCount)) + margin, height * (j / getResources().getInteger(R.integer.gridColumnCount)) + margin,
                            width * ((j % getResources().getInteger(R.integer.gridColumnCount) + 1)) - margin, height * ((j / getResources().getInteger(R.integer.gridColumnCount) + 1)) - margin);
                }
            }
        });
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
}
