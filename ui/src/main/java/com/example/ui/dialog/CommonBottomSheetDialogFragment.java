package com.example.ui.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.common.recycler.RecyclerItemClickListener;
import com.example.ui.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用BottomSheetDialog
 * 支持背景透明
 * 支持自定义布局
 * 用法示例：
 * new CommonBottomSheetDialogFragment.Builder()
 * .addButton("1", 1, -1, null)
 * .addButton("2", 2, -1, null)
 * .setDividerItemDecoration(android.R.color.darker_gray)
 * .setCancelButton(null, -1, null)
 * .create()
 * .show(getSupportFragmentManager(), "")
 * ;
 */
public class CommonBottomSheetDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener, DialogInterface {
    public static final int BUTTON_CANCEL = -99999;

    private static final int DEFAULT_LAYOUT = R.layout.junmeng_dialog_bottom_sheet_simple;
    private static final int DEFAULT_ITEM_LAYOUT = R.layout.junmeng_item_bottom_sheet;

    private Builder mBuilder;

    @Nullable
    private RecyclerView mRecyclerView;
    @Nullable
    private TextView mCancelView;
    @Nullable
    private View mSpliteView;

    private MyAdapter mMyAdapter;

    private CommonBottomSheetDialogFragment(@NonNull Builder builder) {
        mBuilder = builder;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final BottomSheetDialog dialog = new BottomSheetDialog(getContext(), R.style.CustomBottomSheetDialogTheme);//设置背景透明
        return dialog;
    }

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(mBuilder.layoutResId, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.recyclerview);
        mCancelView = view.findViewById(R.id.cancel);
        mSpliteView = view.findViewById(R.id.splite);

