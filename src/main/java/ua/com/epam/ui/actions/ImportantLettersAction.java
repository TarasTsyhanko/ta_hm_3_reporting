package ua.com.epam.ui.actions;

import com.google.inject.Inject;
import io.qameta.allure.Step;
import ua.com.epam.decorator.elements.PageElementCollection;
import ua.com.epam.decorator.elements.elementimpl.CheckBoxElement;
import ua.com.epam.ui.pages.GmailBasePage;
import ua.com.epam.ui.pages.GmailImportantLettersPage;
import ua.com.epam.utils.Wait;

import static ua.com.epam.constant.AttributeConstants.ATTRIBUTE_CLASS;
import static ua.com.epam.constant.AttributeConstants.CLASS_VALUE;
import static ua.com.epam.constant.URLConstants.IMPORTANT_LIST_URL_CONTAINS;

public class ImportantLettersAction {
    @Inject
    private GmailBasePage basePage;
    @Inject
    private GmailImportantLettersPage importantLettersPage;


    @Step("wait letter to be loaded")
    public void waitLetterToBeLoaded(int n) {
        while (basePage.getListLettersCheckBox().size() < n) {
            Wait.untilPageToBeToBeRefreshed();
        }
    }

    @Step("move {0} letters to important list")
    public void moveNLettersToImportantList(int n) {
        Wait.untilPageToBeLoaded();
        PageElementCollection<CheckBoxElement> checkBoxElements = basePage.getListLettersCheckBox();
        for (int i = 0; i < n; i++) {
            checkBoxElements.get(i).waitUntilVisible().actionClick();
            checkBoxElements.get(i).waitUntilAttributeToBe(ATTRIBUTE_CLASS, CLASS_VALUE);
        }
        basePage.getLetterActionButton().actionClick();
        basePage.getMarkAsImportant().actionClick();
    }

    @Step("open important letters list")
    public void openImportantLetterList() {
        basePage.getExtendMenuButton().scrollToElement().actionClick();
        basePage.getImportantLettersButton().waitUntilClickable().click();
    }

    @Step("delete {0} important letter list")
    public void clearImportantLetterList(int n) {
        Wait.forUrlContains(IMPORTANT_LIST_URL_CONTAINS);
        PageElementCollection<CheckBoxElement> checkBoxElements = importantLettersPage.getImportantLettersCheckBox();
        for (int i = 0; i < n; i++) {
            checkBoxElements.get(i).waitUntilVisible().actionClick();
            checkBoxElements.get(i).waitUntilAttributeToBe(ATTRIBUTE_CLASS, CLASS_VALUE);
        }
        importantLettersPage.getDeleteAction().click();
    }

    @Step("is message displayed")
    public boolean isDisplayedMessage() {
        return basePage.getInformMessage().waitUntilVisible().isDisplayed();
    }

    @Step("get inform message text")
    public String getMessageText() {
        return basePage.getInformMessage().getText();
    }
}
