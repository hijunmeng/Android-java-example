package com.example.common.utils;

import android.content.Context;
import android.media.AudioManager;

import java.lang.reflect.Method;

/**
 * [Android Audio 音频输出通道切换 - 简书](https://www.jianshu.com/p/e7eb04ab4485)
 * 音频路由查询工具
 * 此工具类原理是通过反射使用了android的隐藏api,目前在android10测试正常，其他版本未验证
 */
public class AudioRoutingUtil {
    /**
     * 获得指定流类型的输出设备
     *
     * @param context
     * @param streamType 见AudioManager.STREAM_
     * @return -1表示失败，其他见AudioManager.DEVICE_OUT_
     */
    public static int getDevicesForStream(Context context, int streamType) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        try {
            Class ownerClass = audioManager.getClass();
            //public int getDevicesForStream(int streamType);
            Method method = ownerClass.getMethod("getDevicesForStream", int.class);
            return (int) method.invoke(audioManager, streamType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 指定流类型是否正通过听筒输出
     *
     * @param context
     * @param streamType
     * @return
     */
    public static boolean isEarpiece(Context context, int streamType) {
        int deviceStream = getDevicesForStream(context, streamType);
        if (deviceStream == 1) {//AudioManager.DEVICE_OUT_EARPIECE
            return true;
        }
        return false;
    }

    /**
     * 指定流类型是否正通过扬声器输出
     *
     * @param context
     * @param streamType
     * @return
     */
    public static boolean isSpeaker(Context context, int streamType) {
        int deviceStream = getDevicesForStream(context, streamType);
        if (deviceStream == 2) {//AudioManager.DEVICE_OUT_SPEAKER
            return true;
        }
        return false;
    }

    /**
     * 指定流类型是否正通过有线耳机输出
     *
     * @param context
     * @param streamType
     * @return
     */
    public static boolean isWiredHeadset(Context context, int streamType) {
        int deviceStream = getDevicesForStream(context, streamType);
        if (deviceStream == 4 || deviceStream == 8) {//AudioManager.DEVICE_OUT_WIRED_HEADSET AudioManager.DEVICE_OUT_WIRED_HEADPHONE
            return true;
        }
        return false;
    }

    /**
     * 指定流类型是否正通过蓝牙耳机输出
     *
     * @param context
     * @param streamType
     * @return
     */
    public static boolean isBluetoothHeadset(Context context, int streamType) {
        int deviceStream = getDevicesForStream(context, streamType);
        if (deviceStream == 16 || deviceStream == 128) { //AudioManager.DEVICE_OUT_BLUETOOTH_SCO AudioManager.DEVICE_OUT_BLUETOOTH_A2DP
            return true;
        }
        return false;
    }


}
