package com.junmeng.android_java_example.recycler.chat;

import com.example.common.recycler.BaseBindView2;
import com.example.common.recycler.RecyclerViewHolder;
import com.junmeng.android_java_example.R;

public class BindViewTwo extends BaseBindView2<BindViewTwo.Item> {

    @Override
    public int getItemLayoutResId(Item item) {
        int layoutResId;
        if (item.getKind() == 0) {
            layoutResId = R.layout.item_chat_recive_two;
        } else {
            layoutResId = R.layout.item_chat_send_two;
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

        public String text;
        int kind;

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
