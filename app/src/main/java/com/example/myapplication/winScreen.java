package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class winScreen extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winscreen);
        Button titleBtn = findViewById(R.id.titleReturn);
        //time that user took to finish the game
        String time = "";
        double gameAccuracy;

        Bundle extra = getIntent().getExtras();

        //pass in time variable from start game activity
        time = extra.getString("time");

        //formats accuracy to be one decimal point
        gameAccuracy = extra.getDouble("accuracy");
        String format = String.format("%.1f",gameAccuracy);
        double fixedGameAccuracy = Double.parseDouble(format);

        //set end time based on when the user finishes the game
        TextView endTime = findViewById(R.id.timeText);
        endTime.setText("Time: "+time);

        TextView endAccuracy = findViewById(R.id.accuracyText);
        endAccuracy.setText("Accuracy: "+fixedGameAccuracy+"%");
        titleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent titleScreen = new Intent(winScreen.this, MainActivity.class);
                startActivity(titleScreen);
                finish();
            }
        });
    }
}
