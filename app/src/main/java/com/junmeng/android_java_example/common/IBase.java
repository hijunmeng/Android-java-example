package com.junmeng.android_java_example.common;

public interface IBase {
    /**
     * 显示系统toast
     * @param text
     */
    void showToast(String text);

    /**
     * 显示调试toast,在release版中不会出现
     * @param text
     */
    void showDebugToast(String text);

    /**
     * 在当前线程睡眠指定毫秒数
     * @param ms
     */
    void sleep(int ms);
}
