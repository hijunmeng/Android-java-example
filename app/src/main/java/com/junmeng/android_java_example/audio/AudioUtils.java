package com.junmeng.android_java_example.audio;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.MediaRouter;
import android.os.Build;

import static android.media.AudioManager.AUDIOFOCUS_GAIN;
import static android.media.AudioManager.GET_DEVICES_OUTPUTS;

/**
 * headset 指带有麦克风的头戴式耳机；
 * headphone 指头戴式耳机，无麦克风；
 * earphone 也指耳机，非头戴式耳机
 * <uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS"/>
 * <uses-permission android:name="android.permission.BLUETOOTH" />
 */
public class AudioUtils {
    private static int lastModel = -10;

    /**
     * 音频外放
     */
    public static void changeToSpeaker(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        //注意此处，蓝牙未断开时使用MODE_IN_COMMUNICATION而不是MODE_NORMAL
        audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
        audioManager.stopBluetoothSco();
        audioManager.setBluetoothScoOn(false);
        audioManager.setSpeakerphoneOn(true);
    }

    /**
     * 切换到蓝牙音箱
     */
    public static void changeToHeadset(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
        audioManager.startBluetoothSco();
        audioManager.setBluetoothScoOn(true);
        audioManager.setSpeakerphoneOn(false);
    }

    /**
     * 切换到听筒
     */
    public static void changeToReceiver(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setSpeakerphoneOn(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
        } else {
            audioManager.setMode(AudioManager.MODE_IN_CALL);
        }
    }


    public static void dispose(Context context, AudioManager.OnAudioFocusChangeListener focusRequest) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setMode(lastModel);
        if (audioManager.isBluetoothScoOn()) {
            audioManager.setBluetoothScoOn(false);
            audioManager.stopBluetoothSco();
        }
        audioManager.unloadSoundEffects();
        if (null != focusRequest) {
            audioManager.abandonAudioFocus(focusRequest);
        }
    }


    public static void getModel(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        lastModel = audioManager.getMode();
    }

    public static void changeToNomal(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setMode(AudioManager.MODE_NORMAL);
    }

//    public static boolean isWiredHeadsetOn(Context context) {
//        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
//        return audioManager.isWiredHeadsetOn();
//    }

    public static boolean isBluetoothA2dpOn(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        return audioManager.isBluetoothA2dpOn();
    }

    /**
     * context 传入的是MicroContext.getApplication()
     *
     * @param context
     */
    public static void choiceAudioModel(Context context) {
        if (isWiredHeadsetOn(context)) {
            changeToReceiver(context);
        } else if (isBluetoothA2dpOn(context)) {
            changeToHeadset(context);
        } else {
            changeToSpeaker(context);
        }
    }

    public static void pauseMusic(Context context, AudioManager.OnAudioFocusChangeListener focusRequest) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.requestAudioFocus(focusRequest, AudioManager.STREAM_MUSIC, AUDIOFOCUS_GAIN);
    }


    /**
     * 切换到蓝牙耳机
     *
     * @param context
     */
    public static void toggleToBluetooth(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
//        audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
//        audioManager.startBluetoothSco();
//        audioManager.setBluetoothScoOn(true);
//        audioManager.setSpeakerphoneOn(false);
        audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
        audioManager.setSpeakerphoneOn(false);
        audioManager.setBluetoothScoOn(true);

    }

    /**
     * 切换到有线耳机
     *
     * @param context
     */
    public static void toggleToWiredHeadset(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
//        audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
//        if (audioManager.isBluetoothScoOn()) {
//            audioManager.setBluetoothScoOn(false);
//            audioManager.stopBluetoothSco();
//        }
//        audioManager.setSpeakerphoneOn(false);
        audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
        audioManager.setSpeakerphoneOn(false);
        audioManager.setBluetoothScoOn(false);
    }

    /**
     * 切换到外放
     *
     * @param context
     */
    public static void toggleToSpeaker(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

//        audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
//        if (audioManager.isBluetoothScoOn()) {
//            audioManager.setBluetoothScoOn(false);
//            audioManager.stopBluetoothSco();
//        }
//        audioManager.setSpeakerphoneOn(true);
        audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
        audioManager.setSpeakerphoneOn(true);
        audioManager.setBluetoothScoOn(false);
    }

    /**
     * 切换到听筒
     * 在有有线耳机和蓝牙耳机或只有有线耳机的情况下，调用此方法会切换到有线耳机
     * 在只有蓝牙耳机的情况下，调用此方法能正常切换到听筒
     */
    public static void toggleToEarpiece(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
//        } else {
//            audioManager.setMode(AudioManager.MODE_IN_CALL);
//        }
//        audioManager.setSpeakerphoneOn(false);
//        audioManager.setBluetoothScoOn(false);

        audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
        audioManager.setSpeakerphoneOn(false);
        audioManager.setBluetoothScoOn(false);
    }

    /**
     * 判断是否有有线耳机连接着
     *
     * @param context
     * @return
     */
    public static boolean isWiredHeadsetOn(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            AudioDeviceInfo[] ads = audioManager.getDevices(GET_DEVICES_OUTPUTS);
            if (ads == null) {
                return false;
            }
            for (AudioDeviceInfo it : ads) {
                if (it.getType() == AudioDeviceInfo.TYPE_WIRED_HEADSET //3 耳麦（耳机+麦）
                        || it.getType() == AudioDeviceInfo.TYPE_WIRED_HEADPHONES//4 耳机（不带麦）
                ) {
                    return true;
                }
            }
            return false;
        } else {
            return audioManager.isWiredHeadsetOn();
        }
    }

    /**
     * 判断是否有蓝牙耳机
     *
     * @param context
     * @return
     */
    public static boolean isBluetoothHeadsetOn(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            AudioDeviceInfo[] ads = audioManager.getDevices(GET_DEVICES_OUTPUTS);
            if (ads == null) {
                return false;
            }
            for (AudioDeviceInfo it : ads) {
                if (it.getType() == AudioDeviceInfo.TYPE_BLUETOOTH_SCO || it.getType() == AudioDeviceInfo.TYPE_BLUETOOTH_A2DP) {
                    return true;
                }
            }
            return false;
        } else {
            return audioManager.isBluetoothA2dpOn() || audioManager.isBluetoothScoOn();
        }
    }

    /**
     * 判断是否有蓝牙耳机
     * 与isBluetoothHeadsetOn的区别是此方法是通过BluetoothAdapter进行判断，需要声明蓝牙权限
     * <uses-permission android:name="android.permission.BLUETOOTH" />
     *
     * @return
     */
    public static boolean isBluetoothHeadsetConnected() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            return false;
        }
        int a2dp = bluetoothAdapter.getProfileConnectionState(BluetoothProfile.A2DP);
        int headset = bluetoothAdapter.getProfileConnectionState(BluetoothProfile.HEADSET);

        if (a2dp == BluetoothProfile.STATE_CONNECTED || headset == BluetoothProfile.STATE_CONNECTED) {
            return true;
        }
        return false;


    }

    /**
     * 获得当前选择的音频路由
     * 经测试此接口返回并不准确，勿用
     *
     * @param context
     * @return
     */
    @Deprecated
    public static MediaRouter.RouteInfo getSelectedRoute(Context context) {
        final MediaRouter mr = (MediaRouter) context.getSystemService(Context.MEDIA_ROUTER_SERVICE);
        final MediaRouter.RouteInfo ri = mr.getSelectedRoute(MediaRouter.ROUTE_TYPE_LIVE_AUDIO);
        return ri;
    }

    public static AudioManager getAudioManager(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        return audioManager;
    }


}