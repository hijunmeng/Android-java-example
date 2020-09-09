package com.junmeng.android_java_example.recycler.bindview;

import androidx.annotation.NonNull;

import com.junmeng.android_java_example.R;
import com.junmeng.android_java_example.common.recycler.BaseBindView;
import com.junmeng.android_java_example.common.recycler.RecyclerViewHolder;
import com.junmeng.android_java_example.recycler.bean.Bean2;

import java.util.List;

public class Bean2BindView extends BaseBindView<Bean2> {
    @Override
    public int getItemLayoutResId() {
        return R.layout.item_bean_2;
    }

    @Override
    public void bindViewData(RecyclerViewHolder holder, int position, Bean2 item, @NonNull List<Object> payloads) {

    }
}
