package com.junmeng.android_java_example.anim;

import android.os.Bundle;
import android.view.View;

import com.junmeng.android_java_example.R;
import com.junmeng.android_java_example.anim.animatelayout.AnimateLayoutActivity;
import com.junmeng.android_java_example.common.BaseActivityDelegate;

public class AnimListActivity extends BaseActivityDelegate {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_list);
    }

    public void onCLickLayout(View view) {
        gotoActivity(AnimateLayoutActivity.class);
    }
}