package com.example.myapplication;


// This file is a model class and contains no view or controller

public class gameWordInitializer {
    static String englishArray[] = {"APPLE", "YOU", "AND", "GENTLEMAN", "GATE", "GOOD", "GLAD", "PLAY", "EAT", "WITH", "GO", "SAD" };
    static String frenchArray[] = {"POMME", "TU", "ET", "MONSIEUR", "PORTE", "BIEN", "CONTENT", "JOUER", "MANGER", "AVEC", "ALLER", "TRISTE"};
    static wordClass gameWordArray[];


    // no constructor needed for now and will be made for future iterations. default constructor does the job for now and custom constructor will only-
    // be needed for randomization of words

    //getters for class gameWordInitializer
    public static String[] getEnglishArray() {
        return englishArray;
    }

    public static String[] getFrenchArray() {
        return frenchArray;
    }


    //setters not needed for this iteration. future plan is to have a text file and randomly select the word pairs from that file to
    // set the arrays



    // Function fillArray does not return any value but instead fills the array of size 9 with the words we will be using
    // for the game along with the int num which is used for the logic
    public gameWordInitializer(int rows){
        gameWordArray = new wordClass[rows];
        for(int i = 0; i < rows; i++){
            gameWordArray[i] = new wordClass();
            gameWordArray[i].setEnglish(englishArray[i]);
            gameWordArray[i].setTranslation(frenchArray[i]);
            gameWordArray[i].setNum(i+1);
        }
    }


    //Syncs gameWord array in both the startGame.java and validGameBoardGenerator.java
    //Assigns each grid space in valid board generator to an index of gamewordarray based on the number inside the grid space
    public void syncGameWordArray(int rows, int cols) {
        for (int i = 0; i < rows; i++) {
            for (int k = 0; k < rows; k++) {
                for (int j = 0; j < cols; j++) {
                    if (this.gameWordArray[j].getNum() == ValidBoardGenerator.gameWordArray[i][k].getNum()) {
                       ValidBoardGenerator.gameWordArray[i][k].setEnglish(this.gameWordArray[j].getEnglish());
                       ValidBoardGenerator.gameWordArray[i][k].setTranslation(this.gameWordArray[j].getTranslation());
                    }
                }
            }
        }
    }
}
