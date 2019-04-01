package com.automation;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AndroidNavUtils {

    public static AndroidDriver<MobileElement> driver;
    public static WebDriverWait wait;

    public static void testThisShit() {


    }

    public static void clickOnElementByID(AndroidDriver<MobileElement> driver, String elementId) {
        AndroidNavUtils.wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(elementId))).click();

    }
}

