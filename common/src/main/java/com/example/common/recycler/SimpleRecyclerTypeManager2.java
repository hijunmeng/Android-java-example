package com.example.common.recycler;

import android.util.SparseArray;

import androidx.annotation.NonNull;

/**
 * IRecyclerTypeManager接口的默认实现类
 */
public class SimpleRecyclerTypeManager2 implements IRecyclerTypeManager2 {
    private SparseArray<BaseBindView2> mBindViewMap = new SparseArray<>();

    @Override
    public BaseBindView2 getBindView(int type) {
        return mBindViewMap.get(type);
    }

    @Override
    public int getItemViewType(Object item) {
        //此处让getItemViewType等价于getItemLayoutResId
        return getItemLayoutResId(item);
    }

    @Override
    public int getItemLayoutResId(Object item) {
        int type = item.getClass().hashCode();
        BaseBindView2 bindView = getBindView(type);
        if (bindView == null) {
            throw new RuntimeException(item.getClass().getName() + " no found in BindView,you must register it");
        }
        return bindView.getItemLayoutResId(item);
    }

    @Override
    public void putBindView(@NonNull int type, @NonNull BaseBindView2 bindView) {
        mBindViewMap.put(type, bindView);
    }
}