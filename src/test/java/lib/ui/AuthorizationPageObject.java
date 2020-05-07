package lib.ui;

import org.openqa.selenium.remote.RemoteWebDriver;

public class AuthorizationPageObject extends MainPageObject {
    private static final String LOGIN_BUTTON = "xpath://body/div/a[text()='Log in']";
    private static final String LOGIN_INPUT = "css:input[name='wpName']";
    private static final String PASSWORD_INPUT = "css:input[name='wpPassword']";
    private static final String SUBMIT_BUTTON = "css:button#wploginattempt";

    public AuthorizationPageObject(RemoteWebDriver driver) {
        super(driver);
    }

    public  void clickAuthButton() throws Exception {
        this.waitForElementPresent(LOGIN_BUTTON, "Cannot find auth button", 5);
        this.waitForElementClick(LOGIN_BUTTON, "Cannot click auth button", 5);
    }

    public void enterLoginData(String login, String password) throws Exception {
        this.waitForElementSendKeys(LOGIN_INPUT, login, "Cannot input login", 5);
        this.waitForElementSendKeys(PASSWORD_INPUT, password, "Cannot input password", 5);
    }

    public void submitForm() throws Exception {
        this.waitForElementClick(SUBMIT_BUTTON, "Cannot click submit button", 5);
    }
}
