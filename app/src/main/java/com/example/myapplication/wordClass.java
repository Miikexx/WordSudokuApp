package com.example.myapplication;

// This is a model class with no view or controller
//This "wordClass" is used to store information about the word pairs used in the game, each instance of this class represents one grid space of the game puzzle
public class wordClass {

    // English and Translation stores the English word pair and the translation word pair
    public String English = "";
    public String translation = "";

    //If set to 0 this will not be generated onto the initial game board and if set to  1, it will show up on
    // the game board
    public int initial = 1;
    // the num integer is used to write the logic of our game on the back end and is only assigned numbers
    // from 1-9
    public int num = 0;


    //getters and setters:
    public String getEnglish() {
        return English;
    }

    public String getTranslation(){
        return translation;
    }

    public int getInitial() {
        return initial;
    }

    public int getNum() {
        return num;
    }


    public void setEnglish(String english) {
        English = english;
    }

    public void setInitial(int initial) {
        this.initial = initial;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
}
