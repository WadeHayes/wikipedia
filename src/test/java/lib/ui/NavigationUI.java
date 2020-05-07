package lib.ui;

import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

public abstract class NavigationUI extends MainPageObject {
    protected static String MY_LISTS_LINK;
    protected static String OPEN_NAVIGATION;

    public NavigationUI(RemoteWebDriver driver) {
        super(driver);
    }

    public void openNavigation() throws Exception {
        if (Platform.getInstance().isMw()) {
            this.waitForElementClick(OPEN_NAVIGATION, "Cannot click button ''Open navigation", 5);
        } else {
            System.out.println("Method openNavigation() does nothing for platform  " + Platform.getInstance().getPlatformVariables());
        }
    }

    public void clickMyList() throws Exception {
        if (Platform.getInstance().isMw()) {
            this.tryClickElementWithFewAttempts(MY_LISTS_LINK, "Cannot click on the button lisls ", 5);
        } else {
            this.waitForElementClick(MY_LISTS_LINK, "Cannot find element 'My lists'", 5);
        }
    }
}
