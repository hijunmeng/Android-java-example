package com.junmeng.android_java_example.recycler.bean;

import com.example.common.recycler.IRecyclerItemType;
import com.junmeng.android_java_example.recycler.ItemTypeManager;

public class Bean2 implements IRecyclerItemType {
    @Override
    public int getItemType() {
        return ItemTypeManager.ITEM_LAYOUT_2;
    }
}
