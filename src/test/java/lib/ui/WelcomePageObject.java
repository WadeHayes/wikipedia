package lib.ui;

import org.openqa.selenium.remote.RemoteWebDriver;

public class WelcomePageObject extends MainPageObject {
    private static final String STEP_LEARN_MORE_LIST = "id:Learn more about Wikipedia";
    private static final String STEP_NEW_WAYS_TO_EXPLORER_TEXT = "id:New ways to explore";
    private static final String STEP_ADD_OR_EDIT_PREFERRED_LANGUAGES_LINK = "id:Add or edit preferred languages";
    private static final String LEARN_MOER_ABOUT_DATA_COLLECTED_LINK = "id:Learn more about data collected";
    private static final String BUTTON_NEXT = "id:Next";
    private static final String BUTTON_GET_STARTED = "id:Get started";
    private static final String BUTTON_SKIP = "id:Skip";

    public WelcomePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void waitForLearnMoreList() throws Exception {
        this.waitForElementPresent(STEP_LEARN_MORE_LIST, "Cannot find link 'Learn more about Wikipedia'", 5);
    }

    public void waitForNewWaysToExploreText() throws Exception {
        this.waitForElementPresent(STEP_NEW_WAYS_TO_EXPLORER_TEXT, "Cannot find text 'New ways to explore'", 5);
    }

    public void waitForAddOrEditPreferredLanguagesLink() throws Exception {
        this.waitForElementPresent(STEP_ADD_OR_EDIT_PREFERRED_LANGUAGES_LINK, "Cannot find link 'Add or edit preferred languages'", 5);
    }

    public void waitForLearnMoreAboutDataCollectedLink() throws Exception {
        this.waitForElementPresent(LEARN_MOER_ABOUT_DATA_COLLECTED_LINK, "Cannot find link 'Learn more about data collected'", 5);
    }

    public void clickButtonNext() throws Exception {
        this.waitForElementClick(BUTTON_NEXT, "Cannot find button 'Next'", 5);
    }

    public void clickButtonGetStarted() throws Exception {
        this.waitForElementClick(BUTTON_GET_STARTED, "Cannot find button 'Get started'", 5);
    }

    public void clickSkip() throws Exception {
        this.waitForElementClick(BUTTON_SKIP, "Cannot find button 'Skip'", 5);
    }
}
