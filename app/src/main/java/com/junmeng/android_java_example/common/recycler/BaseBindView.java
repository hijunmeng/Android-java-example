package com.junmeng.android_java_example.common.recycler;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * 每种数据类型对应的BindView基类
 * @param <T> 与视图对应的实体类
 */
public abstract class BaseBindView<T> {

    /**
     * 获得布局资源id
     * 实际上可以不用放在这里，放在这里主要是为了可以跳转到界面进行预览
     * @return
     */
    public abstract int getItemLayoutResId();

    /**
     * 数据与视图绑定
     * @param holder
     * @param position
     * @param item
     * @param payloads 要size为0,表示全局刷新，如果不为0,则表示局部刷新
     */
    public abstract void bindViewData(RecyclerViewHolder holder, int position, T item, @NonNull List<Object> payloads);
}