package com.example.myapplication;

import org.junit.Test;
import static org.junit.Assert.*;

public class gameWordInitializerTest {

    @Test
    public void fillArray(){
        gameWordInitializer tester = new gameWordInitializer(9);
        //tester.fillArray();
        assertEquals("YOU", tester.gameWordArray[1].getEnglish());
        assertEquals("MONSIEUR", tester.gameWordArray[3].getTranslation());
        assertEquals(6, tester.gameWordArray[5].getNum());
        assertEquals("GATE", tester.gameWordArray[4].getEnglish());
        assertEquals("PORTE", tester.gameWordArray[4].getTranslation());
        assertEquals(5, tester.gameWordArray[4].getNum());
        assertEquals(1, tester.gameWordArray[5].getInitial());
    }

    @Test
    public void syncGameWordArray(){
        ValidBoardGenerator quickBoard = new ValidBoardGenerator(9, 9, 12);
        gameWordInitializer tester = new gameWordInitializer(9);
        int testNumA = quickBoard.gameWordArray[0][0].getNum();
        String englishTestA = tester.gameWordArray[testNumA - 1].getEnglish();
        int testNumB = quickBoard.gameWordArray[4][5].getNum();
        String translationTestA = tester.gameWordArray[testNumB - 1].getEnglish();
        int testNumC = quickBoard.gameWordArray[8][1].getNum();
        String englishTestB = tester.gameWordArray[testNumC - 1].getEnglish();
        int testNumD = quickBoard.gameWordArray[2][2].getNum();
        String translationTestB = tester.gameWordArray[testNumD - 1].getEnglish();
        tester.syncGameWordArray(9,9);
        assertEquals(englishTestA, quickBoard.gameWordArray[0][0].getEnglish());
        assertEquals(translationTestA, quickBoard.gameWordArray[4][5].getEnglish());
        assertEquals(englishTestB, quickBoard.gameWordArray[8][1].getEnglish());
        assertEquals(translationTestB, quickBoard.gameWordArray[2][2].getEnglish());
    }

    @Test
    public void gettersTest(){
        String englishArray[] = {"APPLE", "YOU", "AND", "GENTLEMAN", "GATE", "GOOD", "GLAD", "PLAY", "EAT", "WITH", "GO", "SAD"};
        String frenchArray[] = {"POMME", "TU", "ET", "MONSIEUR", "PORTE", "BIEN", "CONTENT", "JOUER", "MANGER", "AVEC", "ALLER", "TRISTE"};

        gameWordInitializer test = new gameWordInitializer(9);
        assertArrayEquals(englishArray, gameWordInitializer.getEnglishArray());
        assertArrayEquals(frenchArray, gameWordInitializer.getFrenchArray());


    }
}