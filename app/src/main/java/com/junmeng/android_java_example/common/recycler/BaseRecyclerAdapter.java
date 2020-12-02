package com.junmeng.android_java_example.common.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {
    public static final String FLAG_LOCAL_REFRESH = "FLAG_LOCAL_REFRESH";//局部刷新的标记，例如notifyItemChanged(1,BaseRecyclerAdapter.FLAG_LOCAL_REFRESH)

    @Deprecated
    public interface OnItemClickListener<T> {
        void onItemClick(View v, int position, T t);
    }

    @Deprecated
    public interface OnItemLongClickListener<T> {
        void onItemLongClick(View v, int position, T t);
    }

    private BaseMultiRecyclerAdapter.OnItemClickListener<T> mOnItemClickLitener;
    private BaseMultiRecyclerAdapter.OnItemLongClickListener<T> mOnItemLongClickLitener;
    protected List<T> mList = new ArrayList();
    protected T mSelectedItem;//当前选中项，如无选中则为null

    /**
     * 在此处根据类型返回item布局id
     *
     * @return
     */
    public abstract int getItemLayoutResId(int type);

    @Override
    public abstract void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position, @NonNull List<Object> payloads);

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerViewHolder holder = new RecyclerViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(getItemLayoutResId(viewType), parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        //ignore
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    protected void setClickListener(@NonNull final RecyclerViewHolder holder) {
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

    //****************************************扩展方法***************************************
    //开放以下方法便于用户使用

    /**
     * 设置监听器
     *
     * @param mOnItemClickLitener
     */
    @Deprecated //推荐用RecyclerItemClickListener
    public void setOnItemClickLitener(BaseMultiRecyclerAdapter.OnItemClickListener<T> mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    /**
     * 设置监听器
     *
     * @param mOnItemLongClickLitener
     */
    @Deprecated //推荐用RecyclerItemClickListener
    public void setOnItemLongClickListener(BaseMultiRecyclerAdapter.OnItemLongClickListener<T> mOnItemLongClickLitener) {
        this.mOnItemLongClickLitener = mOnItemLongClickLitener;
    }

    /**
     * 设置当前选中item
     *
     * @param item
     */
    public void setSelectedItem(T item) {
        this.mSelectedItem = item;
    }

    /**
     * 获得当前选中item
     *
     * @return
     */
    @Nullable
    public T getSelectedItem() {
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
        T item = getSelectedItem();
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
    public T getItem(int position) {
        try {
            return mList.get(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得item项在列表中的位置
     *
     * @param item
     * @return
     */
    public int getItemPosition(T item) {
        return mList.indexOf(item);
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
     * 滑动到底部
     * @param recyclerView
     */
    public void smoothScrollToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) {
            return;
        }
        int position = getItemCount()-1;
        if (position != -1) {
            recyclerView.smoothScrollToPosition(position);
        }
    }

    /**
     * 添加数据（不清空旧数据）同时自动刷新
     *
     * @param list
     */
    public void addAllData(@NonNull List<T> list) {
        addAllData(list, false, true);
    }

    /**
     * 添加数据，同时自动刷新
     *
     * @param list
     * @param isClearOld 是否清空旧数据
     */
    public void addAllData(@NonNull List<T> list, boolean isClearOld) {
        addAllData(list, isClearOld, true);
    }

    /**
     * 添加数据
     *
     * @param list
     * @param isClearOld
     * @param isNotifyDataSetChanged
     */
    public void addAllData(@NonNull List<T> list, boolean isClearOld, boolean isNotifyDataSetChanged) {
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
    public void addData(@NonNull T t) {
        this.mList.add(t);
        notifyItemInserted(getItemCount() - 1);
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
    public List<T> getData() {
        return this.mList;
    }

    /**
     * 获取数据
     *
     * @return
     */
    public void setData(@NonNull List<T> list) {
        if (list == null) {
            return;
        }
        this.mList = list;
        notifyDataSetChanged();
    }
}
