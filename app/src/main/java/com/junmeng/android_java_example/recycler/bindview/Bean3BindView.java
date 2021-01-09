package com.junmeng.android_java_example.recycler.bindview;

import androidx.annotation.NonNull;

import com.example.common.recycler.BaseBindView;
import com.example.common.recycler.RecyclerViewHolder;
import com.junmeng.android_java_example.R;

import java.util.List;

public class Bean3BindView extends BaseBindView<Object> {
    @Override
    public int getItemLayoutResId() {
        return R.layout.item_bean_3;
    }

    @Override
    public void bindViewData(RecyclerViewHolder holder, int position, Object item, @NonNull List<Object> payloads) {

    }
}
