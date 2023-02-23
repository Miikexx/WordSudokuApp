package com.example.myapplication;

// This class is a model class and has no control flow or view
// this class constructs the generation of a random sudoku 9x9 board using numbers 1-9 to form the logic and stores the game board in the wordClass array which is later
// synced to the eord pairs
public class ValidBoardGenerator {

    static wordClass[][] gameWordArray;

    public int rows;
    int cols;
    int SUBGRIDSIZE = 3;
    int initialSpotsFilled;
    static int numFilled;


    // getters and setters



    public void setGameWordArray(wordClass newWord, int row, int col) {
        if(CheckIfSafe(newWord.num, row, col)) {
            gameWordArray[row][col] = newWord;
        }

    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        if(rows >= 0) {
            this.rows = rows;
        }
        else{
            this.rows = -1;
        }
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        if(cols >= 0) {
            this.cols = cols;
        }
        else{
            this.cols = -1;
        }
    }

    public int getSUBGRIDSIZE() {
        return SUBGRIDSIZE;
    }

    public void setSUBGRIDSIZE(int SUBGRIDSIZE) {
        if(SUBGRIDSIZE >= 0) {
            this.SUBGRIDSIZE = SUBGRIDSIZE;
        }
        else{
            this.SUBGRIDSIZE = -1;
        }
    }

    public int getInitialSpotsFilled() {
        return initialSpotsFilled;
    }

    public void setInitialSpotsFilled(int initialSpotsFilled) {
        if(initialSpotsFilled >= 0) {
            this.initialSpotsFilled = initialSpotsFilled;
        }
        else{
            this.initialSpotsFilled = -1;
        }
    }

    public static int getNumFilled() {
        return numFilled;
    }

    public static void setNumFilled(int numFilled) {
        if(numFilled >= 0) {
            ValidBoardGenerator.numFilled = numFilled;
        }
    }


    // constructor which takes in the number of rows, cols and initial spots filled in to generate the board
    ValidBoardGenerator(int rows, int cols, int initialSpotsFilled) {

        setRows(rows);
        setCols(cols);
        this.initialSpotsFilled = initialSpotsFilled;
        gameWordArray = new wordClass[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int k = 0; k < cols; k++) {
                gameWordArray[i][k] = new wordClass();
                //this line may or may not work
                gameWordArray[i][k].num = -1;
            }
        }
        numFilled = rows * cols - initialSpotsFilled;
        fillValues();

    }

// fillValues is called from the constructor and is used to call all other methods needed to generate the rest of the game
    public void fillValues() {
        fill3SubGrids();
        fillRemaining(0, SUBGRIDSIZE);
        addNSpaces();
    }

    //The game logic is that we first fill the three subgrids that form a diagonal from the top left to bottom right because this will ensure a unique solution
    void fill3SubGrids() {
        int num;
        for (int r = 0; r < rows; r = r + SUBGRIDSIZE) {
            //fillBox(i, i);
            for (int i = 0; i < SUBGRIDSIZE; i++) {
                for (int j = 0; j < SUBGRIDSIZE; j++) {
                    do {
                        num = randomGenerator(rows);
                    }
                    while (!CheckIfSafe(r + i, r + j, num));
                    gameWordArray[r + i][r + j].setNum(num);

                }
            }
        }
    }

    // random number generator
    int randomGenerator(int num) {
        return (int) Math.floor((Math.random() * num + 1));
    }

    // used to check if a random number generated is safe to insert in its grid position
    // checks the immediate subgrid the row and column that it is in to check for duplicates
    boolean CheckIfSafe(int i, int j, int num) {
        for (int k = 0; k < rows; k++) {
            if (gameWordArray[i][k].getNum() == num) {
                return false;
            }

        }

        for (int p = 0; p < cols; p++) {
            if (gameWordArray[p][j].getNum() == num) {
                return false;
            }
        }

        int rowBegginingOfSubGrid = i - i % SUBGRIDSIZE;
        int colBegginingofSubGrid = j - j % SUBGRIDSIZE;

        for (int q = 0; q < SUBGRIDSIZE; q++) {
            for (int r = 0; r < SUBGRIDSIZE; r++) {
                if (gameWordArray[rowBegginingOfSubGrid + q][colBegginingofSubGrid + r].getNum() == num) {
                    return false;
                }
            }
        }
        return true;
    }

    boolean fillRemaining(int i, int j) {
        if (j >= 9 && i < 9 - 1) {
            i = i + 1;
            j = 0;
        }

        if (i >= 9 && j >= 9)
            return true;
        if (i < SUBGRIDSIZE) {
            if (j < SUBGRIDSIZE)
                j = SUBGRIDSIZE;
        } else if (i < 9 - SUBGRIDSIZE) {
            if (j == (int) (i / SUBGRIDSIZE) * SUBGRIDSIZE)

                j = j + SUBGRIDSIZE;
        } else {

            if (j == 9 - SUBGRIDSIZE) {
                i = i + 1;
                j = 0;
                if (i >= 9) {

                    return true;
                }
            }
        }


        for (int num = 1; num <= 9; num++) {
            if (CheckIfSafe(i, j, num)) {

                gameWordArray[i][j].setNum(num);

                if (fillRemaining(i, j + 1)) {
                    return true;
                }
                gameWordArray[i][j].setNum(0);

            }
        }
        return false;
    }



    // after generation of board is done, we set the "initial" value of the wordClass to 1 or 0 depending on if-
    // we want it to appear as one of the starting spots on the grid, it is done randomly
    public void addNSpaces() {
        int count = initialSpotsFilled;
        while (count != 0) {
            int cellId = randomGenerator(rows*cols)-1;
            int i = (cellId/9);
            int j = cellId%9;

            if (gameWordArray[i][j].getInitial() == 1) {
                count--;
                gameWordArray[i][j].setInitial(0);
            }
        }
    }
}