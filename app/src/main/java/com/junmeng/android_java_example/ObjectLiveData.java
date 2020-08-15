package com.junmeng.android_java_example;

import androidx.lifecycle.MutableLiveData;
import java.util.Observable;
import java.util.Observer;

public class ObjectLiveData<T extends Observable> extends MutableLiveData<T> implements Observer {
    @Override
    public void setValue(T value) {
        super.setValue(value);
        value.addObserver(this);
    }

    @Override
    public void postValue(T value) {
        super.postValue(value);
        value.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        postValue(getValue());
    }

    @Override
    protected void onActive() {

    }

    @Override
    protected void onInactive() {
    }
}
