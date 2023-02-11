package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button howToPlayButton;
    Button startGameButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //opens home screen of app and reveals its layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assign id to how to play button
        howToPlayButton = findViewById(R.id.HowToPlay);
        //Adds a listener to howtoPlay button so that the user can click on it and see the game instructions
        howToPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            //Description: overrides onClick method in order to switch from main activity to how to play activity
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HowToPlay.class);
                startActivity(intent);
            }
        });

        startGameButton = findViewById(R.id.StartGame);

        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StartGame.class);
                startActivity(intent);
            }
        });
    }

}