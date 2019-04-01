package com.NXbus.suites;


import com.NXbus.NXBusAndroidRunner;
import com.runner.annotations.SuiteInformation;
import com.runner.annotations.TestInformation;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;


@SuiteInformation(
            suiteName = "1.0 Launch App",
            suiteDescription = "Launch Application Tests",
            priority = SuiteInformation.SuitePriority.NONE,
            suiteAcceptanceCriteria = {
                    "Validate how the App manages Launching first and subsequent times"
            }
    )

    public class NXbus_LaunchApp {


        @TestInformation(
                testName = "Launch the app for the first time",

                testDescription = "As a new user\n" +
                        "I want to be able to launch the NX App\n" +
                        "So that I can use it",

                expectedBehaviour = "Given user has installed the App\n" +
                        "When the user launches it for the first time\n" +
                        "Then the NX splash screen with the National Express logo is displayed momentarily\n" +
                        "And the 1st Onboarding screen is displayed",

                priority = TestInformation.TestPriority.CRITICAL,
                type = TestInformation.TestType.AUTOMATIC
        )


        @Test
        public void basicTest () throws InterruptedException {
            //Taps the Splash Screen
            try{
                NXBusAndroidRunner.wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/hierarchy/android.widget.FrameLayout"))).click();
            }catch (Exception l) {
                Assert.fail("Ooops; looks like it all went badly wrong" + l);
            }
            // Check Onboard1 appears
            try {

                NXBusAndroidRunner.driver.findElement(By.id("com.nationalexpress.tickets.debug:id/img"));
                NXBusAndroidRunner.driver.findElement(By.id("com.nationalexpress.tickets.debug:id/icon"));
                NXBusAndroidRunner.driver.findElement(By.id("com.nationalexpress.tickets.debug:id/title"));

            } catch (Exception e) {

                Assert.fail("TEST Failed as element is missing" + e);
            }
//            //Swipe
            try {
                AndroidTouchAction Swipe = new AndroidTouchAction(NXBusAndroidRunner.driver);

                Swipe.press(PointOption.point(895, 760)).moveTo(PointOption.point(301, 760)).release();
            } catch (Exception sE) {

                Assert.fail("Opps Sipe action failed" + sE);
            }

        }



    }
