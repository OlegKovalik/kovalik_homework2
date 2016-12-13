package com.example.oleg.kovalik_homework2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
//import android.widget.GridLayout;
import android.widget.ImageButton;
import android.support.v7.widget.GridLayout;

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
            View v = inflate(this, R.layout.data_cell, gridLayout);
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
}
