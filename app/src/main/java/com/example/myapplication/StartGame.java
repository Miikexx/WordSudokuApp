package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.LinearLayout;
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
    int initialSpotsFilled;


    int currentSpotsFilled;
    //this variable is used to  hold the number of lives the usr has in the game
    int livesCounter;

    //increments each time the user loses a life, helps to calculate accuracy

    int livesLost;
    double accuracy;
    //textview of timer, timer count variables and time variable
    TextView timerCount;
    Timer timer;

    TimerTask timerTask;
    Double timeInSeconds = 0.0;

    // Used as a back button so that the user can go back to the main screen
    Button tempButton;


    //pass in data of number rows and cols from pre game screen
    int rowsNcols;

    //pass in data of difficulty level from pre game screen
    private static String difficultyLevel;

    private static double percentageOfGridFilled;

    //pass in time variable from start game activity

    // num rows and cols are used as a variable to store the dimensions of the game board grid
    private static int NUM_ROWS;
    private static int NUM_COLS;

    private static int subGridRowSize;

    private static int subGridColSize;

    private static int bottomWordsRowSize;

    private static int bottomWordsColSize;

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
        if(extra != null) {
            rowsNcols = extra.getInt("gridSizeTag");
            difficultyLevel = extra.getString("difficultyTag");
        }
        //sets the number of rows and columns based on what user selected
        NUM_ROWS = rowsNcols;
        NUM_COLS = rowsNcols;
        livesLost = 0;

        //SET percentage of grid filled based on difficulty level
        if(difficultyLevel != null) {
            switch (difficultyLevel) {
                case "peaceful":
                    percentageOfGridFilled = 0.70;
                    livesCounter = 99;
                    break;
                case "normal":
                    percentageOfGridFilled = 0.50;
                    livesCounter = 10;
                    break;
                case "hard":
                    percentageOfGridFilled = 0.40;
                    livesCounter = 5;
                    break;
                case "hardcore":
                    percentageOfGridFilled = 0.35;
                    livesCounter = 1;
                    break;
                default:
                    percentageOfGridFilled = 0.50;
            }
        }
        else{
            percentageOfGridFilled = 0.50;
        }
        //calculates initialspotsfille based on grid size and difficulty level
        initialSpotsFilled = (int) Math.round(NUM_COLS*NUM_ROWS*percentageOfGridFilled);
        currentSpotsFilled = initialSpotsFilled;

        // A temporary back button to go back to home screen
        tempButton = findViewById(R.id.tempButton);
        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            //Description: overrides onClick method in order to switch from main activity to how to play activity
            public void onClick(View view) {
                Intent backToMain = new Intent(StartGame.this, MainActivity.class);
                startActivity(backToMain);
                finish();
            }

        });

        //calling all functions to start the game this is the control flow and cannot be tested

        //gameWordinitializer holds the array of wordclass objects to be placed into the sudoku puzzle
        gameWordInitializer newGame = new gameWordInitializer(NUM_ROWS);
        //create valid board with a grid size of rowsxcols and a certain number of initial spots filled (only numbers in the board)

        ValidBoardGenerator validBoard = new ValidBoardGenerator(NUM_ROWS, NUM_COLS, initialSpotsFilled);
        //syncs gamewordarray with the validboardgenerator to hold the english and translation based on the number at a certain position

        subGridRowSize = validBoard.getSUBGRIDROWSIZE();
        subGridColSize = validBoard.getSUBGRIDCOLSIZE();

        newGame.syncGameWordArray(NUM_ROWS,NUM_COLS);
        //creates sudoku grid
        populateButtons();
        //creates list of words to insert into sudoku grid (in other language)
        makeGridForBottomWords();

        //calls timer to start
        timerCount = (TextView) findViewById(R.id.timer);
        //create timer object to be able to increment timer
        timer = new Timer();
        //call start timer to start the timer from 00 : 00 : 00
        timerStart();

        //create the lines for the grid (so people know where the subgrids are)
        createGridLines();

        //shows lives counter on screen
        TextView lives = findViewById(R.id.livesCounter);
        lives.setText("Lives Counter: "+livesCounter);
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
        TextView sudokuDisplay = findViewById(R.id.WORDDISPLAY);
        sudokuDisplay.setPadding(0, 0, 0, 0);
        // If the current grid space is empty we will be able to place a word inside of there
        if (ValidBoardGenerator.gameWordArray[row][col].getInitial() == 0){
            sudokuDisplay.setText(btn.getText());
            canPlace = false;
        }
        else if(ValidBoardGenerator.gameWordArray[row][col].getInitial() == 1){
            //holds data of the grid space the user clicks on
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

        if(NUM_ROWS == 9 || NUM_ROWS == 4) {
            bottomWordsRowSize = subGridRowSize;
            bottomWordsColSize = subGridColSize;
        }
        else{
            bottomWordsRowSize = subGridRowSize + 1;
            bottomWordsColSize = subGridColSize;
        }

        TableLayout table = (TableLayout) findViewById(R.id.tableForButtons);


        for(int row = 0; row < bottomWordsRowSize; row++){
            TableRow tableRow = new TableRow(StartGame.this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams (
                    TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));
            table.addView(tableRow);

            for(int cols = 0; cols < bottomWordsColSize; cols++){
                Button button = new Button(StartGame.this);
                button.setLayoutParams(new TableRow.LayoutParams (
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.MATCH_PARENT,
                        1.0f));

                button.setText(gameWordInitializer.englishArray[row*subGridRowSize + cols]);
                // Used to save index of bottom word in StartGame.gameWordArray so we can acess the wordClass
                final int truePosition = subGridRowSize*row + cols;
                button.setPadding(0, 0, 0, 0);
                tableRow.addView(button);

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
                TextView correctResult = findViewById(R.id.WORDDISPLAY);
                correctResult.setTextSize(20);
                correctResult.setText("");

                buttonPlacement.setTextSize(10);
                buttonPlacement.setText(ValidBoardGeneratorWord.getTranslation());
                ValidBoardGeneratorWord.setInitial(0);
                currentSpotsFilled++;

                if(currentSpotsFilled == NUM_COLS*NUM_ROWS){
                    //opens win screen if user fills in all the grid spaces (wins game)
                    int attempts;
                    Intent win = new Intent(StartGame.this, winScreen.class);
                    //pass in time to be saved in win screen class
                    //calculate user accuracy
                    attempts = livesLost + (currentSpotsFilled - initialSpotsFilled);
                    accuracy = 100 - 100*((double) livesLost/attempts);
                    win.putExtra("time",getTime());
                    win.putExtra("accuracy",accuracy);
                    //cancel timer
                    timer.cancel();
                    startActivity(win);
                    finish();
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
                        finish();
                    }
                    TextView lives = findViewById(R.id.livesCounter);
                    lives.setText("Lives Counter: "+livesCounter);

                    TextView incorrectResult = findViewById(R.id.WORDDISPLAY);
                    incorrectResult.setText("Wrong Word, Try Again!");
                    //increment lives lost
                    livesLost++;
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

    public void createGridLines(){
        ImageView sudokuBackground = findViewById(R.id.gridImage);
        //sudokuBackground.setBackgroundResource(R.drawable.sudokugrid9);
        if (NUM_COLS == 4){
            sudokuBackground.setBackgroundResource(R.drawable.sudokugrid4);
        }
        else if (NUM_COLS == 6){
            sudokuBackground.setBackgroundResource(R.drawable.sudokugrid6);
        }
        else if (NUM_COLS == 9){
            sudokuBackground.setBackgroundResource(R.drawable.sudokugrid9);
        }
        else if (NUM_COLS == 12){
            sudokuBackground.setBackgroundResource(R.drawable.sudokugrid12);
        }
    }


}


