package com.example.common.recycler;

public interface IRecyclerTypeManager2 {
    /**
     * 根据类型获得对应的视图数据绑定实现对象
     *
     * @param type BindView的唯一标志
     * @return
     */
    BaseBindView2 getBindView(int type);

    /**
     * 获得item view类型
     * @param item
     * @return
     */
    int getItemViewType(Object item);

    /**
     * 获得资源布局id
     *
     * @param item
     * @return
     */
    int getItemLayoutResId(Object item);

    /**
     * 保存对应类型的视图数据绑定实现对象
     *
     * @param type BindView的唯一标志
     * @param bindView
     * @return
     */
    void putBindView(int type, BaseBindView2 bindView);

}