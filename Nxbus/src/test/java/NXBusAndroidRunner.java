package com.NXbus;

//import com.NXbus.suites.NXbusCreateAccount;
import com.NXbus.suites.NXbus_LaunchApp;
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
        NXbus_LaunchApp.class
})
@Setup(
        application = "National Express Bus",
        reportType = Setup.ReportType.EXCEL,
        version = "1",
        attempt = 420
)
public class NXBusAndroidRunner implements EnhancedTestInterface {

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
        caps.setCapability("deviceName", "Pixel_API_27");
        caps.setCapability("udid", "emulator-5554");
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "8.1");
        caps.setCapability("skipUnlock", "true");
        caps.setCapability("appPackage", "com.nationalexpress.tickets.debug");
        caps.setCapability("appActivity", "com.nationalexpress.tickets.ui.splash.SplashActivity");
        caps.setCapability("noReset", "false");

        setupDriver(caps);
    }

    public void onTestFinished(boolean result, String className, String methodName) {
        destroyDriver();
    }

    public void onTestFailure(String className, String methodName, String message, String stack) {

    }

    public void onTestPassed(String className, String methodName) {

    }

    public void onTestIgnored(String className, String methodName) {

    }

    @AfterSuite
    public void afterSuite() {


    }
}
