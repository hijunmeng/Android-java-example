package com.junmeng.android_java_example.widget;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.customview.widget.ViewDragHelper;

/**
 * 一个可以让任意子view进行拖拽的RelativeLayout
 * [(13条消息)Android ViewDragHelper完全解析 自定义ViewGroup神器_Hongyang-CSDN博客](https://blog.csdn.net/lmj623565791/article/details/46858663)
 */
public class DragRelativeLayout extends RelativeLayout {
    private static final String TAG = "DragRelativeLayout";
    private ViewDragHelper mViewDragHelper;

    private Point mCaptureViewPoint;//保存view被拖拽前的位置坐标

    public DragRelativeLayout(Context context) {
        this(context, null);
    }

    public DragRelativeLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragRelativeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //第二个参数为敏感度
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(@NonNull View child, int pointerId) {
                mCaptureViewPoint = new Point(child.getLeft(), child.getTop());
                Log.i(TAG, "tryCaptureView: pointerId=" + pointerId + ",child.getId()=" + child.getId());
                return true;
            }

            /**
             *
             * @param child
             * @param left 即将移动到的位置，即被拖动view的x坐标
             * @param dx 负表示向左拖，正表示向右拖
             * @return
             */
            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
//                Log.i(TAG, "clampViewPositionHorizontal: left=" + left + ",dx=" + dx);
                //直接返回left则控件可以拖出父view范围
                //return left;


//                让控件在水平方向上无法拖出父view范围
                final int leftBound = getPaddingLeft();
                final int rightBound = getWidth() - child.getWidth() - leftBound;
                final int newLeft = Math.min(Math.max(left, leftBound), rightBound);
                return newLeft;

//                //让控件只能垂直上下移动
//                return child.getLeft();
            }

            /**
             *
             * @param child
             * @param top 即将移动到的位置，即被拖动view的y坐标
             * @param dy 负表示向上拖，正表示向下拖
             * @return
             */
            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
//                Log.i(TAG, "clampViewPositionVertical: top=" + top + ",dy=" + dy);
                //直接返回top则控件可以拖出父view范围
                //return top;


                //让控件在垂直方向上无法拖出父view范围
                final int topBound = getPaddingTop();
                final int bottomBound = getHeight() - child.getHeight() - topBound;
                final int newTop = Math.min(Math.max(top, topBound), bottomBound);
                return newTop;

//                //让控件只能水平左右移动
//                return child.getTop();
            }

            /**
             * 手指释放的时候回调
             * @param releasedChild
             * @param xvel
             * @param yvel
             */
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                Log.i(TAG, "onViewReleased: xvel=" + xvel + ",yvel=" + yvel);
                if (mCaptureViewPoint != null) {
                    //设置view被拖拽后y不变，x变为被拖拽前的
                    mViewDragHelper.settleCapturedViewAt(mCaptureViewPoint.x, releasedChild.getTop());////需要复写computeScroll让view可以回到指定位置
                    postInvalidate();
                }

            }

            /**
             * 当拖动的view是可点击的或者拖动的view带有可点击的子view时，此处不能返回0，否则将无法拖动
             * @param child
             * @return
             */
            @Override
            public int getViewHorizontalDragRange(View child) {
                return getMeasuredWidth() - child.getMeasuredWidth();
            }

            /**
             * 当拖动的view是可点击的或者拖动的view带有可点击的子view时，此处不能返回0，否则将无法拖动
             * @param child
             * @return
             */
            @Override
            public int getViewVerticalDragRange(View child) {
                return getMeasuredHeight() - child.getMeasuredHeight();
            }
        });

        //mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
//        Log.i(TAG, "onInterceptTouchEvent: ");
        return mViewDragHelper.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        Log.i(TAG, "onTouchEvent: ");
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
//        Log.i(TAG, "computeScroll: ");
        if (mViewDragHelper.continueSettling(true)) {
            postInvalidate();
        }
    }

}
