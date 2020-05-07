package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactoty;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Assert;
import org.junit.Test;

public class MyListsTests extends CoreTestCase {
    private static final String nameOfFolder = "Learning programming";
    private static final String login = "";
    private static final String password = "";

    @Test
    public void testSaveFirstArticleToMyList() throws Exception {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        NavigationUI navigationUI = NavigationUIFactoty.get(driver);
        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("bject-oriented programming language");
        articlePageObject.waitForTitleElement();
        String articleTitle = articlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()){
            articlePageObject.addArticleToMyList(nameOfFolder);
        }else {
            articlePageObject.addArticleToMySaved();
        }

        if (Platform.getInstance().isMw()) {
            AuthorizationPageObject Auth = new AuthorizationPageObject(driver);
            Auth.clickAuthButton();
            Auth.enterLoginData(login, password);
            Auth.submitForm();

            articlePageObject.waitForTitleElement();

            assertEquals("We aren't on the same page after login ", articleTitle , articlePageObject.getArticleTitle());

            articlePageObject.addArticleToMySaved();
        }

        articlePageObject.closeArticle();

        navigationUI.openNavigation();
        navigationUI.clickMyList();

        if (Platform.getInstance().isAndroid()){
            myListsPageObject.openFolderByName(nameOfFolder);
        }

        myListsPageObject.swipeByArticleToDelete(articleTitle);
    }

    @Test
    public void testSaveTwoArticles() throws Exception {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);
        NavigationUI navigationUI = NavigationUIFactoty.get(driver);
        MyListsPageObject myListsPageObject = MyListsPageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Hibernate");
        searchPageObject.clickByArticleWithSubstring("ibernate (framework)");

        if (Platform.getInstance().isAndroid()){
            articlePageObject.addArticleToMyList(nameOfFolder);
        }else {
            articlePageObject.addArticleToMySaved();
            articlePageObject.addArticleToMySaved();
        }
        articlePageObject.closeArticle();

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Spring Boot");
        searchPageObject.clickByArticleWithSubstring("pring Framework");


        if (Platform.getInstance().isAndroid()){
            articlePageObject.addArticleToMyList(nameOfFolder);
        }else {
            articlePageObject.addArticleToMySaved();
        }
        articlePageObject.closeArticle();

        navigationUI.clickMyList();
        if (Platform.getInstance().isAndroid()){
            myListsPageObject.openFolderByName(nameOfFolder);
        }
        myListsPageObject.swipeByArticleToDelete("Hibernate");
        Assert.assertEquals("Titles nol equals", "Hibernate (framework)", articlePageObject.getArticleTitle());
    }

    @Test
    public void testAssertTitleExist() throws Exception {
        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Hibernate");
        searchPageObject.clickByArticleWithSubstring("ibernate (framework)");
        Assert.assertNotNull("Title of article is null", articlePageObject.getArticleTitle());
        Assert.assertNotEquals("Title of article is empty", "", articlePageObject.getArticleTitle());
    }
}
