package com.example.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectUtil {
    /**
     * 反射调用某个对象的方法
     * 不适用于方法参数是基本类型的，如int,long等
     *
     * @param methodObject 要反射的对象
     * @param methodName   要反射的对象的方法名，如反射XXX.bbb()，则填写bbb方法名即可
     * @param args         方法参数
     * @return 方法执行后的返回值
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object invokeMethod(Object methodObject, String methodName, Object... args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class ownerClass = methodObject.getClass();
        Class[] argsClass = null;
        if (args != null) {
            argsClass = new Class[args.length];
            for (int i = 0, j = args.length; i < j; i++) {
                argsClass[i] = args[i].getClass();
            }
        }
        Method method = ownerClass.getMethod(methodName, argsClass);
        return method.invoke(methodObject, args);
    }
}
