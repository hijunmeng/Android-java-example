package com.junmeng.android_java_example.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

/**
 * 例如 加载中... 后面的...动画显示
 */
public class LoadingTextView extends androidx.appcompat.widget.AppCompatTextView {
    private ValueAnimator valueAnimator;
    //    private String[] dotText = {"",".", "..", "..."};
    private String[] dotText = {".", "..", "..."};

    public static final int DEFAULT_DURATION = 1200;
    private int mDuration = DEFAULT_DURATION;

    public LoadingTextView(Context context) {
        super(context);
        init();
    }

    public LoadingTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    /**
     * 设置动画时间
     * @param duration
     */
    public void setDuration(int duration) {
        if(duration<=0){
            return;
        }
        mDuration = duration;
        startAnim();
    }

    private void init() {
        startAnim();
    }

    private void startAnim() {
        if (valueAnimator != null) {
            valueAnimator.cancel();
            valueAnimator = null;
        }
        if (valueAnimator == null) {
            valueAnimator = ValueAnimator.ofInt(0, dotText.length).setDuration(mDuration);
            valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int i = (int) animation.getAnimatedValue();
                    setText(dotText[i % dotText.length]);
                }
            });
        }
        valueAnimator.start();
    }


    @Override
    protected void onDetachedFromWindow() {
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        super.onDetachedFromWindow();
    }


}
