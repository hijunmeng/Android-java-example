package com.example.common.recycler;

import android.util.SparseArray;

import androidx.annotation.NonNull;

/**
 * IRecyclerTypeManager接口的默认实现类
 */
public class SimpleRecyclerTypeManager implements IRecyclerTypeManager {
    private SparseArray<BaseBindView> mBindViewMap = new SparseArray<>();

    @Override
    public BaseBindView getBindView(int type) {
        return mBindViewMap.get(type);
    }

    @Override
    public int getItemLayoutResId(int type) {
        if (mBindViewMap.get(type) == null) {
            return 0;
        }
        return mBindViewMap.get(type).getItemLayoutResId();
    }

    @Override
    public void putBindView(@NonNull int type, @NonNull BaseBindView bindView) {
        mBindViewMap.put(type, bindView);
    }
}