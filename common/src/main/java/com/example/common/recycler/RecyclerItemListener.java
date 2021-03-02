package com.example.common.recycler;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * recyclerview item点击事件监听器(单击，双击，长按)
 * 此类只适用于recyclerview item内部控件无监听事件的情况
 * 示例：
 * recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(recyclerView, new RecyclerItemClickListener.OnItemClickListener() {...})
 */
public class RecyclerItemListener extends RecyclerView.SimpleOnItemTouchListener {

    private OnItemClickListener clickListener;
    private GestureDetectorCompat gestureDetector;
    private boolean isIntercept = true;//是否拦截事件

    public interface OnItemClickListener {

        void onItemClick(View view, MotionEvent e, int position);

        void onItemLongClick(View view, MotionEvent e, int position);

        void onItemDoubleClick(View view, MotionEvent e, int position);
    }

    public static class SimpleOnItemClickListener implements OnItemClickListener {

        @Override
        public void onItemClick(View view, MotionEvent e, int position) {
        }

        @Override
        public void onItemLongClick(View view, MotionEvent e, int position) {
        }

        @Override
        public void onItemDoubleClick(View view, MotionEvent e, int position) {
        }
    }

    public RecyclerItemListener(final RecyclerView recyclerView,
                                OnItemClickListener listener) {
        this.clickListener = listener;
        gestureDetector = new GestureDetectorCompat(recyclerView.getContext(),
                new SimpleOnGestureListener() {//使用GestureDetector.SimpleOnGestureListener的话会导致响应双击事件
                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
                        View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                        if (childView != null && clickListener != null) {
                            int pos = recyclerView.getChildAdapterPosition(childView);
                            if (pos >= 0) {
                                clickListener.onItemClick(childView, e, pos);
                            }
                        }
                        return false;
                    }

                    @Override
                    public boolean onDoubleTap(MotionEvent e) {
                        View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                        if (childView != null && clickListener != null) {
                            int pos = recyclerView.getChildAdapterPosition(childView);
                            if (pos >= 0) {
                                clickListener.onItemDoubleClick(childView, e, pos);
                            }
                        }
                        return false;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                        if (childView != null && clickListener != null) {
                            int pos = recyclerView.getChildAdapterPosition(childView);
                            if (pos >= 0) {
                                clickListener.onItemLongClick(childView, e, pos);
                            }

                        }
                    }
                });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetector.onTouchEvent(e);
        rv.findChildViewUnder(e.getX(), e.getY());
        return false;//必须返回false
    }

    public static class SimpleOnGestureListener implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

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

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return false;
        }
    }
}