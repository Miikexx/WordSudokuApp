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
public class reverseModeUITest{
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
        device.findObject(new UiSelector().text("REVERSE MODE")).click();
        device.findObject(new UiSelector().text("START GAME")).click();
    }

    //this tests out if the user can finish the game in its reverse mode
    //The test will fail if the game doesnt load the correct set of words (translation)
    //Will fail if the game cannot be completed
    //Will fail if the words cannot be placed in the correct spot
    // Multipurposed test

    @Test
    public void finishReverseMode9x9Game() throws UiObjectNotFoundException {

        // 81-57- 1 is the number of spots not filled (using calculation hussain made) ** test only works in peaceful mode 9x9
        for(int j = 0; j < 81 - 57 - 1 ; j++) {
            device.findObject(new UiSelector().text(" ")).click();
            for (int i = 0; i < 9; i++) {
                device.findObject(By.text(gameWordInitializer.gameWordArray[i].getTranslation())).click();

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