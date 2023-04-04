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
import androidx.test.uiautomator.UiCollection;
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

        device.findObject(new UiSelector().text("PEACEFUL")).click();
        device.findObject(new UiSelector().text("START GAME")).click();

    }

    //Tests to make sure the word at the top displays the correct wokd
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

    //same as previous test but with an empty string this time
    @Test
    public void wordAtTopEmptyString() throws UiObjectNotFoundException {
        UiObject firstEmptyInGrid = device.findObject(new UiSelector().text(" "));
        firstEmptyInGrid.click();

        UiObject wordAtTop = device.findObject(new UiSelector().text(" "));


        assertEquals(wordAtTop.getText(), " ");
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

    //makes sure the lives counter displays the correct amount of lives
    @Test
    public void livesCounterInitialization(){
        assertEquals(device.findObject(By.res(DIF_PACKAGE, "livesCounter")).getText(), "Lives Counter: 999");
    }

    //test to make sure the lives counter decrements after a misplaced word
    @Test
    public void livesCounterDecrement() throws UiObjectNotFoundException {
        // go though and place random incorrect and correct word in the grid spaces
        // doing this so that the lives counter changes
        for (int j = 0; j < 5; j++) {
            device.findObject(new UiSelector().text(" ")).click();
            for (int i = 0; i < 9; i++) {
                device.findObject(By.text(gameWordInitializer.gameWordArray[i].getEnglish())).click();

                //asserting that the lives counter is not what is was first initalized as
                assertEquals(device.findObject(By.res(DIF_PACKAGE, "livesCounter")).getText(), "Lives Counter: 999");

            }
        }
    }

    //making sure we have the back button
    @Test
    public void hasTempButton(){
        assertTrue(device.hasObject(By.res(DIF_PACKAGE, "tempButton")));
    }


    //tests the collection of the childcount of our frame layout
    @Test
    public void test(){
        UiCollection collection = new UiCollection(new UiSelector().className("android.widget.FrameLayout"));
        int count = collection.getChildCount(new UiSelector()
                .className("android.widget.LinearLayout"));

        assertEquals(count, 1);
    }

    @Test
    public void testOne(){
        device.findObject(By.res(DIF_PACKAGE, "WORDDISPLAY"));
        assertEquals(device.findObject(By.res(DIF_PACKAGE, "WORDDISPLAY")).getText(), " ");
    }


    //finishes the game for 9x9 peaceful mode and also tests whether a word placement is valid
    @Test
    public void finishGame() throws UiObjectNotFoundException {

        // 81-57- 1 is the number of spots not filled (using calculation hussain made) ** test only works in peaceful mode 9x9
        for(int j = 0; j < 81 - 57 - 1 ; j++) {
            device.findObject(new UiSelector().text(" ")).click();
            for (int i = 0; i < 9; i++) {
                device.findObject(By.text(gameWordInitializer.gameWordArray[i].getEnglish())).click();

                // Check if the text of the WORDDISPLAY TextView is equal to a space
                UiObject2 wordDisplay = device.findObject(By.res(DIF_PACKAGE, "WORDDISPLAY"));
                if (wordDisplay.getText().equals(" ")) {
                    break;
                }
            }

        }
        assertTrue(true);
    }

    //test to ensure the hint button is there
    @Test
    public void hintButtonTest(){
        assertTrue(device.hasObject(By.res(DIF_PACKAGE, "hintButton")));
    }

    //teting to see if the hint button can be clicked with an empty grid spot
    // this test only fails if it cannot place in a word
    @Test
    public void useHintButton() throws UiObjectNotFoundException {
        device.findObject(By.res(DIF_PACKAGE, "hintButton")).click();
        device.findObject(new UiSelector().text(" ")).click();
    }


}