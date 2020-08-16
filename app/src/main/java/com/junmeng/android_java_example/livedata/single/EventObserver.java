package com.junmeng.android_java_example.livedata.single;

import androidx.lifecycle.Observer;

public class EventObserver<T> implements Observer<LiveDataEvent<T>> {

    private Listener<T> listener;

    public EventObserver(Listener<T> listener) {
        this.listener = listener;
    }

    @Override
    public void onChanged(LiveDataEvent<T> event) {
        if (event != null) {
            T content = event.getContentIfNotHandled();
            if (content != null) {
                listener.onEventUnhandledContent(content);
            }
        }
    }

    public interface Listener<T> {
        void onEventUnhandledContent(T t);
    }
}