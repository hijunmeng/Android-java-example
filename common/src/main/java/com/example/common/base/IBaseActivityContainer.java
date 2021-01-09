package com.example.common.base;

import androidx.fragment.app.Fragment;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * @Description: 提供包含多fragment的容器Activity的工具方法
 * @Author: hwj
 * @CreateDate: 2020/12/22 9:47
 */
public interface IBaseActivityContainer {
    int TYPE_REPLACE = 1;
    int TYPE_ADD = 2;

    /**
     * 返回可用于承载fragment的容器ID
     *
     * @return
     */
    @IdRes
    int getContainerId();

    /**
     * 是否基于fragment stack返回，即用户在按下物理返回键时是否回退到上一个fragment
     *
     * @return
     */
    boolean isBackByStack();

    void addFragmentWithoutAddToBackStack(@NonNull Fragment fragment);

    void addFragment(@NonNull Fragment fragment, boolean isAddToBackStack);

    void replaceFragmentWithoutAddToBackStack(@NonNull Fragment fragment);

    void replaceFragment(@NonNull Fragment fragment, boolean isAddToBackStack);


    /**
     * @param type             1--TYPE_REPLACE 2--TYPE_ADD
     * @param fragment
     * @param isAddToBackStack
     */
    void turnToFragment(int type, @NonNull Fragment fragment, boolean isAddToBackStack);

    void turnToFragment(int type, @NonNull Fragment fragment, @Nullable String tag, boolean isAddToBackStack);

    /**
     * 获得当前栈顶fragment,只适用于addToBackStack是指定的name和add/replace时指定的tag一致的情况
     *
     * @return
     */
    @Nullable
    Fragment getTopFragment();

    /**
     * 回退，fragment如果需要拦截处理返回事件，则需要实现BackPressListener接口
     */
    boolean popBackStack();

    /**
     * 用法示例：
     *
     * @return 返回true表示消费了此次物理返回事件
     * @Override public void onBackPressed() {
     * if(!onBackPressedDelegate()){
     * super.onBackPressed();
     * }
     * }
     */
    boolean onBackPressedDelegate();

    /**
     * 子fragment如果要处理物理返回事件，则子fragment要实现此接口
     * Activity在收到物理返回事件时，会优先将事件传递给实现了此接口的子fragment,由子fragment进行处理，
     * 如果子fragment不想处理此事件，则在onBackPressed中返回false,事件就会还给Activity，由Activity进行处理
     */
    interface BackPressListener {
        /**
         * @return 是否消费此事件，如果没消费，则返回事件会还给Activity进行处理
         */
        boolean onFragmentBackPressed();
    }
}
