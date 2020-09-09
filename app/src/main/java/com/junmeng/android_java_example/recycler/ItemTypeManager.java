package com.junmeng.android_java_example.recycler;

import com.junmeng.android_java_example.common.recycler.SimpleRecyclerTypeManager;
import com.junmeng.android_java_example.recycler.bindview.Bean1BindView;
import com.junmeng.android_java_example.recycler.bindview.Bean2BindView;

public class ItemTypeManager extends SimpleRecyclerTypeManager {

    public static final int ITEM_LAYOUT_1=1;
    public static final int ITEM_LAYOUT_2=2;

    public ItemTypeManager(){
        put(ITEM_LAYOUT_1,new Bean1BindView());
        put(ITEM_LAYOUT_2,new Bean2BindView());
    }


}
