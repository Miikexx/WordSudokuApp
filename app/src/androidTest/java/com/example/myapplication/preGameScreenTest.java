package com.example.myapplication;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.hamcrest.CoreMatchers.notNullValue;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.SdkSuppress;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
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
public class preGameScreenTest {
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

        UiObject2 startButton = device.findObject(By.res(DIF_PACKAGE, "StartGame"));
        startButton.click();

        }


        @Test
        public void hasButtons() throws UiObjectNotFoundException {
            UiObject2 startButton1 = device.findObject(By.res(DIF_PACKAGE, "StartGame"));
            startButton1.click();

            assertTrue(device.hasObject(By.res(DIF_PACKAGE, "button6x6")));
            //assertEquals(device.findObject(By.res(DIF_PACKAGE, "button6x6")).getText(), "6X6");


            assertTrue(device.hasObject(By.res(DIF_PACKAGE, "button4x4")));
            //assertEquals(device.findObject(By.res(DIF_PACKAGE, "button4x4")).getText(), "4X4");

            assertTrue(device.hasObject(By.res(DIF_PACKAGE, "button9x9")));
            //assertEquals(device.findObject(By.res(DIF_PACKAGE, "button9x9")).getText(), "9X9");

            assertTrue(device.hasObject(By.res(DIF_PACKAGE, "button12x12")));
            //assertEquals(device.findObject(By.res(DIF_PACKAGE, "button12x12")).getText(), "12X12");

            assertTrue(device.hasObject(By.res(DIF_PACKAGE, "peacefulDifficulty")));
            //assertEquals(device.findObject(By.res(DIF_PACKAGE, "peacefulDifficulty")).getText(), "PEACEFUL");

            assertTrue(device.hasObject(By.res(DIF_PACKAGE, "normalDifficulty")));
            //assertEquals(device.findObject(By.res(DIF_PACKAGE, "normalDifficulty")).getText(), "NORMAL");

            assertTrue(device.hasObject(By.res(DIF_PACKAGE, "hardDifficulty")));
            //assertEquals(device.findObject(By.res(DIF_PACKAGE, "hardDifficulty")).getText(), "HARD");

            assertTrue(device.hasObject(By.res(DIF_PACKAGE, "hardcoreDifficulty")));
            //assertEquals(device.findObject(By.res(DIF_PACKAGE, "hardcoreDifficulty")).getText(), "HARDCORE");

        }




    }