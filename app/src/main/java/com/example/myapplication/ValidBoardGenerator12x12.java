package com.example.myapplication;

public class ValidBoardGenerator12x12 {

    int randomNum = 5;

    int genericArray[][] = {

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

    ValidBoardGenerator12x12(){

        switchNums();

        switchColumnsandRows();

        System.out.println("\n");

        printContents();

    }

    int randomGenerator(int num) {

        return (int) Math.floor((Math.random() * num + 1));

    }

    public void switchNums() {

        int randomGeneration;

        for(int i = 0; i < 12; i++) {

            do{

                randomGeneration = randomGenerator(randomNum) % 12;

                if(randomGeneration == 0) {

                    randomGeneration = 1;

                }

            } while(randomGeneration == i);

            swapThroughArray(i+1, randomGeneration);

        }

    }

    public void swapThroughArray(int first, int second) {

        for(int i = 0; i < 12; i++) {

            for(int j = 0; j < 12; j++) {

                if(genericArray[i][j] == first) {

                    genericArray[i][j] = second;

                }

                else if(genericArray[i][j] == second) {

                    genericArray[i][j] = first;

                }

            }

        }

    }

    public void switchColumnsandRows() {


        for(int i = 0; i < 12; i+=4) {

            swapColumns(i);

        }

        for(int j = 0; j < 12; j+=3) {

            swapRows(j);

        }

    }

    public void swapColumns(int columnStart) {

        int temp;

        for(int j = 0; j < 12; j++) {

            temp = genericArray[j][columnStart];

            genericArray[j][columnStart] = genericArray[j][columnStart+1];

            genericArray[j][columnStart+1] = temp;

        }


        for(int i = 0; i < 12; i++) {

            temp = genericArray[i][columnStart];

            genericArray[i][columnStart] = genericArray[i][columnStart+2];

            genericArray[i][columnStart+2] = temp;

        }


        for(int i = 0; i < 12; i++) {

            temp = genericArray[i][columnStart+2];

            genericArray[i][columnStart+2] = genericArray[i][columnStart+3];

            genericArray[i][columnStart+3] = temp;

        }

    }

    public void swapRows(int rowStart) {

        int temp;

        for(int j = 0; j < 12; j++) {

            temp = genericArray[rowStart][j];

            genericArray[rowStart][j] = genericArray[rowStart+1][j];

            genericArray[rowStart + 1][j] = temp;

        }


        for(int i = 0; i < 12; i++) {

            temp = genericArray[rowStart][i];

            genericArray[rowStart][i] = genericArray[rowStart+2][i];

            genericArray[rowStart + 2][i] = temp;

        }

    }

    public void printContents(){

        for(int i = 0; i < 12; i++) {

            for(int j = 0; j < 12; j++) {

                System.out.print(genericArray[i][j] + ", ");

            }

            System.out.println("\n");

        }

    }

    public boolean checkIfSafe(int rowIndex, int colIndex) {

        int numToBeChecked = genericArray[rowIndex][colIndex];

        genericArray[rowIndex][colIndex] = -1;

        int gridRowStart = getGridRowStart(rowIndex);

        int gridColStart = getGridColStart(colIndex);

//Check the posiitons row

        for(int i = 0; i < 12; i++) {

            if(genericArray[rowIndex][i] == numToBeChecked) {

                return false;

            }

        }

// check the position column

        for(int j = 0; j < 12; j++) {

            if(genericArray[j][colIndex] == numToBeChecked) {

                return false;

            }

        }

        for(int k = gridRowStart; k < gridRowStart + 3; k++) {

            for(int p = gridColStart; p < gridColStart + 4; p++) {

                if(genericArray[k][p] == numToBeChecked) {

//System.out.println(gameWordArray[k][p] + ": " + numToBeChecked);

                    return false;

                }

            }

        }

        genericArray[rowIndex][colIndex] = numToBeChecked;

        return true;

    }

    public int getGridColStart(int numToCheck) {

        int start;

        if(numToCheck >= 0 && numToCheck <= 3) {

            start = 0;

        }

        else if(numToCheck >= 4 && numToCheck <= 7) {

            start = 4;

        }

        else {

            start = 8;

        }


        return start;

    }

    public int getGridRowStart(int numToCheck) {

        int start;

        if(numToCheck >= 0 && numToCheck <= 2) {

            start = 0;

        }

        else if(numToCheck >= 3 && numToCheck <= 5) {

            start = 3;

        }

        else if(numToCheck >= 6 && numToCheck <= 8) {

            start = 6;

        }

        else {

            start = 9;

        }

        return start;

    }

}