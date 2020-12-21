package com.example.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.IntRange;

import com.example.ui.R;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 语音录制时音量的跳动效果
 * 支持两种动画模式
 * 支持自定义线条颜色间距等
 */
public class LineWaveVoiceView extends View {
    private static final String TAG = "LineWaveVoiceView";

    private Paint paint = new Paint();
    private Runnable task;
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private RectF rectRight = new RectF();//右边波纹矩形的数据，10个矩形复用一个rectF
    private RectF rectLeft = new RectF();//左边波纹矩形的数据
    private int updateSpeed;

    private static final int DEFAULT_LINE_ROUND = 6; //矩形圆角
    private static final int DEFAULT_LINE_WIDTH = 9;//默认矩形波纹的宽度，9像素, 原则上从layout的attr获得
    private static final int DEFAULT_LINE_SPACE = 9;//默认矩形波纹的宽度，9像素, 原则上从layout的attr获得
    private static final int DEFAULT_MIN_WAVE_HEIGHT = 2;//矩形线最小高
    private static final int DEFAULT_MAX_WAVE_HEIGHT = 12;//矩形线最大高
    private static final int UPDATE_INTERVAL_TIME = 100;//100ms更新一次
    private static final int DEFAULT_LINE_HLAF_COUNT = 7;//矩形条数的一半
    private LinkedList<Integer> mWaveList = new LinkedList<>();
    private float maxDb;

    private int lineHalfCount = DEFAULT_LINE_HLAF_COUNT; //左右两边的线条数
    private float minLineHeight = DEFAULT_MIN_WAVE_HEIGHT;//矩形线最小高
    private float maxLineHeight = DEFAULT_MAX_WAVE_HEIGHT;//矩形线最大高
    private float lineSpace = DEFAULT_LINE_SPACE;//线与线之间的间隔
    private float lineWidth = DEFAULT_LINE_WIDTH;//矩形线的宽度
    private int lineColor = DEFAULT_LINE_SPACE;//矩形线的颜色
    private int lineRound = DEFAULT_LINE_ROUND;//矩形的圆角

    private static final int MODE_FROM_CENTER_TO_EDGE = 1;// 从中间向两边扩散
    private static final int MODE_FROM_LEFT_TO_RIGHT = 2;// 从左到右扩散
    private int mode = MODE_FROM_CENTER_TO_EDGE;

    public LineWaveVoiceView(Context context) {
        super(context);
    }

    public LineWaveVoiceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineWaveVoiceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs, context);
        int[] tmp;
        if (mode == MODE_FROM_LEFT_TO_RIGHT) {
            tmp = new int[lineHalfCount * 2];
            for (int i = 0; i < lineHalfCount * 2; i++) {
                tmp[i] = (int) minLineHeight;
            }
            resetView(mWaveList, tmp);
        } else {
            tmp = new int[lineHalfCount];
            for (int i = 0; i < lineHalfCount; i++) {
                tmp[i] = (int) minLineHeight;
            }

        }

        resetView(mWaveList, tmp);
        task = new LineJitterTask();
    }

    private void initView(AttributeSet attrs, Context context) {
        //获取布局属性里的值
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.LineWaveVoiceView);
        lineColor = mTypedArray.getColor(R.styleable.LineWaveVoiceView_junmeng_voiceLineColor, context.getResources().getColor(android.R.color.white));
        lineWidth = mTypedArray.getDimension(R.styleable.LineWaveVoiceView_junmeng_voiceLineWidth, DEFAULT_LINE_WIDTH);
        lineSpace = mTypedArray.getDimension(R.styleable.LineWaveVoiceView_junmeng_lineSpaceWidth, DEFAULT_LINE_SPACE);
        minLineHeight = mTypedArray.getDimension(R.styleable.LineWaveVoiceView_junmeng_minLineHeight, DEFAULT_MIN_WAVE_HEIGHT);
        maxLineHeight = mTypedArray.getDimension(R.styleable.LineWaveVoiceView_junmeng_maxLineHeight, DEFAULT_MAX_WAVE_HEIGHT);
        lineHalfCount = mTypedArray.getInt(R.styleable.LineWaveVoiceView_junmeng_lineHalfCount, DEFAULT_LINE_HLAF_COUNT);
        updateSpeed = mTypedArray.getColor(R.styleable.LineWaveVoiceView_junmeng_updateSpeed, UPDATE_INTERVAL_TIME);
        mode = mTypedArray.getInt(R.styleable.LineWaveVoiceView_junmeng_mode, MODE_FROM_CENTER_TO_EDGE);
        lineRound = mTypedArray.getInt(R.styleable.LineWaveVoiceView_junmeng_lineRound, DEFAULT_LINE_ROUND);
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

        if (mode == MODE_FROM_LEFT_TO_RIGHT) {
            fromLeftToRight(canvas, widthCentre, heightCentre);
        } else {
            fromCenterToEdge(canvas, widthCentre, heightCentre);
        }
    }

    /**
     * 从中间两边扩散
     *
     * @param canvas
     * @param widthCentre
     * @param heightCentre
     */
    private void fromCenterToEdge(Canvas canvas, int widthCentre, int heightCentre) {
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

            canvas.drawRoundRect(rectRight, lineRound, lineRound, paint);
            canvas.drawRoundRect(rectLeft, lineRound, lineRound, paint);
        }
    }

    /**
     * 从左边到右边
     *
     * @param canvas
     * @param widthCentre
     * @param heightCentre
     */
    private void fromLeftToRight(Canvas canvas, int widthCentre, int heightCentre) {
        int index = 0;
        for (int i = lineHalfCount; i > 0; i--) {
            //左边矩形
            rectLeft.left = widthCentre - (i * lineWidth + (i - 0.5f) * lineSpace);
            rectLeft.top = heightCentre - mWaveList.get(index) / 2;
            rectLeft.right = rectLeft.left + lineWidth;
            rectLeft.bottom = heightCentre + mWaveList.get(index) / 2;

            canvas.drawRoundRect(rectLeft, lineRound, lineRound, paint);
            index++;
        }
        for (int i = 1; i <= lineHalfCount; i++) {
            //右边矩形
            rectRight.left = widthCentre + ((i - 1) * lineWidth + (i - 0.5f) * lineSpace);
            rectRight.top = heightCentre - mWaveList.get(index) / 2;
            rectRight.right = rectRight.left + lineWidth;
            rectRight.bottom = heightCentre + mWaveList.get(index) / 2;
            canvas.drawRoundRect(rectRight, lineRound, lineRound, paint);
            index++;
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
        maxDb = random.nextInt(5) + 2;
        int waveH = (int) (minLineHeight + Math.round(maxDb / 7.0f * (maxLineHeight - minLineHeight)));
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
//        Log.i(TAG, "db=" + dbLevel + ",waveH=" + waveH);
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