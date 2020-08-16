package com.junmeng.android_java_example.livedata.single;

public class LiveDataEvent<T> {

    private T content;

    public LiveDataEvent(T content) {
        this.content = content;
    }

    private boolean hasBeenHandled = false;

    public T getContentIfNotHandled() {
        if (hasBeenHandled) {
            return null;
        }
        hasBeenHandled = true;
        return content;
    }

    public T peekContent() {
        return content;
    }

}