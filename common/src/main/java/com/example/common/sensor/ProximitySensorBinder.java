package com.example.common.sensor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import java.lang.ref.SoftReference;

import static android.content.Context.POWER_SERVICE;
import static android.content.Context.SENSOR_SERVICE;

/**
 * 靠近传感器(靠近息屏，远离亮屏)
 * 使用时增加权限<uses-permission android:name="android.permission.WAKE_LOCK" />
 */
public class ProximitySensorBinder implements LifecycleObserver, SensorEventListener {
    private static final String TAG = "AudioSensorBinder";

    public interface OnSensorChangedListener {
        /**
         * @param distance 0.0  5.0
         * @param isNear   是否靠近
         */
        void onSensorChanged(float distance, boolean isNear);
    }

    private OnSensorChangedListener mOnSensorChangedListener;
    private final PowerManager mPowerManager;

    private SoftReference<Context> mContext;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private PowerManager.WakeLock mWakeLock;
    private float mDistance = -1.0f;

    public ProximitySensorBinder(@NonNull Context context) {
        mContext = new SoftReference<>(context);

        if (context instanceof LifecycleOwner) {
            ((LifecycleOwner) context).getLifecycle().addObserver(this);
        }
        mPowerManager = (PowerManager) context.getSystemService(POWER_SERVICE);
        registerProximitySensorListener();
    }

    /**
     * 在不使用时及时释放资源
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void release() {
        try {
            mOnSensorChangedListener = null;
            mSensorManager.unregisterListener(this);
            mSensorManager = null;

            if (mWakeLock != null && mWakeLock.isHeld()) {
                mWakeLock.release();
            }
            mWakeLock = null;
            mContext.clear();
        } catch (Exception ignore) {
        }

    }

    public void setOnSensorChangedListener(OnSensorChangedListener listener) {
        mOnSensorChangedListener = listener;
    }


    /**
     * 注册距离感应器监听器，监测用户是否靠近手机听筒
     */
    private void registerProximitySensorListener() {
        if (getContext() == null) {
            return;
        }
        mSensorManager = (SensorManager) getContext().getSystemService(SENSOR_SERVICE);
        if (mSensorManager == null) {
            return;
        }
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Nullable
    private Context getContext() {
        if (mContext != null) {
            return mContext.get();
        }
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (mOnSensorChangedListener == null) {
            return;
        }
        float distance = event.values[0];//靠近返回0.0，远离返回5.0
        Log.i(TAG, "distance=" + distance);
        //在某些机型上可能会一直回调onSensorChanged，因此有必要过滤一下
        if (distance == mDistance) {
            return;
        }
        mDistance = distance;


        if (mDistance >= mSensor.getMaximumRange()) {
            setScreenOn();
            mOnSensorChangedListener.onSensorChanged(distance, false);

        } else {
            setScreenOff();
            mOnSensorChangedListener.onSensorChanged(distance, true);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    /**
     * 息屏
     */
    @SuppressLint("InvalidWakeLockTag")
    private void setScreenOff() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Log.i(TAG, "setScreenOff: 熄灭屏幕");
            if (mWakeLock == null) {
                mWakeLock = mPowerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, TAG);
            }
            mWakeLock.acquire(10 * 60 * 1000L /*10 minutes*/);
        }
    }

    /**
     * 亮屏
     */
    private void setScreenOn() {
        if (mWakeLock != null) {
            mWakeLock.setReferenceCounted(false);
            mWakeLock.release();
            mWakeLock = null;
        }
    }

}