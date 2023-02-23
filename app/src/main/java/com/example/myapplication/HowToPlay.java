package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


// This is a view class with no controller or model
public class HowToPlay extends AppCompatActivity {

    Button howToPlayBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_play);


        howToPlayBackButton = findViewById(R.id.HowToPlayBackButton);

        howToPlayBackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            //Description: overrides onClick method in order to switch from main activity to how to play activity
            public void onClick(View view){
                Intent howToPlay = new Intent(HowToPlay.this, MainActivity.class);
                startActivity(howToPlay);
            }
        });
    }
}