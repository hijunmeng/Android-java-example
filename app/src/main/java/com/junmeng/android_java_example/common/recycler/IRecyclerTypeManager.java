package com.junmeng.android_java_example.common.recycler;

public interface IRecyclerTypeManager {
    /**
     * 根据类型获得对应的视图数据绑定实现对象
     * @param type
     * @return
     */
    BaseBindView getBindView(int type);

    /**
     * 获得资源布局id
     * @param type
     * @return
     */
    int getItemLayoutResId(int type);

}