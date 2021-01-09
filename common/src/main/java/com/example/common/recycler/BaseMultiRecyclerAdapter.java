package com.example.common.recycler;


import androidx.annotation.NonNull;

import java.util.List;

/**
 * 支持多类型的recyclerview适配器
 * 用法示例：
 * <pre>
 *  BaseMultiRecyclerAdapter adapter= new BaseMultiRecyclerAdapter();
 *   adapter.register(new Bean1BindView());
 *   adapter.register(new Bean2BindView());
 *  recyclerView.setAdapter(adapter);
 * </pre>
 */
public class BaseMultiRecyclerAdapter extends BaseRecyclerAdapter {

    private static final String TAG = "BaseMultiRecyclerAdapter";
    protected IRecyclerTypeManager mRecyclerTypeManager = new SimpleRecyclerTypeManager();

    public BaseMultiRecyclerAdapter() {
    }

    /**
     * 在此处根据类型返回item布局id
     *
     * @return
     */
    @Override
    public int getItemLayoutResId(int type) {
        return mRecyclerTypeManager.getItemLayoutResId(type);
    }

    /**
     * @param holder
     * @param position
     * @param payloads 只要size为0,表示全局刷新，如果不为0,则表示局部刷新
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, @NonNull List payloads) {
        onBindViewHolderExtend(holder, position, payloads);

        // 如果设置了回调，则设置点击事件,此种方式虽然能实现点击，但实际过程中发现点击响应不灵敏，后期废除
        setClickListener(holder);
    }

    /**
     * 注册
     * 已过时，请使用{@link BaseMultiRecyclerAdapter#register(BaseBindView bindView)}
     *
     * @param cls
     * @param bindView
     */
    @Deprecated
    public void register(Class cls, BaseBindView bindView) {
        mRecyclerTypeManager.putBindView(cls.hashCode(), bindView);
    }

    /**
     * 注册
     *
     * @param bindView
     */
    public void register(BaseBindView bindView) {
        mRecyclerTypeManager.putBindView(bindView.mType.hashCode(), bindView);
    }

    /**
     * 设置控件的显示内容
     *
     * @param holder
     */
    public void onBindViewHolderExtend(RecyclerViewHolder holder, int position, @NonNull List<Object> payloads) {
        mRecyclerTypeManager.getBindView(getItemViewType(position)).bindViewData(holder, position, mList.get(position), payloads);
    }

    /**
     * 获得指定位置的item的类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getClass().hashCode();
    }

}