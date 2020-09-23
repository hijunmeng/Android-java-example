package com.junmeng.android_java_example.audio;

import android.content.Context;
import android.media.AudioManager;
import android.os.Build;

import static android.media.AudioManager.AUDIOFOCUS_GAIN;

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

    public static boolean isWiredHeadsetOn(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        return audioManager.isWiredHeadsetOn();
    }

    public static boolean isBluetoothA2dpOn(Context context) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        return audioManager.isBluetoothA2dpOn();
    }

    /**
     * context 传入的是MicroContext.getApplication()
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


}