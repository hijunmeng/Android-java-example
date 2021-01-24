package com.junmeng.android_java_example.audio;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.util.Log;

import androidx.annotation.IntDef;

/**
 * 需要蓝牙权限
 */
public class AudioRouteReceiver extends BroadcastReceiver {

    private static final String TAG = "AudioRouteReceiver";

    ///摘自android.media.AudioManager#STREAM_DEVICES_CHANGED_ACTION
    public static final String STREAM_DEVICES_CHANGED_ACTION = "android.media.STREAM_DEVICES_CHANGED_ACTION";
    //The stream type for the volume changed intent.
    public static final String EXTRA_VOLUME_STREAM_TYPE = "android.media.EXTRA_VOLUME_STREAM_TYPE";
    //The devices associated with the stream for the stream devices changed intent.
    public static final String EXTRA_VOLUME_STREAM_DEVICES = "android.media.EXTRA_VOLUME_STREAM_DEVICES";
    //The previous volume associated with the stream for the volume changed intent.
    public static final String EXTRA_PREV_VOLUME_STREAM_DEVICES = "android.media.EXTRA_PREV_VOLUME_STREAM_DEVICES";


    public static final int AUDIO_ROUTE_STATUS_SCO_CONNECTED = 1;
    public static final int AUDIO_ROUTE_STATUS_SCO_DISCONNECTED = 2;
    public static final int AUDIO_ROUTE_STATUS_BECOMING_NOISY = 3;
    public static final int AUDIO_ROUTE_STATUS_HEADSET_PLUG_IN = 4;
    public static final int AUDIO_ROUTE_STATUS_HEADSET_PLUG_OUT = 5;
    public static final int AUDIO_ROUTE_STATUS_BTHEADSET_CONNECTED = 6;
    public static final int AUDIO_ROUTE_STATUS_BTHEADSET_DISCONNECTED = 7;
    public static final int AUDIO_ROUTE_STATUS_BLUETOOTH_ON = 8;
    public static final int AUDIO_ROUTE_STATUS_BLUETOOTH_OFF = 9;
    public static final int AUDIO_ROUTE_STATUS_STREAM_DEVICE_CHANGE = 10; //音频路由发生变化

    @IntDef({AUDIO_ROUTE_STATUS_SCO_CONNECTED, AUDIO_ROUTE_STATUS_SCO_DISCONNECTED, AUDIO_ROUTE_STATUS_BECOMING_NOISY,
            AUDIO_ROUTE_STATUS_HEADSET_PLUG_IN, AUDIO_ROUTE_STATUS_HEADSET_PLUG_OUT, AUDIO_ROUTE_STATUS_BTHEADSET_CONNECTED
            , AUDIO_ROUTE_STATUS_BTHEADSET_DISCONNECTED, AUDIO_ROUTE_STATUS_BLUETOOTH_ON, AUDIO_ROUTE_STATUS_BLUETOOTH_OFF, AUDIO_ROUTE_STATUS_STREAM_DEVICE_CHANGE})
    public @interface AudioRouteStatus {

    }

    public interface Callback {

        void onReceive(Intent intent, @AudioRouteStatus int status, Object data);
    }

    private Callback mCallback;

    private int currentStreamDevice = -1;

    public void setCallback(Callback cb) {
        mCallback = cb;
    }

