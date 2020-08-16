package com.junmeng.android_java_example.livedata.single;

import androidx.lifecycle.LiveData;

public class EventLiveData<T> extends LiveData<LiveDataEvent<T>> {

    protected void setValue(LiveDataEvent<T> value) {
        super.setValue(value);
    }

    protected void postValue(LiveDataEvent<T> value) {
        super.postValue(value);
    }

    public void setValueX(T value) {
        this.setValue(new LiveDataEvent<T>(value));
    }

    public void postValueX(T value) {
        this.postValue(new LiveDataEvent<T>(value));
    }
}