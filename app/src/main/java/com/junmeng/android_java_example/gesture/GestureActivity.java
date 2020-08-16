package com.junmeng.android_java_example.gesture;

import android.os.Bundle;
import android.view.ViewGroup;

import com.junmeng.android_java_example.R;
import com.junmeng.android_java_example.common.BaseActivityDelegate;
import com.junmeng.android_java_example.gesture.listener.GestureTouchListener;

public class GestureActivity extends BaseActivityDelegate {

    ViewGroup parent;
    TestSurfaceView surfaceView;
    ViewGroup target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture2);

        parent = findViewById(R.id.parent);
        surfaceView = findViewById(R.id.surfaceview);
        target = findViewById(R.id.target);

        new GestureTouchListener(parent, target);
    }

    @Override
    protected void onDestroy() {
        surfaceView.release();
        super.onDestroy();
    }
}