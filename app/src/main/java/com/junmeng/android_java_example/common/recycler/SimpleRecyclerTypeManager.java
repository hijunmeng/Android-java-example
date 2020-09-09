package com.junmeng.android_java_example.common.recycler;

import android.util.SparseArray;

public class SimpleRecyclerTypeManager implements IRecyclerTypeManager {
    private SparseArray<BaseBindView> mBindViewMap = new SparseArray();
    /**
     *
     * @param itemType item类型
     * @param bindView itemType对应的数据视图绑定对象
     */
    public void put(int itemType, BaseBindView bindView) {
        mBindViewMap.put(itemType, bindView);
    }


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
}