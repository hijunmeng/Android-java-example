package com.example.common.recycler;


import androidx.annotation.NonNull;

import java.util.List;

/**
 * 支持多类型的recyclerview适配器
 * 此类适用于每个item又有多种布局的情况，例如聊天，每个item都需要有发送和接收两种布局
 * 采用了以布局资源id作为getItemViewType的返回值
 * 用法示例：
 * <pre>
 *  BaseMultiRecyclerAdapter2 adapter= new BaseMultiRecyclerAdapter2();
 *   adapter.register(new Bean1BindView2());
 *   adapter.register(new Bean2BindView2());
 *  recyclerView.setAdapter(adapter);
 * </pre>
 */
public class BaseMultiRecyclerAdapter2 extends BaseRecyclerAdapter {

    private static final String TAG = "BaseMultiRecyclerAdapter";
    protected IRecyclerTypeManager2 mRecyclerTypeManager = new SimpleRecyclerTypeManager2();

    public BaseMultiRecyclerAdapter2() {
    }


    /**
     * 在此处根据类型返回item布局id
     *
     * @param type getItemViewType获得
     * @return
     */
    @Override
    public int getItemLayoutResId(int type) {
        //由于getItemViewType返回的就是布局资源id,因此此处直接返回type即可
        return type;
    }

    /**
     * @param holder
     * @param position
     * @param payloads 只要size为0,表示全局刷新，如果不为0,则表示局部刷新
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, @NonNull List payloads) {
        onBindViewHolderExtend(holder, position, payloads);

        // 如果设置了回调，则设置点击事件
        setClickListener(holder);
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerViewHolder holder) {
        int pos = holder.getLayoutPosition();
        if (pos == -1) {
            return;
        }
        Object item = getItem(pos);
        if (item == null) {
            return;
        }
        int code = item.getClass().hashCode();
        BaseBindView2 bindView = mRecyclerTypeManager.getBindView(code);
        if (bindView == null) {
            return;
        }
        bindView.onViewRecycled(holder);
    }

    /**
     * 注册
     *
     * @param bindView
     */
    public void register(BaseBindView2 bindView) {
        mRecyclerTypeManager.putBindView(bindView.mType.hashCode(), bindView);
    }

    /**
     * 设置控件的显示内容
     *
     * @param holder
     */
    public void onBindViewHolderExtend(RecyclerViewHolder holder, int position, @NonNull List<Object> payloads) {
        mRecyclerTypeManager.getBindView(getItem(position).getClass().hashCode()).bindViewData(holder, position, mList.get(position), payloads);
    }

    /**
     * 获得指定位置的item的类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return mRecyclerTypeManager.getItemViewType(mList.get(position));
    }

}