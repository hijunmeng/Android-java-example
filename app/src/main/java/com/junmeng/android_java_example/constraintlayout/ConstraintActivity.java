package com.junmeng.android_java_example.constraintlayout;

import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;

import com.junmeng.android_java_example.R;
import com.junmeng.android_java_example.databinding.ActivityConstraintBinding;

public class ConstraintActivity extends AppCompatActivity {

    ActivityConstraintBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConstraintBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void onClickAnim(View view) {
        if (view.isSelected()) {
            view.setSelected(false);
            anim(R.layout.activity_constraint);
        } else {
            view.setSelected(true);
            anim(R.layout.activity_constraint_keyframe_two);

        }
    }

    /**
     * 这种效果只针对位置和尺寸，不包含颜色等其他
     *
     * @param layoutId
     */
    private void anim(@LayoutRes int layoutId) {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.load(this, R.layout.activity_constraint_keyframe_two);
        TransitionManager.beginDelayedTransition(binding.getRoot());
        constraintSet.applyTo(binding.getRoot());
    }

}