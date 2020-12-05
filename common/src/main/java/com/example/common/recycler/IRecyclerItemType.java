package com.example.common.recycler;

/**
 * recyclerview多类型时每个bean类需要实现此接口
 */
public interface IRecyclerItemType {
    /**
     * 获得item类型
     * @return
     */
    int getItemType();
}
