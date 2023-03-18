package com.example.myapplication;

// This class is a model class and has no control flow or view
// this class constructs the generation of a random sudoku 9x9 board using numbers 1-9 to form the logic and stores the game board in the wordClass array which is later
// synced to the word pairs
public class ValidBoardGenerator {

    //Array will hold all of the words in the grid.
    static wordClass[][] gameWordArray;

    //Variables for the number of rows and columns of the grid. Used for size purposes.
    private int rows;
    private int cols;

    //Variables to determine the number of rows and columns of each subgrid. Used for calculating solutions.
    private int SUBGRIDROWSIZE;
    private int SUBGRIDCOLSIZE;

    //Variables to determine, respectively, the number of "empty" spots and the number of filled spots.
    private int initialSpotsFilled;
    private static int numFilled;

    //Variable to store the randomly generated number. Used for calculating solutions.
    int randomNum = 5;


    // getters and setters

    public void setGameWordArray(wordClass newWord, int row, int col) {

        //Make sure newWord can be placed in the selected spot.
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

        //Eroor handling in case there's a negative number of rows.
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

        //Error handling in case there's a negative number of columns.
        else{
            this.cols = -1;
        }
    }


    public int getSUBGRIDROWSIZE() {
        return this.SUBGRIDROWSIZE;
    }

    public int getSUBGRIDCOLSIZE() {
        return this.SUBGRIDCOLSIZE;
    }

    public void setSUBGRIDROWSIZE(int SUBGRIDROWSIZE) {
        if(SUBGRIDROWSIZE >= 0) {
            this.SUBGRIDROWSIZE = SUBGRIDROWSIZE;
        }

        //Error handling in case there's a negative number of rows.
        else{
            this.SUBGRIDROWSIZE = -1;
        }
    }

    public void setSUBGRIDCOLSIZE(int SUBGRIDCOLSIZE) {
        if(SUBGRIDCOLSIZE >= 0) {
            this.SUBGRIDCOLSIZE = SUBGRIDCOLSIZE;
        }

        //Error handling in case there's a negative number of columns.
        else{
            this.SUBGRIDCOLSIZE = -1;
        }
    }



    public int getInitialSpotsFilled() {
        return initialSpotsFilled;
    }

    public void setInitialSpotsFilled(int initialSpotsFilled) {
        if(initialSpotsFilled >= 0) {
            this.initialSpotsFilled = initialSpotsFilled;
        }

        //Error handling in case the number of empty spots is set to a negative number.
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

        //Error handling in case the number of filled slots is set to a negative number.
        else{
            ValidBoardGenerator.numFilled = 0;
        }
    }

    //arrays for 6x6 and 12x12 board to be randomized
    int array6x6[][] = {

            {3,5,2, 4,1,6},

            {6,4,1, 5,3,2},


            {2,6,3, 1,4,5},

            {4,1,5, 2,6,3},


            {5,3,4, 6,2,1},

            {1,2,6, 3,5,4},


    };

    int array12x12[][] = {

            {12,11,10,8, 7,5,6,3, 1,4,2,9},

            {3,4,7,6, 2,9,1,11, 12,8,5,10},

            {5,9,2,1, 8,4,12,10, 6,7,11,3},


            {4,2,8,5, 9,6,11,1, 10,3,7,12},

            {1,7,6,10, 12,3,4,8, 11,2,9,5},

            {11,12,9,3, 5,2,10,7, 4,6,8,1},


            {10,6,5,4, 11,8,9,2, 3,12,1,7},

            {9,3,1,2, 4,10,7,12, 8,5,6,11},

            {7,8,12,11, 3,1,5,6, 2,9,10,4},


            {8,1,11,12, 6,7,3,9, 5,10,4,2},

            {2,5,3,7, 10,11,8,4, 9,1,12,6},

            {6,10,4,9, 1,12,2,5, 7,11,3,8},

    };


