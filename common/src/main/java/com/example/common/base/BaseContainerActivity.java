package com.example.common.base;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.common.R;

public class BaseContainerActivity extends AppCompatActivity implements IBaseActivityContainer, IBaseActivity {

    BaseActivityContainerSimple baseActivityContainerSimple = new BaseActivityContainerSimple(this);
    private BaseActivitySimple mBaseActivitySimple = new BaseActivitySimple(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_container);
    }

    @Override
    public int getContainerId() {
        return baseActivityContainerSimple.getContainerId();
    }

    @Override
    public boolean isBackByStack() {
        return baseActivityContainerSimple.isBackByStack();
    }

    @Override
    public void addFragmentWithoutAddToBackStack(@NonNull Fragment fragment) {
        baseActivityContainerSimple.addFragmentWithoutAddToBackStack(fragment);
    }

    @Override
    public void addFragment(@NonNull Fragment fragment, boolean isAddToBackStack) {
        baseActivityContainerSimple.addFragment(fragment, isAddToBackStack);
    }

    @Override
    public void replaceFragmentWithoutAddToBackStack(@NonNull Fragment fragment) {
        baseActivityContainerSimple.replaceFragmentWithoutAddToBackStack(fragment);
    }

    @Override
    public void replaceFragment(@NonNull Fragment fragment, boolean isAddToBackStack) {
        baseActivityContainerSimple.replaceFragment(fragment, isAddToBackStack);
    }

    @Override
    public void turnToFragment(int type, @NonNull Fragment fragment, boolean isAddToBackStack) {
        baseActivityContainerSimple.turnToFragment(type, fragment, isAddToBackStack);
    }

    @Override
    public void turnToFragment(int type, @NonNull Fragment fragment, @Nullable String tag, boolean isAddToBackStack) {
        baseActivityContainerSimple.turnToFragment(type, fragment, tag, isAddToBackStack);
    }

    @Nullable
    @Override
    public Fragment getTopFragment() {
        return baseActivityContainerSimple.getTopFragment();
    }

    @Override
    public boolean popBackStack() {
        return baseActivityContainerSimple.popBackStack();
    }

    @Override
    public boolean onBackPressedDelegate() {
        return baseActivityContainerSimple.onBackPressedDelegate();
    }

    @Override
    public boolean dispatchBackPressedEvent() {
        return mBaseActivitySimple.dispatchBackPressedEvent();
    }

    @Override
    public void gotoActivity(Class<?> cls, boolean isFinishCurrent) {
        mBaseActivitySimple.gotoActivity(cls, isFinishCurrent);
    }

    @Override
    public void gotoActivity(Class<?> cls) {
        mBaseActivitySimple.gotoActivity(cls);
    }

    @Override
    public void showToast(String text) {
        mBaseActivitySimple.showToast(text);
    }

    @Override
    public void showDebugToast(String text) {
        mBaseActivitySimple.showDebugToast(text);
    }

    @Override
    public void sleep(int ms) {
        mBaseActivitySimple.sleep(ms);
    }
}