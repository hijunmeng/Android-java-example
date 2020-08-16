package com.junmeng.android_java_example;

import android.os.Bundle;
import android.view.View;

import com.junmeng.android_java_example.common.BaseActivityDelegate;
import com.junmeng.android_java_example.frags.FragContainerActivity;
import com.junmeng.android_java_example.livedata.LiveDataActivity;
import com.junmeng.android_java_example.gesture.GestureActivity;

public class MainActivity extends BaseActivityDelegate {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickFrags(View view) {
        gotoActivity(FragContainerActivity.class);
    }

    public void onClickLiveData(View view) {
        gotoActivity(LiveDataActivity.class);
    }

    public void onClickGesture(View view) {
        gotoActivity(GestureActivity.class);
    }
}