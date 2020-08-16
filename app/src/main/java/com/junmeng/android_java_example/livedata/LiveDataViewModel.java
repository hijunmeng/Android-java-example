package com.junmeng.android_java_example.livedata;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.junmeng.android_java_example.livedata.object.ObjectLiveData;
import com.junmeng.android_java_example.livedata.object.TestObject;
import com.junmeng.android_java_example.livedata.single.EventLiveData;
import com.junmeng.android_java_example.livedata.single.LiveDataEvent;
import com.junmeng.android_java_example.livedata.single.SingleLiveEvent;

public class LiveDataViewModel extends AndroidViewModel {

    //原始的LiveData
    public MutableLiveData<String> toastLiveData = new MutableLiveData<>();

    //将LiveData当做事件的用法1
    public MutableLiveData<LiveDataEvent<String>> toast2LiveData = new MutableLiveData<>();

    //将LiveData当做事件的用法2
    public EventLiveData<String> toast3LiveData = new EventLiveData<>();

    //将LiveData当做事件的用法3
    public SingleLiveEvent<String> toast4LiveData=new SingleLiveEvent<>();

    public ObjectLiveData<TestObject> objectLiveData=new ObjectLiveData<>();

    public LiveDataViewModel(@NonNull Application application) {
        super(application);
        objectLiveData.setValue(new TestObject("xiaoming",18));
    }

    public void onClickToast() {
        toastLiveData.setValue("MutableLiveData");
        toast2LiveData.setValue(new LiveDataEvent("LiveDataEvent"));
        toast3LiveData.setValueX("EventLiveData");
        toast4LiveData.setValue("SingleLiveEvent");
        objectLiveData.getValue().setName("xiaohong");
        objectLiveData.getValue().setAge(18);
    }


}
