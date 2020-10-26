package com.junmeng.android_java_example.mediaprojection;

import android.media.Image;
import android.os.Binder;
import android.os.Build;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class MediaProjectionBinder extends Binder {

    public interface DataCallback {
        void onData(Image image);
    }

    private static final String TAG = "MediaProjectionBinder";

    private DataCallback dataCallback;

    public MediaProjectionBinder() {

    }

    public void setDataCallback(DataCallback dataCallback) {
        this.dataCallback = dataCallback;
    }

    public DataCallback getDataCallback() {
        return dataCallback;
    }


}
