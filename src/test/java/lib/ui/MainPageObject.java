package lib.ui;

import lib.Platform;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.regex.Pattern;

public class MainPageObject {
    protected RemoteWebDriver driver;

    public MainPageObject(RemoteWebDriver driver){
        this.driver = driver;
    }

    public WebElement waitForElementPresent(String locator, String errorMassege, long timeoutInSecond) throws Exception {
        By by = this.getLocatorByString(locator);
        WebDriverWait webDriverWait = new WebDriverWait(driver, timeoutInSecond);
        webDriverWait.withMessage(errorMassege + "\n");
        return webDriverWait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public WebElement waitForElementPresent(String locator, String errorMassege) throws Exception {
        return waitForElementPresent(locator, errorMassege, 5);
    }

    public WebElement waitForElementClick(String locator, String errorMassege, long timeoutInSecond) throws Exception {
        WebElement webElement = waitForElementPresent(locator, errorMassege, timeoutInSecond);
        webElement.click();
        return webElement;
    }

    public WebElement waitForElementSendKeys(String locator, String value, String errorMassege, long timeoutInSecond) throws Exception {
        WebElement webElement = waitForElementPresent(locator, errorMassege, timeoutInSecond);
        webElement.sendKeys(value);
        return webElement;
    }

    public boolean waitForElementNotPresent(String locator, String errorMassege, long timeoutInSecond) throws Exception {
        By by = this.getLocatorByString(locator);
        WebDriverWait webDriverWait = new WebDriverWait(driver, timeoutInSecond);
        webDriverWait.withMessage(errorMassege + "\n");
        return webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public WebElement waitForElementClear(String locator, String errorMassege, long timeoutInSecond) throws Exception {
        WebElement webElement = waitForElementPresent(locator, errorMassege, timeoutInSecond);
        webElement.clear();
        return webElement;
    }

    public void swipeUp(int timeOfSwipe) {
        if (driver instanceof AppiumDriver){
            TouchAction touchAction = new TouchAction((AppiumDriver) driver);
            Dimension size = driver.manage().window().getSize();
            int x = size.width / 2;
            int start_y = (int) (size.height * 0.8);
            int end_y = (int) (size.height * 0.2);
            touchAction
//                .press(x, start_y)
                    .press(x, start_y)
                    .waitAction(timeOfSwipe)
                    .moveTo(x, end_y)
                    .release()
                    .perform();
        }else {
            System.out.println("Method swipeUp() does nothing for platform " + Platform.getInstance().getPlatformVariables());
        }
    }

    public void swipeUpQuick(){
        swipeUp(2000);
    }

    public void scrollWebPageUp() {
        if (Platform.getInstance().isMw()) {
            JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
            JSExecutor.executeScript("window.scrollBy(0, 250)");
        }else {
            System.out.println("Method  scrollWebPageUp() does nothing for platform "+ Platform.getInstance().getPlatformVariables() );
        }
    }

    public void scrollWebPageTillElementNotVisible(String locator, String error_message, int max_swipes) throws Exception {
        int already_swiped = 0;
        WebElement webElement = this.waitForElementPresent(locator, error_message, 5);
        while (!this.isElementLocatedOnTheScreen(locator)) {
            if (already_swiped > max_swipes){
                Assert.assertTrue(error_message, webElement.isDisplayed());
            }

            scrollWebPageUp();
            ++already_swiped;
        }
    }

    public void swipeUpToFindElement(String locator, String errorMessage, int maxSwipes) throws Exception {
        By by = this.getLocatorByString(locator);
        int already_swiped = 0;
        while(driver.findElements(by).size() == 0){
            swipeUpQuick();
            if(already_swiped > maxSwipes){
                waitForElementPresent(locator, "Cannot find element" + errorMessage, 0);
                return;
            }
            swipeUpQuick();
            ++already_swiped;
        }
    }

    public void swipeUpTillelementAppear(String locator, String errorMessage, int maxSwipes) throws Exception {
        int already_swiped = 0;
        while (!this.isElementLocatedOnTheScreen(locator)){
            if (already_swiped > maxSwipes){
                Assert.assertTrue(errorMessage, this.isElementLocatedOnTheScreen(locator));
            }
            swipeUpQuick();
            ++already_swiped;
        }
    }

    public boolean isElementLocatedOnTheScreen(String locator) throws Exception {
        int elementLocationBy_Y = this.waitForElementPresent(locator, "Cannot find element by locator", 5).getLocation().getY();
        if (Platform.getInstance().isMw()) {
            JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
            Object jsResult = JSExecutor.executeScript("return window.pageYOffset");
            elementLocationBy_Y -= Integer.parseInt(jsResult.toString());
        }
        int screenSizeBy_Y = driver.manage().window().getSize().getHeight();
        return elementLocationBy_Y < screenSizeBy_Y;
    }

    public void clickElementToTheRightUpperCorner(String locator, String errorMessage) throws Exception {
        if (driver instanceof AppiumDriver){
            WebElement webElement = this.waitForElementPresent(locator + "/..", errorMessage);
            int right_X = webElement.getLocation().getX();
            int upper_Y = webElement.getLocation().getY();
            int lower_Y = upper_Y + webElement.getSize().getHeight();
            int middle_Y = (upper_Y + lower_Y) / 2;
            int width = webElement.getSize().getWidth();
            int pointToClick_X = (right_X + width) - 3;
            int pointToClick_Y = middle_Y;
            TouchAction touchAction = new TouchAction((AppiumDriver) driver);
            touchAction.tap(pointToClick_X, pointToClick_Y).perform();
        }else {
            System.out.println("Method clickElementToTheRightUpperCorner() does nothing for platform " + Platform.getInstance().getPlatformVariables());
        }
    }

    public void swipeElementToLeft(String locator, String errorMessage) throws Exception {
        if (driver instanceof AppiumDriver){
            WebElement webElement = waitForElementPresent(locator, errorMessage, 5);
            int left_x = webElement.getLocation().getX();
            int right_x = left_x + webElement.getSize().getWidth();
            int upper_y = webElement.getLocation().getY();
            int lower_y = upper_y + webElement.getSize().getHeight();
            int middle_y = (upper_y + lower_y) / 2;

            TouchAction touchAction = new TouchAction((AppiumDriver)driver);
            touchAction.press(right_x, middle_y);
            touchAction.waitAction(300);
            if (Platform.getInstance().isAndroid()){
                touchAction.moveTo(left_x, middle_y);
            }else {
                int offset_X = (-1 * webElement.getSize().getWidth());
                touchAction.moveTo(offset_X, 0);
            }
            touchAction.release();
            touchAction.perform();
        }else {
            System.out.println("Method swipeElementToLeft() does nothing for platform " + Platform.getInstance().getPlatformVariables());
        }
    }

    public int getAmountOfElements(String locator) throws Exception {
        By by = this.getLocatorByString(locator);
        List elements = driver.findElements(by);
        return elements.size();
    }

    public boolean isElementPresent(String locator) throws Exception {
        return getAmountOfElements(locator) > 0;
    }

    public void tryClickElementWithFewAttempts(String locator, String errorMessage, int amountOfAttempts) throws Exception {
        int currentAttempts  = 0;
        boolean needMoreAttempts = true;

        while (needMoreAttempts){
            try {
                this.waitForElementClick(locator, errorMessage, 1);
                needMoreAttempts = false;
            } catch (Exception e) {
                if (currentAttempts > amountOfAttempts) {
                    this.waitForElementClick(locator, errorMessage, 1);
                }
            }
            ++currentAttempts;
        }
    }

    public void assertElementNotRresent(String locator, String errorMessage) throws Exception {
        int amountOfElements = getAmountOfElements(locator);
        if (amountOfElements > 0){
            String defaultMessage = "An element " + locator + " supposed to be not present";
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }
    }

    public String waitForElementAndGetAttribute(String locator, String attribute, String errorMessage, long timeoutInSeconds) throws Exception {
        WebElement webElement = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        return webElement.getAttribute(attribute);
    }

    private By getLocatorByString(String locatorWithType) throws Exception {
        String[] explodedLocator = locatorWithType.split(Pattern.quote(":"), 2);
        String byType = explodedLocator[0];
        String locator = explodedLocator[1];
        if (byType.equals("xpath")){
            return By.xpath(locator);
        }else if (byType.equals("id")){
            return By.id(locator);
        }else if (byType.equals("css")) {
            return By.cssSelector(locator);
        }else {
            throw new Exception("cannot get type of locator. Locator: " + locatorWithType);
        }
    }
}
