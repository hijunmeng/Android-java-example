package com.junmeng.android_java_example.anim;

import android.os.Bundle;
import android.view.View;

import com.example.common.base.BaseActivityDelegate;
import com.junmeng.android_java_example.R;
import com.junmeng.android_java_example.anim.animatelayout.AnimateLayoutActivity;
import com.junmeng.android_java_example.anim.animatelayout.PropertyAnimatorActivity;
import com.junmeng.android_java_example.anim.animatelayout.ViewAnimatorActivity;

public class AnimListActivity extends BaseActivityDelegate {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_list);
    }

    public void onCLickLayout(View view) {
        gotoActivity(AnimateLayoutActivity.class);
    }

    public void onCLickProperty(View view) {
        gotoActivity(PropertyAnimatorActivity.class);
    }

    public void onCLickView(View view) {
        gotoActivity(ViewAnimatorActivity.class);
    }
}