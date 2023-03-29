package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.SoundPool;
import android.os.Bundle;

import android.telephony.PhoneNumberUtils;
import android.util.Log;
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
    //timer in game starts at 0 seconds
    Double timeInSeconds = 0.0;

    // Used as a back button so that the user can go back to the main screen
    // Mostly for testing purposes. May remove later.
    Button tempButton;

    //pass in data of number rows and cols from pre game screen
    int rowsNcols;

    //pass in data of difficulty level from pre game screen
    private static String difficultyLevel;
    //percentage of grid filled based on difficulty of game chosen
    private static double percentageOfGridFilled;


    // num rows and cols are used as a variable to store the dimensions of the game board grid
    private static int NUM_ROWS;
    private static int NUM_COLS;

    //Variables to handle the size of the sub grids.
    private static int subGridRowSize;
    private static int subGridColSize;

    //Variables for the creation of the words below he grid that can be placed in the grid.
    private static int bottomWordsRowSize;
    private static int bottomWordsColSize;

    //variables to keep track of grid space state:

    //buttonPlacementRow and col are used to save the index of the clicked button so we can compare it with the actual solution
    int buttonPlacementRow;
    int buttonPlacementCol;
    //used to save the button clicked on to change the text that appears on the gameBoard square
    Button buttonPlacement;
    //This is used to help as one way to determine whether or not someone can place a word in a "clicked" slot
    boolean canPlace = false;

    //sound variables:

    //soundpool object for listening comprehension feature
    private SoundPool sounds;
    //sound ID for all the translation audio files
    private int sound1,sound2,sound3,sound4,sound5,sound6,sound7,sound8,sound9,sound10,sound11,sound12;
    //this variable is true when the user selects listening mode
    boolean voiceMode = false;

    // this is where the code starts executing from when the user clicks start game on the main screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);
        Bundle extra = getIntent().getExtras();

        if(savedInstanceState != null){
            rowsNcols = savedInstanceState.getInt("Row Amount");
        }
        else if(extra != null) {
            //recieve data from previous activity
            rowsNcols = extra.getInt("gridSizeTag");
            difficultyLevel = extra.getString("difficultyTag");
            voiceMode = extra.getBoolean("voiceModeTag");
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
                    livesCounter = 999;
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

        //calculates initialSpotsFilled based on grid size and difficulty level
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

        //gameWordInitializer holds the array of wordclass objects to be placed into the sudoku puzzle
        gameWordInitializer newGame = new gameWordInitializer(NUM_ROWS);
        //create valid board with a grid size of rowsxcols and a certain number of initial spots filled (only numbers in the board)

        ValidBoardGenerator validBoard = new ValidBoardGenerator(NUM_ROWS, NUM_COLS, initialSpotsFilled);
        //syncs gamewordarray with the validboardgenerator to hold the english and translation based on the number at a certain position

        //get size of rows and columns
        subGridRowSize = validBoard.getSUBGRIDROWSIZE();
        subGridColSize = validBoard.getSUBGRIDCOLSIZE();

        if(savedInstanceState != null){
            int[][] toPlaceInitials = new int[NUM_ROWS][NUM_COLS];
            int[][] toPlaceNums = new int[NUM_ROWS][NUM_COLS];
            String[][] toPlaceEnglish = new String[NUM_ROWS][NUM_COLS];
            String[][] toPlaceTranslation = new String[NUM_ROWS][NUM_COLS];
            for(int i = 0; i < NUM_ROWS; i++){
                String toPlace = Integer.toString(i);
                toPlaceInitials[i] = savedInstanceState.getIntArray("Initials " + toPlace);
                toPlaceNums[i] = savedInstanceState.getIntArray("Nums " + toPlace);
                toPlaceEnglish[i] = savedInstanceState.getStringArray("English " + toPlace);
                toPlaceTranslation[i] = savedInstanceState.getStringArray("Translation " + toPlace);
            }
            for(int i = 0; i < NUM_ROWS; i++) {
                for (int j = 0; j < NUM_COLS; j++) {
                    wordClass placeWord = new wordClass();
                    placeWord.setInitial(toPlaceInitials[i][j]);
                    placeWord.setNum(toPlaceNums[i][j]);
                    placeWord.setEnglish(toPlaceEnglish[i][j]);
                    placeWord.setTranslation((toPlaceTranslation[i][j]));
                    validBoard.gameWordArray[i][j] = placeWord;
                }
            }
            currentSpotsFilled = savedInstanceState.getInt("Spots Filled");
            livesLost = savedInstanceState.getInt("Lives");
            livesCounter -= livesLost;
            accuracy = savedInstanceState.getDouble("Accuracy");

        }

        //Syncs the array in valid board generator so each grid space has the correct english word and its translation based on the number in the grid
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


        // Create an instance of the SoundPool class
        sounds = new SoundPool.Builder().setMaxStreams(1).build();

        //load in all the sounds to use in listening comprehension mode:
        sound1 = sounds.load(this,R.raw.pomme,1);
        sound2 = sounds.load(this,R.raw.tu,1);
        sound3 = sounds.load(this,R.raw.et,1);
        sound4 = sounds.load(this,R.raw.monsieur,1);
        sound5 = sounds.load(this,R.raw.porte,1);
        sound6 = sounds.load(this,R.raw.bien,1);
        sound7 = sounds.load(this,R.raw.content,1);
        sound8 = sounds.load(this,R.raw.jouer,1);
        sound9 = sounds.load(this,R.raw.manger,1);
        sound10 = sounds.load(this,R.raw.avec,1);
        sound11 = sounds.load(this,R.raw.aller,1);
        sound12 = sounds.load(this,R.raw.triste,1);

    }


    // makes the grid table for the game using buttons and also initalizes some of the buttons as the actual solution
    // leaves some of the buttons without text (player places the words in). The function also saves the row and column index if the user
    // decides to click on the grid space
    private void populateButtons(){
        TableLayout table = (TableLayout) findViewById(R.id.tableForButtons);

        //Big for loop to create each button, depending on the size of the grid.
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

                //Variables needed for the clickedGridSpace() function.
                final int currentColumn = cols;
                final int currentRow = row;

                String tempWord = ValidBoardGenerator.gameWordArray[row][cols].getTranslation();
                int tempNum = ValidBoardGenerator.gameWordArray[row][cols].getNum();

                // if initial set to 1 then it will not be displayed in the game board otherwise if initial is 0, then it will be displayed
                if(ValidBoardGenerator.gameWordArray[row][cols].getInitial() != 0) {
                    tempWord = "  ";
                }
                else{
                    //check which mode the game is on to set the buttons to numbers or words:
                    if(voiceMode){
                        button.setText(String.valueOf(tempNum));
                    }
                    else {
                        button.setText(tempWord);
                    }
                }
                tableRow.addView(button);

                button.setTextColor(Color.parseColor("#FF000000"));
                button.setMaxLines(1);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    // sets an on click listener for each button in the grid so that it says the column and the row that it is in
                    public void onClick(View view) {
                        clickedGridSpace(button, currentRow, currentColumn);
                    }
                });

            }
        }
    }

    //this function is meant to display the word that the user clicked on at the top of the grid (some words are too small to fit
    //in the cell) but will not display a word if the cell clicked is blank. The function also helps to validate canPLace to true or
    // false, so the user can insert into the button and saves the column and rox index
    private void clickedGridSpace(Button btn, int row, int col){
        TextView sudokuDisplay = findViewById(R.id.WORDDISPLAY);
        sudokuDisplay.setPadding(0, 0, 0, 0);
        // If the current grid space is empty we will be able to place a word inside of there
        if (ValidBoardGenerator.gameWordArray[row][col].getInitial() == 0){
            //play audio depending on the translation of the word clicked
            String translation = ValidBoardGenerator.gameWordArray[row][col].getTranslation();
            if(voiceMode) {
                playSound(translation);
            }
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



    // this makes the grid at the button for thw word pairs for the user to see and interact with, if the user clicks on a word,
    //the word is saved and used to compare whether or not the word the user clicked is the actual solution
    private void makeGridForBottomWords(){

        //Setting the rows and columns to be even.
        if(NUM_ROWS == 9 || NUM_ROWS == 4) {
            bottomWordsRowSize = subGridRowSize;
            bottomWordsColSize = subGridColSize;
        }
        else{
            bottomWordsRowSize = subGridRowSize + 1;
            bottomWordsColSize = subGridColSize - 1;
        }

        TableLayout table = (TableLayout) findViewById(R.id.tableForButtons);

        //Big for loop to create each button.
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
                // Used to save index of bottom word in StartGame.gameWordArray so we can access the wordClass
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
            wordClass ValidBoardGeneratorWord = ValidBoardGenerator.gameWordArray[buttonPlacementRow][buttonPlacementCol];

            //If the word is correct.
            if(gameWordInitializer.gameWordArray[wordPos].getEnglish()== ValidBoardGeneratorWord.getEnglish() && ValidBoardGeneratorWord.getInitial() == 1 ){
                TextView correctResult = findViewById(R.id.WORDDISPLAY);
                correctResult.setTextSize(20);
                correctResult.setText(" ");

                buttonPlacement.setTextSize(10);
                if(voiceMode){
                    buttonPlacement.setText(String.valueOf(ValidBoardGeneratorWord.getNum()));
                }
                else {
                    buttonPlacement.setText(ValidBoardGeneratorWord.getTranslation());
                }
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
        //timer.scheduleAtFixedRate makes this function call run every 1 second
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

    //Function that sets the background to demonstrate where each sub grid is.
    public void createGridLines(){
        ImageView sudokuBackground = findViewById(R.id.gridImage);

        //Default for error check.
        sudokuBackground.setBackgroundResource(R.drawable.sudokugrid9);

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

    //Saving details of the game board for switching orientation.
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        int[][] arrayOfInitials = new int[NUM_ROWS][NUM_COLS];
        int[][] arrayOfNumLogic = new int[NUM_ROWS][NUM_COLS];
        String[][] arrayOfEnglish = new String[NUM_ROWS][NUM_COLS];
        String[][] arrayOfTranslation = new String[NUM_ROWS][NUM_COLS];

        //Put each aspect of each wordClass into four arrays.
        for(int i = 0; i < NUM_ROWS; i++){
            for(int j = 0; j< NUM_COLS; j++){
                arrayOfInitials[i][j] = ValidBoardGenerator.gameWordArray[i][j].getInitial();
                arrayOfNumLogic[i][j] = ValidBoardGenerator.gameWordArray[i][j].getNum();
                arrayOfEnglish[i][j] = ValidBoardGenerator.gameWordArray[i][j].getEnglish();
                arrayOfTranslation[i][j] = ValidBoardGenerator.gameWordArray[i][j].getTranslation();
            }
        }

        //Put each row into savedInstanceState.
        for(int i = 0; i < NUM_ROWS; i++){
            String currentRow = Integer.toString(i);
            savedInstanceState.putIntArray("Initials " + currentRow, arrayOfInitials[i]);
            savedInstanceState.putIntArray("Nums " + currentRow, arrayOfNumLogic[i]);
            savedInstanceState.putStringArray("English " + currentRow, arrayOfEnglish[i]);
            savedInstanceState.putStringArray("Translation " + currentRow, arrayOfTranslation[i]);
        }

        savedInstanceState.putInt("Spots Filled", currentSpotsFilled);
        savedInstanceState.putInt("Row Amount", NUM_ROWS);
        savedInstanceState.putInt("Col Amount", NUM_COLS);
        savedInstanceState.putInt("Lives", livesLost);
        savedInstanceState.putDouble("Accuracy", accuracy);


        //This is needed everytime we do something like this.
        super.onSaveInstanceState(savedInstanceState);
    }

    //function that plays sounds depending on the word that is clicked
    public void playSound(String translation){
        switch(translation){
            case "POMME" : sounds.play(sound1,1,1,0,0,1);
                break;
            case "TU" : sounds.play(sound2,1,1,0,0,1);
                break;
            case "ET" : sounds.play(sound3,1,1,0,0,1);
                break;
            case "MONSIEUR" : sounds.play(sound4,1,1,0,0,1);
                break;
            case "PORTE" : sounds.play(sound5,1,1,0,0,1);
                break;
            case "BIEN" : sounds.play(sound6,1,1,0,0,1);
                break;
            case "CONTENT" : sounds.play(sound7,1,1,0,0,1);
                break;
            case "JOUER" : sounds.play(sound8,1,1,0,0,1);
                break;
            case "MANGER" : sounds.play(sound9,1,1,0,0,1);
                break;
            case "AVEC" : sounds.play(sound10,1,1,0,0,1);
                break;
            case "ALLER" : sounds.play(sound11,1,1,0,0,1);
                break;
            case "TRISTE" : sounds.play(sound12,1,1,0,0,1);
                break;
            default:
                break;

        }

    }

    //close soundpool
    @Override
    protected void onDestroy(){
        super.onDestroy();
        sounds.release();
        sounds = null;
    }

}


