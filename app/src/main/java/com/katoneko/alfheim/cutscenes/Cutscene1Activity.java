package com.katoneko.alfheim.cutscenes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;

import com.katoneko.alfheim.R;
import com.katoneko.alfheim.activities.MainActivity;
import com.katoneko.alfheim.adapters.FullScreenActivity;

public class Cutscene1Activity extends FullScreenActivity {

    private boolean l2Completed = false;
    private boolean l3Completed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Set immersive mode programmatically
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);

        display(1, "You wake up from a deep slumber,\nawoken from the soft touch of tiny hands.");
        setupClickListener();
    }

    private void setupClickListener() {
        findViewById(R.id.full_screen_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!l2Completed) {
                    display(1, "You are welcomed to the sight of a tiny floating human,\nand she speaks...");
                    l2Completed = true;
                } else if (!l3Completed) {
                    display(0, "\"You are finally awake!\"");
                    l3Completed = true;
                } else {
                    Toast.makeText(Cutscene1Activity.this, "Cutscene completed!", Toast.LENGTH_SHORT).show();
                    transitionToNextActivity();
                }
            }
        });
    }

    private void transitionToNextActivity() {
        Intent intent = new Intent(Cutscene1Activity.this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}
