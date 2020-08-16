package com.junmeng.android_java_example.livedata;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.junmeng.android_java_example.livedata.single.EventLiveData;
import com.junmeng.android_java_example.livedata.single.LiveDataEvent;
import com.junmeng.android_java_example.livedata.single.SingleLiveEvent;

public class LiveDataViewModel extends AndroidViewModel {

    public MutableLiveData<String> toastLiveData = new MutableLiveData<>();

    //将LiveData当做事件的用法1
    public MutableLiveData<LiveDataEvent<String>> toast2LiveData = new MutableLiveData<>();

    //将LiveData当做事件的用法2
    public EventLiveData<String> toast3LiveData = new EventLiveData<>();

    //将LiveData当做事件的用法3
    public SingleLiveEvent<String> toast4LiveData=new SingleLiveEvent<>();

    public LiveDataViewModel(@NonNull Application application) {
        super(application);
    }

    public void onClickToast() {
        toastLiveData.setValue("MutableLiveData");
        toast2LiveData.setValue(new LiveDataEvent("LiveDataEvent"));
        toast3LiveData.setValueX("EventLiveData");
        toast4LiveData.setValue("SingleLiveEvent");
    }


}
