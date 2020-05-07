package lib.ui;

import lib.Platform;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

public abstract class ArticlePageObject extends MainPageObject{
    protected static String TITLE;
    protected static String FOOTER_ELEMENT;
    protected static String OPTIONS_BUTTON;
    protected static String OPTIONS_ADD_TO_MY_LIST_BUTTON;
    protected static String OPTIONS_REMOVE_FROM_MY_LIST_BUTTON;
    protected static String ADD_TO_MY_LIST_OVERLAY;
    protected static String MY_LIST_NAME_INPUT;
    protected static String MY_LIST_OK;
    protected static String CLOSE_ARTICLE_BUTTON;

    public ArticlePageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public WebElement waitForTitleElement() throws Exception {
        return this.waitForElementPresent(TITLE, "Cannot find article title on page!", 15);
    }

    public String getArticleTitle() throws Exception {
        WebElement title_element = waitForTitleElement();
        if (Platform.getInstance().isAndroid()){
            return title_element.getAttribute("text");
        }else if (Platform.getInstance().isIOS()){
            return title_element.getAttribute("name");
        }else {
            return title_element.getText();
        }
    }

    public void swipeToFooter() throws Exception {
        if (Platform.getInstance().isAndroid()){
            this.swipeUpToFindElement(FOOTER_ELEMENT, "Cannot find the end of article", 20);
        }else if (Platform.getInstance().isIOS()){
            this.swipeUpTillelementAppear(FOOTER_ELEMENT, "Cannot find the end of article", 40);
        }else {
            this.scrollWebPageTillElementNotVisible(FOOTER_ELEMENT, "Cannot find the end of article", 20);
        }
    }

    public void addArticleToMyList(String nameOfFolder) throws Exception {
        this.waitForElementClick(OPTIONS_BUTTON, "Cannot find button to open article options", 5);
        this.waitForElementClick(OPTIONS_ADD_TO_MY_LIST_BUTTON, "Cannot find element 'Add to reading list'", 5);
        this.waitForElementClick(ADD_TO_MY_LIST_OVERLAY, "Cannot find 'Got it' tip overlay", 5);
        this.waitForElementClear(MY_LIST_NAME_INPUT, "Cannot find input to set name of articles folder", 5);
        this.waitForElementSendKeys(MY_LIST_NAME_INPUT,  nameOfFolder, "Cannot input text into article folder input", 5);
        this.waitForElementClick(MY_LIST_OK, "Cannot press OK button", 5);
    }

    public void addArticleToMySaved() throws Exception {
        if (Platform.getInstance().isMw()) {
            this.removeArticleFromsavedIfItAdded();
        }
        this.waitForElementClick(OPTIONS_ADD_TO_MY_LIST_BUTTON, "Cannot find button 'My saved list'", 5);
    }

    public void removeArticleFromsavedIfItAdded() throws Exception {
        if (this.isElementPresent(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON)){
            this.waitForElementClick(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON, "Cannot click remove button", 5);
        }
        this.waitForElementPresent(OPTIONS_ADD_TO_MY_LIST_BUTTON, "Cannot find button 'Add to my list'", 5);
    }

    public void closeArticle() throws Exception {
        if ((Platform.getInstance().isIOS()) || (Platform.getInstance().isAndroid())){
            this.waitForElementClick(CLOSE_ARTICLE_BUTTON, "Cannot find element 'Navigate up'", 5);
        }else {
            System.out.println("Method closeArticle() does nothing for platform " + Platform.getInstance().getPlatformVariables());
        }
    }
}
