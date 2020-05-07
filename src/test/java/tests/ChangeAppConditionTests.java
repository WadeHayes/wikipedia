package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class ChangeAppConditionTests extends CoreTestCase {
    @Test
    public void testChangeScreenOrientionOnSearchResault() throws Exception {
        if (Platform.getInstance().isMw()){
            return;
        }

        SearchPageObject searchPageObject = SearchPageObjectFactory.get(driver);
        ArticlePageObject articlePageObject = ArticlePageObjectFactory.get(driver);

        searchPageObject.initSearchInput();
        searchPageObject.typeSearchLine("Java");
        searchPageObject.clickByArticleWithSubstring("Object-oriented programming language");

        String titleBeforeRotation = articlePageObject.getArticleTitle();

        this.rotateScreenLandscape();
        String titleAfterRotation = articlePageObject.getArticleTitle();

        assertEquals("Article title have been changed after screen rotation", titleBeforeRotation, titleAfterRotation);

        this.rotateScreenPortrait();
        String titleAfterSecondRotation =articlePageObject.getArticleTitle();

        assertEquals("Article title have been changed after screen rotation", titleBeforeRotation, titleAfterSecondRotation);
    }
}
