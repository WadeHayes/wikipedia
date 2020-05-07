package lib.ui;

import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

public abstract class MyListsPageObject extends MainPageObject {
    protected static String FOLDER_BY_NAME_TPL;
    protected static String ARTICLE_BY_TITLE_TPL;
    protected static String REMOVE_FROM_SAVED_BUTTON;

    private static String getFolderXpathByName(String nameOfFolder){
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", nameOfFolder);
    }

    private static String getSavedArticleXpathByTitle(String articleTitle){
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", articleTitle);
    }

    private static String getRemoveButtonByTitle(String article_title) {
        return REMOVE_FROM_SAVED_BUTTON.replace("{TITLE}", article_title);
    }

    public MyListsPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public void openFolderByName(String nameOfFolder) throws Exception {
        String folder_name_xpath = getFolderXpathByName(nameOfFolder);
        this.waitForElementClick(folder_name_xpath, "Cannot find element 'My lists'", 5);
    }

    public void waitForArticleToAppearByTitle(String articleTitle) throws Exception {
        String article_xpath = getFolderXpathByName(articleTitle);
        this.waitForElementPresent(article_xpath, "Cannot saved article by title " + articleTitle, 15);
    }

    public void waitForArticleToDisappearByTitle(String articleTitle) throws Exception {
        String article_xpath = getFolderXpathByName(articleTitle);
        this.waitForElementNotPresent(article_xpath, "Saved article still present with title " + articleTitle, 15);
    }

    public void swipeByArticleToDelete(String articleTitle) throws Exception {
        this.waitForArticleToAppearByTitle(articleTitle);
        String articleXpath = getFolderXpathByName(articleTitle);
        if (!Platform.getInstance().isMw()) {
            this.swipeElementToLeft(articleXpath, "Cannot delete article");
        } else {
            String removeLocator = getRemoveButtonByTitle(articleTitle);
            this.waitForElementClick(removeLocator, "Cannot click bt article that d elete", 15);
        }
        this.swipeElementToLeft(articleXpath, "Cannot swipe to left element 'Java (programming language)'");
        if (Platform.getInstance().isIOS()){
            this.clickElementToTheRightUpperCorner(articleXpath, "Cannot tao on icon delete");
        }
        if (Platform.getInstance().isMw()) {
            driver.navigate().refresh();
        }
        this.waitForArticleToDisappearByTitle(articleTitle);
    }
}
