package com.junmeng.android_java_example.particle;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.junmeng.android_java_example.R;
import com.junmeng.android_java_example.databinding.ActivityParticleBinding;
import com.plattysoft.leonids.ParticleSystem;

/**
 * [plattysoft/Leonids: A Particle System for standard Android UI: http://plattysoft.github.io/Leonids/](https://github.com/plattysoft/Leonids)
 */
public class ParticleActivity extends AppCompatActivity {

    ActivityParticleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityParticleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void onClickStart(View view) {
        boolean isOneShot = binding.switchOneshot.isChecked();
        int maxParticles = Integer.parseInt(binding.etMaxNumber.getText().toString());
        int numParticles = Integer.parseInt(binding.etNumber.getText().toString());
        int drawableResId = R.drawable.emoji1;
        int timeToLive = Integer.parseInt(binding.etLivetime.getText().toString());

        float speedStart = Float.parseFloat(binding.etSpeedStart.getText().toString());
        float speedEnd = Float.parseFloat(binding.etSpeedEnd.getText().toString());
        float scaleStart = Float.parseFloat(binding.etScaleStart.getText().toString());
        float scaleEnd = Float.parseFloat(binding.etScaleEnd.getText().toString());
        int angleStart = Integer.parseInt(binding.etAngleStart.getText().toString());
        int angleEnd = Integer.parseInt(binding.etAngleEnd.getText().toString());
        float speedRotation = Float.parseFloat(binding.etSpeedRotation.getText().toString());

        float acceleration = Float.parseFloat(binding.etAcceleration.getText().toString());
        int accelerationAngle = Integer.parseInt(binding.etAccelerationAngle.getText().toString());

        int emitX = Integer.parseInt(binding.etEmitX.getText().toString());
        int emitY = Integer.parseInt(binding.etEmitY.getText().toString());
        int emitNum = Integer.parseInt(binding.etEmitNum.getText().toString());
        int emitTime = Integer.parseInt(binding.etEmitTime.getText().toString());
        int fadeoutTime = Integer.parseInt(binding.etFadeoutTime.getText().toString());

        ParticleSystem particleSystem = new ParticleSystem(this, maxParticles, drawableResId, timeToLive);
        particleSystem.setSpeedRange(speedStart, speedEnd);
        particleSystem.setSpeedModuleAndAngleRange(speedStart, speedEnd, angleStart, angleEnd);
        particleSystem.setRotationSpeed(speedRotation);
        particleSystem.setAcceleration(acceleration, accelerationAngle);
        particleSystem.setScaleRange(scaleStart, scaleEnd);
        particleSystem.setFadeOut(fadeoutTime);

        if (isOneShot) {
            particleSystem.oneShot(view, numParticles);
        } else {
            particleSystem.emit(emitX, emitY, emitNum, emitTime);
        }
    }
}