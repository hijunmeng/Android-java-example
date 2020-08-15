package com.junmeng.android_java_example;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView {
    private static final String TAG = "MySurfaceView";
    public MySurfaceView(Context context) {
        super(context);
        init(context);
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }
    GestureDetector gestureDetector;
    private void init(Context context) {
//        this.getHolder().setFormat(PixelFormat.TRANSLUCENT);

//         gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
//
//            public boolean onSingleTapUp(MotionEvent e) {
//                Log.i(TAG, "onSingleTapUp: ");
//                return false;
//            }
//
//            public void onLongPress(MotionEvent e) {
//                Log.i(TAG, "onLongPress: ");
//            }
//
//            public boolean onScroll(MotionEvent e1, MotionEvent e2,
//                                    float distanceX, float distanceY) {
//                Log.i(TAG, "onScroll: distanceX="+distanceX+",distanceY="+distanceY);
//                return false;
//            }
//
//            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
//                                   float velocityY) {
//                Log.i(TAG, "onFling: velocityX="+velocityX+",velocityY="+velocityY);
//                return false;
//            }
//
//            public void onShowPress(MotionEvent e) {
//                Log.i(TAG, "onShowPress: ");
//            }
//
//            public boolean onDown(MotionEvent e) {
//                Log.i(TAG, "onDown: ");
//                return false;
//            }
//
//            public boolean onDoubleTap(MotionEvent e) {
//                Log.i(TAG, "onDoubleTap: ");
//                return false;
//            }
//
//            public boolean onDoubleTapEvent(MotionEvent e) {
//                Log.i(TAG, "onDoubleTapEvent: ");
//                return false;
//            }
//
//            public boolean onSingleTapConfirmed(MotionEvent e) {
//                Log.i(TAG, "onSingleTapConfirmed: ");
//                return false;
//            }
//
//            public boolean onContextClick(MotionEvent e) {
//                Log.i(TAG, "onContextClick: ");
//                return false;
//            }
//        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);

    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Paint paint = new Paint();

        // 绘制矩形区域-实心矩形
        // 设置颜色
        paint.setColor(Color.BLUE);
        // 设置样式-填充
        paint.setStyle(Paint.Style.FILL);
        // 绘制一个矩形
        canvas.drawRect(new Rect(0, 0, getWidth(), getHeight()), paint);

    }
}
