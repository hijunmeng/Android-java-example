package com.junmeng.android_java_example.sensor;

import android.os.Bundle;
import android.view.View;

import com.example.common.base.BaseActivityDelegate;
import com.example.common.sensor.ProximitySensorBinder;
import com.junmeng.android_java_example.R;


public class SensorTestActivity extends BaseActivityDelegate {

    ProximitySensorBinder proximitySensorBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_test);

    }


    public void onClickProximity(View view) {
        if (!view.isSelected()) {
            view.setSelected(true);
            showToast("开启,请靠近耳朵");
            proximitySensorBinder = new ProximitySensorBinder(this);
            proximitySensorBinder.setOnSensorChangedListener(new ProximitySensorBinder.OnSensorChangedListener() {
                @Override
                public void onSensorChanged(float distance, boolean isNear) {
                    showToast("distance=" + distance + ",near=" + isNear);
                }
            });
        } else {
            view.setSelected(false);
            showToast("关闭");
            proximitySensorBinder.release();
            proximitySensorBinder = null;
        }

    }
}