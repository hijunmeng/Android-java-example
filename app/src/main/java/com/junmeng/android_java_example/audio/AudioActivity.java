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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.common.base.BaseActivityDelegate;
import com.junmeng.android_java_example.R;

import java.util.List;

public class AudioActivity extends BaseActivityDelegate {
    private static final String TAG = "AudioActivity";

    List<BluetoothDevice> mDevices;

    private TextView tvAudioRoute;
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case AudioManager.ACTION_SCO_AUDIO_STATE_UPDATED:
                    int state = intent.getIntExtra(AudioManager.EXTRA_SCO_AUDIO_STATE, -100);
                    Log.i(TAG, "EXTRA_SCO_AUDIO_STATE:" + state);
                    if (state == AudioManager.SCO_AUDIO_STATE_CONNECTED) {
//                        AudioUtils.toggleToBTHeadset(getApplicationContext());
                        Toast.makeText(context, "音频路由已切换到蓝牙耳机", Toast.LENGTH_LONG).show();
                        tvAudioRoute.setText("蓝牙耳机");
                    } else if (state == AudioManager.SCO_AUDIO_STATE_DISCONNECTED) {
//                        Toast.makeText(context, "音频路由已从蓝牙耳机切换到其他", Toast.LENGTH_LONG).show();
//                        if (AudioUtils.isWiredHeadsetOn(getApplicationContext())) {
//                            Toast.makeText(context, "音频路由已从蓝牙耳机切换到有线耳机", Toast.LENGTH_LONG).show();
//                            tvAudioRoute.setText("有线耳机");
//                        }
                    }
                    break;
                case AudioManager.ACTION_SPEAKERPHONE_STATE_CHANGED:
                    if (AudioUtils.getAudioManager(AudioActivity.this).isSpeakerphoneOn()) {
                        Toast.makeText(context, "音频路由已切换到扬声器", Toast.LENGTH_LONG).show();
                        tvAudioRoute.setText("扬声器");
                    } else if(AudioUtils.isWiredHeadsetOn(getApplicationContext())){
                        Toast.makeText(context, "音频路由已切换到有线耳机", Toast.LENGTH_LONG).show();
                        tvAudioRoute.setText("有线耳机");
                    }else{
                        Toast.makeText(context, "音频路由已切换到听筒", Toast.LENGTH_LONG).show();
                        tvAudioRoute.setText("听筒");
                    }
                    break;

                case AudioManager.ACTION_AUDIO_BECOMING_NOISY:
                    Toast.makeText(context, "ACTION_AUDIO_BECOMING_NOISY音频路由已切换到扬声器", Toast.LENGTH_LONG).show();
                    tvAudioRoute.setText("扬声器");
                    break;
                case Intent.ACTION_HEADSET_PLUG:
                    //state —— 0 表示耳机不在位，1表示在位；
                    //name —— 耳机类型；
                    //microphone —— 1 表示耳机有麦克风，0 表示没有
                    if (intent.hasExtra("state")) {
                        if (intent.getIntExtra("state", 0) == 0) {
                            Toast.makeText(context, "有线耳机拔出", Toast.LENGTH_LONG).show();
                            if (AudioUtils.isBluetoothHeadsetOn(getApplicationContext())) {
                                tvAudioRoute.setText("蓝牙耳机");
                            }
                        } else if (intent.getIntExtra("state", 0) == 1) {
//                            Toast.makeText(context, "有线耳机插上", Toast.LENGTH_LONG).show();
//                            AudioUtils.toggleToWiredHeadset(getApplicationContext());
                            Toast.makeText(context, "音频路由已切换到有线耳机", Toast.LENGTH_LONG).show();
                            tvAudioRoute.setText("有线耳机");


                        }
                    }
                    break;
                case BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED:
                case BluetoothHeadset.ACTION_AUDIO_STATE_CHANGED:
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    int state1 = intent.getIntExtra(BluetoothProfile.EXTRA_STATE, -1);
                    Log.e(TAG, "HEADSET STATE -> " + state1);
                    if (state1 == BluetoothProfile.STATE_CONNECTED) {
                        if (device == null) {
                            return;
                        }
                        Toast.makeText(context, "已连接蓝牙耳机：" + device.getName(), Toast.LENGTH_LONG).show();

                    } else if (state1 == BluetoothProfile.STATE_DISCONNECTED) {
                        if (device != null) {
                            Toast.makeText(context, "已断开蓝牙耳机:" + device.getName(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(context, "已断开蓝牙耳机", Toast.LENGTH_LONG).show();
                        }

                    }
                    break;
                case BluetoothAdapter.ACTION_STATE_CHANGED:
                    int state2 = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
                    if(state2==BluetoothAdapter.STATE_ON){
                        showToast("蓝牙打开");
                    }else if(state2==BluetoothAdapter.STATE_OFF){
                        showToast("蓝牙关闭");
                        if(AudioUtils.isWiredHeadsetOn(getApplicationContext())){
                            Toast.makeText(context, "音频路由已切换到有线耳机", Toast.LENGTH_LONG).show();
                            tvAudioRoute.setText("有线耳机");
                        }
                    }
                    break;

            }

        }


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        tvAudioRoute = findViewById(R.id.tv_audio_route);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(AudioManager.ACTION_HEADSET_PLUG);//监听有线耳机插拔
        intentFilter.addAction(BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED);//监听蓝牙耳机是否连接
        intentFilter.addAction(BluetoothHeadset.ACTION_AUDIO_STATE_CHANGED);//监听蓝牙耳机是否连接
        intentFilter.addAction(AudioManager.ACTION_SCO_AUDIO_STATE_UPDATED);//监听蓝牙耳机是否连接
        intentFilter.addAction(AudioManager.ACTION_SCO_AUDIO_STATE_UPDATED);//监听蓝牙耳机是否连接
        intentFilter.addAction(AudioManager.ACTION_SPEAKERPHONE_STATE_CHANGED);//监听蓝牙耳机是否连接
        intentFilter.addAction(AudioManager.ACTION_AUDIO_BECOMING_NOISY);//监听蓝牙耳机是否连接
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);//监听蓝牙耳机是否连接

        registerReceiver(mReceiver, intentFilter);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
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


}