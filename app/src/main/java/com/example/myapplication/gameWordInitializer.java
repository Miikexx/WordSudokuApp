package com.example.myapplication;

// THIS IS A TEST TO SEE IF PUSHING WORKS NOW
// THIS IS ARMANS TEST, I CAN SEE RILEYS CHANGE
// THIS IS A TEST TO SEE IF Hussains PUSH worksS
// armans test commend again to make sure it works
// THIS IS A TEST TO SEE IF Hussain can push to Another branch
// This file is a model class and contains no view or controller

public class gameWordInitializer {
    static String englishArray[] = {"Apple", "You", "And", "Gentleman", "Gate", "Good", "Glad", "Play", "Eat"};
    static String frenchArray[] = {"Pomme", "Tu", "Et", "Monsieur", "Porte", "Bien", "Content", "Jouer", "Manger"};
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
    public void fillArray(){
        gameWordArray = new wordClass[9];
        for(int i = 0; i < 9; i++){
            gameWordArray[i] = new wordClass();
            gameWordArray[i].setEnglish(englishArray[i]);
            gameWordArray[i].setTranslation(frenchArray[i]);
            gameWordArray[i].setNum(i+1);
        }
    }


    //Syncs gameWord array in both the startGame.java and validGameBoardGenerator.java
    //Assigns each grid space in valid board generator to an index of gamewordarray based on the number inside the grid space
    public void syncGameWordArray() {
        for (int i = 0; i < 9; i++) {
            for (int k = 0; k < 9; k++) {
                for (int j = 0; j < 9; j++) {
                    if (this.gameWordArray[j].getNum() == ValidBoardGenerator.gameWordArray[i][k].getNum()) {
                        ValidBoardGenerator.gameWordArray[i][k].setEnglish(this.gameWordArray[j].getEnglish());
                        ValidBoardGenerator.gameWordArray[i][k].setTranslation(this.gameWordArray[j].getTranslation());
                    }
                }
            }
        }
    }
}
