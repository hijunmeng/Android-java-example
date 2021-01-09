package com.example.common.recycler;

import androidx.annotation.NonNull;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 每种数据类型对应的BindView基类
 * 用户只需继承此类并实现抽象方法即可
 *
 * @param <T> 与视图对应的实体类
 */
public abstract class BaseBindView<T> {
    protected Type mType; //存放泛型T的实际类型

    public BaseBindView() {
        mType = getSuperclassTypeParameter(getClass());
    }

    /**
     * 获得布局资源id
     *
     * @return
     */
    public abstract int getItemLayoutResId();

    /**
     * 数据与视图绑定
     * 用户可以在这里将数据设置给具体的视图对象
     *
     * @param holder
     * @param position
     * @param item
     * @param payloads 要size为0,表示全局刷新，如果不为0,则表示局部刷新
     */
    public abstract void bindViewData(RecyclerViewHolder holder, int position, T item, @NonNull List<Object> payloads);

    public Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return parameterized.getActualTypeArguments()[0];
    }
}