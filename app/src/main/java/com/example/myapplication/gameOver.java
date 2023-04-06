package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class gameOver extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);

        //Sets tablet to landscape mode
        if(getResources().getBoolean(R.bool.landscape_only)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        Button titleBtn = findViewById(R.id.gameOver);

        titleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            // Description: Overrides onClick method to switch from gameOver activity to MainActivity.
            public void onClick(View view) {
                Intent titleScreen = new Intent(gameOver.this, MainActivity.class);
                startActivity(titleScreen);
                finish();
            }
        });
    }
}
