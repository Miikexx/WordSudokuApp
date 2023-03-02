package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import android.widget.TableLayout;
import java.lang.*;

import java.util.Timer;
import java.util.TimerTask;


import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;
// hi this is a comment
// This is primarily a view class with some controller which is needed. Control flow is-
// very simple and does not need another class. Will make new controller class for iteration 3.  Contains no model
public class StartGame extends AppCompatActivity {

    //this variable is used to determine how many spots are filled in the board before the game starts
    int initialSpotsFilled = 77;
    //this variable is used to
    int livesCounter = 10;

    //textview of timer, timer count variables and time variable
    TextView timerCount;
    Timer timer;

    TimerTask timerTask;
    Double timeInSeconds = 0.0;

    // Used as a back button so that the user can go back to the main screen
    Button tempButton;


    //pass in data of number rows and cols from pre game screen
    int rowsNcols;

    //pass in time variable from start game activity

    // num rows and cols are used as a variable to store the dimensions of the game board grid
    private static int NUM_ROWS;
    private static int NUM_COLS;

    //buttonPlacementRow and col are used to save the index of the clicked button so we can compare it with the actual solution
    int buttonPlacementRow;
    int buttonPlacementCol;
    //used to save the button clicked on to change the text that appears on the gameBoard square
    Button buttonPlacement;

    //This is used to help as one way to determine whether or not someone can place a word in a "clicked" slot
    boolean canPlace = false;

    // this is where the code starts executing from when the user clicks start game on the main screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        Bundle extra = getIntent().getExtras();

        //pass in time variable from start game activity
        rowsNcols = extra.getInt("gridSizeTag");

        NUM_ROWS = rowsNcols;
        NUM_COLS = rowsNcols;

