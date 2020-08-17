package com.junmeng.android_java_example.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * @Description: 动画工具类
 * @Author: hwj
 * @CreateDate: 2020/7/15 14:28
 */
public class AnimUtil {

    private static final int DEFAULT_DURATION_MS = 300;//默认动画时间


    /**
     * 向上滑动至显示（效果类似从底部弹出）
     *
     * @param view
     * @param duration  动画时长
     * @param startAnim 是否立即开始动画
     * @return
     */
    public static TranslateAnimation upToShow(View view, int duration, boolean startAnim) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation ta = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,//RELATIVE_TO_SELF表示操作自身
                0,//fromXValue表示开始的X轴位置
                Animation.RELATIVE_TO_SELF,
                0,//fromXValue表示结束的X轴位置
                Animation.RELATIVE_TO_SELF,
                1,//fromXValue表示开始的Y轴位置
                Animation.RELATIVE_TO_SELF,
                0);//fromXValue表示结束的Y轴位置
        ta.setRepeatMode(Animation.REVERSE);
        ta.setDuration(duration);
        if (startAnim) {
            view.startAnimation(ta);
        }
        return ta;
    }

    /**
     * 向上滑动至显示（效果类似从底部弹出）
     *
     * @return
     */
    public static TranslateAnimation upToShow(View view) {
        return upToShow(view, DEFAULT_DURATION_MS, true);
    }

    /**
     * 向下滑动直至显示（效果类似从顶部弹出）
     *
     * @return
     */
    public static TranslateAnimation downToShow(View view) {
        return downToShow(view, DEFAULT_DURATION_MS, true);
    }

    /**
     * 向下滑动直至显示（效果类似从顶部弹出）
     *
     * @param view
     * @param duration  动画时长
     * @param startAnim 是否立即开始动画
     * @return
     */
    public static TranslateAnimation downToShow(View view, int duration, boolean startAnim) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation ta = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,//RELATIVE_TO_SELF表示操作自身
                0,//fromXValue表示开始的X轴位置
                Animation.RELATIVE_TO_SELF,
                0,//fromXValue表示结束的X轴位置
                Animation.RELATIVE_TO_SELF,
                -1,//fromXValue表示开始的Y轴位置
                Animation.RELATIVE_TO_SELF,
                0);//fromXValue表示结束的Y轴位置
        ta.setRepeatMode(Animation.REVERSE);
        ta.setDuration(duration);
        if (startAnim) {
            view.startAnimation(ta);
        }
        return ta;
    }


    /**
     * 向上滑动至隐藏（效果类似从顶部收起）
     *
     * @return
     */
    public static TranslateAnimation upToHide(final View view) {

        return upToHide(view, DEFAULT_DURATION_MS, true);
    }

    /**
     * 向上滑动至隐藏（效果类似从顶部收起）
     *
     * @param view
     * @param duration  动画时长
     * @param startAnim 是否立即开始动画
     * @return
     */
    public static TranslateAnimation upToHide(final View view, int duration, boolean startAnim) {
        TranslateAnimation ta = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,//RELATIVE_TO_SELF表示操作自身
                0,//fromXValue表示开始的X轴位置
                Animation.RELATIVE_TO_SELF,
                0,//fromXValue表示结束的X轴位置
                Animation.RELATIVE_TO_SELF,
                0,//fromXValue表示开始的Y轴位置
                Animation.RELATIVE_TO_SELF,
                -1);//fromXValue表示结束的Y轴位置
        ta.setRepeatMode(Animation.REVERSE);
        ta.setDuration(duration);
        ta.setAnimationListener(getAnimationListener(view));
        if (startAnim) {
            view.startAnimation(ta);
        }
        return ta;
    }


    /**
     * 向下滑动至隐藏（效果类似从底部收起）
     *
     * @param view
     * @param duration  动画时长
     * @param startAnim 是否立即开始动画
     * @return
     */
    public static TranslateAnimation downToHide(final View view, int duration, boolean startAnim) {
        TranslateAnimation ta = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,//RELATIVE_TO_SELF表示操作自身
                0,//fromXValue表示开始的X轴位置
                Animation.RELATIVE_TO_SELF,
                0,//fromXValue表示结束的X轴位置
                Animation.RELATIVE_TO_SELF,
                0,//fromXValue表示开始的Y轴位置
                Animation.RELATIVE_TO_SELF,
                1);//fromXValue表示结束的Y轴位置
        ta.setRepeatMode(Animation.REVERSE);
        ta.setDuration(duration);
        ta.setAnimationListener(getAnimationListener(view));
        if (startAnim) {
            view.startAnimation(ta);
        }
        return ta;
    }

    /**
     * 向下滑动至隐藏（效果类似从底部收起）
     *
     * @param view
     * @return
     */
    public static TranslateAnimation downToHide(final View view) {
        return downToHide(view, DEFAULT_DURATION_MS, true);
    }

    /**
     * 向右滑动至显示（效果类似从左侧弹出）
     *
     * @param view
     * @param duration  动画时长
     * @param startAnim 是否立即开始动画
     * @return
     */
    public static TranslateAnimation rightToShow(View view, int duration, boolean startAnim) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation ta = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,//RELATIVE_TO_SELF表示操作自身
                -1,//fromXValue表示开始的X轴位置
                Animation.RELATIVE_TO_SELF,
                0,//fromXValue表示结束的X轴位置
                Animation.RELATIVE_TO_SELF,
                0,//fromXValue表示开始的Y轴位置
                Animation.RELATIVE_TO_SELF,
                0);//fromXValue表示结束的Y轴位置
        ta.setRepeatMode(Animation.REVERSE);
        ta.setDuration(duration);
        if (startAnim) {
            view.startAnimation(ta);
        }
        return ta;
    }

    /**
     * 向右滑动至显示（效果类似从左侧弹出）
     *
     * @param view
     * @return
     */
    public static TranslateAnimation rightToShow(View view) {
        return rightToShow(view, DEFAULT_DURATION_MS, true);
    }

    /**
     * 向左滑动至显示（效果类似从右侧弹出）
     *
     * @param view
     * @param duration  动画时长
     * @param startAnim 是否立即开始动画
     * @return
     */
    public static TranslateAnimation leftToShow(View view, int duration, boolean startAnim) {
        view.setVisibility(View.VISIBLE);
        TranslateAnimation ta = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,//RELATIVE_TO_SELF表示操作自身
                1,//fromXValue表示开始的X轴位置
                Animation.RELATIVE_TO_SELF,
                0,//fromXValue表示结束的X轴位置
                Animation.RELATIVE_TO_SELF,
                0,//fromXValue表示开始的Y轴位置
                Animation.RELATIVE_TO_SELF,
                0);//fromXValue表示结束的Y轴位置
        ta.setRepeatMode(Animation.REVERSE);
        ta.setDuration(duration);
        if (startAnim) {
            view.startAnimation(ta);
        }
        return ta;
    }

    /**
     * 向左滑动至显示（效果类似从右侧弹出）
     *
     * @param view
     * @return
     */
    public static TranslateAnimation leftToShow(View view) {
        return leftToShow(view, DEFAULT_DURATION_MS, true);
    }

    /**
     * 向左滑动至隐藏（效果类似从左侧收起）
     *
     * @param view
     * @param duration  动画时长
     * @param startAnim 是否立即开始动画
     * @return
     */
    public static TranslateAnimation leftToHide(View view, int duration, boolean startAnim) {
        TranslateAnimation ta = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,//RELATIVE_TO_SELF表示操作自身
                0,//fromXValue表示开始的X轴位置
                Animation.RELATIVE_TO_SELF,
                -1,//fromXValue表示结束的X轴位置
                Animation.RELATIVE_TO_SELF,
                0,//fromXValue表示开始的Y轴位置
                Animation.RELATIVE_TO_SELF,
                0);//fromXValue表示结束的Y轴位置
        ta.setRepeatMode(Animation.REVERSE);
        ta.setDuration(duration);
        ta.setAnimationListener(getAnimationListener(view));
        if (startAnim) {
            view.startAnimation(ta);
        }
        return ta;
    }

    /**
     * 向左滑动至隐藏（效果类似从左侧收起）
     *
     * @param view
     * @return
     */
    public static TranslateAnimation leftToHide(View view) {
        return leftToHide(view, DEFAULT_DURATION_MS, true);
    }

    /**
     * 向右滑动至隐藏（效果类似从右侧收起）
     *
     * @param view
     * @param duration  动画时长
     * @param startAnim 是否立即开始动画
     * @return
     */
    public static TranslateAnimation rightToHide(View view, int duration, boolean startAnim) {
        TranslateAnimation ta = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF,//RELATIVE_TO_SELF表示操作自身
                0,//fromXValue表示开始的X轴位置
                Animation.RELATIVE_TO_SELF,
                1,//fromXValue表示结束的X轴位置
                Animation.RELATIVE_TO_SELF,
                0,//fromXValue表示开始的Y轴位置
                Animation.RELATIVE_TO_SELF,
                0);//fromXValue表示结束的Y轴位置
        ta.setRepeatMode(Animation.REVERSE);
        ta.setDuration(duration);
        ta.setAnimationListener(getAnimationListener(view));
        if (startAnim) {
            view.startAnimation(ta);
        }

        return ta;
    }

    /**
     * 向右滑动至隐藏（效果类似从右侧收起）
     *
     * @param view
     * @return
     */
    public static TranslateAnimation rightToHide(View view) {
        return rightToHide(view, DEFAULT_DURATION_MS, true);
    }

    private static Animation.AnimationListener getAnimationListener(final View view) {
        return new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
    }
}