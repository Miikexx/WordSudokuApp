package com.example.myapplication;

public class ValidBoardGenerator
{

    static StartGame.wordClass[][] gameWordArray;

    int rows;
    int cols;

    int SUBGRIDSIZE = 3;

    int K;

    static int numFilled;


    ValidBoardGenerator(int rows, int cols, int K) {
        this.rows = rows;
        this.cols = cols;
        this.K = K;
        gameWordArray = new StartGame.wordClass[rows][cols];
        for(int i = 0; i < rows; i++){
            for(int k = 0; k < cols; k++){
                gameWordArray[i][k] = new StartGame.wordClass();
            }
        }
        numFilled = rows*cols - K;
        fillValues();
        removeKDigits();

    }



    public void fillValues() {
        fill3SubGrids();
        fillRemaining(0, SUBGRIDSIZE);
        removeKDigits();
    }

    void fill3SubGrids() {
        int num;
        for (int r = 0; r < rows; r=r+SUBGRIDSIZE) {
            //fillBox(i, i);
            for (int i = 0; i< SUBGRIDSIZE; i++) {
                for (int j = 0; j< SUBGRIDSIZE; j++) {
                    do
                    {
                        num = randomGenerator(rows);
                    }
                    while (!CheckIfSafe(r + i, r + j, num));
                    gameWordArray[r+i][r+j].num = num;

                }
            }
        }
    }


/*
    void fillBox(int row,int col) {
        int num;
        for (int i = 0; i< SUBGRIDSIZE; i++) {
            for (int j = 0; j< SUBGRIDSIZE; j++) {
                do
                {
                    num = randomGenerator(rows);
                }
                while (!CheckIfSafe(row + i, col + j, num));
                gameWordArray[row+i][col+j].num = num;

            }
        }
    }

*/

    int randomGenerator(int num) {
        return (int) Math.floor((Math.random()*num+1));
    }

    boolean CheckIfSafe(int i,int j,int num) {
        for(int k = 0; k < rows; k++){
            if(gameWordArray[i][k].num == num){
                return false;
            }

        }

        for(int p = 0; p < cols; p++){
            if(gameWordArray[p][j].num == num){
                return false;
            }
        }

        int rowBegginingOfSubGrid = i-i%SUBGRIDSIZE;
        int colBegginingofSubGrid = j-j%SUBGRIDSIZE;

        for(int q = 0; q < SUBGRIDSIZE; q++){
            for(int r = 0; r < SUBGRIDSIZE; r++){
                if(gameWordArray[rowBegginingOfSubGrid + q][colBegginingofSubGrid+r].num == num){
                    return false;
                }
            }
        }
        return true;




        //return (unUsedInRow(i, num) && unUsedInCol(j, num) && unUsedInBox(i-i% SUBGRIDSIZE, j-j% SUBGRIDSIZE, num));
    }
/*
    boolean unUsedInBox(int rowStart, int colStart, int num) {
        for (int i = 0; i< SUBGRIDSIZE; i++)
            for (int j = 0; j< SUBGRIDSIZE; j++)
                if (gameWordArray[rowStart+i][colStart+j].num == num)
                    return false;
        return true;
    }

    boolean unUsedInRow(int i,int num) {
        for (int j = 0; j<rows; j++)
            if (gameWordArray[i][j].num == num)
                return false;
        return true;
    }
    boolean unUsedInCol(int j,int num) {
        for (int i = 0; i < cols; i++)
            if (gameWordArray[i][j].num == num)
                return false;
        return true;

    }


 */
    boolean fillRemaining(int i, int j) {
        if (j>=9 && i<9-1) {
            i = i + 1;
            j = 0;
        }

        if (i>=9 && j>=9)
            return true;
        if (i < SUBGRIDSIZE) {
            if (j < SUBGRIDSIZE)
                j = SUBGRIDSIZE;
        }

        else if (i < 9- SUBGRIDSIZE) {
            if (j==(int)(i/ SUBGRIDSIZE)* SUBGRIDSIZE)

                j =  j + SUBGRIDSIZE;
        }

        else {

            if (j == 9 - SUBGRIDSIZE) {
                i = i + 1;
                j = 0;
                if (i>=9){

                    return true;
                }
            }
        }


        for (int num = 1; num<=9; num++) {
            if (CheckIfSafe(i, j, num)) {

                gameWordArray[i][j].num = num;

                if (fillRemaining(i, j+1)){
                    return true;
                }
                gameWordArray[i][j].num = 0;

            }
        }
        return false;
    }



    public void removeKDigits() {
        int count = K;
        while (count != 0) {
            int cellId = randomGenerator(rows*cols)-1;

            int i = (cellId/9);

            int j = cellId%9;

            if (j != 0)

                j = j - 1;

            if (gameWordArray[i][j].initial != 0) {
                count--;
                gameWordArray[i][j].initial = 0;
            }
        }
    }
}
