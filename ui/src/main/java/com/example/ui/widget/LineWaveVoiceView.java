package com.example.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.IntRange;

import com.example.ui.R;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 语音录制音量分贝的动画效果
 */
public class LineWaveVoiceView extends View {
    private static final String TAG = "LineWaveVoiceView";

    private Paint paint = new Paint();
    private Runnable task;
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private RectF rectRight = new RectF();//右边波纹矩形的数据
    private RectF rectLeft = new RectF();//左边波纹矩形的数据
    private int updateSpeed;

    private static final int DEFAULT_LINE_WIDTH = 9;//默认矩形线的宽度
    private static final int DEFAULT_LINE_SPACE = 9;//默认矩形线之间的间隔
    private static final int DEFAULT_MIN_WAVE_HEIGHT = 2;//默认矩形线最小高
    private static final int DEFAULT_MAX_WAVE_HEIGHT = 12;//默认矩形线最大高
    private static final int UPDATE_INTERVAL_TIME = 100;//100ms更新一次
    private static final int DEFAULT_LINE_HLAF_COUNT = 7;//默认左右两边的矩形线个数（总数为DEFAULT_LINE_HLAF_COUNT*2）
    private LinkedList<Integer> mWaveList = new LinkedList<>();//存放分贝级别，最新的分贝级别放在0位置
    private float mRandomDb;//随机产生的分贝

    private int lineHalfCount = DEFAULT_LINE_HLAF_COUNT; //左右两边的线条数
    private float minLineHeight = DEFAULT_MIN_WAVE_HEIGHT;//矩形线最小高
    private float maxLineHeight = DEFAULT_MAX_WAVE_HEIGHT;//矩形线最大高
    private float lineSpace;//线与线之间的间隔
    private float lineWidth = DEFAULT_LINE_WIDTH;//矩形线的宽度
    private int lineColor = DEFAULT_LINE_SPACE;//矩形线的颜色


    public LineWaveVoiceView(Context context) {
        super(context);
    }

    public LineWaveVoiceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineWaveVoiceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs, context);
        int[] tmp = new int[lineHalfCount];
        for (int i = 0; i < lineHalfCount; i++) {
            tmp[i] = (int) minLineHeight;
        }
        resetView(mWaveList, tmp);
        task = new LineJitterTask();
    }

    private void initView(AttributeSet attrs, Context context) {
        //获取布局属性里的值
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.LineWaveVoiceView);
        lineColor = mTypedArray.getColor(R.styleable.LineWaveVoiceView_voiceLineColor, context.getResources().getColor(android.R.color.white));
        lineWidth = mTypedArray.getDimension(R.styleable.LineWaveVoiceView_voiceLineWidth, DEFAULT_LINE_WIDTH);
        lineSpace = mTypedArray.getDimension(R.styleable.LineWaveVoiceView_lineSpaceWidth, DEFAULT_LINE_SPACE);
        minLineHeight = mTypedArray.getDimension(R.styleable.LineWaveVoiceView_minLineHeight, DEFAULT_MIN_WAVE_HEIGHT);
        maxLineHeight = mTypedArray.getDimension(R.styleable.LineWaveVoiceView_maxLineHeight, DEFAULT_MAX_WAVE_HEIGHT);
        lineHalfCount = mTypedArray.getInt(R.styleable.LineWaveVoiceView_lineHalfCount, DEFAULT_LINE_HLAF_COUNT);
        updateSpeed = mTypedArray.getColor(R.styleable.LineWaveVoiceView_updateSpeed, UPDATE_INTERVAL_TIME);
        mTypedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取实际宽高的一半
        int widthCentre = getWidth() / 2;
        int heightCentre = getHeight() / 2;
        //设置颜色
        paint.setColor(lineColor);
        //填充内部
        paint.setStyle(Paint.Style.FILL);
        //设置抗锯齿
        paint.setAntiAlias(true);
        for (int i = 0; i < lineHalfCount; i++) {
            rectRight.left = widthCentre + (0.5f + i) * lineSpace + (i) * lineWidth;
            rectRight.top = heightCentre - mWaveList.get(i) / 2;
            rectRight.right = rectRight.left + lineWidth;
            rectRight.bottom = heightCentre + mWaveList.get(i) / 2;

            //左边矩形
            rectLeft.left = widthCentre - ((i + 1) * lineWidth + (0.5f + i) * lineSpace);
            rectLeft.top = heightCentre - mWaveList.get(i) / 2;
            rectLeft.right = rectLeft.left + lineWidth;
            rectLeft.bottom = heightCentre + mWaveList.get(i) / 2;

            canvas.drawRoundRect(rectRight, 6, 6, paint);
            canvas.drawRoundRect(rectLeft, 6, 6, paint);
        }
    }


    private void resetView(List<Integer> list, int[] array) {
        list.clear();
        for (int anArray : array) {
            list.add(anArray);
        }
    }

    private synchronized void refreshElement() {
        Random random = new Random();
        mRandomDb = random.nextInt(5) + 2;
        int waveH = (int) (minLineHeight + Math.round(mRandomDb / 7.0f * (maxLineHeight - minLineHeight)));
        mWaveList.add(0, waveH);
        mWaveList.removeLast();
    }

    public boolean isStart = false;

    private class LineJitterTask implements Runnable {
        @Override
        public void run() {
            while (isStart) {
                refreshElement();
                try {
                    Thread.sleep(updateSpeed);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                postInvalidate();
            }
        }
    }


    public synchronized void refreshElement(@IntRange(from = 0, to = 6) int dbLevel) {
        int waveH = (int) (minLineHeight + Math.round(dbLevel / 6.0f * (maxLineHeight - minLineHeight)));
        Log.i(TAG, "db=" + dbLevel + ",waveH=" + waveH);
        mWaveList.add(0, waveH);
        mWaveList.removeLast();
        postInvalidate();
    }


    public void setUpdateSpeed(int updateSpeed) {
        this.updateSpeed = updateSpeed;
    }

    public synchronized void startRandomAnim() {
        isStart = true;
        executorService.execute(task);
    }

    public synchronized void stopRandomAnim() {
        isStart = false;
        mWaveList.clear();
        int[] tmp = new int[lineHalfCount];
        for (int i = 0; i < lineHalfCount; i++) {
            tmp[i] = (int) minLineHeight;
        }
        resetView(mWaveList, tmp);
        postInvalidate();
    }
}