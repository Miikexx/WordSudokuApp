package com.example.myapplication;

import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.hamcrest.CoreMatchers.notNullValue;

import android.content.Context;
import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.SdkSuppress;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class startGame9x9Test {
    static Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    private static final String DIF_PACKAGE = appContext.getPackageName();
    private static final int LAUNCH_TIMEOUT = 5000;
    private UiDevice device;

    @Before
    public void setUp() throws Exception {
        device = UiDevice.getInstance(getInstrumentation());

        device.pressHome();
        final String launcherPackage = device.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);


        Context context = ApplicationProvider.getApplicationContext();

        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(DIF_PACKAGE);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        device.wait(Until.hasObject(By.pkg(DIF_PACKAGE).depth(0)), LAUNCH_TIMEOUT);


        //gets us to 9x9 board from start screen
        device.findObject(new UiSelector().text("START GAME")).click();
        device.findObject(new UiSelector().text("START GAME")).click();

    }


    @Test
    public void wordAtTopAppears() throws UiObjectNotFoundException {
       // finds first instance of "POMME" in the grid
        UiObject firstWordInGrid = device.findObject(new UiSelector().text("POMME"));
        firstWordInGrid.click();

        // this is the same code but it finds the text at the top since POMME should be at the top since it was clicked
        // the wordAtTop has a higher hierarchy and thus can be accessed by this
        UiObject wordAtTop = device.findObject(new UiSelector().text("POMME"));
        assertEquals(firstWordInGrid.getText(), wordAtTop.getText());
    }

    @Test
    public void wordAtTopEmptyString() throws UiObjectNotFoundException {
        UiObject firstEmptyInGrid = device.findObject(new UiSelector().text(" "));
        firstEmptyInGrid.click();

        UiObject wordAtTop = device.findObject(new UiSelector().text(" "));


        assertEquals(wordAtTop.getText(), " ");
    }

    @Test
    public void countSubGridSpaces() throws UiObjectNotFoundException {
        UiObject2 grid =  device.findObject(By.res(DIF_PACKAGE, "WORDDISPLAY"));
        assertEquals(grid.getChildCount(), 0);
    }



    //making sure we have the 9x9 grid
    @Test
    public void has9x9Grid(){
        assertTrue(device.hasObject(By.res(DIF_PACKAGE, "gridImage")));
    }

    //making sure we have the top word display
    @Test
    public void hasWordDisplay(){
        assertTrue(device.hasObject(By.res(DIF_PACKAGE, "WORDDISPLAY")));
    }


    //making sure we have the table for the bottom words
    @Test
    public void hasBottomWords(){
        assertTrue(device.hasObject(By.res(DIF_PACKAGE, "tableForButtons")));
    }

    //making sure we have the timer
    @Test
    public void hasTimer(){
        assertTrue(device.hasObject(By.res(DIF_PACKAGE, "timer")));
    }

    //making sure we have the lives counter
    @Test
    public void hasLivesCounter(){
        assertTrue(device.hasObject(By.res(DIF_PACKAGE, "livesCounter")));
    }

    //making sure we have the back button
    @Test
    public void test(){
        assertTrue(device.hasObject(By.res(DIF_PACKAGE, "tempButton")));
    }
}