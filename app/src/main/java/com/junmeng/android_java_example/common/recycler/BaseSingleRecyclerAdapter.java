package com.junmeng.android_java_example.common.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 对RecyclerView.Adapter的封装，使其更易用，此类需要与RecyclerViewHolder一起使用
 * 使用时只需继承此类，然后实现抽象方法即可
 * 此类适用于单一数据类型的RecyclerView，如果是多数据类型的，请使用BaseMultiRecyclerAdapter
 * <p>
 * 增加选中item的保存与获取
 *
 * @param <T>
 */
public abstract class BaseSingleRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {
    private static final String TAG = "BaseSingleRecyclerAdapter";
    public static final String FLAG_LOCAL_REFRESH = "FLAG_LOCAL_REFRESH";//局部刷新的标记，例如notifyItemChanged(1,BaseRecyclerAdapter.FLAG_LOCAL_REFRESH)

    @Deprecated
    public interface OnItemClickListener<T> {
        void onItemClick(View v, int position, T t);
    }

    @Deprecated
    public interface OnItemLongClickListener<T> {
        void onItemLongClick(View v, int position, T t);
    }


    @Deprecated
    public Context mApplicationContext;
    private OnItemClickListener<T> mOnItemClickLitener;
    private OnItemLongClickListener<T> mOnItemLongClickLitener;

    private List<T> mList = new ArrayList<T>();

    private T mSelectedItem;

    public BaseSingleRecyclerAdapter() {

    }

    @Deprecated//后期删除
    public BaseSingleRecyclerAdapter(Context context) {
        this.mApplicationContext = context.getApplicationContext();
    }


    /**
     * 在此处返回item布局id
     *
     * @return
     */
    public abstract int getItemLayoutResId();

//    /**
//     * 设置控件的显示内容
//     *
//     * @param holder
//     * @param t
//     */
//    public  void convert(RecyclerViewHolder holder, int position, T t){
//        this.convert(holder,position,t,new ArrayList<Object>());
//    }


    /**
     * 设置控件的显示内容
     *
     * @param holder
     * @param t
     */
    public abstract void convert(RecyclerViewHolder holder, int position, T t, @NonNull List<Object> payloads);

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewHolder holder = new RecyclerViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(getItemLayoutResId(), parent,
                false));
        return holder;
    }


    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        //require empty
    }

    @Override
    public void onBindViewHolder(final @NonNull RecyclerViewHolder holder, int position, @NonNull List<Object> payloads) {
        final T t = mList.get(position);
        convert(holder, position, t, payloads);
        // 如果设置了回调，则设置点击事件,此种方式虽然能实现点击，但实际过程中发现
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos, t);
                }
            });
        }
        if (mOnItemLongClickLitener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemLongClickLitener.onItemLongClick(holder.itemView, pos, t);
                    return false;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

//*************************** 扩展方法********************************************
//开放以下方法便于用户使用

    /**
     * 设置监听器
     *
     * @param mOnItemClickLitener
     */
    @Deprecated //推荐用RecyclerItemClickListener
    public void setOnItemClickLitener(OnItemClickListener<T> mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    /**
     * 设置监听器
     *
     * @param mOnItemLongClickLitener
     */
    @Deprecated //推荐用RecyclerItemClickListener
    public void setOnItemLongClickListener(OnItemLongClickListener<T> mOnItemLongClickLitener) {
        this.mOnItemLongClickLitener = mOnItemLongClickLitener;
    }

    /**
     * 设置当前选中item
     *
     * @param item
     */
    public void setSelectedItem(T item) {
        this.mSelectedItem = item;
    }

    /**
     * 获得当前选中item
     *
     * @return
     */
    @Nullable
    public T getSelectedItem() {
        return this.mSelectedItem;
    }

    /**
     * 获得当前选中item在列表中的位置，从0开始
     *
     * @return
     */
    public int getSelectedItemPosition() {
        if (mList == null) {
            return -1;
        }
        T item = getSelectedItem();
        if (item == null) {
            return -1;
        }

        return mList.indexOf(item);
    }

    /**
     * 滑动到选中位置
     *
     * @param recyclerView
     */
    public void smoothScrollToSelectedPosition(RecyclerView recyclerView) {
        if (recyclerView == null) {
            return;
        }
        int position = getSelectedItemPosition();
        if (position != -1) {
            recyclerView.smoothScrollToPosition(position);
        }
    }

    /**
     * 添加数据（不清空旧数据）同时自动刷新
     *
     * @param list
     */
    public void addAllData(@NonNull List<T> list) {
        addAllData(list, false, true);
    }

    /**
     * 添加数据，同时自动刷新
     *
     * @param list
     * @param isClearOld 是否清空旧数据
     */
    public void addAllData(@NonNull List<T> list, boolean isClearOld) {
        addAllData(list, isClearOld, true);
    }

    /**
     * 添加数据
     *
     * @param list
     * @param isClearOld
     * @param isNotifyDataSetChanged
     */
    public void addAllData(@NonNull List<T> list, boolean isClearOld, boolean isNotifyDataSetChanged) {
        if (isClearOld) {
            this.mList.clear();
        }
        this.mList.addAll(list);
        if (isNotifyDataSetChanged) {
            notifyDataSetChanged();
        }

    }

    /**
     * 添加数据
     *
     * @param t
     */
    public void addData(@NonNull T t) {
        this.mList.add(t);
        notifyDataSetChanged();
    }

    /**
     * 清空数据
     */
    public void clearData() {
        this.mList.clear();
        notifyDataSetChanged();
    }

    /**
     * 获取数据
     *
     * @return
     */
    public List<T> getData() {
        return this.mList;
    }

    /**
     * 获取数据
     *
     * @return
     */
    public void setData(@NonNull List<T> list) {
        if (list == null) {
            return;
        }
        this.mList = list;
        notifyDataSetChanged();
    }

}