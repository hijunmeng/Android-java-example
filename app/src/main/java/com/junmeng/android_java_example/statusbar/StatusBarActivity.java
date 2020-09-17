package com.junmeng.android_java_example.statusbar;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.junmeng.android_java_example.R;
import com.junmeng.android_java_example.common.BaseActivityDelegate;

import java.util.Random;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

public class StatusBarActivity extends BaseActivityDelegate {
    private static final String TAG = "StatusBarActivity";

    View vRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_status_bar);
        vRoot = findViewById(R.id.v_root);

        View decorView = getWindow().getDecorView();

        //setOnApplyWindowInsetsListener的第一个参数为decorView时会导致状态栏背景变白色,而且背景色无法设置成其他
        ViewCompat.setOnApplyWindowInsetsListener(vRoot, new OnApplyWindowInsetsListener() {
            @Override
            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                //获得状态栏高度和导航栏高度,首次进入activity会回调此
                Log.i(TAG, "onApplyWindowInsets: getStableInsetTop="+insets.getStableInsetTop());//可为0
                Log.i(TAG, "onApplyWindowInsets: getSystemWindowInsetTop="+insets.getSystemWindowInsetTop());//状态栏高度
                Log.i(TAG, "onApplyWindowInsets: getStableInsetBottom="+insets.getStableInsetBottom());//可为0
                Log.i(TAG, "onApplyWindowInsets: getSystemWindowInsetBottom="+insets.getSystemWindowInsetBottom());//导航栏高度，当系统设置导航栏不可见时为0
                return insets;
            }
        });

        Log.i(TAG, "onCreate:SystemUiVisibility= " + decorView.getSystemUiVisibility());
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                Log.i(TAG, "onSystemUiVisibilityChange:visibility=" + visibility);
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

                if ((visibility & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0) {
                    // TODO: The navigation bar is visible. Make any desired
                    // adjustments to your UI, such as showing the action bar or
                    // other navigational controls.
                    showToast("导航栏可见");

                } else {
                    // TODO: The navigation bar is NOT visible. Make any desired
                    // adjustments to your UI, such as hiding the action bar or
                    // other navigational controls.
                    showToast("导航栏隐藏");
                }
            }
        });

    }


    public void onClickLowProfile(View view) {
        //状态栏中的图标会消失
        //此标记实际上没什么应用场景，一般不会使用
        SystemUiUtil.hideStatusBarIcon(this);

    }

    /**
     * 显示系统栏状态
     *
     * @param view
     */
    public void onClickShowStatusBarAndNavigationBar(View view) {
        SystemUiUtil.showStatusBarAndNavigationBar(this);
    }

    public void onClickHideStatusBar(View view) {
        SystemUiUtil.hideStatusBarSticky(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onClickTransparentStatusBar(View view) {
        SystemUiUtil.transparentStatusBar(this);
    }

    public void onClickHideNavigationBar(View view) {
        SystemUiUtil.hideNavigationBarSticky(this);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onClickTransparentNavigationBar(View view) {
        SystemUiUtil.transparentNavigationBar(this);
    }




    public void onClickFullScreen(View view) {
        View decorView = getWindow().getDecorView();
        int uiOptions = SYSTEM_UI_FLAG_FULLSCREEN | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public void onClickRealFullScreen(View view) {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE | SYSTEM_UI_FLAG_FULLSCREEN | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public void onClickStickyFullScreen(View view) {
        SystemUiUtil.hideStatusBarAndNavigationBarSticky(this);
    }


    public void onClickGetStatusBarHeight(View view) {
        showToast("statusbar height = " + SystemUiUtil.getStatusBarHeight(this));

    }
    public void onClickGetNavigationBarHeightWithVisibility(View view) {
        showToast("navigationbar height = " + SystemUiUtil.getNavigationBarHeightWithVisibility(this));

    }

    public void onClickGetNavigationBarHeight(View view) {
        showToast("navigationbar height = " + SystemUiUtil.getNavigationBarHeight(this));

    }

    public void onClickCheckStatusBar(View view) {
        showToast("isStatusBarShown = " + SystemUiUtil.isStatusBarShown(this));

    }
    public void onClickCheckNavigationBar(View view) {
        showToast("isNavigationBarShown = " + SystemUiUtil.isNavigationBarShown(this));

    }

    public void onClickSetStatusBarLightMode(View view) {
        SystemUiUtil.onClickSetStatusBarMode(this,false);
    }
    public void onClickSetStatusBarDarkMode(View view) {
        SystemUiUtil.onClickSetStatusBarMode(this,true);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onClickSetStatusBarBg(View view) {

        Random r=new Random();
        int i=r.nextInt()%3;
        if(i==0){
            SystemUiUtil.setStatusBarBgColor(this, Color.RED);
        }
        if(i==1){
            SystemUiUtil.setStatusBarBgColor(this, 0x88ff0000);
        }
        if(i==2){
            SystemUiUtil.setStatusBarBgColor(this, getResources().getColor(R.color.colorPrimaryDark));
        }



    }

    public void onClickGetScreenHeight(View view) {
        showToast("screen height = " + SystemUiUtil.getScreenHeight(this));
    }

    public void onClickGetContentHeight(View view) {
        showToast("content height = " + SystemUiUtil.getContentHeight(this));
    }

    public void onClickCheckNotchScreen(View view) {
        showToast("是否挖孔屏？ " + SystemUiUtil.isNotchScreen(this));
    }

    public void onClickGetScreenWidth(View view) {
        showToast("screen width = " + SystemUiUtil.getScreenWidth(this));
    }

    public void onClickGetStatusBarHeightWithVisibility(View view) {
        showToast("statusbar height = " + SystemUiUtil.getStatusBarHeightWithVisibility(this));
    }
}