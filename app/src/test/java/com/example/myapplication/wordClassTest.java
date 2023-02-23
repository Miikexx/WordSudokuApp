package com.example.myapplication;

import static org.junit.Assert.*;

import org.junit.Test;

public class wordClassTest {


    @Test
    public void getEnglish() {
        wordClass wordclass = new wordClass();
        wordclass.setEnglish("apple");
        assertEquals("apple", wordclass.getEnglish());
    }


    @Test
    public void getTranslation()
    {
        wordClass wordclass = new wordClass();
        wordclass.setTranslation("pomme");
        assertEquals("pomme",wordclass.getTranslation());
    }

    @Test
    public void getInitial()
    {
        wordClass wordclass = new wordClass();
        wordclass.setInitial(1);
        assertEquals(1,wordclass.getInitial());
    }
    @Test
    public void getNum()
    {
        wordClass wordclass = new wordClass();
        wordclass.setNum(0);
        assertEquals(0, wordclass.getNum());
    }
    @Test
    public void setEnglish()
    {
        wordClass wordclass = new wordClass();
        wordclass.setEnglish("hello");
        assertEquals("hello",wordclass.getEnglish());
    }
    @Test
    public void setInitial()
    {
        wordClass wordclass = new wordClass();
        wordclass.setInitial(0);
        assertEquals(0, wordclass.getInitial());
    }
    @Test
    public void setNum()
    {
        wordClass wordclass = new wordClass();
        wordclass.setNum(0);
        assertEquals(0,wordclass.getNum());
    }
    @Test
    public void setTranslation()
    {
        wordClass wordclass = new wordClass();
        wordclass.setTranslation("bonjour");
        assertEquals("bonjour",wordclass.getTranslation());
    }

}