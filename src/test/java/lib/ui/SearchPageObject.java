package lib.ui;

import org.openqa.selenium.remote.RemoteWebDriver;

public abstract class SearchPageObject extends MainPageObject{
    protected static String SEARCH_INIT_ELEMENT;
    protected static String SEARCH_INPUT;
    protected static String SEARCH_CANCEL_BUTTON;
    protected static String SEARCH_RESULT_BY_SUBSTRING_TPL;
    protected static String SEARCH_RESULT_ELEMENT;
    protected static String EMPTY_SEARCH_RESULT_ELEMENT;

    public SearchPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    /* TEMPLATES MEHTODS */
    public static String getResultSearchElement(String substring){
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
    /* TEMPLATES MEHTODS */

    public void initSearchInput() throws Exception {
        this.waitForElementClick(SEARCH_INIT_ELEMENT, "Cannot find and click search init element", 5);
        this.waitForElementPresent(SEARCH_INIT_ELEMENT, "cannot find search input after clicking search init element");
    }

    public void waitForCancelButtonToAppear() throws Exception {
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON, "Cannot find search cancel button!");
    }

    public void waitForCancelButtonToDisappear() throws Exception {
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "Search cancel button is still present!", 5);
    }

    public void clickCancelSearchButton() throws Exception {
        this.waitForElementClick(SEARCH_CANCEL_BUTTON, "Cannot find and click search cancel button.", 5);
    }

    public void typeSearchLine(String searchLine) throws Exception {
        this.waitForElementSendKeys(SEARCH_INPUT, searchLine, "Cannot find and type into search input", 5);
    }

    public void waitForSearchResult(String substring) throws Exception {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(search_result_xpath, "Cannot find search result with substring" + substring);
    }

    public void clickByArticleWithSubstring(String substring) throws Exception {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementClick(search_result_xpath, "Cannot find search result with substring " + substring, 10);
    }

    public int getAmountOfFoundArticles() throws Exception {
        this.waitForElementPresent(SEARCH_RESULT_ELEMENT, "Csnnot find anything by the request ", 15);
        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    public void waitForEmptyResultsLabel() throws Exception {
        this.waitForElementPresent(EMPTY_SEARCH_RESULT_ELEMENT, "Cannnot find empty result element.", 15);
    }

    public void assertThereIsNoResultSearch() throws Exception {
        this.assertElementNotRresent(SEARCH_RESULT_ELEMENT, "We supposed not to find any results");
    }
}
