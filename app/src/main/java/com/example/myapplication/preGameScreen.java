package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class preGameScreen extends AppCompatActivity {

    Button button12x12;

    Button button9x9;
    Button button6x6;
    Button button4x4;

    int gridSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_game_screen);

        button12x12 = findViewById(R.id.button12x12);

        button9x9 = findViewById(R.id.button9x9);

        button6x6 = findViewById(R.id.button6x6);

        button4x4 = findViewById(R.id.button4x4);

        button9x9.setOnClickListener(new View.OnClickListener() {
            //Description: overrides onClick method in order to switch from main activity to startgame activity
            //this calls startgame.java which holds the logic for the game board and game
            @Override
            public void onClick(View view) {
                Intent grid9x9 = new Intent(preGameScreen.this, StartGame.class);
                gridSize = 9;
                grid9x9.putExtra("gridSizeTag",gridSize);
                startActivity(grid9x9);
            }
        });

        button12x12.setOnClickListener(new View.OnClickListener() {
            //Description: overrides onClick method in order to switch from main activity to startgame activity
            //this calls startgame.java which holds the logic for the game board and game
            @Override
            public void onClick(View view) {
                Intent grid12x12 = new Intent(preGameScreen.this, StartGame.class);
                gridSize = 12;
                grid12x12.putExtra("gridSizeTag",gridSize);
                startActivity(grid12x12);
            }
        });

        button6x6.setOnClickListener(new View.OnClickListener() {
            //Description: overrides onClick method in order to switch from main activity to startgame activity
            //this calls startgame.java which holds the logic for the game board and game
            @Override
            public void onClick(View view) {
                Intent grid6x6 = new Intent(preGameScreen.this, StartGame.class);
                gridSize = 6;
                grid6x6.putExtra("gridSizeTag",gridSize);
                startActivity(grid6x6);
            }
        });

        button4x4.setOnClickListener(new View.OnClickListener() {
            //Description: overrides onClick method in order to switch from main activity to startgame activity
            //this calls startgame.java which holds the logic for the game board and game
            @Override
            public void onClick(View view) {
                Intent grid4x4 = new Intent(preGameScreen.this, StartGame.class);
                gridSize = 4;
                grid4x4.putExtra("gridSizeTag",gridSize);
                startActivity(grid4x4);
            }
        });

    }
}