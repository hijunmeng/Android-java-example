package com.junmeng.android_java_example.common;

import androidx.fragment.app.FragmentManager;

public interface IBaseActivity extends IBase {
    /**
     * 将Activity的物理返回键事件传递给fragment
     *
     * @return true表示fragment消费了此次事件，false则表示fragment不处理此事件，还给Activity处理
     */
    boolean dispatchBackPressedEvent();

    void gotoActivity(Class<?> cls, boolean isFinishCurrent);

    void gotoActivity(Class<?> cls);


}
