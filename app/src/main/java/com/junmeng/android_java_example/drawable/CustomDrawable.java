package com.junmeng.android_java_example.drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomDrawable extends Drawable {

    private Paint mPaint;

    public CustomDrawable() {
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setDither(true);
        mPaint.setStrokeWidth(1);//描边大小
    }


    @Override
    public void draw(@NonNull Canvas canvas) {
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        Rect rect = getBounds();//drawable实际的大小
        //在中心以最小边为半径画圆
        canvas.drawCircle(rect.exactCenterX(),
                rect.exactCenterY(),
                Math.min(rect.exactCenterX(), rect.exactCenterY()),
                mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSPARENT;
    }

    /**
     * 如果绘制的是图片，则在此处返回图片的宽度
     * @return
     */
    @Override
    public int getIntrinsicWidth() {
        return super.getIntrinsicWidth();
    }
    /**
     * 如果绘制的是图片，则在此处返回图片的高度
     * @return
     */
    @Override
    public int getIntrinsicHeight() {
        return super.getIntrinsicHeight();
    }

    /**
     * 当drawable实际大小发生变化时
     * @param bounds
     */
    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
    }
}
