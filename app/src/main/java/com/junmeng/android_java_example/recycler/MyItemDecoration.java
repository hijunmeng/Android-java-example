package com.junmeng.android_java_example.recycler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.junmeng.android_java_example.R;

public class MyItemDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "MyItemDecoration";
    private Paint dividerPaint;
    public MyItemDecoration(Context context){
        dividerPaint = new Paint();
        dividerPaint.setColor(context.getResources().getColor(R.color.colorAccent));
    }
    /**
     * 根据getItemOffsets在腾出的空间里绘制
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int count=state.getItemCount();
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 0; i < childCount ; i++) {
            View view = parent.getChildAt(i);
            float top = view.getBottom();
            float bottom = view.getBottom() + 10;
            c.drawRect(left, top, right, bottom, dividerPaint);
        }
    }

    /**
     *
     * @param outRect 指定上下左右边框的宽度
     * @param view
     * @param parent
     * @param state
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int pos=((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        if(pos%2==0){
            outRect.set(0,0,10,10);
        }else{
            outRect.set(0,0,0,10);
        }


    }

    /**
     * 在item上层绘制
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount ; i++) {
            View view = parent.getChildAt(i);
            float top = view.getTop();
            float left = view.getLeft();
            float right = view.getRight();
            float bottom = view.getBottom();
            c.drawRect(left+20, top+20, right-20, bottom-20, dividerPaint);

        }
    }
}