    public void register(Context context) {
        if (context != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(AudioManager.ACTION_HEADSET_PLUG);//监听有线耳机插拔
            intentFilter.addAction(BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED);
            intentFilter.addAction(BluetoothHeadset.ACTION_AUDIO_STATE_CHANGED);
            intentFilter.addAction(AudioManager.ACTION_SCO_AUDIO_STATE_UPDATED);//监听蓝牙耳机是否连接
            intentFilter.addAction(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
            intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
            intentFilter.addAction(STREAM_DEVICES_CHANGED_ACTION);
            context.registerReceiver(this, intentFilter);
        }
    }

    public void unregister(Context context) {
        if (context != null) {
            context.unregisterReceiver(this);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (mCallback == null) {
            return;
        }
        String action = intent.getAction();
        switch (action) {
            case AudioManager.ACTION_SCO_AUDIO_STATE_UPDATED:
                int state = intent.getIntExtra(AudioManager.EXTRA_SCO_AUDIO_STATE, -100);
                if (state == AudioManager.SCO_AUDIO_STATE_CONNECTED) {
                    mCallback.onReceive(intent, AUDIO_ROUTE_STATUS_SCO_CONNECTED, null);
                } else if (state == AudioManager.SCO_AUDIO_STATE_DISCONNECTED) {
                    mCallback.onReceive(intent, AUDIO_ROUTE_STATUS_SCO_DISCONNECTED, null);
                }
                break;
            case AudioManager.ACTION_AUDIO_BECOMING_NOISY:
                mCallback.onReceive(intent, AUDIO_ROUTE_STATUS_BECOMING_NOISY, null);
                break;

            case Intent.ACTION_HEADSET_PLUG:
                //state —— 0 表示耳机不在位，1表示在位；
                //name —— 耳机类型；
                //microphone —— 1 表示耳机有麦克风，0 表示没有
                if (intent.hasExtra("state")) {
                    if (intent.getIntExtra("state", 0) == 0) {
                        mCallback.onReceive(intent, AUDIO_ROUTE_STATUS_HEADSET_PLUG_OUT, null);
                    } else if (intent.getIntExtra("state", 0) == 1) {
                        mCallback.onReceive(intent, AUDIO_ROUTE_STATUS_HEADSET_PLUG_IN, null);
                    }
                }
                break;
            case BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED:
            case BluetoothHeadset.ACTION_AUDIO_STATE_CHANGED:
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                int state1 = intent.getIntExtra(BluetoothProfile.EXTRA_STATE, -1);
                if (state1 == BluetoothProfile.STATE_CONNECTED) {
                    mCallback.onReceive(intent, AUDIO_ROUTE_STATUS_BTHEADSET_CONNECTED, device);

                } else if (state1 == BluetoothProfile.STATE_DISCONNECTED) {
                    mCallback.onReceive(intent, AUDIO_ROUTE_STATUS_BTHEADSET_DISCONNECTED, device);
                }
                break;
            case BluetoothAdapter.ACTION_STATE_CHANGED:
                int state2 = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
                if (state2 == BluetoothAdapter.STATE_ON) {
                    mCallback.onReceive(intent, AUDIO_ROUTE_STATUS_BLUETOOTH_ON, null);
                } else if (state2 == BluetoothAdapter.STATE_OFF) {
                    mCallback.onReceive(intent, AUDIO_ROUTE_STATUS_BLUETOOTH_OFF, null);
                }
                break;

            case STREAM_DEVICES_CHANGED_ACTION://经测试，此广播并不靠谱，而且存在多次反复回调的问题
                int streamType = intent.getIntExtra(EXTRA_VOLUME_STREAM_TYPE, -1);//STREAM_
                int streamDevice = intent.getIntExtra(EXTRA_VOLUME_STREAM_DEVICES, -1);//见DEVICE_OUT_
                int preStreamDevice = intent.getIntExtra(EXTRA_PREV_VOLUME_STREAM_DEVICES, -1);//见DEVICE_OUT_

                Log.i(TAG, "stream type(STREAM_VOICE_CALL=0,STREAM_MUSIC=3,STREAM_BLUETOOTH_SCO=6,STREAM_ACCESSIBILITY=10):" + streamType);
                Log.i(TAG, "stream device(DEVICE_OUT_EARPIECE=1,DEVICE_OUT_SPEAKER=2,DEVICE_OUT_WIRED_HEADSET=4,DEVICE_OUT_WIRED_HEADPHONE=8,DEVICE_OUT_BLUETOOTH_SCO=16,DEVICE_OUT_BLUETOOTH_A2DP=128):" + streamDevice);
                Log.i(TAG, "pre stream device:" + preStreamDevice);

                if (currentStreamDevice != streamDevice) {
                    currentStreamDevice = streamDevice;
                    mCallback.onReceive(intent, AUDIO_ROUTE_STATUS_STREAM_DEVICE_CHANGE, currentStreamDevice);
                }
                break;
        }
    }
}