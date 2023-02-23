package com.example.myapplication;

import static org.junit.Assert.*;

import org.junit.Test;

public class ValidBoardGeneratorTest {

    @Test
    public void setGameWordArray() {
        int row = 0;
        int col = 0;
        wordClass testWordClass = new wordClass();
        testWordClass.setNum(5);

        ValidBoardGenerator validBoard = new ValidBoardGenerator(9,9,10);

        for(int i = 0; i < 9; i++) {
           for(int j = 0; j < 9; j++){
               if(validBoard.gameWordArray[i][j].num == testWordClass.num){
                   row = i;
                   col = j;
                   validBoard.setGameWordArray(testWordClass, row, col);

                   assertEquals(5, validBoard.gameWordArray[i][j].num);
               }
           }
        }



    }

    @Test
    public void getRows() {
        ValidBoardGenerator test = new ValidBoardGenerator(9,9,9);
        int rows = test.getRows();
        assertEquals(9,rows);
    }

    @Test
    public void setPositiveRows() {
        ValidBoardGenerator test = new ValidBoardGenerator(9,9,9);
        test.setRows(8);
        assertEquals(8, test.getRows());
    }

    @Test
    public void setNegativeRows() {
        ValidBoardGenerator test = new ValidBoardGenerator(9,9,9);
        test.setRows(-11);
        assertEquals(-1, test.getRows());
    }

    @Test
    public void set0Rows() {
        ValidBoardGenerator test = new ValidBoardGenerator(9,9,9);
        test.setRows(0);
        assertEquals(0, test.getRows());
    }

    @Test
    public void getCols() {
        ValidBoardGenerator test = new ValidBoardGenerator(9,9,9);
        int cols = test.getCols();
        assertEquals(9,cols);
    }

    @Test
    public void setPositiveCols() {
        ValidBoardGenerator test = new ValidBoardGenerator(9,9,9);
        test.setCols(8);
        assertEquals(8, test.getCols());
    }

    @Test
    public void setNegativeCols() {
        ValidBoardGenerator test = new ValidBoardGenerator(9,9,9);
        test.setCols(-11);
        assertEquals(-1, test.getCols());
    }

    @Test
    public void set0Cols() {
        ValidBoardGenerator test = new ValidBoardGenerator(9,9,9);
        test.setCols(0);
        assertEquals(0, test.getCols());
    }


    @Test
    public void getSUBGRIDSIZE() {
        ValidBoardGenerator test = new ValidBoardGenerator(9,9,3);
        assertEquals(3,test.getSUBGRIDSIZE());
    }
    @Test
    public void setPositiveSubGridSize() {
        ValidBoardGenerator test = new ValidBoardGenerator(9,9,9);
        test.setSUBGRIDSIZE(8);
        assertEquals(8, test.getSUBGRIDSIZE());
    }

    @Test
    public void setNegativeSubGridSize() {
        ValidBoardGenerator test = new ValidBoardGenerator(9,9,9);
        test.setSUBGRIDSIZE(-11);
        assertEquals(-1, test.getSUBGRIDSIZE());
    }

    @Test
    public void set0SubGridSize() {
        ValidBoardGenerator test = new ValidBoardGenerator(9,9,9);
        test.setSUBGRIDSIZE(0);
        assertEquals(0, test.getSUBGRIDSIZE());
    }


    @Test
    public void getInitialSpotsFilled() {
        ValidBoardGenerator test = new ValidBoardGenerator(9,9,3);
        test.setInitialSpotsFilled(5);
        assertEquals(5,test.getInitialSpotsFilled());
    }

    @Test
    public void setPositiveInitialSpotsFilled() {
        ValidBoardGenerator test = new ValidBoardGenerator(9,9,9);
        test.setInitialSpotsFilled(8);
        assertEquals(8, test.getInitialSpotsFilled());
    }

    @Test
    public void setNegativeInitialSpotsFilled() {
        ValidBoardGenerator test = new ValidBoardGenerator(9,9,9);
        test.setInitialSpotsFilled(-11);
        assertEquals(-1, test.getInitialSpotsFilled());
    }

    @Test
    public void set0InitialSpotsFilled() {
        ValidBoardGenerator test = new ValidBoardGenerator(9,9,9);
        test.setInitialSpotsFilled(0);
        assertEquals(0, test.getInitialSpotsFilled());
    }


    @Test
    public void fill3SubGrids() {
        int num;
        int rows = 9;
        int SUBGRIDSIZE = 3;
        ValidBoardGenerator test = new ValidBoardGenerator(9,9,3);
        for (int r = 0; r < rows; r = r + SUBGRIDSIZE) {
            for (int i = 0; i < SUBGRIDSIZE; i++) {
                for (int j = 0; j < SUBGRIDSIZE; j++) {
                    if(test.gameWordArray[r+i][r+j].num == -1){
                        assertTrue(false);
                    }
                }
            }
        }
    }

    @Test
    public void checkAll(){
        ValidBoardGenerator test = new ValidBoardGenerator(9,9,3);
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                if(test.gameWordArray[i][j].num == -1){
                    assertTrue(false);
                }
            }
        }
    }


    @Test
    public void checkIfNotSafe() {
        ValidBoardGenerator test = new ValidBoardGenerator(9,9,10);
        int arrayNum = test.gameWordArray[1][1].num;
        if(arrayNum == 9){
            arrayNum -=1;
        }
        else{
            arrayNum +=1;
        }

        assertFalse(test.CheckIfSafe(1,1,arrayNum));

    }

    @Test
    public void checkIfIsSafe(){
        ValidBoardGenerator test = new ValidBoardGenerator(9,9,10);
        int arrayNum = test.gameWordArray[1][1].num;
        assertFalse(test.CheckIfSafe(1,1,arrayNum));

    }

    @Test
    public void fillRemaining() {
    }

    @Test
    public void addNSpaces() {
        int initial = 10;
        int counter = 0;
        ValidBoardGenerator test = new ValidBoardGenerator(9,9,initial);

        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                if(test.gameWordArray[i][j].initial == 0){
                    counter++;
                }
            }
        }
        assertEquals(initial, counter);
    }
}