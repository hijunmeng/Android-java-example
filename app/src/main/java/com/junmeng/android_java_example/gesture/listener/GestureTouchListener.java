package com.junmeng.android_java_example.gesture.listener;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.core.util.Preconditions;

public class GestureTouchListener extends GestureDetector.SimpleOnGestureListener implements ScaleGestureDetector.OnScaleGestureListener {
    private static final String TAG = "GestureTouchListener";

    private static final int ANIM_DURATION_MS = 300;//动画时间
    private static final int FLOAT_TIMES = 10000;//浮点型精度，10000表示小数点4位

    private float doubleClickScale = 2.0f;//双击放大的倍数

    private float maxScale = 3.0f;//最大缩放比例，不能小于doubleClickScale
    private float minScale = 1.0f;//最小缩放比例

    private ViewGroup mParentView;
    private View mTargetView;

    //最开始的尺寸
    private int mTargetViewSourceWidth;
    private int mTargetViewSourceHeight;
    private int mParentViewSourceWidth;
    private int mParentViewSourceHeight;


    //当前尺寸
    private int mTargetViewCurrentWidth;
    private int mTargetViewCurrentHeight;
    private int mParentViewCurrentWidth;
    private int mParentViewCurrentHeight;

    private GestureDetector mGestureDetector;
    private ScaleGestureDetector mScaleGestureDetector;

    private boolean isScaleEnd = true;//手指缩放是否结束
    private boolean isFitCenter = true;//是否还原原位置大小
    private boolean isAnimEnd = true;//动画是否结束

    private float tempDistanceX;//相对于原始view的位置
    private float tempDistanceY;//相对于原始view的位置


    private float scale = 1.0f;//相对于当前view的尺寸的缩放比例
    private float scaleTemp = 1.0f;//相对于当前view的尺寸的缩放比例

    private float absoluteScale = 1.0f;//相对于原始view的尺寸的缩放比例


