package com.example.myapplication;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static java. lang.Thread.sleep;


import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SdkSuppress;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class MainActivityTest {
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

    }

    @Test
    public void checkPreConditions() {
        assertThat(device, notNullValue());
    }



    @Test
    public void checkChildCount() throws UiObjectNotFoundException {
        UiObject startGameBtn = device.findObject(new UiSelector().text("START GAME"));
        assertEquals(startGameBtn.getChildCount(), 0);

    }

    @Test
    public void hasHowToPlay(){
        assert(device.hasObject(By.res(DIF_PACKAGE, "HowToPlay")));
    }



    @Test
    public void checkStartGame() throws UiObjectNotFoundException {
        UiObject startGameBtn = device.findObject(new UiSelector().text("START GAME"));
        assertEquals(startGameBtn.getText(), "START GAME");
    }


    @Test
    public void checkHowToPlay() throws UiObjectNotFoundException {
        UiObject howToPlayBtn = device.findObject(new UiSelector().text("HOW TO PLAY"));
        assertEquals(howToPlayBtn.getText(), "HOW TO PLAY");
    }

}