package com.junmeng.android_java_example.anim;

import android.animation.TypeEvaluator;
import android.graphics.Point;

/**
 * 自定义求值器
 */
public class MyTypeEvaluator implements TypeEvaluator<Point> {

    Point p = new Point();

    @Override
    public Point evaluate(float fraction, Point startValue, Point endValue) {
        int startX = startValue.x;
        int x = (int) (startX + fraction * (endValue.x - startX));
        int startY = startValue.y;
        int y = (int) (startY + fraction * (endValue.y - startY));
        p.x = x;
        p.y = y;
        return p;
    }
}
