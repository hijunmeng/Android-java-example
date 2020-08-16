package com.junmeng.android_java_example.common;

/**
 * 如果fragment想要处理Activity的物理返回键事件，则实现此接口
 * 相应的在Activity的onBackPressed中要先将事件分发给fragment,BaseFragmentSimple.dispatchBackPressedEvent提供了实现
 */
public interface IFragmentBackPressed {
    /**
     * 接收到Activity的物理返回键事件
     * 如果fragment决定消费，则在此实现消费逻辑，然后返回true;如果fragment不想处理，直接返回false即可
     * @return true表示fragment消费了物理返回键事件，false则表示事件还给Activity
     */
    boolean onFragmentBackPressed();
}
