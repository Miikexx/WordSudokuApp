package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;



public class StartGame extends AppCompatActivity {

        public class wordClass{
          public String English = "";
          public String translation = "";
          public int initial = 0;
          public int num;
        }

        String englishArray[] = {"Apple", "You", "And", "Gentleman", "Gate", "Good", "Glad", "Play", "Eat"};
        String frenchArray[] = {"Pomme", "Tu", "Et", "Monsieur", "Porte", "Bien", "Content", "Jouer", "Manger"};

        wordClass gameWordArray[];

         public void fillArray(){
             gameWordArray = new wordClass[9];
             for(int i = 0; i < 9; i++){gameWordArray[i] = new wordClass();
                gameWordArray[i].English = englishArray[i];
                gameWordArray[i].translation = frenchArray[i];
                gameWordArray[i].num = i;
            }
        }












    Button tempButton;
    private static final int NUM_ROWS = 9;
    private static final int NUM_COLS = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        tempButton = findViewById(R.id.tempButton);

        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            //Description: overrides onClick method in order to switch from main activity to how to play activity
            public void onClick(View view) {
                Intent howToPlay = new Intent(StartGame.this, MainActivity.class);
                startActivity(howToPlay);
            }

        });
        fillArray();
        populateButtons();
        makeGridForBottomWords();
    }

    private void populateButtons(){
        TableLayout table = (TableLayout) findViewById(R.id.tableForButtons);
        for(int row = 0; row < NUM_ROWS; row++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams (
                    TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));
            table.addView(tableRow);

            for(int cols = 0; cols < NUM_COLS; cols++){
                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams (
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.MATCH_PARENT,
                1.0f));
                tableRow.addView(button);
            }
        }
    }

    private void makeGridForBottomWords(){
        TableLayout table = (TableLayout) findViewById(R.id.tableForButtons);
        for(int row = 0; row < 3; row++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams (
                    TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));
            table.addView(tableRow);

            for(int cols = 0; cols < 3; cols++){
                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams (
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.MATCH_PARENT,
                        1.0f));
                tableRow.addView(button);
                button.setText("Hello");
                button.setText(gameWordArray[(row)*(3) + (cols)].English);
                button.setTextSize(10);
            }
        }
    }
}


