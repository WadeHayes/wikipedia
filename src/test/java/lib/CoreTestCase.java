package lib;

import lib.ui.WelcomePageObject;
import io.appium.java_client.AppiumDriver;
import junit.framework.TestCase;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.RemoteWebDriver;

public class CoreTestCase extends TestCase {
    private static final String PLATFORM_ANDROID = "android";
    private static final String PLATFORM_IOS = "iOS";

    protected RemoteWebDriver driver;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        driver = Platform.getInstance().getDriver();
        this.rotateScreenPortrait();
        this.skipWelcomePageForIOSApp();
        this.openWikipediaWebPageForMobileWeb();
    }

    @Override
    protected void tearDown() throws Exception{
        driver.quit();
        super.tearDown();
    }

    protected void rotateScreenPortrait(){
        if (driver instanceof AppiumDriver){
            AppiumDriver appiumDriver = (AppiumDriver) this.driver;
            appiumDriver.rotate(ScreenOrientation.PORTRAIT);
        }else {
            System.out.println("Method rotateScreenPortrait() does nothing for platform " + Platform.getInstance().getPlatformVariables());
        }
    }
    protected void rotateScreenLandscape(){
        if (driver instanceof AppiumDriver){
            AppiumDriver appiumDriver = (AppiumDriver) this.driver;
            appiumDriver.rotate(ScreenOrientation.LANDSCAPE);
        }else {
            System.out.println("Method rotateScreenPortrait() does nothing for platform " + Platform.getInstance().getPlatformVariables());
        }
    }

    protected void backgroundApp(int seconds){
        if (driver instanceof AppiumDriver){
            AppiumDriver appiumDriver = (AppiumDriver) this.driver;
            appiumDriver.runAppInBackground(seconds);
        }else {
            System.out.println("Method rotateScreenPortrait() does nothing for platform " + Platform.getInstance().getPlatformVariables());
        }
    }

    protected void openWikipediaWebPageForMobileWeb(){
        if (Platform.getInstance().isMw()){
            driver.get("https://en.m.wikipedia.org");
        }else {
            System.out.println("Method openWikipediaWebPageForMobileWeb() does nothing for platform " + Platform.getInstance().getPlatformVariables());
        }
    }

    private void skipWelcomePageForIOSApp() throws Exception {
        if (Platform.getInstance().isIOS()){
            AppiumDriver appiumDriver = (AppiumDriver) this.driver;
            WelcomePageObject welcomePageObject = new WelcomePageObject(appiumDriver);
            welcomePageObject.clickSkip();
        }
    }
}
