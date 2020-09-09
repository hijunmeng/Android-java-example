package com.junmeng.android_java_example.recycler;

import androidx.annotation.NonNull;

import com.junmeng.android_java_example.common.recycler.BaseMultiRecyclerAdapter;
import com.junmeng.android_java_example.common.recycler.IRecyclerTypeManager;

public class TestRecyclerAdapter extends BaseMultiRecyclerAdapter {

    private ItemTypeManager itemTypeManager=new ItemTypeManager();
    public TestRecyclerAdapter(){

    }

    @NonNull
    @Override
    public IRecyclerTypeManager getRecyclerTypeManager() {
        return itemTypeManager;
    }
}
