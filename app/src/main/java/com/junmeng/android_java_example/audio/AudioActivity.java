package com.junmeng.android_java_example.audio;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.common.base.BaseActivityDelegate;
import com.example.common.utils.AudioRoutingUtil;
import com.junmeng.android_java_example.R;
import com.plattysoft.leonids.ParticleSystem;

import java.util.List;

public class AudioActivity extends BaseActivityDelegate {
    private static final String TAG = "AudioActivity";

    List<BluetoothDevice> mDevices;

    private AudioStatusReceiver audioStatusReceiver;

    private TextView tvAudioRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        tvAudioRoute = findViewById(R.id.tv_audio_route);
        audioStatusReceiver = new AudioStatusReceiver();
        audioStatusReceiver.register(this);

        audioStatusReceiver.setCallback(new AudioStatusReceiver.Callback() {
            @Override
            public void onReceive(Intent intent, int status, Object data) {
                Log.i(TAG, "音频连接状态(有线5拔4插，蓝牙6连7断，sco1连2断，noisy3)：" + status);
                switch (status) {
                    case AudioStatusReceiver.AUDIO_ROUTE_STATUS_SCO_CONNECTED:
                        showDebugToast("蓝牙sco已连上");
                        break;
                    case AudioStatusReceiver.AUDIO_ROUTE_STATUS_SCO_DISCONNECTED:
                        showDebugToast("蓝牙sco已断开");
                        break;
                    case AudioStatusReceiver.AUDIO_ROUTE_STATUS_BECOMING_NOISY:
                        showDebugToast("音频BECOMING_NOISY");
                        break;
                    case AudioStatusReceiver.AUDIO_ROUTE_STATUS_HEADSET_PLUG_OUT:
                        showDebugToast("有线耳机拔出");
                        break;
                    case AudioStatusReceiver.AUDIO_ROUTE_STATUS_HEADSET_PLUG_IN:
                        showDebugToast("有线耳机插入");
                        break;
                    case AudioStatusReceiver.AUDIO_ROUTE_STATUS_BTHEADSET_CONNECTED:
                        if (data != null && data instanceof BluetoothDevice) {
                            BluetoothDevice device = (BluetoothDevice) data;
                            showDebugToast("已连接蓝牙耳机：" + device.getName());
                        } else {
                            showDebugToast("已连接蓝牙耳机");
                        }
                        break;
                    case AudioStatusReceiver.AUDIO_ROUTE_STATUS_BTHEADSET_DISCONNECTED:
                        if (data != null && data instanceof BluetoothDevice) {
                            BluetoothDevice device = (BluetoothDevice) data;
                            showDebugToast("已断开蓝牙耳机：" + device.getName());
                        } else {
                            showDebugToast("已断开蓝牙耳机");
                        }
                        break;
                    case AudioStatusReceiver.AUDIO_ROUTE_STATUS_BLUETOOTH_ON:
                        showDebugToast("蓝牙打开");
                        break;
                    case AudioStatusReceiver.AUDIO_ROUTE_STATUS_BLUETOOTH_OFF:
                        showDebugToast("蓝牙关闭");
                        break;
                    case AudioStatusReceiver.AUDIO_ROUTE_STATUS_STREAM_DEVICE_CHANGE:
                        if (data != null) {
                            int streamDevice = (int) data;
                            Log.i(TAG, "stream device(DEVICE_OUT_EARPIECE=1,DEVICE_OUT_SPEAKER=2,DEVICE_OUT_WIRED_HEADSET=4,DEVICE_OUT_WIRED_HEADPHONE=8,DEVICE_OUT_BLUETOOTH_SCO=16,DEVICE_OUT_BLUETOOTH_A2DP=128):" + streamDevice);
                            switch (streamDevice) {
                                case 1:
                                    tvAudioRoute.setText("听筒");
                                    break;
                                case 2:
                                    tvAudioRoute.setText("扬声器");
                                    break;
                                case 4:
                                    tvAudioRoute.setText("有线耳机(带麦克风)");
                                    break;
                                case 8:
                                    tvAudioRoute.setText("有线耳机(无麦克风)");
                                    break;
                                case 16:
                                    tvAudioRoute.setText("蓝牙sco");
                                    break;
                                case 128:
                                    tvAudioRoute.setText("蓝牙a2dp");
                                    break;
                            }
                        }
                        break;
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (audioStatusReceiver != null) {
            audioStatusReceiver.unregister(this);
        }
    }

    public void onClickReceiver(View view) {
        AudioUtils.toggleToEarpiece(this);
    }

    public void onClickSpeaker(View view) {
        AudioUtils.toggleToSpeaker(this);
    }

    public void onClickBTHeadset(View view) {
        AudioUtils.toggleToBluetooth(this);
    }

    public void onClickWiredHeadset(View view) {
        AudioUtils.toggleToWiredHeadset(this);
    }


    public void onClickHasWiredHeadset(View view) {
        showToast("有线耳机：" + AudioUtils.isWiredHeadsetOn(this));
    }

    public void onClickHasBTHeadset(View view) {
        showToast("蓝牙耳机：" + AudioUtils.isBluetoothHeadsetOn(this));
    }

    public void onClickShowBTHeadset(View view) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        int a2dp = bluetoothAdapter.getProfileConnectionState(BluetoothProfile.A2DP);
        int headset = bluetoothAdapter.getProfileConnectionState(BluetoothProfile.HEADSET);

        int flag = -1;
        if (a2dp == BluetoothProfile.STATE_CONNECTED) {
            flag = BluetoothProfile.A2DP;
        } else if (headset == BluetoothProfile.STATE_CONNECTED) {
            flag = BluetoothProfile.HEADSET;
        }

        if (flag != -1) {
            bluetoothAdapter.getProfileProxy(AudioActivity.this, new BluetoothProfile.ServiceListener() {

                @Override
                public void onServiceDisconnected(int profile) {
                    Log.i(TAG, "onServiceDisconnected:profile=" + profile);
                    showToast("onServiceDisconnected:profile=" + profile);
                }

                @Override
                public void onServiceConnected(int profile, BluetoothProfile proxy) {
                    Log.i(TAG, "onServiceConnected:profile=" + profile);
                    mDevices = proxy.getConnectedDevices();
                    if (mDevices != null && mDevices.size() > 0) {
                        for (BluetoothDevice device : mDevices) {
                            Log.i(TAG, "已连接的device name: " + device.getName());
                            showToast("已连接的device name: " + device.getName());
                        }
                    } else {
                        Log.i(TAG, "mDevices is null");
                    }
                }
            }, flag);
        }
    }

    public void onClickBluetoothHeadsetConnected(View view) {

        showToast("蓝牙耳机是否连接：" + AudioUtils.isBluetoothHeadsetConnected());

    }


    public void onClickGetDevicesForStream(View view) {
        int streamDevice = AudioRoutingUtil.getDevicesForStream(this, AudioManager.STREAM_MUSIC);
        switch (streamDevice) {
            case 1:
                showDebugToast("当前是听筒");
                break;
            case 2:
                showDebugToast("当前是扬声器");
                break;
            case 4:
            case 8:
                showDebugToast("当前是有线耳机");
                break;
            case 16:
            case 128:
                showDebugToast("当前是蓝牙耳机");
                break;
        }
    }
}