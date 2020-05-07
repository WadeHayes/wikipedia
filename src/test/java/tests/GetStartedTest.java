package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.WelcomePageObject;
import org.junit.Test;

public class GetStartedTest extends CoreTestCase {
    @Test
    public void testPassThroughWelcome() throws Exception {
        if ((Platform.getInstance().isAndroid()) || (Platform.getInstance().isMw())){
            return;
        }

        WelcomePageObject welcomePageObject = new WelcomePageObject(driver);

        welcomePageObject.waitForLearnMoreList();
        welcomePageObject.clickButtonNext();
        welcomePageObject.waitForNewWaysToExploreText();
        welcomePageObject.clickButtonNext();
        welcomePageObject.waitForAddOrEditPreferredLanguagesLink();
        welcomePageObject.clickButtonNext();
        welcomePageObject.waitForLearnMoreAboutDataCollectedLink();
        welcomePageObject.clickButtonGetStarted();
    }
}
