package com.katoneko.alfheim.adapters;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.katoneko.alfheim.R;

public class FullScreenActivity extends AppCompatActivity {

    private TextView fullScreenText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adapter_fullscreen);
        fullScreenText = findViewById(R.id.full_screen_text);
    }

    protected void display(int choice, String text) {
        if (choice == 0) {
            fullScreenText.setTextAppearance(this, R.style.FullScreenTextItalic);
        } else {
            fullScreenText.setTextAppearance(this, R.style.FullScreenText);
        }
        fullScreenText.setText(text);
    }
}
