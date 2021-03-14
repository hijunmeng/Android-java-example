package com.junmeng.android_java_example.recycler.chat;

import com.example.common.recycler.BaseBindView2;
import com.example.common.recycler.RecyclerViewHolder;
import com.junmeng.android_java_example.R;

public class BindViewOne extends BaseBindView2<BindViewOne.Item> {

    @Override
    public int getItemLayoutResId(Item item) {
        int layoutResId;
        if (item.getKind() == 0) {
            layoutResId = R.layout.item_chat_recive_one;
        } else {
            layoutResId = R.layout.item_chat_send_one;
        }
        return layoutResId;
    }

    @Override
    public void bindViewDataWithFull(RecyclerViewHolder holder, int position, Item item) {

        holder.setText(R.id.text, item.text);
    }

    public static class Item implements IKind {

        public Item(int kind) {
            this.kind = kind;
        }

        public Item(int kind, String text) {
            this.kind = kind;
            this.text = text;
        }

        int kind;

        public String text;

        @Override
        public void setKind(int kind) {
            this.kind = kind;
        }

        @Override
        public int getKind() {
            return kind;
        }
    }

}
