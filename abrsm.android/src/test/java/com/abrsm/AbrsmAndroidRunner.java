package com.abrsm;

import com.abrsm.suites.OnboardingScreenTests;
import com.automation.AndroidNavUtils;
import com.runner.annotations.Setup;
import com.runner.interfaces.EnhancedTestInterface;
import com.runner.runner.EnhancedSuite;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import java.net.MalformedURLException;
import java.net.URL;

@RunWith(EnhancedSuite.class)
@Suite.SuiteClasses({
        OnboardingScreenTests.class
})
@Setup(
        application = "ABRSM Music Case (Android)",
        reportType = Setup.ReportType.EXCEL,
        version = "1",
        attempt = 420
)
public class AbrsmAndroidRunner implements EnhancedTestInterface {

    public static AndroidDriver<MobileElement> driver;
    public static WebDriverWait wait;

    public static AndroidDriver<MobileElement> setupDriver(DesiredCapabilities capabilities) throws MalformedURLException {
        URL url = new URL("http://127.0.0.1:4723/wd/hub");
        driver = new AndroidDriver<MobileElement>(url, capabilities);
        wait = new WebDriverWait (driver,10);
        return driver;
    }

    public static void destroyDriver() {
        driver.quit();
    }

    public void onTestStarted(String className, String methodName) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName", "Pixel_2_XL_API_28");
        caps.setCapability("udid", "emulator-5554");
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "9.0");
        caps.setCapability("skipUnlock", "true");
        caps.setCapability("appPackage", "org.abrsm.music.case.qa");
        caps.setCapability("appActivity", "com.abrsm.musiccase.ui.splash.SplashActivity");
        caps.setCapability("noReset", "false");

        setupDriver(caps);
    }

    public void onTestFinished(boolean result, String className, String methodName) {
        destroyDriver();
    }

    public void onTestFailure(String className, String methodName, String message, String stack) {

    }

    public void onTestPassed(String className, String methodName) {
        AndroidNavUtils.clickOnElement(driver, "elementId");

    }

    public void onTestIgnored(String className, String methodName) {

    }

    @AfterSuite
    public void afterSuite() {


    }
}
