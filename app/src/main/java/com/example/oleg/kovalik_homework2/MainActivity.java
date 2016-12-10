package com.example.oleg.kovalik_homework2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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
        TableLayout tableLayout = (TableLayout) findViewById(R.id.table_layuot);

        for (int i = 0; i < 4; i++) {
            TableRow tableRow = new TableRow(this);
            for (int j = 0; j < 3; j++) {
                View.inflate(this, R.layout.data_cell, tableRow);
            }
            tableLayout.addView(tableRow, new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT, 1.0f));
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}
