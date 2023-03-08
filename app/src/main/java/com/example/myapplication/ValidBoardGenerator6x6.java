package com.example.myapplication;



public class ValidBoardGenerator6x6{


    int randomNum = 5;


    int genericArray[][] = {

            {3,5,2, 4,1,6},

            {6,4,1, 5,3,2},


            {2,6,3, 1,4,5},

            {4,1,5, 2,6,3},


            {5,3,4, 6,2,1},

            {1,2,6, 3,5,4},


    };



    ValidBoardGenerator6x6(){

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

        for(int i = 0; i < 6; i++) {

            do{

                randomGeneration = randomGenerator(randomNum) % 6;

                if(randomGeneration == 0) {

                    randomGeneration = 1;

                }

            } while(randomGeneration == i);


            swapThroughArray(i+1, randomGeneration);

        }

    }


    public void swapThroughArray(int first, int second) {

        for(int i = 0; i < 6; i++) {

            for(int j = 0; j < 6; j++) {

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


        for(int i = 0; i < 6; i+=3) {

            swapColumns(i);

        }


        for(int j = 0; j < 6; j+=2) {

            swapRows(j);

        }


    }


    public void swapColumns(int columnStart) {

        int temp;

        for(int j = 0; j < 6; j++) {

            temp = genericArray[j][columnStart];

            genericArray[j][columnStart] = genericArray[j][columnStart+1];

            genericArray[j][columnStart+1] = temp;


        }


        for(int i = 0; i < 6; i++) {

            temp = genericArray[i][columnStart];

            genericArray[i][columnStart] = genericArray[i][columnStart+2];

            genericArray[i][columnStart+2] = temp;

        }


    }



    public void swapRows(int rowStart) {

        int temp;

        for(int j = 0; j < 6; j++) {

            temp = genericArray[rowStart][j];

            genericArray[rowStart][j] = genericArray[rowStart+1][j];

            genericArray[rowStart + 1][j] = temp;


        }



    }



    public void printContents(){

        for(int i = 0; i < 6; i++) {

            for(int j = 0; j < 6; j++) {

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

        for(int i = 0; i < 6; i++) {

            if(genericArray[rowIndex][i] == numToBeChecked) {

                return false;

            }


        }

// check the position column

        for(int j = 0; j < 6; j++) {

            if(genericArray[j][colIndex] == numToBeChecked) {

                return false;

            }

        }


        for(int k = gridRowStart; k < gridRowStart + 2; k++) {

            for(int p = gridColStart; p < gridColStart + 3; p++) {

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


        if(numToCheck >= 0 && numToCheck <= 2) {

            start = 0;

        }

        else {

            start = 3;

        }


        return start;



    }


    public int getGridRowStart(int numToCheck) {

        int start;


        if(numToCheck >= 0 && numToCheck <= 1) {

            start = 0;

        }


        else if(numToCheck >= 2 && numToCheck<= 3) {

            start = 2;

        }

        else {

            start = 4;

        }


        return start;



    }


}