package com.junmeng.android_java_example;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.junmeng.android_java_example.common.BaseActivityDelegate;
import com.junmeng.android_java_example.frags.FragContainerActivity;
import com.junmeng.android_java_example.livedata.LiveDataActivity;

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
}