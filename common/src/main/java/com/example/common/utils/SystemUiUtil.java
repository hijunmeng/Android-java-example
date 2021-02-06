package com.example.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.lang.reflect.Method;
import java.util.List;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
import static android.view.View.SYSTEM_UI_FLAG_LOW_PROFILE;
import static android.view.View.SYSTEM_UI_FLAG_VISIBLE;

/**
 * 华为手机在设置SYSTEM_UI_FLAG_FULLSCREEN时刘海屏会变为黑条，如果想内容显示到刘海屏处，
 * 需要在所在的activity的清单文件中增加 <meta-data android:name="android.notch_support" android:value="true" />
 * 如果想全局生效，可在清单文件中的application元素下增加 <meta-data android:name="android.notch_support" android:value="true" />
 * <p>
 * <p>
 * <p>
 * 如果想知道导航栏是否可见，可以在DecoderView设置监听
 * decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
 *
 * @Override public void onSystemUiVisibilityChange(int visibility) {
 * Log.i(TAG, "onSystemUiVisibilityChange:visibility=" + visibility);
 * if ((visibility & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0) {
 * showToast("导航栏可见");
 * } else {
 * showToast("导航栏隐藏");
 * }
 * }
 * });
 *
 * 屏幕主要分为3部分，分别是状态栏，内容区，导航栏
 */
public class SystemUiUtil {

