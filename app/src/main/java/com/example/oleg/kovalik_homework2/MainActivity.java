package com.example.oleg.kovalik_homework2;

import android.content.ClipData;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayout;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;


public class MainActivity extends BaseActivity implements View.OnClickListener {
    private ArrayList<View> appCell = new ArrayList<>();
    private int maxAppOnTop;
    private String[] appOnTop;
    private ViewGroup mainView;
    private View delPanel;
    private GridLayout gridLayout;
    private PackageManager pm;


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

        mainView = (ViewGroup) findViewById(R.id.main_layout);
        delPanel = getLayoutInflater().inflate(R.layout.del_panel, mainView, false);
        delPanel.setOnDragListener(new AppOnDelDragListener());


        maxAppOnTop = getSaveManager().getMaxOnTop();


        try {
            showData();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void showData() throws PackageManager.NameNotFoundException {

        appCell.clear();
        gridLayout = (GridLayout) findViewById(R.id.grid_layuot);


        for (int i = 0; i < maxAppOnTop; i++) {
            View view = getLayoutInflater().inflate(R.layout.data_cell, gridLayout, false);
            view.setTag(new ViewHolderCell(new ViewHolderItem(view.findViewById(R.id.app_on_top_text), view.findViewById(R.id.app_on_top_image), view.findViewById(R.id.app_container)), i));
            ((ViewHolderCell) view.getTag()).viewHolderItem.container.setOnTouchListener(new onAppTouchListener());
            ((ViewHolderCell) view.getTag()).viewHolderItem.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (appOnTop[((ViewHolderCell) view.getTag()).id] != getResources().getString(R.string.cellName)) {
                        startApp(appOnTop[((ViewHolderCell) view.getTag()).id]);
                    }
                }
            });
            view.setOnDragListener(new AppOnTopDragListener());
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

    private class ViewHolderItem {
        public TextView textView;
        public ImageView imageView;
        public LinearLayout container;


        public ViewHolderItem(View textView, View imageView, View contrainer) {
            this.textView = (TextView) textView;
            this.imageView = (ImageView) imageView;
            this.container = (LinearLayout) contrainer;


        }


    }

    private class ViewHolderCell {
        public ViewHolderItem viewHolderItem;
        public int id;


        public ViewHolderCell(ViewHolderItem viewHolderItem, int id) {
            this.viewHolderItem = viewHolderItem;
            this.id = id;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        appOnTop = getSaveManager().getAppOnTop();

        pm = this.getPackageManager();
        ApplicationInfo applicationInfo = null;

        for (int i = 0; i < maxAppOnTop; i++) {
            View view = appCell.get(i);

            if (!appOnTop[i].equals(getResources().getString(R.string.cellName))) {
                try {
                    applicationInfo = getPackageManager().getApplicationInfo(appOnTop[i], PackageManager.GET_META_DATA);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                ((ViewHolderCell) view.getTag()).viewHolderItem.textView.setText(applicationInfo.loadLabel(pm).toString());
                ((ViewHolderCell) view.getTag()).viewHolderItem.imageView.setImageDrawable(null);
                ((ViewHolderCell) view.getTag()).viewHolderItem.imageView.setImageDrawable(applicationInfo.loadIcon(pm));


            } else {
                ((ViewHolderCell) view.getTag()).viewHolderItem.textView.setText(getResources().getString(R.string.cellName));
                ((ViewHolderCell) view.getTag()).viewHolderItem.imageView.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_launcher));
            }


        }

    }

    private void startApp(String appPackage) {
        Intent launchIntent = pm.getLaunchIntentForPackage(appPackage);
        if (launchIntent != null) {
            startActivity(launchIntent);
        }
    }

    private final class onAppTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                System.out.println("Start Drug from " + ((ViewHolderCell) ((View) view.getParent()).getTag()).id + " " + ((ViewHolderCell) ((View) view.getParent()).getTag()).viewHolderItem.textView.getText());
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                mainView.addView(delPanel);
                view.setVisibility(View.INVISIBLE);
                gridLayout.invalidate();
                return true;
            } else {
                return false;
            }
        }
    }

    public class AppOnTopDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            Drawable enterShape = getResources().getDrawable(R.drawable.shape_droptarget);
            Drawable normalShape = getResources().getDrawable(R.drawable.shape);

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

                    if (((ViewHolderCell) to.getTag()).viewHolderItem.textView.getText().toString().equals(getResources().getString(R.string.cellName)) && ((ViewHolderCell) to.getTag()).id != ((ViewHolderCell) from.getTag()).id) {


                        View viewItemTo = to.getChildAt(0);

                        ViewHolderItem viewHolderFrom = ((ViewHolderCell) from.getTag()).viewHolderItem;
                        ((ViewHolderCell) from.getTag()).viewHolderItem = ((ViewHolderCell) to.getTag()).viewHolderItem;
                        ((ViewHolderCell) to.getTag()).viewHolderItem = viewHolderFrom;
                        appOnTop = getSaveManager().swapApp(appOnTop, ((ViewHolderCell) from.getTag()).id, ((ViewHolderCell) to.getTag()).id);

                        from.removeView(viewItem);
                        to.removeView(viewItemTo);

                        to.addView(viewItem);
                        from.addView(viewItemTo);

                    }
                    viewItem.setVisibility(View.VISIBLE);
                    mainView.removeView(delPanel);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackgroundDrawable(normalShape);
                    viewItem.setVisibility(View.VISIBLE);
                    mainView.removeView(delPanel);
                default:

                    //viewItem.setVisibility(View.VISIBLE);
                    break;
            }
            return true;
        }
    }

    public class AppOnDelDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            View viewItem = (View) event.getLocalState();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundColor(getResources().getColor(R.color.colorDelPanelActive));
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundColor(getResources().getColor(R.color.colorDelPanelNotActive));
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    ViewGroup from = (ViewGroup) viewItem.getParent();
                    appOnTop = getSaveManager().removeApp(appOnTop, ((ViewHolderCell) from.getTag()).id);
                    ((ViewHolderCell) from.getTag()).viewHolderItem.textView.setText(getResources().getString(R.string.cellName));
                    ((ViewHolderCell) from.getTag()).viewHolderItem.imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.mipmap.ic_launcher));
                    mainView.removeView(delPanel);
                    viewItem.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackgroundColor(getResources().getColor(R.color.colorDelPanelNotActive));
                default:


                    break;
            }
            return true;
        }
    }

}
