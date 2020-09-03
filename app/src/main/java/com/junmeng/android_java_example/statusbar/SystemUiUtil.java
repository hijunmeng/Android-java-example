package com.junmeng.android_java_example.statusbar;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_VISIBLE;

/**
 * 华为手机在设置SYSTEM_UI_FLAG_FULLSCREEN时刘海屏会变为黑条，如果想内容显示到刘海屏处，
 * 需要在所在的activity的清单文件中增加 <meta-data android:name="android.notch_support" android:value="true" />
 * 如果想全局生效，可在清单文件中的application元素下增加 <meta-data android:name="android.notch_support" android:value="true" />
 */
public class SystemUiUtil {
    /**
     * 隐藏状态栏
     * 界面由不可见变为可见时（如home键）状态栏会重新出现，且会遮挡内容区
     * 在状态栏区域处滑动会重新显示状态栏，且会遮挡内容区
     *
     * @param activity
     */
    public static void hideStatusBar(@NonNull Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        int uiOptions = SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

    }

    /**
     * 显示状态栏和导航栏
     * 内容区会在状态栏和导航栏之间，也就是说状态栏和导航栏不会覆盖内容区之上
     * @param activity
     */
    public static void showStatusBarAndNavigationBar(@NonNull Activity activity) {
        View decorView =activity.getWindow().getDecorView();
        decorView.setSystemUiVisibility(SYSTEM_UI_FLAG_VISIBLE);
    }
    /**
     * 隐藏状态栏
     * 在状态栏处滑动会显示半透明状态栏，且状态栏悬浮在内容区之上，过几秒后状态栏会自动隐藏
     * note:界面由不可见变为可见时（如home键）状态栏会重新出现，且会遮挡内容区
     *
     * @param activity
     */
    public static void hideStatusBarSticky(@NonNull Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | SYSTEM_UI_FLAG_FULLSCREEN | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    /**
     * 隐藏导航栏
     * 在导航栏处滑动会显示半透明导航栏，且导航栏悬浮在内容区之上，过几秒后导航栏会自动隐藏
     * @param activity
     */
    public static void hideNavigationBarSticky(@NonNull Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY|SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
    }


    /**
     * 隐藏状态栏和导航栏
     * 在状态栏区域或导航栏区域处滑动会重新显示状态栏和导航栏，但状态栏会覆盖在内容区之上，导航栏则不会
     * 可以配合View.setOnSystemUiVisibilityChangeListener监听系统ui可见时调用showStatusBarAndNavigationBar方法，使得状态栏和导航栏不会覆盖在内容区之上
     * @param activity
     */
    public void hideStatusBarAndNavigationBar(@NonNull Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE | SYSTEM_UI_FLAG_FULLSCREEN |SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN| SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
    }
    /**
     * 隐藏状态栏和导航栏(真正的全屏)
     * 在状态栏区域或导航栏区域处滑动会显示半透明状态栏和半透明导航栏，且状态栏和导航栏悬浮在内容区之上，过几秒后状态栏和导航栏会自动隐藏
     *
     * @param activity
     */
    public static void hideStatusBarAndNavigationBarSticky(@NonNull Activity activity) {
        View decorView = activity.getWindow().getDecorView();
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