        // A temporary back button to go back to home screen
        tempButton = findViewById(R.id.tempButton);
        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            //Description: overrides onClick method in order to switch from main activity to how to play activity
            public void onClick(View view) {
                Intent backToMain = new Intent(StartGame.this, MainActivity.class);
                startActivity(backToMain);
            }

        });



        //calling all functions to start the game this is the control flow and cannot be tested
        gameWordInitializer newGame = new gameWordInitializer();
        newGame.fillArray();
        //create valid board with a grid size of 9x9 and a certain number of initial spots filled
        ValidBoardGenerator validBoard = new ValidBoardGenerator(9, 9, initialSpotsFilled);
        int currentValue;
        newGame.syncGameWordArray();
        populateButtons();
        makeGridForBottomWords();

        //Set timer textview
        timerCount = (TextView) findViewById(R.id.timer);
        //create timer object to be able to increment timer
        timer = new Timer();

        //call start timer to start the timer from 00 : 00 : 00
        timerStart();
    }


    // makes the grid table for the game (9x9) using buttons and also initalizes some of the buttons as the actual solution
    // leaves some of the buttons without text (player places the words in). The function also saves the row and column index if the user
    // decides to click on the grid space
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
                button.setTextSize(10);
                button.setLayoutParams(new TableRow.LayoutParams (
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.MATCH_PARENT,
                1.0f));


                final int currentColumn = cols;
                final int currentRow = row;

                String tempWord = ValidBoardGenerator.gameWordArray[row][cols].getTranslation();

                // if initial set to 1 then it will not be displayed in the game board otherwise if initial is 0, then it will be displayed
                if(ValidBoardGenerator.gameWordArray[row][cols].getInitial() != 0) {
                    tempWord = " ";
                }
                tableRow.addView(button);
                button.setText(tempWord);
                button.setTextColor(Color.parseColor("#FF000000"));
                button.setMaxLines(1);


                TextView lives = findViewById(R.id.livesCounter);
                lives.setTextSize(20);
                lives.setText("Lives Counter: "+livesCounter);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    // sets an on click listener for each button in the 9x9 grid so that it says the column and the row that it is in
                    public void onClick(View view) {
                        clickedGridSpace(button, currentRow, currentColumn);
                    }
                });

            }
        }
    }

    //this function is meant to display the word that the user clicked on at the top of the grid (some words are too small to fit
    //in the cell) but will not display a word if the cell clickedd is blank. The function also helps to validate canPLace to true or
    // false, so the user can insert into the button and saves the column and rox index
    private void clickedGridSpace(Button btn, int row, int col){
        TextView sudokuDisplay = findViewById(R.id.wordDisplay);
        sudokuDisplay.setPadding(0, 0, 0, 0);
        // If the current grid space is empty we will be able to place a word insdie of there
        if (ValidBoardGenerator.gameWordArray[row][col].getInitial() == 0){
            sudokuDisplay.setText(btn.getText());
            canPlace = false;
        }
        else if(ValidBoardGenerator.gameWordArray[row][col].getInitial() == 1){
            canPlace = true;

            sudokuDisplay.setText("");
            buttonPlacementCol = col;
            buttonPlacementRow = row;
            buttonPlacement = btn;
        }
    }



    // this makes the grid at the buttom for thw word pairs for the user to see and interact with, if the user clicks on a word,
    //the word is saved and used to compare whether or not the word the user clicked is the actual solution
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
                button.setText(gameWordInitializer.englishArray[row*3 + cols]);
                button.setPadding(0, 0, 0, 0);
                tableRow.addView(button);

                // Used to save index of bottom word in StartGame.gameWordArray so we can acess the wordClass
                final int truePosition = 3*row + cols;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    //calls clickedWord with the index of the word in the StartGame.gameWordArray
                    public void onClick(View view) {
                        clickedWord(truePosition);
                    }
                });
            }
        }
    }
    //Checks if a word (in the list of words at bottom of screen) clicked on is equal to the actual solution of the grid space
    // the user pressed before clicking on the word, if the user clicks the wrong word, a message will be displayed at
    // the top of the screen.
    //If the user correctly selects the last word in the grid then a message will be displayed "You win"
    private void clickedWord(int wordPos){

        if(canPlace == true){
           // wordClass wordToBeChecked = this.gameWordArray[wordPos];
            wordClass ValidBoardGeneratorWord = ValidBoardGenerator.gameWordArray[buttonPlacementRow][buttonPlacementCol];

            if(gameWordInitializer.gameWordArray[wordPos].getEnglish()== ValidBoardGeneratorWord.getEnglish() && ValidBoardGeneratorWord.getInitial() == 1 ){
                TextView correctResult = findViewById(R.id.wordDisplay);
                correctResult.setTextSize(20);
                correctResult.setText("");

                buttonPlacement.setTextSize(10);
                buttonPlacement.setText(ValidBoardGeneratorWord.getTranslation());
                ValidBoardGeneratorWord.setInitial(0);
                initialSpotsFilled++;

                if(initialSpotsFilled == 81){
                    //opens win screen if user fills in all the grid spaces (wins game)
                    Intent win = new Intent(StartGame.this, winScreen.class);
                    //pass in time to be saved in win screen class
                    win.putExtra("time",getTime());
                    //cancel timer
                    timer.cancel();
                    startActivity(win);
                }
            }
            else{
                //only shows that user entered a wrong word if the grid space is still empty
                if(ValidBoardGenerator.gameWordArray[buttonPlacementRow][buttonPlacementCol].getInitial() == 1) {
                    livesCounter--;
                    if(livesCounter == 0){
                        Intent lose  = new Intent(StartGame.this, gameOver.class);
                        // If you just use this that is not a valid context. Use ActivityName.this
                        //cancel timer
                        timer.cancel();
                        startActivity(lose);
                    }
                    TextView lives = findViewById(R.id.livesCounter);
                    lives.setText("Lives Counter: "+livesCounter);

                    TextView incorrectResult = findViewById(R.id.wordDisplay);
                    incorrectResult.setText("Wrong Word, Try Again!");
                }
            }

        }
    }

    //this function keeps track of the time, which starts when the user starts the game and does not stop until the user finishes the game
    private void timerStart() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                timeInSeconds++;
                //Update the UI on the main thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //updates text every second
                        timerCount.setText(getTime());
                    }
                });

            }
        };
        //timer.scheduleatfixedrate makes this function call run every 1 second
        timer.scheduleAtFixedRate(timerTask,0,1000);
    }
    //gets the time at every second and returns the formatted string back
    public String getTime(){
        int roundTime = (int) Math.round(timeInSeconds);
        //calculates the seconds, minutes and hours respectively based on the time passed in
        int secs = ((roundTime % 86400) % 3600) % 60;
        int mins = ((roundTime % 86400) % 3600) / 60;
        int hours = ((roundTime % 86400)/3600);
        //formats time into a displayable form
        return String.format("%02d : %02d : %02d", hours, mins, secs);
    }

}


