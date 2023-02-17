package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import java.lang.*;
import android.widget.TableRow;
import android.widget.TextView;


public class StartGame extends AppCompatActivity {

        public static class wordClass{
          public String English = "";
          public String translation = "";

          //If set to 0 this will not be generated onto the initial game board and if set to  1, it will show up on
          // the game board
          public int initial = 1;
         // the num integer is used to write the logic of our game on the back end and is only assigned numbers
         // from 1-9
          public int num = 0;
        }

        String englishArray[] = {"Apple", "You", "And", "Gentleman", "Gate", "Good", "Glad", "Play", "Eat"};
        String frenchArray[] = {"Pomme", "Tu", "Et", "Monsieur", "Porte", "Bien", "Content", "Jouer", "Manger"};

        wordClass gameWordArray[];

    Button tempButton;
    private static final int NUM_ROWS = 9;
    private static final int NUM_COLS = 9;
    int buttonPlacementRow;
    int buttonPlacementCol;
    Button buttonPlacement;
    boolean canPlace = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        // A temporary back button to go back to home screen
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
        ValidBoardGenerator validBoard = new ValidBoardGenerator(9, 9, 30);
        int currentValue;
       /* for(int i = 0; i < 9; i++){
            for(int k = 0; k < 9; k++){
                currentValue = ValidBoardGenerator.gameWordArray[i][k].num - 1;
                ValidBoardGenerator.gameWordArray[i][k] = this.gameWordArray[currentValue];

            }
        }

        */


        for(int i = 0; i < 9; i++){
            for(int k = 0; k < 9; k++){
                for(int j = 0; j < 9; j++){
                    if(this.gameWordArray[j].num  == ValidBoardGenerator.gameWordArray[i][k].num){
                        ValidBoardGenerator.gameWordArray[i][k].English = this.gameWordArray[j].English;
                        ValidBoardGenerator.gameWordArray[i][k].translation = this.gameWordArray[j].translation;
                    }
                }
            }
        }

        populateButtons();
        makeGridForBottomWords();
    }

    // Function fillArray does not return any value but instead fills the array of size 9 with the words we will be using
    // for the game along with the int num which is used for the logic
    public void fillArray(){
        gameWordArray = new wordClass[9];
        for(int i = 0; i < 9; i++){gameWordArray[i] = new wordClass();
            gameWordArray[i].English = englishArray[i];
            gameWordArray[i].translation = frenchArray[i];
            gameWordArray[i].num = i + 1;
        }
    }



    //dont check
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
                final int currentColumn = cols;
                final int currentRow = row;

                String tempWord = ValidBoardGenerator.gameWordArray[row][cols].translation;
                if(ValidBoardGenerator.gameWordArray[row][cols].initial != 0) {
                    tempWord = " ";
                }
                tableRow.addView(button);
                button.setText(tempWord);
                //wordClass buttonSpot = ValidBoardGenerator.gameWordArray[row][cols];
                //button.setText(buttonSpot.translation);
                button.setMaxLines(1);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clickedGridSpace(button, currentRow, currentColumn);
                    }
                });

            }
        }
    }
    private void clickedGridSpace(Button btn, int row, int col){
        if (ValidBoardGenerator.gameWordArray[row][col].initial == 0){//If the word can be changed, change it.
            buttonPlacementRow = row;
            buttonPlacementCol = col;
            buttonPlacement = btn;
            canPlace = true;
            TextView sudokuDisplay = findViewById(R.id.wordDisplay);
            sudokuDisplay.setText("");
        }
        else{
            TextView sudokuDisplay = findViewById(R.id.wordDisplay);
            sudokuDisplay.setText(btn.getText());
            sudokuDisplay.setPadding(0, 0, 0, 0);
        }
    }

    //dont check
    private void makeGridForBottomWords(){
        TableLayout table = (TableLayout) findViewById(R.id.tableForButtons);
        for(int row = 0; row < 3; row++){
            TableRow tableRow = new TableRow(StartGame.this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams (
                    TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));
            table.addView(tableRow);

            for(int cols = 0; cols < 3; cols++){
                Button button = new Button(StartGame.this);
                button.setLayoutParams(new TableRow.LayoutParams (
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.MATCH_PARENT,
                        1.0f));
                button.setText(frenchArray[row*3 + cols]);
                button.setPadding(0, 0, 0, 0);
                tableRow.addView(button);

                final int truePosition = 3*row + cols;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clickedWord(truePosition);
                    }
                });
            }
        }
    }
    private void clickedWord(int wordPos){
        if(canPlace) {
            wordClass toChange = ValidBoardGenerator.gameWordArray[buttonPlacementRow][buttonPlacementCol];
            toChange.English = gameWordArray[wordPos].English;
            toChange.translation = gameWordArray[wordPos].translation;
            toChange.num = gameWordArray[wordPos].num;
            toChange.initial = 1;
           if (ValidBoardGenerator.gameWordArray[buttonPlacementRow][buttonPlacementCol].num == toChange.num){
                ValidBoardGenerator.gameWordArray[buttonPlacementRow][buttonPlacementCol] = toChange;
                buttonPlacement.setText(toChange.translation);
                ValidBoardGenerator.numFilled++;
            }
            else if (ValidBoardGenerator.gameWordArray[buttonPlacementRow][buttonPlacementCol].num == toChange.num){
                TextView incorrectResult = findViewById(R.id.wordDisplay);
                incorrectResult.setText("FAIL");
            }
            canPlace = false;

            if(ValidBoardGenerator.numFilled == NUM_ROWS * NUM_COLS){
                /*Intent finishGame = new Intent(StartGame.this, WinScreen.class);
                startActivity(finishGame);
                finish();*/
                TextView incorrectResult = findViewById(R.id.wordDisplay);
                incorrectResult.setText("YOU WIN");

            }
        }
    }
}


