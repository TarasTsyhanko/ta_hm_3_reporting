package ua.com.epam.gmailtest;

import com.google.inject.Inject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ua.com.epam.asserters.ImportantLetterAsserter;
import ua.com.epam.ui.actions.ImportantLettersAction;
import ua.com.epam.ui.actions.LoginAction;
import ua.com.epam.config.ConfigProperties;
import ua.com.epam.constant.URLConstants;
import ua.com.epam.models.User;
import ua.com.epam.utils.FileManager;

import static ua.com.epam.constant.MessageConstant.*;

public class ImportantLettersTest extends BaseTest {
    @Inject
    private ImportantLettersAction importantListAction;
    @Inject
    private ImportantLetterAsserter letterAsserter;
    @Inject
    private LoginAction loginAction;

    @Test(description = "mark messages like important and delete it ", dataProvider = "users")
    public void markMessagesLikeImportant(User user) {
        loginAction.logInToGmailAccount(user.getLogin(), user.getPassword());
        Assert.assertTrue(loginAction.isBasePage(URLConstants.BASE_PAGE_URL));

        importantListAction.waitLetterToBeLoaded(ConfigProperties.getSizeOfMarkMessages());
        importantListAction.moveNLettersToImportantList(ConfigProperties.getSizeOfMarkMessages());
        Assert.assertTrue(importantListAction.isDisplayedMessage());
        letterAsserter.assertMessage(importantListAction.getMessageText(), SUCCESSFUL_MOVING_MESSAGE);

        importantListAction.openImportantLetterList();
        importantListAction.clearImportantLetterList(ConfigProperties.getSizeOfMarkMessages());
        Assert.assertTrue(importantListAction.isDisplayedMessage());
        letterAsserter.assertMessage(importantListAction.getMessageText(), SUCCESSFUL_DELETION_MESSAGE);
    }

    @DataProvider()
    public Object[] users() {
        return FileManager.getUsers().toArray();
    }
}
