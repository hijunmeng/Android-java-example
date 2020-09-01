package com.junmeng.android_java_example.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.RelativeLayout;

import com.junmeng.android_java_example.R;


/**
 * 圆角的RelativeLayout
 */

public class RoundRectLayout extends RelativeLayout {

    private Path mPath;
//    private int mRadius;

    private int mWidth;
    private int mHeight;
    private float mLastRadius;

    public static final int MODE_NONE = 0;
    public static final int MODE_ALL = 1;
    public static final int MODE_LEFT = 2;
    public static final int MODE_TOP = 3;
    public static final int MODE_RIGHT = 4;
    public static final int MODE_BOTTOM = 5;

    private int mRoundMode = MODE_ALL;

    private float mRadius = dp2px(5);
    private float mBottomLeftRadius = mRadius;

    private float mBottomRightRadius = mRadius;
    private float mTopRightRadius = mRadius;
    private float mTopLeftRadius = mRadius;

    public RoundRectLayout(Context context) {
        this(context, null, 0);
    }

    public RoundRectLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundRectLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);


    }

    private void init(AttributeSet attrs, int defStyleAttr) {

        TypedArray ta = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.RoundLayout, defStyleAttr, 0);

        mRadius = ta.getDimension(R.styleable.RoundLayout_junmeng_radius, mRadius);
        mBottomLeftRadius = ta.getDimension(R.styleable.RoundLayout_junmeng_bottomLeftRadius, 0);
        mBottomRightRadius = ta.getDimension(R.styleable.RoundLayout_junmeng_bottomRightRadius, 0);
        mTopRightRadius = ta.getDimension(R.styleable.RoundLayout_junmeng_topRightRadius, 0);
        mTopLeftRadius = ta.getDimension(R.styleable.RoundLayout_junmeng_topLeftRadius, 0);

        mPath = new Path();
        mPath.setFillType(Path.FillType.EVEN_ODD);

        setCornerRadius(30);
    }

    /**
     * 设置是否圆角裁边
     *
     * @param roundMode
     */
    public void setRoundMode(int roundMode) {
        mRoundMode = roundMode;
    }

    /**
     * 设置圆角半径
     *
     * @param radius
     */
    public void setCornerRadius(int radius) {
        mRadius = radius;
    }

    private void checkPathChanged() {

        if (getWidth() == mWidth && getHeight() == mHeight && mLastRadius == mRadius) {
            return;
        }

        mWidth = getWidth();
        mHeight = getHeight();
        mLastRadius = mRadius;

        mPath.reset();

        switch (mRoundMode) {
            case MODE_ALL:
                mPath.addRoundRect(new RectF(0, 0, mWidth, mHeight), mRadius, mRadius, Path.Direction.CW);
                break;
            case MODE_LEFT:
                mPath.addRoundRect(new RectF(0, 0, mWidth, mHeight),
                        new float[]{mRadius, mRadius, 0, 0, 0, 0, mRadius, mRadius},
                        Path.Direction.CW);
                break;
            case MODE_TOP:
                mPath.addRoundRect(new RectF(0, 0, mWidth, mHeight),
                        new float[]{mRadius, mRadius, mRadius, mRadius, 0, 0, 0, 0},
                        Path.Direction.CW);
                break;
            case MODE_RIGHT:
                mPath.addRoundRect(new RectF(0, 0, mWidth, mHeight),
                        new float[]{0, 0, mRadius, mRadius, mRadius, mRadius, 0, 0},
                        Path.Direction.CW);
                break;
            case MODE_BOTTOM:
                mPath.addRoundRect(new RectF(0, 0, mWidth, mHeight),
                        new float[]{0, 0, 0, 0, mRadius, mRadius, mRadius, mRadius},
                        Path.Direction.CW);
                break;
        }

    }

    @Override
    public void draw(Canvas canvas) {

        if (mRoundMode != MODE_NONE) {
            int saveCount = canvas.save();

            checkPathChanged();

            canvas.clipPath(mPath);
            super.draw(canvas);

            canvas.restoreToCount(saveCount);
        } else {
            super.draw(canvas);
        }


    }


    private float dp2px(float dpVal) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, getResources().getDisplayMetrics());
    }

    private float sp2px(float spVal) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, getResources().getDisplayMetrics());
    }
}