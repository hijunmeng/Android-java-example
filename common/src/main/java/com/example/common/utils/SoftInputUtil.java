package com.example.common.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class SoftInputUtil {
    /**
     * 显示软键盘
     *
     * @param view
     */
    public static void showSoftInput(EditText view) {
        if (view == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.showSoftInput(view, 0);
        }
    }

    /**
     * 隐藏软键盘
     *
     * @param view 只要能获得getWindowToken()的view都可以
     */
    public static void hideSoftInput(View view) {
        if (view == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private int softInputHeight = 0;
    private boolean softInputHeightChanged = false;

    private boolean isNavigationBarShow = false;
    private int navigationHeight = 0;

    private View anyView;
    private ISoftInputChanged listener;
    private boolean isSoftInputShowing = false;

    public interface ISoftInputChanged {
        /**
         * @param isSoftInputShow
         * @param softInputHeight
         * @param viewOffset      为了不被软键盘遮挡，该view需要向上的偏移量
         */
        void onChanged(boolean isSoftInputShow, int softInputHeight, int viewOffset);
    }

    /**
     * @param anyView  可以是任意view,也可以是不希望被软键盘遮挡的view
     * @param listener
     */
    public void attachSoftInput(final View anyView, final ISoftInputChanged listener) {
        if (anyView == null || listener == null) {
            return;
        }

        //根View
        final View rootView = anyView.getRootView();
        if (rootView == null) {
            return;
        }

        navigationHeight = getNavigationBarHeight(anyView.getContext());

        //anyView为需要调整高度的View，理论上来说可以是任意的View
        this.anyView = anyView;
        this.listener = listener;

        rootView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                //对于Activity来说，该高度即为屏幕高度
                int rootHeight = rootView.getHeight();
                Rect rect = new Rect();
                //获取当前可见部分，默认可见部分是除了状态栏和导航栏剩下的部分
                rootView.getWindowVisibleDisplayFrame(rect);

                if (rootHeight - rect.bottom == navigationHeight) {
                    //如果可见部分底部与屏幕底部刚好相差导航栏的高度，则认为有导航栏
                    isNavigationBarShow = true;
                } else if (rootHeight - rect.bottom == 0) {
                    //如果可见部分底部与屏幕底部平齐，说明没有导航栏
                    isNavigationBarShow = false;
                }

                //cal softInput height
                boolean isSoftInputShow = false;
                int softInputHeight = 0;
                //如果有导航栏，则要去除导航栏的高度
                int mutableHeight = isNavigationBarShow == true ? navigationHeight : 0;
                if (rootHeight - mutableHeight > rect.bottom) {
                    //除去导航栏高度后，可见区域仍然小于屏幕高度，则说明键盘弹起了
                    isSoftInputShow = true;
                    //键盘高度
                    softInputHeight = rootHeight - mutableHeight - rect.bottom;
                    if (SoftInputUtil.this.softInputHeight != softInputHeight) {
                        softInputHeightChanged = true;
                        SoftInputUtil.this.softInputHeight = softInputHeight;
                    } else {
                        softInputHeightChanged = false;
                    }
                }

                //获取目标View的位置坐标
                int[] location = new int[2];
                anyView.getLocationOnScreen(location);

                //条件1减少不必要的回调，只关心前后发生变化的
                //条件2针对软键盘切换手写、拼音键等键盘高度发生变化
                if (isSoftInputShowing != isSoftInputShow || (isSoftInputShow && softInputHeightChanged)) {
                    if (listener != null) {
                        //第三个参数为该View需要调整的偏移量
                        //此处的坐标都是相对屏幕左上角(0,0)为基准的
                        listener.onChanged(isSoftInputShow, softInputHeight, location[1] + anyView.getHeight() - rect.bottom);
                    }
                    isSoftInputShowing = isSoftInputShow;
                }
            }
        });
    }


    //***************STATIC METHOD******************

    public static int getNavigationBarHeight(Context context) {
        if (context == null)
            return 0;
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

}
