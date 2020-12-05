package com.junmeng.android_java_example.recycler.bindview;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.junmeng.android_java_example.R;
import com.example.common.recycler.BaseBindView;
import com.example.common.recycler.RecyclerViewHolder;
import com.junmeng.android_java_example.recycler.bean.Bean1;

import java.util.List;

public class Bean1BindView extends BaseBindView<Bean1> {
    private static final String TAG = "Bean1BindView";

    @Override
    public int getItemLayoutResId() {
        return R.layout.item_bean_1;
    }

    @Override
    public void bindViewData(RecyclerViewHolder holder, int position, Bean1 item, @NonNull List<Object> payloads) {

        holder.setOnClickListener(R.id.btn_test, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "on click position=" + position);
            }
        });
    }
}
