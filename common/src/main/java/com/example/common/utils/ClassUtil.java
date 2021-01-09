package com.example.common.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ClassUtil {
    /**
     * 获得类的实际泛型对象类型（单个泛型的情况）
     * 比如你想获得如下XXClass的泛型T的具体类型
     * public class XXClass<T>｛｝
     *
     *
     * @param subclass
     * @return
     */
    public static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return parameterized.getActualTypeArguments()[0];
    }
}
