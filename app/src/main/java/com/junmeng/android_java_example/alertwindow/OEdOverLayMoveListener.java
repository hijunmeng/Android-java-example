package com.junmeng.android_java_example.alertwindow;

import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class OEdOverLayMoveListener implements View.OnTouchListener {
    private final WindowManager windowManager;
    private final View view;
    private WindowManager.LayoutParams params;
    private int initialX;
    private int initialY;
    private float initialTouchX;
    private float initialTouchY;


    public OEdOverLayMoveListener(WindowManager windowManager, View view) {
        this.windowManager = windowManager;
        this.view = view;
        this.params = (WindowManager.LayoutParams) view.getLayoutParams();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //remember the initial position.
                initialX = params.x;
                initialY = params.y;
                //get the touch location
                initialTouchX = event.getRawX();
                initialTouchY = event.getRawY();
                return false;
            case MotionEvent.ACTION_MOVE:
                //Calculate the X and Y coordinates of the view.
                params.x = initialX + (int) (event.getRawX() - initialTouchX);
                params.y = initialY + (int) (event.getRawY() - initialTouchY);
                //Update the layout with new X & Y coordinate
                windowManager.updateViewLayout(view, params);
                return true;
        }
        return false;
    }
}