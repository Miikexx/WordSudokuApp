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
public class howToPlayUITest{
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


        //gets us to howToPlay  from start screen
        device.findObject(new UiSelector().text("HOW TO PLAY")).click();


    }

    //making sure we are on the correct screen (we have the how to play page)
    // also making sure we have the title for our how to play page
    @Test
    public void hasTitle(){
        assert(device.hasObject(By.res(DIF_PACKAGE, "editTextTextPersonName4")));
    }


    //making sure the title text is what it is supposed to be
    @Test
    public void titleText(){
        assertEquals(device.findObject(By.res(DIF_PACKAGE, "editTextTextPersonName4")).getText(), "Rules and General Information");
    }

    //making sure the game has a back button
    @Test
    public void hasBackButton(){
        assert(device.hasObject(By.res(DIF_PACKAGE, "HowToPlayBackButton")));
    }

    //making sure we have the objective for the rules
    @Test
    public void hasObjectiveForRules(){
        assert(device.hasObject(By.res(DIF_PACKAGE, "textView")));
    }

}


