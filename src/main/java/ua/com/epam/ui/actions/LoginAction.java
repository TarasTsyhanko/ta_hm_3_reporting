package ua.com.epam.ui.actions;

import com.google.inject.Inject;
import io.qameta.allure.Step;
import ua.com.epam.utils.Wait;
import ua.com.epam.ui.pages.GmailBasePage;
import ua.com.epam.ui.pages.GmailLoginPage;

public class LoginAction {

    @Inject
    private GmailLoginPage loginPage;

    @Inject
    private GmailBasePage basePage;

    @Step("log in to gmail account [{0}, {1}]")
    public void logInToGmailAccount(String login, String password) {
        loginPage.inputLoginAndClickNext(login);
        loginPage.inputPasswordAndClickNext(password);
    }

    @Step("get user full name")
    public String getUserFullName() {
        basePage.getUserIcon().waitUntilClickable().click();
        return basePage.getUserName().getText();
    }

    @Step("is url {0}")
    public boolean isBasePage(String basePage) {
        return Wait.forUrlToRe(basePage);
    }
}
