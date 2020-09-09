package com.junmeng.android_java_example.recycler;

import android.content.Context;

import com.junmeng.android_java_example.recycler.decoration.YDivider;
import com.junmeng.android_java_example.recycler.decoration.YDividerBuilder;
import com.junmeng.android_java_example.recycler.decoration.YDividerItemDecoration;

public class DividerItemDecoration extends YDividerItemDecoration {

    public DividerItemDecoration(Context context) {
        super(context);
    }

    @Override
    public YDivider getDivider(int itemPosition) {
        YDivider divider = null;
        switch (itemPosition % 2) {
            case 0:
                //每一行第一个显示rignt和bottom
                divider = new YDividerBuilder()
                        .setRightSideLine(true, 0xffffffff, 10, 0, 0)
                        .setBottomSideLine(true, 0xffffffff, 10, 0, 0)
                        .create();
                break;
            case 1:
                //第二个显示Left和bottom
                divider = new YDividerBuilder()
                        .setLeftSideLine(false, 0xffffffff, 5, 0, 0)
                        .setBottomSideLine(true, 0xffffffff, 10, 0, 0)
                        .create();
                break;
            default:
                break;
        }
        return divider;
    }
}