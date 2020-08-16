package com.junmeng.android_java_example.gesture;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;

import com.junmeng.android_java_example.surfaceview.BaseSurfaceView;
import com.junmeng.android_java_example.utils.ColorUtil;

import java.util.Timer;
import java.util.TimerTask;

public class TestSurfaceView extends BaseSurfaceView {

    private int color= Color.RED;
    public TestSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public TestSurfaceView(Context context) {
        super(context);
        init();
    }

    public TestSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private Timer timer;
    public void init(){

        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                color= ColorUtil.getRandColorInt();
            }
        },0,1000);
    }


    @Override
    public void doDraw(Canvas c) {
        c.drawColor(color);
    }


    public void release(){
        if(timer!=null){
            timer.cancel();
            timer=null;
        }
    }



}
