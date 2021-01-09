package com.example.common.recycler;

public interface IRecyclerTypeManager {
    /**
     * 根据类型获得对应的视图数据绑定实现对象
     *
     * @param type
     * @return
     */
    BaseBindView getBindView(int type);

    /**
     * 获得资源布局id
     *
     * @param type
     * @return
     */
    int getItemLayoutResId(int type);

    /**
     * 保存对应类型的视图数据绑定实现对象
     * @param type
     * @param bindView
     * @return
     */
    void putBindView(int type, BaseBindView bindView);

}