        initRecyclerview();
        initCancelView();
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mBuilder.backgroundTransparent) {
            //背景透明
            if (getDialog() == null || getDialog().getWindow() == null) {
                return;
            }
            Window window = getDialog().getWindow();
            WindowManager.LayoutParams windowParams = window.getAttributes();
            windowParams.dimAmount = 0.0f;
            window.setAttributes(windowParams);
        }

    }

    private void initCancelView() {
        if (mCancelView == null) {
            return;
        }
        if (mBuilder.isShowCancel) {
            mCancelView.setVisibility(View.VISIBLE);
            mCancelView.setOnClickListener(this);
            if (mBuilder.buttonSizeSP != -1) {
                mCancelView.setTextSize(mBuilder.buttonSizeSP);
            }
            if (mBuilder.cancelButtonText != null) {
                mCancelView.setText(mBuilder.cancelButtonText);
            }
            if (mBuilder.cancelButtonColor != -1) {
                mCancelView.setTextColor(mBuilder.cancelButtonColor);
            }

            if (mSpliteView == null) {
                return;
            }
            mSpliteView.setVisibility(View.VISIBLE);
            if (mBuilder.spliteLineHeightPx != -1) {
                ViewGroup.LayoutParams lp = mSpliteView.getLayoutParams();
                if (lp != null) {
                    lp.height = mBuilder.spliteLineHeightPx;
                    mSpliteView.setLayoutParams(lp);
                }
            }
            if (mBuilder.spliteLineColor != -1) {
                mSpliteView.setBackgroundColor(mBuilder.spliteLineColor);
            }
        } else {
            mCancelView.setVisibility(View.GONE);
            if (mSpliteView == null) {
                return;
            }
            mSpliteView.setVisibility(View.GONE);
        }
    }

    private void initRecyclerview() {
        if (mRecyclerView == null) {
            return;
        }
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mMyAdapter = new MyAdapter(mBuilder.items, mBuilder.itemLayoutResId));

        if (mBuilder.dividerItemDecorationResId != -1) {
            DividerItemDecoration itemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
            itemDecoration.setDrawable(ContextCompat.getDrawable(mRecyclerView.getContext(), mBuilder.dividerItemDecorationResId));
            mRecyclerView.addItemDecoration(itemDecoration);

        }

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(mRecyclerView, new RecyclerItemClickListener.SimpleOnItemClickListener() {
            @Override
            public void onItemClick(MotionEvent e, View view, int position) {
                try {
                    if (mBuilder.isAutoDismiss) {
                        dismiss();
                    }
                    ItemBean item = mMyAdapter.mItems.get(position);
                    if (item.onClickListener != null) {
                        item.onClickListener.onClick(CommonBottomSheetDialogFragment.this, item.which);
                    } else if (mBuilder.onClickListener != null) {
                        mBuilder.onClickListener.onClick(CommonBottomSheetDialogFragment.this, item.which);
                    }
                } catch (Exception ignore) {
                }

            }
        }));
    }


    @Override
    public void cancel() {
        dismiss();
    }

    @Override
    public void onClick(View v) {
        if (mBuilder.isAutoDismiss) {
            dismiss();
        }
        if (v.getId() == R.id.cancel) {
            if (isVisible()) {
                dismiss();
            }
            if (mBuilder.cancelClickListener != null) {
                mBuilder.cancelClickListener.onClick(this, BUTTON_CANCEL);
            } else if (mBuilder.onClickListener != null) {
                mBuilder.onClickListener.onClick(this, BUTTON_CANCEL);
            }
        }
    }

    class MyAdapter extends RecyclerView.Adapter<ItemViewHolder> {

        public List<ItemBean> mItems = new ArrayList<>();
        private int mItemLayoutResId = DEFAULT_ITEM_LAYOUT;

        public MyAdapter(int itemLayoutResId) {
            mItemLayoutResId = itemLayoutResId;
        }

        public MyAdapter(@NonNull List<ItemBean> lists, int itemLayoutResId) {
            mItems.addAll(lists);
            mItemLayoutResId = itemLayoutResId;
        }

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View root = View.inflate(parent.getContext(), mItemLayoutResId, null);
            return new ItemViewHolder(root);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            try {

                ItemBean item = mItems.get(position);
                holder.itemText.setText(item.text);
                if (item.color != -1) {
                    holder.itemText.setTextColor(item.color);
                }
                if (mBuilder.buttonSizeSP != -1) {
                    holder.itemText.setTextSize(mBuilder.buttonSizeSP);
                }
                if (item.onClickListener != null) {
                    holder.itemText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            item.onClickListener.onClick(CommonBottomSheetDialogFragment.this, item.which);
                        }
                    });
                }
            } catch (Exception ignore) {
            }
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }
    }

    public static class ItemBean {
        public String text;
        public int which;
        public @ColorInt
        int color = -1;
        public DialogInterface.OnClickListener onClickListener;

        public ItemBean(String text, int which, int color, OnClickListener onClickListener) {
            this.text = text;
            this.which = which;
            this.color = color;
            this.onClickListener = onClickListener;
        }

        public ItemBean(String text, int which) {
            this.text = text;
            this.which = which;
        }
    }


    class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView itemText;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemText = itemView.findViewById(R.id.text);
        }
    }


    public static class Builder implements IDialog {
        private int layoutResId = DEFAULT_LAYOUT;
        private int itemLayoutResId = DEFAULT_ITEM_LAYOUT;

        private int buttonSizeSP = -1;//-1表示默认
        private boolean isAutoDismiss = true;
        private boolean isCancelable = true;
        private boolean backgroundTransparent = false;

        private int dividerItemDecorationResId = -1;

        private int spliteLineHeightPx = -1; //分割线高度
        private int spliteLineColor = -1; //分割线颜色

        private String cancelButtonText = null;
        private int cancelButtonColor = -1;
        private DialogInterface.OnClickListener cancelClickListener;
        private boolean isShowCancel = false;

        private DialogInterface.OnClickListener onClickListener;

        private List<ItemBean> items = new ArrayList<>();

        @Override
        public Builder setButtonSize(int buttonSizeSP) {
            this.buttonSizeSP = buttonSizeSP;
            return this;
        }

        @Override
        public Builder setCancelButton(String text, @ColorInt int color, @Nullable DialogInterface.OnClickListener onClickListener) {
            isShowCancel = true;
            this.cancelClickListener = onClickListener;
            this.cancelButtonColor = color;
            this.cancelButtonText = text;
            return this;
        }


        @Override
        public Builder isAutoDismiss(boolean isAutoDismiss) {
            this.isAutoDismiss = isAutoDismiss;
            return this;
        }

        @Override
        public Builder setCancelable(boolean cancelable) {
            this.isCancelable = cancelable;
            return this;
        }

        @Override
        public Builder setLayout(int layoutResId) {
            this.layoutResId = layoutResId;
            return this;
        }

        @Override
        public Builder setItemLayout(int layoutResId) {
            this.itemLayoutResId = layoutResId;
            return this;
        }

        @Override
        public Builder setSpliteLine(int color, int height) {
            this.spliteLineColor = color;
            this.spliteLineHeightPx = height;
            return this;
        }

        @Override
        public Builder setDividerItemDecoration(int resId) {
            this.dividerItemDecorationResId = resId;
            return this;
        }

        @Override
        public Builder setOnClickListener(DialogInterface.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
            return this;
        }

        @Override
        public Builder addButton(@Nullable String text, int which, @ColorInt int color, @Nullable DialogInterface.OnClickListener onClickListener) {
            items.add(new ItemBean(text, which, color, onClickListener));
            return this;
        }

        @Override
        public Builder addButtons(List<ItemBean> btns) {
            items.addAll(btns);
            return this;
        }

        @Override
        public Builder backgroundTransparent(boolean isTransparent) {
            this.backgroundTransparent = isTransparent;
            return this;
        }

        public CommonBottomSheetDialogFragment create() {
            CommonBottomSheetDialogFragment dialog = new CommonBottomSheetDialogFragment(this);
            dialog.setCancelable(isCancelable);
            return dialog;
        }
    }

    public interface IDialog {

        /**
         * 设置按钮文字大小
         *
         * @param buttonSizeSP
         * @return
         */
        Builder setButtonSize(int buttonSizeSP);

        /**
         * @param text            为null表示使用默认
         * @param color           -1表示使用默认
         * @param onClickListener
         * @return
         */
        Builder setCancelButton(@Nullable String text, @ColorInt int color, @Nullable DialogInterface.OnClickListener onClickListener);

        /**
         * @param text
         * @param which           在监听器onClickListener中返回此值
         * @param color
         * @param onClickListener
         * @return
         */
        Builder addButton(@NonNull String text, int which, @ColorInt int color, @Nullable DialogInterface.OnClickListener onClickListener);

        /**
         * @param btns
         * @return
         */
        Builder addButtons(List<ItemBean> btns);

        /**
         * 在点击时对话框是否自动消失，默认true
         *
         * @param isAutoDismiss
         * @return
         */
        Builder isAutoDismiss(boolean isAutoDismiss);

        /**
         * 点击外部是否会关闭对话框
         *
         * @param cancelable
         * @return
         */
        Builder setCancelable(boolean cancelable);

        /**
         * 自定义布局
         *
         * @param layoutResId
         * @return
         */
        Builder setLayout(@LayoutRes int layoutResId);

        /**
         * 自定义recyclerview子item布局
         *
         * @param layoutResId
         * @return
         */
        Builder setItemLayout(@LayoutRes int layoutResId);

        /**
         * 设置recyclerview与取消键的分割线
         *
         * @param color  分割线颜色，-1表示默认
         * @param height 分割线高度，px,-1表示默认
         * @return
         */
        Builder setSpliteLine(@ColorInt int color, int height);

        /**
         * 设置recyclerview分割线
         *
         * @param resId -1表示不设置,示例：
         *              <shape xmlns:android="http://schemas.android.com/apk/res/android"
         *              android:shape="rectangle">
         *              <size android:height="1dp" />
         *              <solid android:color="@color/color_bg_gray" />
         *              </shape>
         * @return
         */
        Builder setDividerItemDecoration(@DrawableRes int resId);

        /**
         * 优先级低于addButton设置的监听器（即如果addButton中监听器不为空的话，则不会触发这里）
         *
         * @param onClickListener
         * @return
         */
        Builder setOnClickListener(DialogInterface.OnClickListener onClickListener);

        /**
         * 设置背景是否透明
         *
         * @param isTransparent
         * @return
         */
        Builder backgroundTransparent(boolean isTransparent);

    }


}
