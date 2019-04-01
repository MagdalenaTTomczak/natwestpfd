package com.RAC_Consumer.Suites;


import com.RAC_Consumer.RACAndroidRunner;
import com.runner.annotations.SuiteInformation;
import com.runner.annotations.TestInformation;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.functions.ExpectedCondition;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

@SuiteInformation (

        suiteName = "ALaunch App",
        suiteDescription = "Ability to interact with the various elements of the App once Launched",
        priority = SuiteInformation.SuitePriority.NONE,
        suiteAcceptanceCriteria = {
                ""
        }
)

public class RAC_Consumer_Launcher {

    @TestInformation(
            testName = "Accept the Terms & Conditions",
            testDescription = "As a new user\n" +
                    "I want to use the app for the first time\n" +
                    "So That I can see what actions I can agree to T&C's and configurations",
            expectedBehaviour = "\"Given user on the Terms and conditions screen\\n\" +\n" +
                    "                        \"When 'Accept' action button is selected\\n\" +\n" +
                    "                        \"Then the first time welcome screen is shown\\n\" +\n" +
                    "                        \"And the 'Notifications & Alerts' popup appears\\n\" +\n" +
                    "                        \"And the user is notified they can allow or Don't allow notifications\"" ,
            priority = TestInformation.TestPriority.CRITICAL,
            type = TestInformation.TestType.AUTOMATIC
    )
    @Test
    public void basicTest () throws InterruptedException{

        RACAndroidRunner.wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.id("android:id/content"))).click();


        Assert.assertTrue("T&C's Apperaing", RACAndroidRunner.driver.findElementById("uk.co.rac.roadside:id/tandc_text_container").isDisplayed());


        AndroidTouchAction Swipe = new AndroidTouchAction(RACAndroidRunner.driver);
        Swipe.press(PointOption.point(630, 1601)).moveTo(PointOption.point(630, 1400)).release();

        RACAndroidRunner.wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.id("uk.co.rac.roadside:id/tandc_go_button"))).click();

        Assert.assertTrue("", RACAndroidRunner.driver.findElementById("uk.co.rac.roadside:id/parentPanel").isDisplayed());
        String popUpTitle = "";
        popUpTitle = RACAndroidRunner.driver.findElementById("uk.co.rac.roadside:id/alertTitle").getText();
        if (popUpTitle.equals("NOTIFICATIONS & ALERTS")){
            Assert.assertTrue("YAY, Found POP-up Title: " + popUpTitle, true);

            //RACAndroidRunner.driver.findElementById("android:id/button1").click();
        }
        else{
            Assert.fail("OH-OH, Didn't find popUp");
        }

        if (RACAndroidRunner.driver.findElementById("android:id/button2").isDisplayed()){
            Assert.assertTrue("YAY, Found NOT NOW Button " + popUpTitle, true);
            Assert.assertTrue(RACAndroidRunner.driver.findElementById("android:id/button2").getText() == "NOT NOW");

            //RACAndroidRunner.driver.findElementById("android:id/button1").click();
        }
        else{
            Assert.fail("OH-OH, Didn't find NOT NOW button:" );
        }

        if (RACAndroidRunner.driver.findElementById("android:id/button1").isDisplayed()){

            Assert.assertTrue("YAY, Found Enable Button " + popUpTitle, true);
            Assert.assertTrue(RACAndroidRunner.driver.findElementById("android:id/button1").getText() == "ENABLE");

            //RACAndroidRunner.driver.findElementById("android:id/button1").click();
        }
        else{
            Assert.fail("OH-OH, Didn't find Enable button:" );
        }


        // uk.co.rac.roadside:id/alertTitle
 //wait(2500);
    }

}
