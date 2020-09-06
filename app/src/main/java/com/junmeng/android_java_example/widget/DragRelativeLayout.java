package com.junmeng.android_java_example.widget;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
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
        //第二个参数为敏感度,值越大越敏感，1.0表示正常
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            /**
             * 是否捕获view
             * 在这里可以对要进行拖拽的view进行判断，返回true则表示要对此view进行拖拽
             * @param child
             * @param pointerId
             * @return
             */
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

                if (child instanceof FrameLayout) {
                    return dx < 0 ? child.getLeft() : left;//不能向左拖动
                }

                //直接返回left则控件可以拖出父view范围
                //return left;


//                让控件在水平方向上无法拖出父view范围,包含父view padding
//                final int leftPadding=getPaddingLeft();
//                final int rightPadding=getPaddingRight();
//                final int leftBound = leftPadding;
//                final int rightBound = getWidth() - child.getWidth() - rightPadding;
//                final int newLeft = Math.min(Math.max(left, leftBound), rightBound);
//                return newLeft;


                //让控件在水平方向上无法拖出父view范围,包含父view padding及child的margin
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                int leftMargin = 0;
                int rightMargin = 0;
                if (lp != null) {
                    leftMargin = lp.leftMargin;
                    rightMargin = lp.rightMargin;
                }
                final int leftPadding = getPaddingLeft();
                final int rightPadding = getPaddingRight();

                final int leftBound = leftPadding + leftMargin;
                final int rightBound = getWidth() - child.getWidth() - rightPadding - rightMargin;
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


                //让控件在垂直方向上无法拖出父view范围,包含父view padding
//                final int topPadding = getPaddingTop();
//                final int bottomPadding = getPaddingBottom();
//                final int topBound=topPadding;
//                final int bottomBound = getHeight() - child.getHeight() - bottomPadding;
//                final int newTop = Math.min(Math.max(top, topBound), bottomBound);
//                return newTop;

                //让控件在垂直方向上无法拖出父view范围,包含父view padding及child的margin
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                int topMargin = 0;
                int bottomMargin = 0;
                if (lp != null) {
                    topMargin = lp.topMargin;
                    bottomMargin = lp.bottomMargin;
                }

                final int bottomPadding = getPaddingBottom();
                final int topPadding = getPaddingTop();

                final int topBound = topPadding + topMargin;
                final int bottomBound = getHeight() - child.getHeight() - bottomPadding - bottomMargin;
                final int newTop = Math.min(Math.max(top, topBound), bottomBound);
                return newTop;

                //让控件只能水平左右移动
//                return child.getTop();
            }

            /**
             * 手指释放的时候回调
             * @param releasedChild
             * @param xvel x轴的速率
             * @param yvel y轴的速率
             */
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                Log.i(TAG, "onViewReleased: xvel=" + xvel + ",yvel=" + yvel);

                if (releasedChild instanceof FrameLayout) {
                    int fatherWidth = getMeasuredWidth();
                    if (releasedChild.getLeft() > fatherWidth / 2) {
                        Log.i(TAG, "onViewReleased: 超过一半了");
                        //向右拖动超过一半则松手后全部向右滑出
                        mViewDragHelper.settleCapturedViewAt(fatherWidth, releasedChild.getTop());////需要复写computeScroll让view可以回到指定位置
                        postInvalidate();
                        return;
                    }

                }


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

            /**
             * 边界检测，需要开启ViewDragHelper.setEdgeTrackingEnabled
             * @param edgeFlags 见ViewDragHelper.EDGE_
             * @param pointerId
             */
            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                Log.i(TAG, "onEdgeDragStarted: edgeFlags="+edgeFlags+",pointerId="+pointerId);
                //此处只是演示了在右边缘拖拽第二个子view
                mViewDragHelper.captureChildView(getChildAt(1), pointerId);
            }
        });

        //开启边界检测
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_RIGHT);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        Log.i(TAG, "onInterceptTouchEvent: ");
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
