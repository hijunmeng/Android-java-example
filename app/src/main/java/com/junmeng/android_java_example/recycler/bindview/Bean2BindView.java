package com.junmeng.android_java_example.recycler.bindview;

import com.example.common.recycler.BaseBindView;
import com.example.common.recycler.RecyclerViewHolder;
import com.junmeng.android_java_example.R;
import com.junmeng.android_java_example.recycler.bean.Bean2;

public class Bean2BindView extends BaseBindView<Bean2> {
    @Override
    public int getItemLayoutResId() {
        return R.layout.item_bean_2;
    }

    @Override
    public void bindViewDataWithFull(RecyclerViewHolder holder, int position, Bean2 item) {

    }
}
