package com.junmeng.android_java_example.mediaprojection;

import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Binder;
import android.os.Build;
import android.util.Log;
import android.view.Surface;

import androidx.annotation.RequiresApi;
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class MediaProjectionBinder extends Binder {
    private static final String TAG = "MediaProjectionBinder";
    private VirtualDisplay virtualDisplay;

    public MediaProjectionBinder(){

    }


    public void stop() {
        if(virtualDisplay!=null){
            virtualDisplay.release();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void start(MediaProjectionManager mediaProjectionManager, int resultCode, Intent data, Surface surface) {
        MediaProjection mediaProjection=mediaProjectionManager.getMediaProjection(resultCode,data);
        virtualDisplay = mediaProjection.createVirtualDisplay("demo",
                500, 500, 1,
                DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
                surface,
                new VirtualDisplay.Callback() {
                    @Override
                    public void onPaused() {
                        Log.i(TAG, "onPaused: ");
                    }

                    @Override
                    public void onResumed() {
                        Log.i(TAG, "onResumed: ");
                    }

                    @Override
                    public void onStopped() {
                        Log.i(TAG, "onStopped: ");
                    }
                }, null);
    }

}