    // constructor which takes in the number of rows, cols and initial spots filled in to generate the board
    ValidBoardGenerator(int rows, int cols, int initialSpotsFilled) {

        setRows(rows);
        setCols(cols);
        setInitialSpotsFilled(initialSpotsFilled);
        gameWordArray = new wordClass[rows][cols];

        //depending on the grid size, the subgrid size varies as well as the way the grid is generated
        if(getRows() == 9 || getRows() == 4){
            setSUBGRIDROWSIZE((int)Math.sqrt(rows));
            setSUBGRIDCOLSIZE((int)Math.sqrt(rows));

            //Creating default words throughout the array.
            for (int i = 0; i < rows; i++) {
                for (int k = 0; k < cols; k++) {
                    gameWordArray[i][k] = new wordClass();
                    gameWordArray[i][k].num = -1;
                }
            }
            numFilled = rows * cols - initialSpotsFilled;
            fillValues();

        }
        else if(getRows() == 6){
         setSUBGRIDROWSIZE(2);
         setSUBGRIDCOLSIZE(3);
         switchNums6x6();
         switchColumnsandRows6x6();
         switchType6x6();
         addNSpaces();
        }
        else if(getRows() == 12){
            setSUBGRIDROWSIZE(3);
            setSUBGRIDCOLSIZE(4);
            switchNums12x12();
            switchColumnsandRows12x12();
            switchType12x12();
            addNSpaces();

        }

    }

// fillValues is called from the constructor in the 9x9 or 4x4 case and is used to call all other methods needed to generate the rest of the grid
    public void fillValues() {
        fill3SubGrids();
        if(!completeBoard(0, SUBGRIDROWSIZE)){
            //makes 4x4 grid generation valid if it is not already
            if(SUBGRIDROWSIZE == 2){
                int temp;
                temp = gameWordArray[2][3].getNum();
                gameWordArray[2][3].setNum(gameWordArray[3][2].getNum());
                gameWordArray[3][2].setNum(temp);
                completeBoard(0, SUBGRIDROWSIZE);
            }
        }
        addNSpaces();
    }

