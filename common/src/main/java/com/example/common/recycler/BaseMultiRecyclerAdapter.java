package com.example.common.recycler;


import androidx.annotation.NonNull;

import java.util.List;

/**
 * 与BaseMultiRecyclerAdapter不同的是此类指定了item数据类型必须实现IRecyclerItemType接口
 */
public abstract class BaseMultiRecyclerAdapter extends BaseRecyclerAdapter<IRecyclerItemType> {

    private static final String TAG = "BaseMultiRecyclerAdapter";

    public BaseMultiRecyclerAdapter() {
    }

    /**
     * 在此处根据类型返回item布局id
     *
     * @return
     */
    @Override
    public int getItemLayoutResId(int type) {
        return getRecyclerTypeManager().getItemLayoutResId(type);
    }

    @NonNull
    public abstract IRecyclerTypeManager getRecyclerTypeManager();


    /**
     * 设置控件的显示内容
     *
     * @param holder
     * @param t
     */
    public void onBindViewHolderExtend(RecyclerViewHolder holder, int position, int viewType, IRecyclerItemType t, @NonNull List<Object> payloads) {
        getRecyclerTypeManager().getBindView(viewType).bindViewData(holder, position, t, payloads);
    }


    /**
     * @param holder
     * @param position
     * @param payloads 只要size为0,表示全局刷新，如果不为0,则表示局部刷新
     */
    @Override
    public void onBindViewHolder(final @NonNull RecyclerViewHolder holder, int position, @NonNull List<Object> payloads) {

        onBindViewHolderExtend(holder, position, mList.get(position).getItemType(), mList.get(position), payloads);

        // 如果设置了回调，则设置点击事件,此种方式虽然能实现点击，但实际过程中发现点击响应不灵敏，后期废除
        setClickListener(holder);

    }

    /**
     * 获得指定位置的item的类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getItemType();
    }

}