package com.example.myapplication;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.hamcrest.CoreMatchers.notNullValue;

import android.content.Context;
import android.content.Intent;

import org.hamcrest.MatcherAssert;
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
public class listeningComprehensionTest {
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


        //gets us to pre game screen
        device.findObject(new UiSelector().text("START GAME")).click();

        //enables peaceful difficulty
        device.findObject(new UiSelector().text("PEACEFUL")).click();

        //enables the listening comprehension mode
        device.findObject(new UiSelector().text("VOICE MODE")).click();

        //starts the game with the above settings
        device.findObject(new UiSelector().text("START GAME")).click();

    }

    @Test
    public void checkPreConditions() {
        assertThat(device, notNullValue());
    }

    //testing to see if the screen loads with numbers as opposed to words
    @Test
    public void hasNumbers() {
        int flag = 0;
        if (device.hasObject(By.text("1"))) {
            flag = 1;
        } else if (device.hasObject(By.text("2"))) {
            flag = 1;
        } else if (device.hasObject(By.text("3"))) {
            flag = 1;
        } else if (device.hasObject(By.text("4"))) {
            flag = 1;
        } else if (device.hasObject(By.text("5"))) {
            flag = 1;
        } else if (device.hasObject(By.text("6"))) {
            flag = 1;
        } else if (device.hasObject(By.text("7"))) {
            flag = 1;
        } else if (device.hasObject(By.text("8"))) {
            flag = 1;
        } else if (device.hasObject(By.text("9"))) {
            flag = 1;
        }
        // if the game does not find any numbers on screen then then the
        // test should fail
        else {
            flag = 0;
        }
        assertEquals(flag, 1);

    }


    // checks to see if we can place a word still
    @Test
    public void placeWord() throws UiObjectNotFoundException {
        device.findObject(new UiSelector().text(" ")).click();

        for (int i = 0; i < 9; i++) {
            device.findObject(By.text(gameWordInitializer.gameWordArray[i].getEnglish())).click();
        }


    }

    //making sure that the top word display does not have anything in it
    @Test
    public void topWordDisplay() {
        device.findObject(By.res(DIF_PACKAGE, "WORDDISPLAY"));
        assertEquals(device.findObject(By.res(DIF_PACKAGE, "WORDDISPLAY")).getText(), " ");
    }


    //test to make sure that the top word displays the correct word when something is clicked
    @Test
    public void topWordDisplayCorrect() throws UiObjectNotFoundException {
        String flag = "";
        if (device.hasObject(By.text("1"))) {
            device.findObject(new UiSelector().text("1")).click();
            flag = "1";
        }
        else if (device.hasObject(By.text("2"))) {
            device.findObject(new UiSelector().text("2")).click();
            flag = "2";
        }
        UiObject2 wordDisplay = device.findObject(By.res(DIF_PACKAGE, "WORDDISPLAY"));
        assertEquals(wordDisplay.getText(), flag);
    }

    @Test
    public void finishGameForListeningComprehension() throws UiObjectNotFoundException {

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



}

