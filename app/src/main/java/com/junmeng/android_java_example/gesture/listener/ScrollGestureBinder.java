package com.junmeng.android_java_example.gesture.listener;

import android.content.Context;
import android.view.GestureDetector;

/**
 * Created by JarvisLau on 2018/5/30.
 * Description :
 */

class ScrollGestureBinder extends GestureDetector {

    ScrollGestureBinder(Context context, ScrollGestureListener scrollGestureListener) {
        super(context, scrollGestureListener);
    }

}