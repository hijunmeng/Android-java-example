package com.junmeng.android_java_example.checkrom;

import android.os.Bundle;
import android.view.View;

import com.example.common.base.BaseActivityDelegate;
import com.example.common.utils.RomUtil;
import com.junmeng.android_java_example.databinding.ActivityCheckRomBinding;

import static com.example.common.utils.RomUtil.ROM_EMUI;
import static com.example.common.utils.RomUtil.ROM_FLYME;
import static com.example.common.utils.RomUtil.ROM_MIUI;
import static com.example.common.utils.RomUtil.ROM_OPPO;
import static com.example.common.utils.RomUtil.ROM_VIVO;

public class CheckRomActivity extends BaseActivityDelegate {

    ActivityCheckRomBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityCheckRomBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

    }

    public void onClickCheckEmui(View view) {
        showToast("is emui:" + RomUtil.check(ROM_EMUI));
    }

    public void onClickCheckVivo(View view) {
        showToast("is vivo:" + RomUtil.check(ROM_VIVO));
    }

    public void onClickCheckOppo(View view) {
        showToast("is oppo:" + RomUtil.check(ROM_OPPO));
    }

    public void onClickCheckMiui(View view) {
        showToast("is miui:" + RomUtil.check(ROM_MIUI));
    }

    public void onClickCheckFlyme(View view) {
        showToast("is flyme:" + RomUtil.check(ROM_FLYME));
    }
}