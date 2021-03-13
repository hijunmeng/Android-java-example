package com.example.common.sharemodel;

/**
 * 用户可以继承此类存储自己的业务数据
 */
public abstract class ShareModel {
    /**
     * 当ShareModel引用个数变为0时会自动调用此清理方法
     * 用户可以在此回调中清理业务资源
     */
    public abstract void onCleared();
}