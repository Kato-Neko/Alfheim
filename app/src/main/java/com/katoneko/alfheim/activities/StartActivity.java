package com.katoneko.alfheim.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.katoneko.alfheim.R;

public class StartActivity extends AppCompatActivity {

    private static final int START_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        EdgeToEdge.enable(this);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(StartActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();

            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        }, START_DELAY);
    }
}
