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
    public void getSUBGRIDROWSIZE() {
        ValidBoardGenerator test = new ValidBoardGenerator(9,9,3);
        assertEquals(3,test.getSUBGRIDROWSIZE());
    }
    @Test
    public void setPositiveSubGridRowSize() {
        ValidBoardGenerator test = new ValidBoardGenerator(9,9,9);
        test.setSUBGRIDROWSIZE(8);
        assertEquals(8, test.getSUBGRIDROWSIZE());
    }

    @Test
    public void setNegativeSubGridRowSize() {
        ValidBoardGenerator test = new ValidBoardGenerator(9,9,9);
        test.setSUBGRIDROWSIZE(-11);
        assertEquals(-1, test.getSUBGRIDROWSIZE());
    }

    @Test
    public void set0SubGridRowSize() {
        ValidBoardGenerator test = new ValidBoardGenerator(9,9,9);
        test.setSUBGRIDROWSIZE(0);
        assertEquals(0, test.getSUBGRIDROWSIZE());
    }

    @Test
    public void getSUBGRIDCOLIZE() {
        ValidBoardGenerator test = new ValidBoardGenerator(9,9,3);
        assertEquals(3,test.getSUBGRIDCOLSIZE());
    }
    @Test
    public void setPositiveSubGridColSize() {
        ValidBoardGenerator test = new ValidBoardGenerator(9,9,9);
        test.setSUBGRIDCOLSIZE(8);
        assertEquals(8, test.getSUBGRIDCOLSIZE());
    }

    @Test
    public void setNegativeSubGridColSize() {
        ValidBoardGenerator test = new ValidBoardGenerator(9,9,9);
        test.setSUBGRIDCOLSIZE(-11);
        assertEquals(-1, test.getSUBGRIDCOLSIZE());
    }

    @Test
    public void set0SubGridColSize() {
        ValidBoardGenerator test = new ValidBoardGenerator(9,9,9);
        test.setSUBGRIDCOLSIZE(0);
        assertEquals(0, test.getSUBGRIDCOLSIZE());
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

        rows = 4;
        SUBGRIDSIZE = 2;
        ValidBoardGenerator test4 = new ValidBoardGenerator(4, 4, 2);
        for (int r = 0; r < rows; r = r + SUBGRIDSIZE) {
            for (int i = 0; i < SUBGRIDSIZE; i++) {
                for (int j = 0; j < SUBGRIDSIZE; j++) {
                    if(test4.gameWordArray[r+i][r+j].num == -1){
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

        ValidBoardGenerator test4 = new ValidBoardGenerator(4,4,3);
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                if(test4.gameWordArray[i][j].num == -1){
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
        test.gameWordArray[1][1].num = -1;


        assertFalse(test.CheckIfSafe(1,1,arrayNum));

        ValidBoardGenerator test4 = new ValidBoardGenerator(4, 4, 5);

        arrayNum = test4.gameWordArray[1][1].num;
        if(arrayNum == 4){
            arrayNum -=1;
        }
        else{
            arrayNum +=1;
        }
        test4.gameWordArray[1][1].num = -1;


        assertFalse(test4.CheckIfSafe(1,1,arrayNum));

    }

    @Test
    public void checkIfIsSafe(){
        ValidBoardGenerator test = new ValidBoardGenerator(9,9,10);
        int arrayNum = test.gameWordArray[1][1].num;
        test.gameWordArray[1][1].num = -1;

        assertTrue(test.CheckIfSafe(1,1,arrayNum));

        ValidBoardGenerator test4 = new ValidBoardGenerator(4, 4, 5);
        arrayNum = test4.gameWordArray[1][1].num;
        test4.gameWordArray[1][1].num = -1;

        assertTrue(test4.CheckIfSafe(1,1,arrayNum));

    }

    @Test
    public void completeBoard() {
        ValidBoardGenerator test = new ValidBoardGenerator(9,9,10);
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                int testNum = test.gameWordArray[i][j].getNum();
                test.gameWordArray[i][j].setNum(-1);
                if(test.CheckIfSafe(i,j,testNum) == false){
                    assertFalse(true);
                }

                test.gameWordArray[i][j].setNum(testNum);
            }
        }

        assertTrue(true);

        ValidBoardGenerator test4 = new ValidBoardGenerator(9,9,10);
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                int testNum = test4.gameWordArray[i][j].getNum();
                test4.gameWordArray[i][j].setNum(-1);
                if(test4.CheckIfSafe(i,j,testNum) == false){
                    assertFalse(true);
                }

                test4.gameWordArray[i][j].setNum(testNum);
            }
        }

        assertTrue(true);
    }

    @Test
    public void  CheckIfSafe() {
        ValidBoardGenerator test6 = new ValidBoardGenerator(6,6,12);
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 6; j++){
                int testNum = test6.gameWordArray[i][j].getNum();
                test6.gameWordArray[i][j].setNum(-1);
                if(test6.CheckIfSafe(i,j,testNum) == false){
                    assertFalse(true);
                }

                test6.gameWordArray[i][j].setNum(testNum);
            }
        }

        assertTrue(true);

        ValidBoardGenerator test12 = new ValidBoardGenerator(12,12,30);
        for(int i = 0; i < 12; i++){
            for(int j = 0; j < 12; j++){
                int testNum = test12.gameWordArray[i][j].getNum();
                test12.gameWordArray[i][j].setNum(-1);
                if(test12.CheckIfSafe(i,j,testNum) == false){
                    assertFalse(true);
                }

                test12.gameWordArray[i][j].setNum(testNum);
            }
        }
        assertTrue(true);

    }

    @Test
    public void getNumFilled(){
        ValidBoardGenerator test = new ValidBoardGenerator(9, 9, 10);
        assertEquals(71, ValidBoardGenerator.getNumFilled());
    }


    @Test
    public void setNumFilledPositive(){
        ValidBoardGenerator test = new ValidBoardGenerator(9,9,10);
        ValidBoardGenerator.setNumFilled(10);
        int getNumFilled = ValidBoardGenerator.getNumFilled();
        assertEquals(10, getNumFilled);

    }

    @Test
    public void setNumFilledNegative(){
        ValidBoardGenerator test = new ValidBoardGenerator(9,9,10);
        ValidBoardGenerator.setNumFilled(-10);
        int getNumFilled = ValidBoardGenerator.getNumFilled();
        assertEquals(0, getNumFilled);
    }

    @Test
    public void setNumFilled0(){
        ValidBoardGenerator test = new ValidBoardGenerator(9, 9, 10);
        ValidBoardGenerator.setNumFilled(0);
        int getNumFilled = ValidBoardGenerator.getNumFilled();
        assertEquals(0, getNumFilled);
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

        initial = 5;
        counter = 0;
        ValidBoardGenerator test4 = new ValidBoardGenerator(4, 4, initial);
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                if(test4.gameWordArray[i][j].initial == 0){
                    counter++;
                }
            }
        }
        assertEquals(initial, counter);

        initial = 7;
        counter = 0;
        ValidBoardGenerator test6 = new ValidBoardGenerator(6, 6, initial);
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 6; j++){
                if(test6.gameWordArray[i][j].initial == 0){
                    counter++;
                }
            }
        }
        assertEquals(initial, counter);

        initial = 13;
        counter = 0;
        ValidBoardGenerator test12 = new ValidBoardGenerator(12, 12, initial);
        for(int i = 0; i < 12; i++){
            for(int j = 0; j < 12; j++){
                if(test12.gameWordArray[i][j].initial == 0){
                    counter++;
                }
            }
        }
        assertEquals(initial, counter);
    }

    @Test
    public void check9x9GridUnique(){
        ValidBoardGenerator test = new ValidBoardGenerator(9,9,5);
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                int testNum = test.gameWordArray[i][j].getNum();
                test.gameWordArray[i][j].setNum(-1);
                if(test.CheckIfSafe(i,j,testNum) == false){
                    assertFalse(true);
                }
                else{
                    test.gameWordArray[i][j].setNum(testNum);
                }
            }
        }
        assertTrue(true);
    }

    @Test
    public void check4x4GridUnique(){
        ValidBoardGenerator test = new ValidBoardGenerator(4, 4, 3);
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                int testNum = test.gameWordArray[i][j].getNum();
                test.gameWordArray[i][j].setNum(-1);
                if(test.CheckIfSafe(i,j,testNum) == false){
                    assertFalse(true);
                }
                else{
                    test.gameWordArray[i][j].setNum(testNum);
                }
            }
        }
        assertTrue(true);
    }

    @Test
    public void swapThroughArray(){
        ValidBoardGenerator test6 = new ValidBoardGenerator(6, 6, 9);
        int check1 = test6.array6x6[0][0];
        int check2 = test6.array6x6[1][1];
        test6.swapThroughArray6x6(check1, check2);
        assertEquals(check1, test6.array6x6[1][1]);
        assertEquals(check2, test6.array6x6[0][0]);

        ValidBoardGenerator test12 = new ValidBoardGenerator(12, 12, 20);
        check1 = test12.array12x12[0][0];
        check2 = test12.array12x12[1][1];
        test12.swapThroughArray12x12(check1, check2);

    }

    @Test
    public void swapColumns(){
        ValidBoardGenerator test6 = new ValidBoardGenerator(6, 6, 9);
        int check1 = test6.array6x6[0][3];
        int check2 = test6.array6x6[0][4];
        int check3 = test6.array6x6[0][5];
        test6.swapColumns6x6(3);
        assertEquals(check1, test6.array6x6[0][4]);
        assertEquals(check2, test6.array6x6[0][5]);
        assertEquals(check3, test6.array6x6[0][3]);

        ValidBoardGenerator test12 = new ValidBoardGenerator(12, 12, 20);
        check1 = test12.array12x12[0][3];
        check2 = test12.array12x12[0][4];
        check3 = test12.array12x12[0][5];
        int check4 = test12.array12x12[0][6];
        test12.swapColumns12x12(3);
        assertEquals(check1, test12.array12x12[0][4]);
        assertEquals(check2, test12.array12x12[0][6]);
        assertEquals(check3, test12.array12x12[0][3]);
        assertEquals(check4, test12.array12x12[0][5]);

    }

    @Test
    public void swapRows(){
        ValidBoardGenerator test6 = new ValidBoardGenerator(6, 6, 9);
        int check1 = test6.array6x6[3][0];
        int check2 = test6.array6x6[4][0];
        test6.swapRows6x6(3);
        assertEquals(check1, test6.array6x6[4][0]);
        assertEquals(check2, test6.array6x6[3][0]);

        ValidBoardGenerator test12 = new ValidBoardGenerator(12, 12, 20);
        check1 = test12.array12x12[3][0];
        check2 = test12.array12x12[4][0];
        int check3 = test12.array12x12[5][0];
        test12.swapRows12x12(3);
        assertEquals(check1, test12.array12x12[4][0]);
        assertEquals(check2, test12.array12x12[5][0]);
        assertEquals(check3, test12.array12x12[3][0]);
    }

    @Test
    public void switchTypes(){
        ValidBoardGenerator test6 = new ValidBoardGenerator(6, 6, 9);
        test6.switchNums6x6();
        int numCheck = test6.array6x6[0][0];
        test6.switchType6x6();
        int wordCheck = test6.gameWordArray[0][0].getNum();
        assertEquals(numCheck, wordCheck);

        ValidBoardGenerator test12 = new ValidBoardGenerator(12, 12, 20);
        test12.switchNums12x12();
        numCheck = test12.array12x12[0][0];
        test12.switchType12x12();
        wordCheck = test12.gameWordArray[0][0].getNum();
        assertEquals(numCheck, wordCheck);
    }


}