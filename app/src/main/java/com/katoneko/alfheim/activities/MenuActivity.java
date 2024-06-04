package com.katoneko.alfheim.activities;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.katoneko.alfheim.R;
import com.katoneko.alfheim.cutscenes.Cutscene1Activity;

public class MenuActivity extends AppCompatActivity {

    private ImageView menuForeground;
    private ImageView menuForegroundDuplicate;
    private float imageWidth;
    private View blackOverlayView;

    private static final int START_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu); // Ensure your layout file has the black overlay view
        EdgeToEdge.enable(this);

        // Set immersive mode programmatically
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);

        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein);
        findViewById(R.id.activity_menu).startAnimation(fadeInAnimation); // Optional animation

        menuForeground = findViewById(R.id.menu_foreground);
        menuForegroundDuplicate = findViewById(R.id.menu_foreground_duplicate);

        // Reference the black overlay view from the layout
        blackOverlayView = findViewById(R.id.black_overlay);

        setupClickListener();

        startForegroundAnimation();
    }

    private void setupClickListener() {
        findViewById(R.id.activity_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blackOverlayView.setVisibility(View.VISIBLE);
                animateBlackOverlay();
                new Handler().postDelayed(() -> transitionToNextActivity(), START_DELAY);
            }
        });
    }

    // Removed animateWhiteCircle method

    private void transitionToNextActivity() {
        Intent intent = new Intent(MenuActivity.this, Cutscene1Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    private void startForegroundAnimation() {
        menuForeground.post(() -> {
            imageWidth = menuForeground.getWidth();

            menuForegroundDuplicate.setTranslationX(imageWidth);

            ValueAnimator animator = ValueAnimator.ofFloat(0, -imageWidth);
            animator.setDuration(3000);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setInterpolator(new LinearInterpolator());

            animator.addUpdateListener(animation -> {
                float value = (float) animation.getAnimatedValue();
                menuForeground.setTranslationX(value);
                menuForegroundDuplicate.setTranslationX(value + imageWidth);

                if (value <= -imageWidth) {
                    float overflow = -imageWidth - value;
                    menuForeground.setTranslationX(overflow / 2);
                    menuForegroundDuplicate.setTranslationX((overflow / 2) + imageWidth);
                }
            });

            animator.start();
        });
    }

    private void animateBlackOverlay() {
        // Animate black overlay view from fully transparent to fully opaque
        ObjectAnimator blackOverlayAlpha = ObjectAnimator.ofFloat(blackOverlayView, "alpha", 0f, 1f);
        blackOverlayAlpha.setDuration(3000); // Adjust duration as needed

        // Animate black overlay view from right to left (screen width)
        float screenWidth = getResources().getDisplayMetrics().widthPixels;
        ObjectAnimator blackOverlayTranslation = ObjectAnimator.ofFloat(blackOverlayView, "translationX", screenWidth, 0f);
        blackOverlayTranslation.setDuration(3000); // Adjust duration as needed

        // Combine animations into a set and start
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(blackOverlayAlpha, blackOverlayTranslation);
        animatorSet.start();
    }
}