    /**
     * 隐藏状态栏中图标，文字颜色也会变浅
     * 如果想恢复可以调用showStatusBarAndNavigationBar
     *
     * @param activity
     */
    public static void hideStatusBarIcon(@NonNull Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        int uiOptions = SYSTEM_UI_FLAG_LOW_PROFILE;
        decorView.setSystemUiVisibility(uiOptions);
    }

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
     * 如果系统已经设置导航栏不可见，则此操作后导航栏依然不可见
     *
     * @param activity
     */
    public static void showStatusBarAndNavigationBar(@NonNull Activity activity) {
        View decorView = activity.getWindow().getDecorView();
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
     * 透明状态栏（状态栏会覆盖在内容区之上，沉浸式效果）
     * 适用于api21及以上
     *
     * @param activity
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void transparentStatusBar(@NonNull Activity activity) {
        Window window = activity.getWindow();
        View decorView = window.getDecorView();
        // Hide the status bar.
        int uiOptions = SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(uiOptions);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.TRANSPARENT);
        }

    }

    /**
     * 半透明导航栏，导航栏会覆盖在内容区上面，但同时内容区也会被状态栏覆盖
     *
     * @param activity
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void transparentNavigationBar(@NonNull Activity activity) {
        Window window = activity.getWindow();
        View decorView = window.getDecorView();
        //此模式会使得内容顶上状态栏，被状态栏覆盖
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }


    /**
     * 隐藏导航栏
     * 在导航栏处滑动会显示半透明导航栏，且导航栏悬浮在内容区之上，过几秒后导航栏会自动隐藏
     *
     * @param activity
     */
    public static void hideNavigationBarSticky(@NonNull Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
    }


    /**
     * 隐藏状态栏和导航栏
     * 在状态栏区域或导航栏区域处滑动会重新显示状态栏和导航栏，但状态栏会覆盖在内容区之上，导航栏则不会
     * 可以配合View.setOnSystemUiVisibilityChangeListener监听系统ui可见时调用showStatusBarAndNavigationBar方法，使得状态栏和导航栏不会覆盖在内容区之上
     *
     * @param activity
     */
    public void hideStatusBarAndNavigationBar(@NonNull Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE | SYSTEM_UI_FLAG_FULLSCREEN | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | SYSTEM_UI_FLAG_HIDE_NAVIGATION;
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

    /**
     * 获得状态栏高度(不管当前状态栏是否可见)
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(@NonNull Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;


    }

    /**
     * 获得状态栏高度(如果当前状态栏不可见则为0)
     *
     * @param activity
     * @return
     */
    public static int getStatusBarHeightWithVisibility(@NonNull Activity activity) {
        Rect outRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        return outRect.top;
    }

    /**
     * 获得导航栏高度(如果当前导航栏不可见则为0)
     *
     * @param activity
     * @return
     */
    public static int getNavigationBarHeightWithVisibility(@NonNull Activity activity) {
        Rect outRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        return getScreenHeight(activity) - outRect.bottom;
    }


    /**
     * 获得导航栏高度(不管当前导航栏是否可见)
     *
     * @param context
     * @return
     */
    public static int getNavigationBarHeight(@NonNull Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    /**
     * 判断状态栏是否可见
     * （背景透明的状态栏也算可见）
     *
     * @param activity
     * @return
     */
    public static boolean isStatusBarShown(@NonNull Activity activity) {

        //note: 在沉浸模式下，即使内容区已经被状态栏遮盖，但outRect.top仍然为状态栏高度，不为0
        Rect outRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        Log.i("123456", "left=" + outRect.left + ",top=" + outRect.top);
        if (outRect.top == 0) {
            return false;
        }
        return true;
    }

    /**
     * 判断导航栏是否可见
     * （背景透明的导航栏也算可见）
     *
     * @param activity
     * @return
     */
    public static boolean isNavigationBarShown(@NonNull Activity activity) {
        //note: 在沉浸模式下，即使内容区已经被导航栏遮盖，但outRect.bottom仍然不为屏幕高度，而是屏幕高度-导航栏高度
        Rect outRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        Log.i("123456", "right=" + outRect.right + ",bottom=" + outRect.bottom);
        int screenHeight = getScreenHeight(activity);
        if (outRect.bottom == screenHeight) {
            return false;
        }
        return true;
    }

    /**
     * 设置状态栏背景颜色
     *
     * @param activity
     * @param color
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarBgColor(@NonNull Activity activity, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(color);
        }
    }

    /**
     * 设置状态栏文字颜色模式
     *
     * @param activity
     * @param dark     true--文字白色 false--文字黑色
     */
    public static void onClickSetStatusBarMode(@NonNull Activity activity, boolean dark) {
        View decorView = activity.getWindow().getDecorView();
        if (dark) {
            //去除View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR标记
            decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & (~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR));
        } else {
            //增加View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR标记
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | decorView.getSystemUiVisibility());


        }

    }

    /**
     * 获得状态栏背景颜色
     *
     * @param activity
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static int getStatusBarBgColor(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return activity.getWindow().getStatusBarColor();
        }
        return 0;
    }

    /**
     * 获得当前内容区高度（会随着系统UI的可见性而变化，例如弹出输入法时，会相应减去输入法的高度）
     *
     * @param activity
     * @return
     */
    public static int getContentVisibleHeight(@NonNull Activity activity) {
        Rect outRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        Log.i("123456", "left=" + outRect.left + ",top=" + outRect.top);
        return outRect.height();
    }

    /**
     * 获得手机屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager)
                context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.y;
    }

    /**
     * 获得手机屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager)
                context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.x;
    }


    /**
     * 判断是否是刘海屏
     *
     * @return
     */
    public static boolean isNotchScreen(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowInsets wi = activity.getWindow().getDecorView().getRootWindowInsets();
            if (wi != null) {
                DisplayCutout displayCutout = wi.getDisplayCutout();
                if (displayCutout != null) {
                    List<Rect> rects = displayCutout.getBoundingRects();
                    //通过判断是否存在rects来确定是否刘海屏手机
                    if (rects != null && rects.size() > 0) {
                        return true;
                    }

                }
            }
        }
        if (isHuaweiNotchScreen(activity)
                || isOppoNotchScreen(activity) || isVivoNotchScreen(activity) || isXiaomiNotchScreen(activity)) { //TODO 各种品牌
            return true;
        }
        return false;
    }

    /**
     * 华为刘海屏判断
     *
     * @param context
     * @return
     */
    public static boolean isHuaweiNotchScreen(Context context) {
        boolean ret = false;
        try {
            ClassLoader cl = context.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("hasNotchInScreen");
            ret = (boolean) get.invoke(HwNotchSizeUtil);
        } catch (Exception e) {
        } finally {
            return ret;
        }
    }

    /**
     * OPPO刘海屏判断
     * [OPPO开放平台](https://open.oppomobile.com/wiki/doc#id=10159)
     *
     * @return
     */
    public static boolean isOppoNotchScreen(Context context) {
        return context.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
    }


    /**
     * VIVO刘海屏判断
     * [OPPO开放平台](https://open.oppomobile.com/wiki/doc#id=10159)
     *
     * @return
     */
    public static boolean isVivoNotchScreen(Context context) {
        int VIVO_NOTCH = 0x00000020;//是否有刘海
        boolean ret = false;
        try {
            ClassLoader classLoader = context.getClassLoader();
            Class FtFeature = classLoader.loadClass("android.util.FtFeature");
            Method method = FtFeature.getMethod("isFeatureSupport", int.class);
            ret = (boolean) method.invoke(FtFeature, VIVO_NOTCH);
        } catch (Exception e) {
        } finally {
            return ret;
        }
    }

    /**
     * 小米刘海屏判断
     * [文档中心](https://dev.mi.com/console/doc/detail?pId=1293)
     *
     * @param context
     * @return
     */
    public static boolean isXiaomiNotchScreen(Context context) {
        int result = 0;
        try {
            ClassLoader classLoader = context.getClassLoader();
            @SuppressWarnings("rawtypes")
            Class SystemProperties = classLoader.loadClass("android.os.SystemProperties");
            //参数类型
            @SuppressWarnings("rawtypes")
            Class[] paramTypes = new Class[2];
            paramTypes[0] = String.class;
            paramTypes[1] = int.class;
            Method getInt = SystemProperties.getMethod("getInt", paramTypes);
            //参数
            Object[] params = new Object[2];
            params[0] = new String("ro.miui.notch");
            params[1] = new Integer(0);
            result = (Integer) getInt.invoke(SystemProperties, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result == 1;
    }


}
