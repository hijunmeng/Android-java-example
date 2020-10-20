package com.junmeng.android_java_example;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.junmeng.android_java_example.alertwindow.AlertWindowActivity;
import com.junmeng.android_java_example.anim.AnimListActivity;
import com.junmeng.android_java_example.audio.AudioActivity;
import com.junmeng.android_java_example.common.BaseActivityDelegate;
import com.junmeng.android_java_example.frags.FragContainerActivity;
import com.junmeng.android_java_example.gesture.DragActivity;
import com.junmeng.android_java_example.gesture.GestureActivity;
import com.junmeng.android_java_example.livedata.LiveDataActivity;
import com.junmeng.android_java_example.mediaprojection.MediaProjectionActivity;
import com.junmeng.android_java_example.notification.NotificationActivity;
import com.junmeng.android_java_example.recycler.RecyclerViewActivity;
import com.junmeng.android_java_example.round_layout.RoundLayoutActivity;
import com.junmeng.android_java_example.setting.SettingActivity;
import com.junmeng.android_java_example.statusbar.StatusBarActivity;

public class MainActivity extends BaseActivityDelegate {
    private static final String TAG = "MainActivity";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setBackgroundColor(Color.RED);

//
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

    public void onClickAnim(View view) {
        gotoActivity(AnimListActivity.class);
    }

    public void onClickRoundLayout(View view) {
        gotoActivity(RoundLayoutActivity.class);

    }

    public void onClickStatusBar(View view) {
        gotoActivity(StatusBarActivity.class);
    }

    public void onClickDrag(View view) {
        gotoActivity(DragActivity.class);
    }

    public void onClickRecycler(View view) {
        gotoActivity(RecyclerViewActivity.class);
    }

    public void onClickAudio(View view) {
        gotoActivity(AudioActivity.class);
    }

    public void onClickMediaProjection(View view) {
        gotoActivity(MediaProjectionActivity.class);
    }

    public void onClickNotification(View view) {
        gotoActivity(NotificationActivity.class);
    }

    public void onClickAlertWindow(View view) {
        gotoActivity(AlertWindowActivity.class);
    }

    public void onClickSetting(View view) {
        gotoActivity(SettingActivity.class);
    }
}