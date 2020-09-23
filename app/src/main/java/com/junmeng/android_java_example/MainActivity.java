package com.junmeng.android_java_example;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.junmeng.android_java_example.anim.AnimListActivity;
import com.junmeng.android_java_example.common.BaseActivityDelegate;
import com.junmeng.android_java_example.frags.FragContainerActivity;
import com.junmeng.android_java_example.gesture.DragActivity;
import com.junmeng.android_java_example.gesture.GestureActivity;
import com.junmeng.android_java_example.livedata.LiveDataActivity;
import com.junmeng.android_java_example.recycler.RecyclerViewActivity;
import com.junmeng.android_java_example.round_layout.RoundLayoutActivity;
import com.junmeng.android_java_example.statusbar.StatusBarActivity;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MainActivity extends BaseActivityDelegate {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setBackgroundColor(Color.RED);
//        setStatusBarTransparent();

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> set=bluetoothAdapter.getBondedDevices();
        Iterator<BluetoothDevice> it=set.iterator();
        while(it.hasNext()){
            BluetoothDevice bd= it.next();
            Log.i(TAG,String.format("name=%s,bond=%d",bd.getName(),bd.getBondState()));
            if(bd.getBluetoothClass()!=null){
                Log.i(TAG,String.format("major=%d",bd.getBluetoothClass().getMajorDeviceClass()));
            }
        }
        int a2dp = bluetoothAdapter.getProfileConnectionState(BluetoothProfile.A2DP);
        int headset = bluetoothAdapter.getProfileConnectionState(BluetoothProfile.HEADSET);

        int flag = -1;
        if (a2dp == BluetoothProfile.STATE_CONNECTED) {
            flag = a2dp;
        } else if (headset == BluetoothProfile.STATE_CONNECTED) {
            flag = headset;
        }

        if (flag != -1) {
            bluetoothAdapter.getProfileProxy(MainActivity.this, new BluetoothProfile.ServiceListener() {

                @Override
                public void onServiceDisconnected(int profile) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onServiceConnected(int profile, BluetoothProfile proxy) {
                    // TODO Auto-generated method stub
                    List<BluetoothDevice> mDevices = proxy.getConnectedDevices();
                    if (mDevices != null && mDevices.size() > 0) {
                        for (BluetoothDevice device : mDevices) {
                            Log.i(TAG, "已连接的device name: " + device.getName());
                        }
                    } else {
                        Log.i(TAG, "mDevices is null");
                    }
                }
            }, flag);
        }

    }

    /**
     * 设置透明状态栏
     */
    private void setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

    }

    public void onClickFrags(View view) {
        gotoActivity(FragContainerActivity.class);
    }

    public void onClickLiveData(View view) {
        gotoActivity(LiveDataActivity.class);
    }

    public void onClickGesture(View view) {
        gotoActivity(GestureActivity.class);
    }

    public void onClickAnim(View view) {
        gotoActivity(AnimListActivity.class);
    }

    public void onClickRoundLayout(View view) {
        gotoActivity(RoundLayoutActivity.class);

    }

    public void onClickStatusBar(View view) {
        gotoActivity(StatusBarActivity.class);
    }

    public void onClickDrag(View view) {
        gotoActivity(DragActivity.class);
    }

    public void onClickRecycler(View view) {
        gotoActivity(RecyclerViewActivity.class);
    }
}