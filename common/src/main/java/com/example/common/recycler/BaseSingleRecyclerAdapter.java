package com.example.common.recycler;

import androidx.annotation.NonNull;

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
public abstract class BaseSingleRecyclerAdapter<T> extends BaseRecyclerAdapter<T> {
    private static final String TAG = "BaseSingleRecyclerAdapter";

    public BaseSingleRecyclerAdapter() {
    }

    /**
     * 在此处返回item布局id
     *
     * @return
     */
    public abstract int getItemLayoutResId();

    /**
     * 设置控件的显示内容
     *
     * @param holder
     * @param t
     */
    public abstract void onBindView(RecyclerViewHolder holder, int position, T t, @NonNull List<Object> payloads);

    @Override
    public int getItemLayoutResId(int type) {
        return getItemLayoutResId();
    }

    @Override
    public void onBindViewHolder(final @NonNull RecyclerViewHolder holder, int position, @NonNull List<Object> payloads) {
        final T t = mList.get(position);
        onBindView(holder, position, t, payloads);
        setClickListener(holder);
    }
}