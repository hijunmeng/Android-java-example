package com.junmeng.android_java_example.gesture;

import android.os.Bundle;
import android.view.View;

import com.junmeng.android_java_example.R;
import com.junmeng.android_java_example.common.BaseActivityDelegate;

public class DragActivity extends BaseActivityDelegate {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag);
    }

    public void onClickImage(View view) {
        showToast("click image");
    }

    public void onClickLL(View view) {
        showToast("click linearlayout");
    }
}