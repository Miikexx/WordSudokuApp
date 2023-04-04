package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.SoundPool;
import android.os.Bundle;

import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.ImageButton;
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
    //Variable counts how many hints were used.
    int numberOfHints = 3;
    //textview of timer, timer count variables and time variable
    TextView timerCount;

    TextView sudokuDisplay;
    Timer timer;
    TimerTask timerTask;
    //timer in game starts at 0 seconds
    Double timeInSeconds = 0.0;

    // Used as a back button so that the user can go back to the main screen
    // Mostly for testing purposes. May remove later.
    Button tempButton;

    ImageButton hintButton;

    //Colour variable so the buttons that are filled on the grid are visible.
    ColorStateList filledColour;
    ColorStateList filledColour2;

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
    //This variable checks if Hint Mode is active.
    boolean hintActive = false;

    //Mode Variable:

    //Variable to determine if the grid displays English or French.
    boolean englishGrid = false;

    //sound variables:

    //soundpool object for listening comprehension feature
    private SoundPool sounds;
    //sound ID for all the translation audio files
    private int frenchSound1,frenchSound2,frenchSound3,frenchSound4,frenchSound5,frenchSound6,frenchSound7,frenchSound8,frenchSound9,frenchSound10,frenchSound11,frenchSound12;
    //this variable is true when the user selects listening mode
    private int englishSound1,englishSound2,englishSound3,englishSound4,englishSound5,englishSound6,englishSound7,englishSound8,englishSound9,englishSound10,englishSound11,englishSound12;

    boolean voiceMode = false;

    // this is where the code starts executing from when the user clicks start game on the main screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);
        Bundle extra = getIntent().getExtras();
        filledColour = ColorStateList.valueOf(Color.parseColor("#00FFFF"));
        filledColour2 = ColorStateList.valueOf(Color.parseColor("#FFFFF176"));

        if(savedInstanceState != null){
            rowsNcols = savedInstanceState.getInt("Row Amount");
            voiceMode = savedInstanceState.getBoolean("Sound");
            englishGrid = savedInstanceState.getBoolean("Mode");
        }
        else if(extra != null) {
            //recieve data from previous activity
            rowsNcols = extra.getInt("gridSizeTag");
            difficultyLevel = extra.getString("difficultyTag");
            voiceMode = extra.getBoolean("voiceModeTag");
            englishGrid = extra.getBoolean("translationModeTag");
            //GRAB MODE
        }

        //sets the number of rows and columns based on what user selected
        NUM_ROWS = rowsNcols;
        NUM_COLS = rowsNcols;
        livesLost = 0;

        //SET percentage of grid filled based on difficulty level

        percentageOfGridFilled = setPercentageOfGridFilled();

        /*
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

         */



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
        // CREATE HINT BUTTON

        hintButton = findViewById(R.id.hintButton);

        hintButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(hintActive){
                    hintActive = false;
                    hintButton.setBackgroundTintList(filledColour2);
                }
                else{
                    if(numberOfHints != 0) {
                        hintActive = true;
                        hintButton.setBackgroundTintList(filledColour);
                    }
                }
            }
        });

        //calling all functions to start the game this is the control flow and cannot be tested

        //gameWordInitializer holds the array of wordclass objects to be placed into the sudoku puzzle
        gameWordInitializer newGame = new gameWordInitializer(NUM_ROWS);
        //create valid board with a grid size of rowsxcols and a certain number of initial spots filled (only numbers in the board)
        ValidBoardGenerator validBoard = new ValidBoardGenerator(NUM_ROWS, NUM_COLS, initialSpotsFilled);
        //get size of rows and columns
        subGridRowSize = validBoard.getSUBGRIDROWSIZE();
        subGridColSize = validBoard.getSUBGRIDCOLSIZE();
        //syncs gamewordarray with the validboardgenerator to hold the english and translation based on the number at a certain position

        //Getting all saved values.
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
            //get size of rows and columns
            subGridRowSize = validBoard.getSUBGRIDROWSIZE();
            subGridColSize = validBoard.getSUBGRIDCOLSIZE();
            currentSpotsFilled = savedInstanceState.getInt("Spots Filled");
            livesLost = savedInstanceState.getInt("Lives");
            livesCounter -= livesLost;
            numberOfHints = savedInstanceState.getInt("Hints");
            accuracy = savedInstanceState.getDouble("Accuracy");

        }

        //Syncs the array in valid board generator so each grid space has the correct english word and its translation based on the number in the grid
        newGame.syncGameWordArray(NUM_ROWS,NUM_COLS);
        //creates sudoku grid
        populateButtons();
        //creates list of words to insert into sudoku grid (in other language)
        makeGridForBottomWords();

        sudokuDisplay = findViewById(R.id.WORDDISPLAY);
        sudokuDisplay.setPadding(0, 0, 0, 0);

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
        frenchSound1 = sounds.load(this,R.raw.pomme,1);
        frenchSound2 = sounds.load(this,R.raw.tu,1);
        frenchSound3 = sounds.load(this,R.raw.et,1);
        frenchSound4 = sounds.load(this,R.raw.monsieur,1);
        frenchSound5 = sounds.load(this,R.raw.porte,1);
        frenchSound6 = sounds.load(this,R.raw.bien,1);
        frenchSound7 = sounds.load(this,R.raw.content,1);
        frenchSound8 = sounds.load(this,R.raw.jouer,1);
        frenchSound9 = sounds.load(this,R.raw.manger,1);
        frenchSound10 = sounds.load(this,R.raw.avec,1);
        frenchSound11 = sounds.load(this,R.raw.aller,1);
        frenchSound12 = sounds.load(this,R.raw.triste,1);

        englishSound1 = sounds.load(this,R.raw.apple,1);
        englishSound2 = sounds.load(this,R.raw.you,1);
        englishSound3 = sounds.load(this,R.raw.and,1);
        englishSound4 = sounds.load(this,R.raw.gentleman,1);
        englishSound5 = sounds.load(this,R.raw.gate,1);
        englishSound6 = sounds.load(this,R.raw.glad,1);
        englishSound7 = sounds.load(this,R.raw.good,1);
        englishSound8 = sounds.load(this,R.raw.play,1);
        englishSound9 = sounds.load(this,R.raw.eat,1);
        englishSound10 = sounds.load(this,R.raw.with,1);
        englishSound11 = sounds.load(this,R.raw.go,1);
        englishSound12 = sounds.load(this,R.raw.sad,1);


        //    static String englishArray[] = {"APPLE", "YOU", "AND", "GENTLEMAN", "GATE", "GOOD", "GLAD", "PLAY", "EAT", "WITH", "GO", "SAD" };
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

                //Setting values for the buttons based on the different modes.
                String tempWord;
                if(englishGrid){
                    tempWord = ValidBoardGenerator.gameWordArray[row][cols].getEnglish();
                }
                else{
                    tempWord = ValidBoardGenerator.gameWordArray[row][cols].getTranslation();
                }
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

                    //Colour the button.
                    button.setBackgroundTintList(filledColour);
                }
                tableRow.addView(button);

                button.setTextColor(Color.parseColor("#FF000000"));
                button.setMaxLines(1);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // sets an on click listener for each button in the grid so that it says the column and the row that it is in
                        //Only does this if hintActive is false
                        if(!hintActive){
                            clickedGridSpace(button, currentRow, currentColumn);
                        }
                        else{
                            performHint(button, currentRow, currentColumn);
                        }
                    }
                });

            }
        }
    }

    //this function is meant to display the word that the user clicked on at the top of the grid (some words are too small to fit
    //in the cell) but will not display a word if the cell clicked is blank. The function also helps to validate canPLace to true or
    // false, so the user can insert into the button and saves the column and rox index
    private void clickedGridSpace(Button btn, int row, int col){
        // If the current grid space is empty we will be able to place a word inside of there
        if (ValidBoardGenerator.gameWordArray[row][col].getInitial() == 0){
            //play audio depending on the translation of the word clicked
            String translation;
            if(englishGrid){
                translation = ValidBoardGenerator.gameWordArray[row][col].getEnglish();
            }
            else{
                translation = ValidBoardGenerator.gameWordArray[row][col].getTranslation();
            }

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

                //Setting the different modes
                if (englishGrid){
                    button.setText(gameWordInitializer.gameWordArray[row*subGridRowSize + cols].getTranslation());
                }
                else{
                    button.setText(gameWordInitializer.gameWordArray[row*subGridRowSize + cols].getEnglish());
                }

                // Used to save index of bottom word in StartGame.gameWordArray so we can access the wordClass
                final int truePosition = subGridRowSize*row + cols;
                button.setPadding(0, 0, 0, 0);
                button.setBackgroundTintList(filledColour2);
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
                buttonPlacement.setTextColor(Color.parseColor("#FF000000"));
                buttonPlacement.setBackgroundTintList(filledColour);

                buttonPlacement.setTextSize(10);
                if(voiceMode){
                    buttonPlacement.setText(String.valueOf(ValidBoardGeneratorWord.getNum()));
                }
                else {
                    //SHOULD PLACED WORDS BE SAME OR DIFFERENT?
                    if (englishGrid){
                        buttonPlacement.setText(ValidBoardGeneratorWord.getEnglish());
                    }
                    else{
                        buttonPlacement.setText(ValidBoardGeneratorWord.getTranslation());
                    }
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

    //Function to fill in grid spots when the hint option is checked.
    private void performHint(Button toHint, int hintRow, int hintCol){
        wordClass wordHint = ValidBoardGenerator.gameWordArray[hintRow][hintCol];
        if(wordHint.getInitial() != 0 && numberOfHints != 0 && (currentSpotsFilled + 3) < NUM_ROWS*NUM_COLS){
            toHint.setTextColor(Color.parseColor("#FF000000"));
            toHint.setBackgroundTintList(filledColour);
            toHint.setTextSize(10);
            if(voiceMode){
                toHint.setText(String.valueOf(wordHint.getNum()));
            }
            else {
                //SHOULD PLACED WORDS BE SAME OR DIFFERENT?
                if (englishGrid){
                    toHint.setText(wordHint.getEnglish());
                }
                else{
                    toHint.setText(wordHint.getTranslation());
                }
            }

            wordHint.setInitial(0);
            currentSpotsFilled++;
            numberOfHints--;
            hintButton.setBackgroundTintList(filledColour2);
            hintActive = false;
        }
        //THEN A BUNCH OF WRONG CASES. For example, one for clicking a word that's already filled.
        //I need UI stuff before I can do that.
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
            if (!voiceMode) {
                sudokuBackground.setBackgroundResource(R.drawable.sudokugrid9);
            }
            else{
                sudokuBackground.setBackgroundResource(R.drawable.soundsudokugrid);
            }
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

        //Put each aspect of each wordClass in the grid into four arrays.
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
        savedInstanceState.putInt("Hints", numberOfHints);
        savedInstanceState.putBoolean("Mode", englishGrid);
        savedInstanceState.putBoolean("Sound", voiceMode);


        //This is needed everytime we do something like this.
        super.onSaveInstanceState(savedInstanceState);
    }

    //function that plays sounds depending on the word that is clicked
    //ADD MORE AND MAKE IT SO ENGLISH CAN ALSO BE SAID
    public void playSound(String translation){
        if(!englishGrid) {
            switch (translation) {
                case "POMME":
                    sounds.play(frenchSound1, 1, 1, 0, 0, 1);
                    break;
                case "TU":
                    sounds.play(frenchSound2, 1, 1, 0, 0, 1);
                    break;
                case "ET":
                    sounds.play(frenchSound3, 1, 1, 0, 0, 1);
                    break;
                case "MONSIEUR":
                    sounds.play(frenchSound4, 1, 1, 0, 0, 1);
                    break;
                case "PORTE":
                    sounds.play(frenchSound5, 1, 1, 0, 0, 1);
                    break;
                case "BIEN":
                    sounds.play(frenchSound6, 1, 1, 0, 0, 1);
                    break;
                case "CONTENT":
                    sounds.play(frenchSound7, 1, 1, 0, 0, 1);
                    break;
                case "JOUER":
                    sounds.play(frenchSound8, 1, 1, 0, 0, 1);
                    break;
                case "MANGER":
                    sounds.play(frenchSound9, 1, 1, 0, 0, 1);
                    break;
                case "AVEC":
                    sounds.play(frenchSound10, 1, 1, 0, 0, 1);
                    break;
                case "ALLER":
                    sounds.play(frenchSound11, 1, 1, 0, 0, 1);
                    break;
                case "TRISTE":
                    sounds.play(frenchSound12, 1, 1, 0, 0, 1);
                    break;
                default:
                    break;

            }
        }
        else{
            switch(translation){
                case "APPLE":
                    sounds.play(englishSound1, 1, 1, 0, 0, 1);
                    break;
                case "YOU":
                    sounds.play(englishSound2, 1, 1, 0, 0, 1);
                    break;
                case "AND":
                    sounds.play(englishSound3, 1, 1, 0, 0, 1);
                    break;
                case "GENTLEMAN":
                    sounds.play(englishSound4, 1, 1, 0, 0, 1);
                    break;
                case "GATE":
                    sounds.play(englishSound5, 1, 1, 0, 0, 1);
                    break;
                case "GOOD":
                    sounds.play(englishSound6, 1, 1, 0, 0, 1);
                    break;
                case "GLAD":
                    sounds.play(englishSound7, 1, 1, 0, 0, 1);
                    break;
                case "PLAY":
                    sounds.play(englishSound8, 1, 1, 0, 0, 1);
                    break;
                case "EAT":
                    sounds.play(englishSound9, 1, 1, 0, 0, 1);
                    break;
                case "WITH":
                    sounds.play(englishSound10, 1, 1, 0, 0, 1);
                    break;
                case "GO":
                    sounds.play(englishSound11, 1, 1, 0, 0, 1);
                    break;
                case "SAD":
                    sounds.play(englishSound12, 1, 1, 0, 0, 1);
                    break;
                default:
                    break;
            }
        }

    }

    //sets the percentage of grid filled based on difficulty level
    public double setPercentageOfGridFilled(){
        double percentageOfGridFilled = 1;
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
        return percentageOfGridFilled;
    }

    //close soundpool
    @Override
    protected void onDestroy(){
        super.onDestroy();
        sounds.release();
        sounds = null;
    }

}


