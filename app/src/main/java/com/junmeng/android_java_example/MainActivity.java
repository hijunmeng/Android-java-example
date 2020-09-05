package com.junmeng.android_java_example;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.junmeng.android_java_example.anim.AnimListActivity;
import com.junmeng.android_java_example.common.BaseActivityDelegate;
import com.junmeng.android_java_example.frags.FragContainerActivity;
import com.junmeng.android_java_example.gesture.DragActivity;
import com.junmeng.android_java_example.gesture.GestureActivity;
import com.junmeng.android_java_example.livedata.LiveDataActivity;
import com.junmeng.android_java_example.round_layout.RoundLayoutActivity;
import com.junmeng.android_java_example.statusbar.StatusBarActivity;

public class MainActivity extends BaseActivityDelegate {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setBackgroundColor(Color.RED);
//        setStatusBarTransparent();
    }

    /**
     * 设置透明状态栏
     */
    private void setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

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
}