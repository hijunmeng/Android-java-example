package com.example.common.recycler;

/**
 * 每种数据类型对应的BindView基类
 * 用户只需继承此类并实现抽象方法即可
 *
 * @param <T> 与视图对应的实体类
 */
public abstract class BaseBindView2<T> extends BaseBindView<T> {

    public int getItemLayoutResId() {
        //此处空实现即可,子类不需要实现
        return 0;
    }

    /**
     * 获得布局资源id
     *
     * @param item
     * @return
     */
    public abstract int getItemLayoutResId(T item);
}