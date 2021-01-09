package com.example.common.recycler;

import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * recyclerview item点击事件监听器
 * recyclerview adapter必须是BaseRecyclerAdapter的子类
 * 示例：
 * recyclerView.addOnItemTouchListener(new RecyclerItemClickListenerExt(recyclerView, new RecyclerItemClickListenerExt.OnItemClickListener() {...})
 *
 * @param <T> item的类型
 */
public class RecyclerItemClickListenerExt<T> extends RecyclerView.SimpleOnItemTouchListener {

    private OnItemClickListener clickListener;
    private GestureDetectorCompat gestureDetector;

    public interface OnItemClickListener<T> {

        void onItemClick(View view, int position, @Nullable T item);

        void onItemLongClick(View view, int position, @Nullable T item);
    }

    public static class SimpleOnItemClickListener<T> implements OnItemClickListener<T> {

        @Override
        public void onItemClick(View view, int position, @Nullable T item) {
        }

        @Override
        public void onItemLongClick(View view, int position, @Nullable T item) {
        }
    }

    public RecyclerItemClickListenerExt(final RecyclerView recyclerView,
                                        OnItemClickListener<T> listener) {
        this.clickListener = listener;
        gestureDetector = new GestureDetectorCompat(recyclerView.getContext(),
                new RecyclerItemClickListener.SimpleOnGestureListener() {//使用GestureDetector.SimpleOnGestureListener的话会导致响应双击事件
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {

                        View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                        if (childView != null && clickListener != null) {
                            int pos = recyclerView.getChildAdapterPosition(childView);
                            if (pos >= 0) {
                                T t = null;
                                if (recyclerView.getAdapter() instanceof BaseRecyclerAdapter) {
                                    BaseRecyclerAdapter baseRecyclerAdapter = (BaseRecyclerAdapter) recyclerView.getAdapter();
                                    t = (T) baseRecyclerAdapter.getItem(pos);
                                }
                                clickListener.onItemClick(childView, pos, t);

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
                                T t = null;
                                if (recyclerView.getAdapter() instanceof BaseRecyclerAdapter) {
                                    BaseRecyclerAdapter baseRecyclerAdapter = (BaseRecyclerAdapter) recyclerView.getAdapter();

                                    t = (T) baseRecyclerAdapter.getItem(pos);
                                }
                                clickListener.onItemLongClick(childView, pos, t);
                            }

                        }
                    }
                });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetector.onTouchEvent(e);
        return false;
    }
}