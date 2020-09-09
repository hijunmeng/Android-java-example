package com.junmeng.android_java_example.recycler.bindview;

import androidx.annotation.NonNull;

import com.junmeng.android_java_example.R;
import com.junmeng.android_java_example.common.recycler.BaseBindView;
import com.junmeng.android_java_example.common.recycler.RecyclerViewHolder;
import com.junmeng.android_java_example.recycler.bean.Bean1;

import java.util.List;

public class Bean1BindView extends BaseBindView<Bean1> {
    @Override
    public int getItemLayoutResId() {
        return R.layout.item_bean_1;
    }

    @Override
    public void bindViewData(RecyclerViewHolder holder, int position, Bean1 item, @NonNull List<Object> payloads) {

    }
}