    //The game logic is that we first fill the three subgrids that form a diagonal from the top left to bottom right because this will ensure a unique solution
    void fill3SubGrids() {
        int num;
        for (int r = 0; r < rows; r = r + SUBGRIDROWSIZE) {
            for (int i = 0; i < SUBGRIDROWSIZE; i++) {
                for (int j = 0; j < SUBGRIDROWSIZE; j++) {

                    //Based on rows, generate a random number until the number can be safely inserted.
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

        //Checking the row.
        for (int k = 0; k < rows; k++) {
            if (gameWordArray[i][k].getNum() == num) {

                return false;
            }

        }

        //Checking the column.
        for (int p = 0; p < cols; p++) {
            if (gameWordArray[p][j].getNum() == num) {
                return false;
            }
        }

        int rowBegginingOfSubGrid = i - i % SUBGRIDROWSIZE;
        int colBegginingofSubGrid = j - j % SUBGRIDCOLSIZE;

        //Checking the subgrid.
        for (int q = 0; q < SUBGRIDROWSIZE; q++) {
            for (int r = 0; r < SUBGRIDCOLSIZE; r++) {
                if (gameWordArray[rowBegginingOfSubGrid + q][colBegginingofSubGrid + r].getNum() == num) {
                    return false;
                }
            }
        }
        return true;
    }


    //Fill in the rest of the grid.
    //Works best for 9x9, which is why the other lines in fillValues() are needed for 4x4.
boolean completeBoard(int rowIndex, int colIndex) {
    int full = 0;

    //moves to the next row if the end of a column is reached
    if (colIndex >= cols && rowIndex < rows - 1) {
        rowIndex = rowIndex + 1;
        colIndex = 0;
    }
    //subgrid size is 3
    if (rowIndex >= rows && colIndex >= cols) {

        return true;
    }
    //checks if parameters are located in first diagonal and if so, makes the first diagonal square get skipped [0,0], [1,0], [2,0]
    if (rowIndex < SUBGRIDROWSIZE && colIndex < SUBGRIDROWSIZE) {
            colIndex = SUBGRIDROWSIZE;
    }
    //checks if parameters are located in second diagonal and if so, makes the second diagonal square get skipped [3,3], [4,3], [5,3]
    else if (rowIndex < rows - SUBGRIDROWSIZE) {
        if (colIndex == (int) (rowIndex / SUBGRIDROWSIZE) * SUBGRIDROWSIZE)
            if(rows == 9) {
                colIndex += SUBGRIDROWSIZE;
            }
            else{
                if(colIndex < 3) {
                    colIndex = 0;
                }
            }
    }
    //makes the third diagonal square get skipped  [6,6], [7,6], [8,6], return true
    //if the grid goes to row 9 (doesn't exist), return true as completeBoard is done
    else {

        if (colIndex == cols - SUBGRIDROWSIZE) {
            rowIndex++;
            colIndex = 0;
            if (rowIndex >= rows) {
                return true;
            }
        }
    }
    //go through numbers from 1 to 9 for each grid spot and check if they are safe, if it is then setNum to that number and then
    //calls completeBoard function on coordinate, i and j+1
    for (int num = 1; num <= rows; num++) {
        if (CheckIfSafe(rowIndex, colIndex, num)) {
            gameWordArray[rowIndex][colIndex].setNum(num);

            if (completeBoard(rowIndex, colIndex + 1)) {
                return true;
            }
            gameWordArray[rowIndex][colIndex].setNum(0);

        }

        full++;
        //if board is not generated properly, return false
        if(full == rows){
            return false;
        }

    }
    return true;
}


    // after generation of board is done, we set the "initial" value of the wordClass to 1 or 0 depending on if-
    // we want it to appear as one of the starting spots on the grid, it is done randomly
    public void addNSpaces() {
        int count = initialSpotsFilled;
        while (count != 0) {
            int cellId = randomGenerator(rows*cols)-1;
            int i = (cellId/rows);
            int j = cellId%rows;

            if (gameWordArray[i][j].getInitial() == 1) {
                count--;
                gameWordArray[i][j].setInitial(0);
            }
        }
    }


    //6x6 functions --------------------------------------------------------------------------------------------

    //Function to choose replacement numbers.
    public void switchNums6x6() {

        int randomGeneration;

        //Go through the rows, randomly generate a number that can work, then swap through.
        for(int i = 0; i < rows; i++) {

            do{

                randomGeneration = randomGenerator(randomNum) % rows;

                if(randomGeneration == 0) {

                    randomGeneration = 1;

                }

            } while(randomGeneration == i);


            swapThroughArray6x6(i+1, randomGeneration);

        }

    }


    //Function to swap two chosen numbers
    public void swapThroughArray6x6(int first, int second) {

        //Swaps all instances of second and first.
        for(int i = 0; i < 6; i++) {

            for(int j = 0; j < 6; j++) {

                if(array6x6[i][j] == first) {

                    array6x6[i][j] = second;

                }

                else if(array6x6[i][j] == second) {

                    array6x6[i][j] = first;

                }

            }

        }

    }


    //Sets up and performs swapColumns6x6() and swapRows6x6() for each sub grid.
    public void switchColumnsandRows6x6() {


        for(int i = 0; i < 6; i+=3) {

            swapColumns6x6(i);

        }


        for(int j = 0; j < 6; j+=2) {

            swapRows6x6(j);

        }


    }


    //Swaps the three columns part of the sub grid columnStart is in.
    public void swapColumns6x6(int columnStart) {

        int temp;

        //Swaps the columnStart column and the columnStart+1 column.
        for(int j = 0; j < 6; j++) {

            temp = array6x6[j][columnStart];

            array6x6[j][columnStart] = array6x6[j][columnStart+1];

            array6x6[j][columnStart+1] = temp;


        }

        //Swaps the columnStart column and the columnStart+2 column.
        for(int i = 0; i < 6; i++) {

            temp = array6x6[i][columnStart];

            array6x6[i][columnStart] = array6x6[i][columnStart+2];

            array6x6[i][columnStart+2] = temp;

        }


    }

    //Swaps the two rows of the sub grid rowStart is in.
    public void swapRows6x6(int rowStart) {

        int temp;

        //Swap the rowStart row with the rowStart+1 row.
        for(int j = 0; j < 6; j++) {

            temp = array6x6[rowStart][j];

            array6x6[rowStart][j] = array6x6[rowStart+1][j];

            array6x6[rowStart + 1][j] = temp;


        }



    }

    //Set gameWordArray to include the numbers inside array6x6.
    public void switchType6x6(){
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 6;j++){
                gameWordArray[i][j] = new wordClass();
                gameWordArray[i][j].setNum(array6x6[i][j]);
            }
        }
    }


    //12x12 functions -------------------------------------------------------------------------------------

    //Function to choose replacement numbers.
    public void switchNums12x12() {

        int randomGeneration;

        //Go through the rows, randomly choose a number that can work, then swap through.
        for(int i = 0; i < rows; i++) {

            do{

                randomGeneration = randomGenerator(randomNum) % rows;

                if(randomGeneration == 0) {

                    randomGeneration = 1;

                }

            } while(randomGeneration == i);


            swapThroughArray12x12(i+1, randomGeneration);

        }

    }

    //Function to swap two chosen numbers.
    public void swapThroughArray12x12(int first, int second) {

        //Swaps all instances of second and first.
        for(int i = 0; i < 12; i++) {

            for(int j = 0; j < 12; j++) {

                if(array12x12[i][j] == first) {

                    array12x12[i][j] = second;

                }

                else if(array12x12[i][j] == second) {

                    array12x12[i][j] = first;

                }

            }

        }

    }

    //Sets up and performs swapColumns12x12() and swapRows12x12() for each sub grid.
    public void switchColumnsandRows12x12() {


        for(int i = 0; i < 12; i+=4) {

            swapColumns12x12(i);

        }

        for(int j = 0; j < 12; j+=3) {

            swapRows12x12(j);

        }

    }

    //Swaps the 4 columns of the sub grid columnStart is in.
    public void swapColumns12x12(int columnStart) {

        int temp;

        //Swap the columnStart column with the columnStart+1 column.
        for(int j = 0; j < 12; j++) {

            temp = array12x12[j][columnStart];

            array12x12[j][columnStart] = array12x12[j][columnStart+1];

            array12x12[j][columnStart+1] = temp;

        }


        //Swap the columnStart column with the columnStart+2 column.
        for(int i = 0; i < 12; i++) {

            temp = array12x12[i][columnStart];

            array12x12[i][columnStart] = array12x12[i][columnStart+2];

            array12x12[i][columnStart+2] = temp;

        }


        //Swap the columnStart+2 column with the columnStart+3 column.
        for(int i = 0; i < 12; i++) {

            temp = array12x12[i][columnStart+2];

            array12x12[i][columnStart+2] = array12x12[i][columnStart+3];

            array12x12[i][columnStart+3] = temp;

        }

    }

    //Swaps the 3 rows of the sub grid rowStart is in.
    public void swapRows12x12(int rowStart) {

        int temp;

        //Swap the rowStart row and the rowStart+1 row.
        for(int j = 0; j < 12; j++) {

            temp = array12x12[rowStart][j];

            array12x12[rowStart][j] = array12x12[rowStart+1][j];

            array12x12[rowStart + 1][j] = temp;

        }


        //Swap the rowStart row and the rowStart+2 row.
        for(int i = 0; i < 12; i++) {

            temp = array12x12[rowStart][i];

            array12x12[rowStart][i] = array12x12[rowStart+2][i];

            array12x12[rowStart + 2][i] = temp;

        }

    }


    //Sets gameWordArray to include the numbers inside of array12x12.
    public void switchType12x12(){
        for(int i = 0; i < 12; i++){
            for(int j = 0; j < 12;j++){
                gameWordArray[i][j] = new wordClass();
                gameWordArray[i][j].setNum(array12x12[i][j]);
            }
        }
    }


}