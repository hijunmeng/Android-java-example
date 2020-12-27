package com.junmeng.android_java_example.media;

import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.common.base.BaseActivityDelegate;
import com.junmeng.android_java_example.databinding.ActivityMediaTestBinding;

/**
 * https://developer.android.google.cn/guide/topics/media/media-codecs
 */
public class MediaTestActivity extends BaseActivityDelegate {
    private static final String TAG = "MediaTestActivity";
    ActivityMediaTestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMediaTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }


//    private int in;

    public void onClickMediaCodecInfo(View view) {
//        binding.logTextView.appendLine(""+in++);

        showToast("" + binding.logTextView.isScrollBottom());
        MediaCodecList mediaCodecList = new MediaCodecList(MediaCodecList.REGULAR_CODECS);
//        new MediaCodecList(MediaCodecList.ALL_CODECS);
        MediaCodecInfo[] medias = mediaCodecList.getCodecInfos();
        if (medias == null) {
            return;
        }
        for (MediaCodecInfo it : medias) {
            binding.logTextView.appendLine("MediaCodecInfo:" + it.getName());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                binding.logTextView.appendLine("\tgetCanonicalName(api29):" + it.getCanonicalName());
                binding.logTextView.appendLine("\tisSoftwareOnly(api29):" + it.isSoftwareOnly());
                binding.logTextView.appendLine("\tisHardwareAccelerated(api29):" + it.isHardwareAccelerated());
                binding.logTextView.appendLine("\tisVendor(api29):" + it.isVendor());
                binding.logTextView.appendLine("\tisAlias(api29):" + it.isAlias());
            }
            binding.logTextView.appendLine("\tisEncoder:" + it.isEncoder());

            String[] types = it.getSupportedTypes();
            for (int j = 0; j < types.length; j++) {
                binding.logTextView.appendLine("\tsupport type:" + types[j]);

            }
        }
    }
}