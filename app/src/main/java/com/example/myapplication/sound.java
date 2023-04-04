package com.example.myapplication;
import android.media.SoundPool;

public class sound {
   boolean englishGrid;
   SoundPool sounds = new SoundPool.Builder().setMaxStreams(1).build();

    //this variable is true when the user selects listening mode
    //load in all the sounds to use in listening comprehension mode:
    int frenchSound1 = sounds.load(this,R.raw.pomme,1);
    int frenchSound2 = sounds.load(this,R.raw.tu,1);
    int frenchSound3 = sounds.load(this,R.raw.et,1);
    int frenchSound4 = sounds.load(this,R.raw.monsieur,1);
    int frenchSound5 = sounds.load(this,R.raw.porte,1);
    int frenchSound6 = sounds.load(this,R.raw.bien,1);
    int frenchSound7 = sounds.load(this,R.raw.content,1);
    int frenchSound8 = sounds.load(this,R.raw.jouer,1);
    int frenchSound9 = sounds.load(this,R.raw.manger,1);
    int frenchSound10 = sounds.load(this,R.raw.avec,1);
    int frenchSound11 = sounds.load(this,R.raw.aller,1);
    int frenchSound12 = sounds.load(this,R.raw.triste,1);

    int englishSound1 = sounds.load(this,R.raw.apple,1);
    int englishSound2 = sounds.load(this,R.raw.you,1);
    int englishSound3 = sounds.load(this,R.raw.and,1);
    int englishSound4 = sounds.load(this,R.raw.gentleman,1);
    int englishSound5 = sounds.load(this,R.raw.gate,1);
    int englishSound6 = sounds.load(this,R.raw.glad,1);
    int englishSound7 = sounds.load(this,R.raw.good,1);
    int englishSound8 = sounds.load(this,R.raw.play,1);
    int englishSound9 = sounds.load(this,R.raw.eat,1);
    int englishSound10 = sounds.load(this,R.raw.with,1);
    int englishSound11 = sounds.load(this,R.raw.go,1);
    int englishSound12 = sounds.load(this,R.raw.sad,1);


    sound(boolean englishGrid){
        this.englishGrid = englishGrid;
    }


    public void playSound(String translation){
        if(!englishGrid) {
            switch (translation) {
                case "POMME":
                    sounds.play(frenchSound1, 1, 1, 0, 0, 1);
                    break;
                case "TU":
                    sounds.play(frenchSound2, 1, 1, 0, 0, 1);
                    break;
                case "ET":
                    sounds.play(frenchSound3, 1, 1, 0, 0, 1);
                    break;
                case "MONSIEUR":
                    sounds.play(frenchSound4, 1, 1, 0, 0, 1);
                    break;
                case "PORTE":
                    sounds.play(frenchSound5, 1, 1, 0, 0, 1);
                    break;
                case "BIEN":
                    sounds.play(frenchSound6, 1, 1, 0, 0, 1);
                    break;
                case "CONTENT":
                    sounds.play(frenchSound7, 1, 1, 0, 0, 1);
                    break;
                case "JOUER":
                    sounds.play(frenchSound8, 1, 1, 0, 0, 1);
                    break;
                case "MANGER":
                    sounds.play(frenchSound9, 1, 1, 0, 0, 1);
                    break;
                case "AVEC":
                    sounds.play(frenchSound10, 1, 1, 0, 0, 1);
                    break;
                case "ALLER":
                    sounds.play(frenchSound11, 1, 1, 0, 0, 1);
                    break;
                case "TRISTE":
                    sounds.play(frenchSound12, 1, 1, 0, 0, 1);
                    break;
                default:
                    break;

            }
        }
        else{
            switch(translation){
                case "APPLE":
                    sounds.play(englishSound1, 1, 1, 0, 0, 1);
                    break;
                case "YOU":
                    sounds.play(englishSound2, 1, 1, 0, 0, 1);
                    break;
                case "AND":
                    sounds.play(englishSound3, 1, 1, 0, 0, 1);
                    break;
                case "GENTLEMAN":
                    sounds.play(englishSound4, 1, 1, 0, 0, 1);
                    break;
                case "GATE":
                    sounds.play(englishSound5, 1, 1, 0, 0, 1);
                    break;
                case "GOOD":
                    sounds.play(englishSound6, 1, 1, 0, 0, 1);
                    break;
                case "GLAD":
                    sounds.play(englishSound7, 1, 1, 0, 0, 1);
                    break;
                case "PLAY":
                    sounds.play(englishSound8, 1, 1, 0, 0, 1);
                    break;
                case "EAT":
                    sounds.play(englishSound9, 1, 1, 0, 0, 1);
                    break;
                case "WITH":
                    sounds.play(englishSound10, 1, 1, 0, 0, 1);
                    break;
                case "GO":
                    sounds.play(englishSound11, 1, 1, 0, 0, 1);
                    break;
                case "SAD":
                    sounds.play(englishSound12, 1, 1, 0, 0, 1);
                    break;
                default:
                    break;
            }
        }

    }
}
