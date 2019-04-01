package com.abrsm.suites;
import com.abrsm.AbrsmAndroidRunner;
import com.automation.AndroidNavUtils;
import com.runner.annotations.SuiteInformation;
import com.runner.annotations.TestInformation;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

@SuiteInformation(
        suiteName = "Onboarding Tests",
        suiteDescription = "Onboarding Tests for ABRSM Music Case",
        priority = SuiteInformation.SuitePriority.NONE,
        suiteAcceptanceCriteria = {
                "Shit DOESNT work fam"
        }
)
public class OnboardingScreenTests {

    @TestInformation(
            testName = "Onboarding test",
            testDescription = "Totally not a 420",
            expectedBehaviour = "Dank noodles",
            priority = TestInformation.TestPriority.CRITICAL,
            type = TestInformation.TestType.AUTOMATIC
    )
    @Test
    public void basicTest () throws InterruptedException {
        //Taps the skip button for onboarding
        AbrsmAndroidRunner.wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.id("org.abrsm.music.case.qa:id/onboarding_navigation_skip"))).click();


        //Taps the login button
        AbrsmAndroidRunner.wait.until(ExpectedConditions.visibilityOfElementLocated
                (By.id("org.abrsm.music.case.qa:id/login_select_login"))).click();
        AndroidNavUtils.clickOnElementByID(AbrsmAndroidRunner.driver,"");


    }

}
