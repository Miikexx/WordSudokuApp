package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;

public class preGameScreen extends AppCompatActivity {


    //Difficulty level buttons
    Button buttonPeaceful;

    Button buttonNormal;

    Button buttonHard;

    Button buttonHardcore;

    //GameBoard Size buttons
    Button button12x12;
    Button button9x9;
    Button button6x6;
    Button button4x4;

    Button listenMode;

    Button translationMode;
    //Start Game button
    Button buttonStartGame;

    //Help button

    Button help;

    //sets default difficulty level and gridsize
    String difficulty = "normal";
    int gridSize = 9;

    boolean voiceMode = false;

    boolean translation = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_game_screen);

        //Sets tablet to landscape mode
        if(getResources().getBoolean(R.bool.landscape_only)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        ColorStateList colorOfButton = ColorStateList.valueOf(Color.parseColor("#00FFFF"));
        ColorStateList defaultColor = ColorStateList.valueOf(Color.parseColor("#FFFFF176"));

        buttonPeaceful = findViewById(R.id.peacefulDifficulty);

        buttonNormal = findViewById(R.id.normalDifficulty);

        buttonHard = findViewById(R.id.hardDifficulty);

        buttonHardcore = findViewById(R.id.hardcoreDifficulty);

        button12x12 = findViewById(R.id.button12x12);

        button9x9 = findViewById(R.id.button9x9);

        button6x6 = findViewById(R.id.button6x6);

        button4x4 = findViewById(R.id.button4x4);

        buttonStartGame = findViewById(R.id.startGameButton);

        listenMode = findViewById(R.id.listenMode);

        translationMode = findViewById(R.id.translationButton);

        help = findViewById(R.id.help);

        //buttons to set the difficulty level
        buttonPeaceful.setOnClickListener(new View.OnClickListener() {

            @Override
            //Description: Overrides onClick method to set the difficulty and change this button's colour.
            public void onClick(View view) {
                buttonNormal.setBackgroundTintList(defaultColor);
                buttonHard.setBackgroundTintList(defaultColor);
                buttonHardcore.setBackgroundTintList(defaultColor);

                difficulty = "peaceful";
                buttonPeaceful.setBackgroundTintList(colorOfButton);
            }
        });

        buttonNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            //Description: Overrides onClick method to set the difficulty and change this button's colour.
            public void onClick(View view) {
                buttonPeaceful.setBackgroundTintList(defaultColor);
                buttonHard.setBackgroundTintList(defaultColor);
                buttonHardcore.setBackgroundTintList(defaultColor);

                difficulty = "normal";
                buttonNormal.setBackgroundTintList(colorOfButton);
            }
        });

        buttonHard.setOnClickListener(new View.OnClickListener() {
            @Override
            //Description: Overrides onClick method to set the difficulty and change this button's colour.
            public void onClick(View view) {
                buttonPeaceful.setBackgroundTintList(defaultColor);
                buttonNormal.setBackgroundTintList(defaultColor);
                buttonHardcore.setBackgroundTintList(defaultColor);

                difficulty = "hard";
                buttonHard.setBackgroundTintList(colorOfButton);
            }
        });

        buttonHardcore.setOnClickListener(new View.OnClickListener() {

            @Override
            //Description: Overrides onClick method to set the difficulty and change this button's colour.
            public void onClick(View view) {
                buttonPeaceful.setBackgroundTintList(defaultColor);
                buttonNormal.setBackgroundTintList(defaultColor);
                buttonHard.setBackgroundTintList(defaultColor);

                difficulty = "hardcore";
                buttonHardcore.setBackgroundTintList(colorOfButton);
            }
        });

        //buttons to set the grid size
        button9x9.setOnClickListener(new View.OnClickListener() {
            @Override
            //Description: Overrides onClick method to set the grid's size and change this button's colour.
            public void onClick(View view) {
                button12x12.setBackgroundTintList(defaultColor);
                button6x6.setBackgroundTintList(defaultColor);
                button4x4.setBackgroundTintList(defaultColor);

                gridSize = 9;
                button9x9.setBackgroundTintList(colorOfButton);
            }
        });

        button12x12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Description: Overrides onClick method to set the grid's size and change this button's colour.
                button9x9.setBackgroundTintList(defaultColor);
                button6x6.setBackgroundTintList(defaultColor);
                button4x4.setBackgroundTintList(defaultColor);

                gridSize = 12;
                button12x12.setBackgroundTintList(colorOfButton);
            }
        });

        button6x6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Description: Overrides onClick method to set the grid's size and change this button's colour.
                button12x12.setBackgroundTintList(defaultColor);
                button9x9.setBackgroundTintList(defaultColor);
                button4x4.setBackgroundTintList(defaultColor);

                gridSize = 6;
                button6x6.setBackgroundTintList(colorOfButton);
            }
        });

        button4x4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Description: Overrides onClick method to set the grid's size and change this button's colour.
                button12x12.setBackgroundTintList(defaultColor);
                button9x9.setBackgroundTintList(defaultColor);
                button6x6.setBackgroundTintList(defaultColor);

                gridSize = 4;
                button4x4.setBackgroundTintList(colorOfButton);
            }
        });

        //Button to say if Listen Mode should be on.
        listenMode.setOnClickListener(new View.OnClickListener() {

            @Override
            //Description: Overrides onClick method to set the difficulty and change this button's colour.
            public void onClick(View view) {

                if(voiceMode == false) {
                    listenMode.setBackgroundTintList(colorOfButton);
                    voiceMode = true;
                }
                else{
                    listenMode.setBackgroundTintList(defaultColor);
                    voiceMode = false;
                }

            }
        });

        //Button to say whether or not English words will be on the grid.
        translationMode.setOnClickListener(new View.OnClickListener() {

            @Override
            //Description: Overrides onClick method to set the difficulty and change this button's colour.
            public void onClick(View view) {

                if(translation == false) {
                    translationMode.setBackgroundTintList(colorOfButton);
                    translation = true;
                }
                else{
                    translationMode.setBackgroundTintList(defaultColor);
                    translation = false;
                }

            }
        });



        buttonStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            //Description: overrides onClick method in order to switch from main activity to startgame activity
            //this calls startgame.java which holds the logic for the game board and game
            public void onClick(View view) {
                Intent newGame = new Intent(preGameScreen.this, StartGame.class);
                newGame.putExtra("gridSizeTag",gridSize);
                newGame.putExtra("difficultyTag",difficulty);
                newGame.putExtra("voiceModeTag",voiceMode);
                newGame.putExtra("translationModeTag",translation);
                startActivity(newGame);
                finish();
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(preGameScreen.this, HowToPlay.class));
                finish();
            }
        });

    }
}