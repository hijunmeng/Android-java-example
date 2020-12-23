package com.junmeng.android_java_example.recycler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.junmeng.android_java_example.R;

/**
 * 演示了使用View作为分割线的用法，这样可以省去繁琐的绘制代码
 */
public class ViewItemDecoration extends RecyclerView.ItemDecoration {

    private static final int ITEM_LAYOUT=R.layout.item_simple;

    private View mView;
    private TextView mTextView;

    private Paint mPaint;
    private int mHeight;

    public ViewItemDecoration(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);

        mView = View.inflate(context, ITEM_LAYOUT, null);
        mTextView = mView.findViewById(R.id.text);

    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (mHeight == 0) {
            mView.measure(View.MeasureSpec.makeMeasureSpec(parent.getMeasuredWidth(), View.MeasureSpec.AT_MOST),
                    View.MeasureSpec.makeMeasureSpec(parent.getMeasuredHeight(), View.MeasureSpec.AT_MOST));
            mHeight = mView.getMeasuredHeight();
            mView.layout(0, 0, mView.getMeasuredWidth(), mHeight);
        }
        outRect.top = mView.getMeasuredHeight();
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        c.save();

        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = parent.getChildAt(i);
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) child.getLayoutParams();

            int left = child.getLeft() - lp.leftMargin;
            int top = child.getTop() - lp.topMargin - mView.getMeasuredHeight();
            int right = child.getRight() + lp.rightMargin;
            int bottom = top + mView.getMeasuredHeight();

            mTextView.setText(i + "");
            mView.setDrawingCacheEnabled(true);
            Bitmap bitmap = mView.getDrawingCache(true);

            c.drawBitmap(bitmap, left, top, mPaint);
            mView.destroyDrawingCache();//清空缓存，释放资源,这样也是为了下次getDrawingCache能拿到最新的数据
            mView.setDrawingCacheEnabled(false);
        }
        c.restore();
    }
}
