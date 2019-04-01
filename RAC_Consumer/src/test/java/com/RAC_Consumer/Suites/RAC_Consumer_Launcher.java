package com.RAC_Consumer.Suites;


import com.RAC_Consumer.RACAndroidRunner;
import com.runner.annotations.SuiteInformation;
import com.runner.annotations.TestInformation;
import io.appium.java_client.functions.ExpectedCondition;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

@SuiteInformation (

        suiteName = "Onboarding Tests",
        suiteDescription = "Onboarding Tests for RAC",
        priority = SuiteInformation.SuitePriority.NONE,
        suiteAcceptanceCriteria = {
                "ROUTE ONBOARDING IS FIRST SCREEN HIT"
        }
)

public class RAC_Consumer_Launcher {

    @TestInformation(
            testName = "Onboarding test",
            testDescription = "Totally not a 420",
            expectedBehaviour = "Dank noodles",
            priority = TestInformation.TestPriority.CRITICAL,
            type = TestInformation.TestType.AUTOMATIC
    )
    @Test
    public void basicTest () throws InterruptedException{

        RACAndroidRunner.wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.id("android:id/content"))).click();

//        try{

            Assert.assertTrue("T&C's Apperaing", RACAndroidRunner.driver.findElementById("uk.co.rac.roadside:id/tandc_text_container").isDisplayed());
//        } catch(Exception e){
//            Assert.fail("COULD NOT FIND ELEMENT : uk.co.rac.roadside:id/tandc_text_container \n" + e.getMessage().toString());
//        }

        RACAndroidRunner.wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.id("uk.co.rac.roadside:id/tandc_accept_text"))).click();
 //wait(2500);
    }

}
