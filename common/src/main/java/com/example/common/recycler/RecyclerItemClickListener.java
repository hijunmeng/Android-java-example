package com.example.common.recycler;

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

    public interface OnItemClickListener {

        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public static class SimpleOnItemClickListener implements OnItemClickListener {

        @Override
        public void onItemClick(View view, int position) {
        }

        @Override
        public void onItemLongClick(View view, int position) {
        }
    }

    public RecyclerItemClickListener(final RecyclerView recyclerView,
                                     OnItemClickListener listener) {
        this.clickListener = listener;
        gestureDetector = new GestureDetectorCompat(recyclerView.getContext(),
                new SimpleOnGestureListener() {//使用GestureDetector.SimpleOnGestureListener的话会导致响应双击事件
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
        return false;//必须返回false
    }

    public static class SimpleOnGestureListener implements GestureDetector.OnGestureListener{

        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        public void onLongPress(MotionEvent e) {
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            return false;
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            return false;
        }

        public void onShowPress(MotionEvent e) {
        }

        public boolean onDown(MotionEvent e) {
            return false;
        }
    }
}