    @SuppressLint("RestrictedApi")
    public GestureTouchListener(@NonNull ViewGroup parentView, @NonNull View targetView) {


        this.mParentView = Preconditions.checkNotNull(parentView, "parentView == null");
        this.mTargetView = Preconditions.checkNotNull(targetView, "targetView == null");
        mGestureDetector = new GestureDetector(mTargetView.getContext(), this);
        mScaleGestureDetector = new ScaleGestureDetector(mTargetView.getContext(), this);
        //当视图树的布局、视图树的焦点、视图树将要绘制、视图树滚动等发生改变时，ViewTreeObserver都会收到通知
        mTargetView.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        //调用一次后需要注销这个监听，否则会阻塞ui线程
                        targetView.getViewTreeObserver().removeOnPreDrawListener(this);

                        if (mTargetViewSourceWidth <= 0 && targetView.getWidth() > 0) {
                            mTargetViewSourceWidth = targetView.getWidth();
                        }
                        if (mTargetViewSourceHeight <= 0 && targetView.getHeight() > 0) {
                            mTargetViewSourceHeight = targetView.getHeight();
                        }

                        mTargetViewCurrentWidth = targetView.getWidth();
                        mTargetViewCurrentHeight = targetView.getHeight();

                        if (mParentViewSourceWidth <= 0 && mParentView.getWidth() > 0) {
                            mParentViewSourceWidth = mParentView.getWidth();
                        }
                        if (mParentViewSourceHeight <= 0 && mParentView.getHeight() > 0) {
                            mParentViewSourceHeight = mParentView.getHeight();
                        }
                        mParentViewCurrentWidth = mParentView.getWidth();
                        mParentViewCurrentHeight = mParentView.getHeight();

                        Log.i(TAG, "onPreDraw:mTargetViewSourceWidth= " + mTargetViewSourceWidth + ",mTargetViewSourceHeight=" + mTargetViewSourceHeight);
                        Log.i(TAG, "onPreDraw:mTargetViewCurrentWidth= " + mTargetViewCurrentWidth + ",mTargetViewCurrentHeight=" + mTargetViewCurrentHeight);
                        Log.i(TAG, "onPreDraw:mParentViewSourceWidth= " + mParentViewSourceWidth + ",mParentViewSourceHeight=" + mParentViewSourceHeight);
                        Log.i(TAG, "onPreDraw:mParentViewCurrentWidth= " + mParentViewCurrentWidth + ",mParentViewCurrentHeight=" + mParentViewCurrentHeight);
                        return true;
                    }
                });

        mTargetView.setClickable(false);
        mParentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getPointerCount() == 1 && isScaleEnd) {
                    return mGestureDetector.onTouchEvent(event);
                } else if (event.getPointerCount() == 2 || !isScaleEnd) {
                    isScaleEnd = event.getAction() == MotionEvent.ACTION_UP;
                    if (isScaleEnd) {
                        onActionUp();
                    }
                    return mScaleGestureDetector.onTouchEvent(event);
                }
                return false;
            }
        });
    }

    private void onActionUp() {
        if (mParentView instanceof FrameLayout) {
            Log.i(TAG, "onActionUp: ");
            requestLayoutWithScale();
        }
    }

    private void requestLayoutWithScale() {

        if (absoluteScale * scaleTemp > maxScale) {//大于最大比例，
            scaleTemp = maxScale / absoluteScale;
        }
        if (absoluteScale * scaleTemp < minScale) {
            scaleTemp = minScale / absoluteScale;
        }
        absoluteScale *= scaleTemp;
        mTargetViewCurrentWidth = (int) (scaleTemp * mTargetViewCurrentWidth);
        mTargetViewCurrentHeight = (int) (scaleTemp * mTargetViewCurrentHeight);

        scale = scaleTemp = 1.0f;

        mTargetView.setScaleX(scale);
        mTargetView.setScaleY(scale);

        ViewGroup.LayoutParams lp = mTargetView.getLayoutParams();
        lp.width = mTargetViewCurrentWidth;
        lp.height = mTargetViewCurrentHeight;
        mTargetView.requestLayout();
        isFitCenter = false;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        Log.i(TAG, "onScale: factor=" + detector.getScaleFactor());
        float factor = detector.getScaleFactor();
        scale = factor * scaleTemp;
        mTargetView.setScaleX(scale);
        mTargetView.setScaleY(scale);
        isFitCenter = false;
        return false;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        Log.i(TAG, "onScaleBegin: factor=" + detector.getScaleFactor());
        return true;//返回true才能onScale
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        Log.i(TAG, "onScaleEnd: factor=" + detector.getScaleFactor());
        scaleTemp = scale;


    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.i(TAG, "onDoubleTap: ");
        if (!isAnimEnd) {
            return false;
        }
        if (isFitCenter) {
//            scale(doubleClickScale);
            scaleWithAnim2(doubleClickScale);
        } else {
//            fitCenter();
            fitCenterWithAnim2();
        }


        return super.onDoubleTap(e);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.i(TAG, "onDown: ");
        return true;//必须返回true

    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.i(TAG, "onScroll: distanceX=" + distanceX + ",distanceY=" + distanceY);

        distanceX = -distanceX;
        distanceY = -distanceY;
        tempDistanceX += distanceX;
        tempDistanceY += distanceY;
        mTargetView.setTranslationX(tempDistanceX);
        mTargetView.setTranslationY(tempDistanceY);
        isFitCenter = false;
        return super.onScroll(e1, e2, distanceX, distanceY);
    }


    public void fitCenter() {

        mTargetViewCurrentWidth = mTargetViewSourceWidth;
        mTargetViewCurrentHeight = mTargetViewSourceHeight;

        absoluteScale=scale = scaleTemp = 1.0f;
        mTargetView.setScaleX(scale);
        mTargetView.setScaleY(scale);
        tempDistanceX = 0;
        tempDistanceY = 0;
        mTargetView.setTranslationX(tempDistanceX);
        mTargetView.setTranslationY(tempDistanceY);


        ViewGroup.LayoutParams lp = mTargetView.getLayoutParams();
        lp.width = mTargetViewCurrentWidth;
        lp.height = mTargetViewCurrentHeight;
        mTargetView.requestLayout();
        isFitCenter = true;
    }


    @Deprecated//推荐用fitCenterWithAnim2
    public void fitCenterWithAnim() {

        ValueAnimator translatioAnimator = ValueAnimator.ofFloat(1.0f, 0.0f);
        translatioAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v = (float) animation.getAnimatedValue();

                mTargetView.setTranslationX(v * tempDistanceX);
                mTargetView.setTranslationY(v * tempDistanceY);
                if (v == 0.0f) {
                    tempDistanceX = 0;
                    tempDistanceY = 0;
                    mTargetView.setTranslationX(tempDistanceX);
                    mTargetView.setTranslationY(tempDistanceY);
                    isAnimEnd = true;
                }

            }
        });

        translatioAnimator.setInterpolator(new LinearInterpolator());
        translatioAnimator.setDuration(ANIM_DURATION_MS);


        float tmpScale = mTargetViewSourceWidth * 1.0f / mTargetViewCurrentWidth;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1.0f, tmpScale);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v = (float) animation.getAnimatedValue();
                if (Math.abs(v - tmpScale) <= 1e-6) {//在此误差内算相等
                    mTargetViewCurrentWidth = mTargetViewSourceWidth;
                    mTargetViewCurrentHeight = mTargetViewSourceHeight;

                    absoluteScale=  scale = scaleTemp = 1.0f;
                    mTargetView.setScaleX(scale);
                    mTargetView.setScaleY(scale);

                    ViewGroup.LayoutParams lp = mTargetView.getLayoutParams();
                    lp.width = mTargetViewCurrentWidth;
                    lp.height = mTargetViewCurrentHeight;
                    mTargetView.requestLayout();
                    isFitCenter = true;

                } else {

                    mTargetView.setScaleX(v);
                    mTargetView.setScaleY(v);
                }
            }
        });

        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(ANIM_DURATION_MS);
        valueAnimator.start();

        translatioAnimator.start();
        isAnimEnd = false;


    }

    public void fitCenterWithAnim2() {

        ValueAnimator translatioAnimator = ValueAnimator.ofFloat(1.0f, 0.0f);
        translatioAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v = (float) animation.getAnimatedValue();

                mTargetView.setTranslationX(v * tempDistanceX);
                mTargetView.setTranslationY(v * tempDistanceY);
                if (v == 0.0f) {
                    tempDistanceX = 0;
                    tempDistanceY = 0;
                    mTargetView.setTranslationX(tempDistanceX);
                    mTargetView.setTranslationY(tempDistanceY);
                    isAnimEnd = true;
                }

            }
        });

        translatioAnimator.setInterpolator(new LinearInterpolator());
        translatioAnimator.setDuration(ANIM_DURATION_MS);


        float tmpScale = mTargetViewSourceWidth * 1.0f / mTargetViewCurrentWidth;
        int tmpScaleInt = (int) (tmpScale * FLOAT_TIMES);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1 * FLOAT_TIMES, tmpScaleInt);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int v = (int) animation.getAnimatedValue();
                float s = v * 1.0f / FLOAT_TIMES;
                if (v == tmpScaleInt) {
                    mTargetViewCurrentWidth = mTargetViewSourceWidth;
                    mTargetViewCurrentHeight = mTargetViewSourceHeight;

                    absoluteScale = scale = scaleTemp = 1.0f;
                    mTargetView.setScaleX(scale);
                    mTargetView.setScaleY(scale);

                    ViewGroup.LayoutParams lp = mTargetView.getLayoutParams();
                    lp.width = mTargetViewCurrentWidth;
                    lp.height = mTargetViewCurrentHeight;
                    mTargetView.requestLayout();
                    isFitCenter = true;

                } else {

                    mTargetView.setScaleX(s);
                    mTargetView.setScaleY(s);
                }
            }
        });

        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(ANIM_DURATION_MS);
        valueAnimator.start();

        translatioAnimator.start();
        isAnimEnd = false;


    }

    public void scale(float factor) {
        this.scale = scaleTemp * factor;
        mTargetView.setScaleX(this.scale);
        mTargetView.setScaleY(this.scale);
        scaleTemp = scale;
        requestLayoutWithScale();

    }

    @Deprecated//推荐用scaleWithAnim2
    public void scaleWithAnim(float factor) {

        this.scale = scaleTemp * factor;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1.0f, this.scale);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v = (float) animation.getAnimatedValue();
                if (Math.abs(v - scale) <= 1e-6) {
                    scaleTemp = scale;

                    requestLayoutWithScale();
                    isAnimEnd = true;
                } else {

                    mTargetView.setScaleX(v);
                    mTargetView.setScaleY(v);
                }
            }
        });

        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(ANIM_DURATION_MS);
        valueAnimator.start();
        isAnimEnd = false;


    }

    public void scaleWithAnim2(float factor) {


        this.scale = scaleTemp * factor;
        int tmpScaleInt = (int) (this.scale * FLOAT_TIMES);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1 * FLOAT_TIMES, tmpScaleInt);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int v = (int) animation.getAnimatedValue();

                float s = v * 1.0f / FLOAT_TIMES;
                if (v == tmpScaleInt) {
                    scaleTemp = scale;

                    requestLayoutWithScale();
                    isAnimEnd = true;
                } else {

                    mTargetView.setScaleX(s);
                    mTargetView.setScaleY(s);
                }
            }
        });

        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(ANIM_DURATION_MS);
        valueAnimator.start();
        isAnimEnd = false;


    }


}
