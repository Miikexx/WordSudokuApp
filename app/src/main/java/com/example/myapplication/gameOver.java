package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class gameOver extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);
        Button titleBtn = findViewById(R.id.gameOver);

        titleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent titleScreen = new Intent(gameOver.this, MainActivity.class);
                startActivity(titleScreen);
                finish();
            }
        });
    }
}
