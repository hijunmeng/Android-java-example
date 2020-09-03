package com.junmeng.android_java_example.statusbar;

import android.app.ActionBar;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.junmeng.android_java_example.R;
import com.junmeng.android_java_example.common.BaseActivityDelegate;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
import static android.view.View.SYSTEM_UI_FLAG_LOW_PROFILE;
import static android.view.View.SYSTEM_UI_FLAG_VISIBLE;

public class StatusBarActivity extends BaseActivityDelegate {
    private static final String TAG = "StatusBarActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_status_bar);

        View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                Log.i(TAG,"onSystemUiVisibilityChange:visibility="+visibility);
                switch(visibility){
                    case SYSTEM_UI_FLAG_LOW_PROFILE:
                        Log.i(TAG,"onSystemUiVisibilityChange:SYSTEM_UI_FLAG_LOW_PROFILE(1)");
                        break;
                    case SYSTEM_UI_FLAG_HIDE_NAVIGATION:
                        Log.i(TAG,"onSystemUiVisibilityChange:SYSTEM_UI_FLAG_HIDE_NAVIGATION(2)");
                        break;
                    case SYSTEM_UI_FLAG_FULLSCREEN:
                        Log.i(TAG,"onSystemUiVisibilityChange:SYSTEM_UI_FLAG_FULLSCREEN(4)");
                        break;
                }
                // Note that system bars will only be "visible" if none of the
                // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
                if ((visibility & SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    // TODO: The system bars are visible. Make any desired
                    // adjustments to your UI, such as showing the action bar or
                    // other navigational controls.
                    showToast("系统ui可见");
                } else {
                    // TODO: The system bars are NOT visible. Make any desired
                    // adjustments to your UI, such as hiding the action bar or
                    // other navigational controls.
                    showToast("系统ui隐藏");
                }
            }
        });

    }

    public void onClickLowProfile(View view) {
        //状态栏中的图标会消失
        //此标记实际上没什么应用场景，一般不会使用
        View decorView = getWindow().getDecorView();
        int uiOptions = SYSTEM_UI_FLAG_LOW_PROFILE;
        decorView.setSystemUiVisibility(uiOptions);

    }

    /**
     * 显示系统栏状态
     *
     * @param view
     */
    public void onClickShowStatusBar(View view) {

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(SYSTEM_UI_FLAG_VISIBLE);
    }

    public void onClickHideStatusBar(View view) {
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY|SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    public void onClickTransparentStatusBar(View view) {
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(uiOptions);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }


    }

    public void onClickHideNavigationBar(View view) {


        View decorView = getWindow().getDecorView();
        // Hide both the navigation bar and the status bar.
        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
        // a general rule, you should design your app to hide the status bar whenever you
        // hide the navigation bar.
        //隐藏导航栏和状态栏
//        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        //隐藏导航栏
        int uiOptions = SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);


    }


    public void onClickTransparentNavigationBar(View view) {
        View decorView = getWindow().getDecorView();
        //此模式会使得内容顶上状态栏，被状态栏覆盖
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }
        decorView.setSystemUiVisibility(uiOptions);
    }

    public void onClickFullScreen(View view) {
        View decorView = getWindow().getDecorView();
        int uiOptions = SYSTEM_UI_FLAG_FULLSCREEN | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public void onClickRealFullScreen(View view) {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE | SYSTEM_UI_FLAG_FULLSCREEN |SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN| SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public void onClickStickyFullScreen(View view) {
        View decorView = getWindow().getDecorView();
//        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | SYSTEM_UI_FLAG_FULLSCREEN | SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | SYSTEM_UI_FLAG_FULLSCREEN | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public void onClickShowSystemUI(View view) {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

    }

    public void onClickHideSystemUI(View view) {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | SYSTEM_UI_FLAG_FULLSCREEN);


    }
}