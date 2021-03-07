package com.junmeng.android_java_example.drawable;

import android.content.res.Resources;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.junmeng.android_java_example.R;
import com.junmeng.android_java_example.databinding.ActivityDrawableBinding;

public class DrawableActivity extends AppCompatActivity {

    ActivityDrawableBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDrawableBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.imageView2.setBackground(new CustomDrawable());

    }

    public void onClickTransition(View view) {
        Resources res = getResources();
        TransitionDrawable transition =
                (TransitionDrawable) ResourcesCompat.getDrawable(res, R.drawable.expand_collapse, null);

        ImageView image =binding.imageView2;
        image.setImageDrawable(transition);

        // Then you can call the TransitionDrawable object's methods.
        transition.startTransition(10000);
    }
}