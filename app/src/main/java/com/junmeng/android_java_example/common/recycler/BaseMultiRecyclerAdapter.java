package com.junmeng.android_java_example.common.recycler;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 与BaseMultiRecyclerAdapter不同的是此类指定了item数据类型必须实现IRecyclerItemType接口
 */
public abstract class BaseMultiRecyclerAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private static final String TAG = "BaseRecyclerAdapter2";
    public static final String FLAG_LOCAL_REFRESH = "FLAG_LOCAL_REFRESH";//局部刷新的标记，例如notifyItemChanged(1,BaseRecyclerAdapter.FLAG_LOCAL_REFRESH)

    @Deprecated
    public interface OnItemClickListener<IRecyclerItemType> {
        void onItemClick(View v, int position, IRecyclerItemType t);
    }

    @Deprecated
    public interface OnItemLongClickListener<IRecyclerItemType> {
        void onItemLongClick(View v, int position, IRecyclerItemType t);
    }


    private OnItemClickListener<IRecyclerItemType> mOnItemClickLitener;
    private OnItemLongClickListener<IRecyclerItemType> mOnItemLongClickLitener;


    private List<IRecyclerItemType> mList = new ArrayList<IRecyclerItemType>();//存放列表数据,推荐使用List.addAll进行更新数据
    private IRecyclerItemType mSelectedItem;//当前选中项，如无选中则为null


    public BaseMultiRecyclerAdapter() {
    }

    /**
     * 在此处根据类型返回item布局id
     *
     * @return
     */
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
     * @param parent
     * @param viewType 从getItemViewType获取
     * @return
     */
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewHolder holder = new RecyclerViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(getItemLayoutResId(viewType), parent,
                false));
        return holder;
    }


    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        //require empty
        //由于本类要提供局部更新功能，因此不会使用此方法，但此方法是抽象方法，因此提供空实现即可
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

    private void setClickListener(@NonNull final RecyclerViewHolder holder) {
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos, getItem(pos));
                }
            });
        }
        if (mOnItemLongClickLitener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemLongClickLitener.onItemLongClick(holder.itemView, pos, getItem(pos));
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
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


    //****************************************扩展方法***************************************
    //开放以下方法便于用户使用

    /**
     * 设置监听器
     *
     * @param mOnItemClickLitener
     */
    @Deprecated //推荐用RecyclerItemClickListener
    public void setOnItemClickLitener(OnItemClickListener<IRecyclerItemType> mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    /**
     * 设置监听器
     *
     * @param mOnItemLongClickLitener
     */
    @Deprecated //推荐用RecyclerItemClickListener
    public void setOnItemLongClickListener(OnItemLongClickListener<IRecyclerItemType> mOnItemLongClickLitener) {
        this.mOnItemLongClickLitener = mOnItemLongClickLitener;
    }

    /**
     * 设置当前选中item
     *
     * @param item
     */
    public void setSelectedItem(IRecyclerItemType item) {
        this.mSelectedItem = item;
    }

    /**
     * 获得当前选中item
     *
     * @return
     */
    @Nullable
    public IRecyclerItemType getSelectedItem() {
        return this.mSelectedItem;
    }

    /**
     * 获得当前选中item在列表中的位置，从0开始
     *
     * @return
     */
    public int getSelectedItemPosition() {
        if (mList == null) {
            return -1;
        }
        IRecyclerItemType item = getSelectedItem();
        if (item == null) {
            return -1;
        }

        return mList.indexOf(item);
    }

    /**
     * 获得指定位置的item数据
     *
     * @param position
     * @return
     */
    public IRecyclerItemType getItem(int position) {
        try {
            return mList.get(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 滑动到选中位置
     *
     * @param recyclerView
     */
    public void smoothScrollToSelectedPosition(RecyclerView recyclerView) {
        if (recyclerView == null) {
            return;
        }
        int position = getSelectedItemPosition();
        if (position != -1) {
            recyclerView.smoothScrollToPosition(position);
        }
    }

    /**
     * 添加数据（不清空旧数据）同时自动刷新
     *
     * @param list
     */
    public void addAllData(@NonNull List list) {
        addAllData(list, false, true);
    }

    /**
     * 添加数据，同时自动刷新
     *
     * @param list
     * @param isClearOld 是否清空旧数据
     */
    public void addAllData(@NonNull List list, boolean isClearOld) {
        addAllData(list, isClearOld, true);
    }

    /**
     * 添加数据
     *
     * @param list
     * @param isClearOld
     * @param isNotifyDataSetChanged
     */
    public void addAllData(@NonNull List list, boolean isClearOld, boolean isNotifyDataSetChanged) {
        if (isClearOld) {
            this.mList.clear();
        }
        this.mList.addAll(list);
        if (isNotifyDataSetChanged) {
            notifyDataSetChanged();
        }

    }

    /**
     * 添加数据
     *
     * @param t
     */
    public void addData(@NonNull IRecyclerItemType t) {
        this.mList.add(t);
        notifyDataSetChanged();
    }

    /**
     * 清空数据
     */
    public void clearData() {
        this.mList.clear();
        notifyDataSetChanged();
    }

    /**
     * 获取数据
     *
     * @return
     */
    public List<IRecyclerItemType> getData() {
        return this.mList;
    }

    /**
     * 设置列表数据
     * fixme:开放此方法有点危险，后期再考虑是否删除
     *
     * @return
     */
    public void setData(@NonNull List list) {
        if (list == null) {
            return;
        }
        this.mList = list;
        notifyDataSetChanged();
    }

}