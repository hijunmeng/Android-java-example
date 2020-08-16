package com.junmeng.android_java_example;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.junmeng.android_java_example.gesture.listener.DragTouchListener;


public class GestureActivity extends AppCompatActivity {
    private static final String TAG = "GestureActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture);

        SurfaceView sv_one=findViewById(R.id.sv_one);
        ImageView iv_image=findViewById(R.id.iv_image);
        FrameLayout fl_video=findViewById(R.id.fl_video);
        RelativeLayout rl_root=findViewById(R.id.rl_root);
//        GestureViewBinder gestureVeiwBinder=GestureViewBinder.bind(this, rl_root, fl_video);
//        gestureVeiwBinder.setFullGroup(true);
        fl_video.setOnTouchListener(new DragTouchListener());
//        sv_one.setVisibility(View.GONE);
//        final GestureDetector gestureDetector=new GestureDetector(this, new GestureDetector.SimpleOnGestureListener(){
//
//            public boolean onSingleTapUp(MotionEvent e) {
//                Log.i(TAG, "onSingleTapUp: ");
//                return false;
//            }
//
//            public void onLongPress(MotionEvent e) {
//                Log.i(TAG, "onLongPress: ");
//            }
//
//            public boolean onScroll(MotionEvent e1, MotionEvent e2,
//                                    float distanceX, float distanceY) {
//                Log.i(TAG, "onScroll: distanceX="+distanceX+",distanceY="+distanceY);
//                return false;
//            }
//
//            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
//                                   float velocityY) {
//                Log.i(TAG, "onFling: velocityX="+velocityX+",velocityY="+velocityY);
//                return false;
//            }
//
//            public void onShowPress(MotionEvent e) {
//                Log.i(TAG, "onShowPress: ");
//            }
//
//            public boolean onDown(MotionEvent e) {
//                Log.i(TAG, "onDown: ");
//                return false;
//            }
//
//            public boolean onDoubleTap(MotionEvent e) {
//                Log.i(TAG, "onDoubleTap: ");
//                return false;
//            }
//
//            public boolean onDoubleTapEvent(MotionEvent e) {
//                Log.i(TAG, "onDoubleTapEvent: ");
//                return false;
//            }
//
//            public boolean onSingleTapConfirmed(MotionEvent e) {
//                Log.i(TAG, "onSingleTapConfirmed: ");
//                return false;
//            }
//
//            public boolean onContextClick(MotionEvent e) {
//                Log.i(TAG, "onContextClick: ");
//                return false;
//            }
//        });

        final GestureDetector gestureDetector=new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                Log.i(TAG, "onDown: ");
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                Log.i(TAG, "onShowPress: ");
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.i(TAG, "onSingleTapUp: ");
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Log.i(TAG, "onScroll: distanceX="+distanceX+",distanceY="+distanceY);
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.i(TAG, "onLongPress: ");
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Log.i(TAG, "onFling: velocityX="+velocityX+",velocityY="+velocityY);
                return false;
            }
        });
        gestureDetector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                Log.i(TAG, "onSingleTapConfirmed:");
                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Log.i(TAG, "onDoubleTap: ");
                return false;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                Log.i(TAG, "onDoubleTapEvent: ");
                return false;
            }
        });

        iv_image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });




//        GestureViewBinder.bind(this, rl_root, sv_one);
    }
}