package com.junmeng.android_java_example.common.recycler;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * recyclerview item点击事件监听器
 * 示例：
 * recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(recyclerView, new RecyclerItemClickListener.OnItemClickListener() {...})
 */
public class RecyclerItemClickListener extends RecyclerView.SimpleOnItemTouchListener {

    private OnItemClickListener clickListener;
    private GestureDetectorCompat gestureDetector;

    private boolean mIsInterceptTouchEvent = false;

    public interface OnItemClickListener {

        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public class SimpleOnItemClickListener implements OnItemClickListener {

        @Override
        public void onItemClick(View view, int position) {
        }

        @Override
        public void onItemLongClick(View view, int position) {
        }
    }


    /**
     * 是否拦截事件
     *
     * @param intercept true的话则item内部按钮的事件则不会触发到
     */
    public void isInterceptTouchEvent(boolean intercept) {
        mIsInterceptTouchEvent = intercept;
    }

    public RecyclerItemClickListener(final RecyclerView recyclerView,
                                     OnItemClickListener listener) {
        this(recyclerView, listener, false);
    }

    public RecyclerItemClickListener(final RecyclerView recyclerView,
                                     OnItemClickListener listener, boolean isInterceptTouchEvent) {
        this.clickListener = listener;
        mIsInterceptTouchEvent = isInterceptTouchEvent;
        gestureDetector = new GestureDetectorCompat(recyclerView.getContext(),
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {

                        View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                        if (childView != null && clickListener != null) {
                            int pos = recyclerView.getChildAdapterPosition(childView);
                            if (pos >= 0) {
                                clickListener.onItemClick(childView, pos);
                            }

                        }
                        return true;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                        if (childView != null && clickListener != null) {
                            int pos = recyclerView.getChildAdapterPosition(childView);
                            if (pos >= 0) {
                                clickListener.onItemLongClick(childView, pos);
                            }

                        }
                    }
                });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetector.onTouchEvent(e);
        return mIsInterceptTouchEvent;
    }
}