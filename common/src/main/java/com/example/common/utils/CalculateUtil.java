package com.example.common.utils;

import android.content.Context;
import android.graphics.PointF;
import android.util.TypedValue;

/**
 * 计算工具
 */
public class CalculateUtil {

    /**
     * 求a点绕o点旋转rotate度后的点的坐标
     *
     * @param a      原始点
     * @param o      圆点，即围绕的中心点
     * @param rotate 旋转度数，正为顺时针，负为逆时针
     * @return 旋转后的点的坐标
     */
    public static PointF getPointAfterRotate(PointF a, PointF o, float rotate) {
        PointF b = new PointF();
        if (a == null || o == null) {
            return b;
        }
        double angle = Math.toRadians(rotate);//将角度转为弧度
        b.x = (float) ((a.x - o.x) * Math.cos(angle) - (a.y - o.y) * Math.sin(angle) + o.x);
        b.y = (float) ((a.x - o.x) * Math.sin(angle) + (a.y - o.y) * Math.cos(angle) + o.y);
        return b;
    }

    /**
     * dp换算成px
     *
     * @param context
     * @param dpVal
     * @return
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources().getDisplayMetrics());
    }

    /**
     * sp换算成px
     *
     * @param context
     * @param spVal
     * @return
     */
    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * 计算InSampleSize，例如将2048x1536以InSampleSize为4进行解码，大概得到位图尺寸512x384
     * https://developer.android.google.cn/topic/performance/graphics/load-bitmap
     *
     * @param sourceWidth  原图尺寸
     * @param sourceHeight
     * @param reqWidth     目标尺寸
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(int sourceWidth, int sourceHeight, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = sourceWidth;
        final int width = sourceHeight;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    /**
     * 根据原尺寸计算在指定范围内的合适尺寸
     *
     * @param sourceWidth  原宽
     * @param sourceHeight 原高
     * @param minWidth     指定最小宽度
     * @param minHeight    指定最小高度
     * @param maxWidth     指定最大宽度
     * @param maxHeight    指定最大高度
     * @return 目标宽高
     */
    public static int[] getFitSize(int sourceWidth, int sourceHeight, int minWidth, int minHeight, int maxWidth, int maxHeight) {
        int targetWidth = 0;
        int targetHeight = 0;

        if (sourceWidth < minWidth) {//原宽小于最小宽度
            //把宽度缩放为最小宽度
            targetWidth = minWidth;
            targetHeight = targetWidth * sourceHeight / sourceWidth;
            if (targetHeight < minHeight) {
                //把高度缩放为最小高度
                targetHeight = minHeight;
                targetWidth = sourceWidth * targetHeight / sourceHeight;
                targetWidth = Math.min(maxWidth, targetWidth);

            } else if (targetHeight < maxHeight) {
                //不用修改
            } else {
                targetWidth = minWidth;
                targetHeight = maxHeight;
            }
        } else if (sourceWidth < maxWidth) {//原宽在最小宽度和最大宽度之间
            if (sourceHeight < minHeight) {
                //把高度缩放为最小高度
                targetHeight = minHeight;
                targetWidth = targetHeight * sourceWidth / sourceHeight;

                targetWidth = Math.min(maxWidth, targetWidth);
            } else if (sourceHeight < maxHeight) {
                targetWidth = sourceWidth;
                targetHeight = sourceHeight;
            } else {
                //把高度缩放为最大高度
                targetHeight = maxHeight;
                targetWidth = maxHeight * sourceWidth / sourceHeight;

                targetWidth = Math.max(minWidth, targetWidth);
            }
        } else {//原宽大于最大宽度
            //把宽度缩放为最大宽度
            targetWidth = maxWidth;
            targetHeight = targetWidth * sourceHeight / sourceWidth;
            if (targetHeight < minHeight) {
                targetHeight = minHeight;
                targetWidth = maxWidth;
            } else if (targetHeight < maxHeight) {
                //不用修改
            } else {
                //把高度缩放为最大高度
                targetHeight = maxHeight;
                targetWidth = targetHeight * sourceWidth / sourceHeight;

                targetWidth = Math.max(targetWidth, minWidth);
            }
        }
        return new int[]{targetWidth, targetHeight};
    }

    /**
     * 获得原尺寸在指定矩形内的等比例尺寸
     * 例如要将一张图片等比例放置在指定矩形内，类似ImageView的fitCenter
     *
     * @param sourceWidth  原宽
     * @param sourceHeight 原高
     * @param inWidth      矩形宽
     * @param inHeight     矩形高
     * @return
     */
    public static int[] get(int sourceWidth, int sourceHeight, int inWidth, int inHeight) {
        int targetWidth = 0;
        int targetHeight = 0;
        targetWidth = inWidth;
        targetHeight = inWidth * sourceHeight / sourceWidth;
        if (targetHeight > inHeight) {//说明需要按高度为基准
            targetHeight = inHeight;
            targetWidth = sourceWidth * inHeight / sourceHeight;
        }
        return new int[]{targetWidth, targetHeight};
    }
}