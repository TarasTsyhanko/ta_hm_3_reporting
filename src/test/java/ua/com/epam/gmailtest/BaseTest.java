package ua.com.epam.gmailtest;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;
import ua.com.epam.api.GmailClient;
import ua.com.epam.config.ConfigProperties;
import ua.com.epam.factory.DriverContainer;
import ua.com.epam.guice.ActionModule;
import ua.com.epam.guice.AsserterModule;
import ua.com.epam.guice.GmailApiModule;
import ua.com.epam.guice.PageModule;
import ua.com.epam.listener.GmailTestListeners;
import ua.com.epam.utils.FileManager;
import ua.com.epam.utils.allure.AllureAttachment;

import java.io.IOException;

@Guice(modules = {PageModule.class, ActionModule.class, AsserterModule.class, GmailApiModule.class})
@Listeners({GmailTestListeners.class})
public class BaseTest {
    private static final Logger log = LogManager.getLogger(BaseTest.class);
    @Inject
    protected GmailClient gmailClient;

    @BeforeMethod
    public void setUp() {
        DriverContainer.getDriver().get(ConfigProperties.getBaseUrl());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() throws IOException {
        DriverContainer.quitDriver();
        AllureAttachment.addTestLog(ConfigProperties.getLogsFilePath());
    }

    @BeforeSuite
    public void setTestLetters() {
        FileManager.getUsers()
                .forEach(user -> gmailClient.setTestLetters(FileManager.getLetters(), user));
    }

    @AfterSuite
    public void clearGmailApi() {
        FileManager.getUsers()
                .forEach(user -> {
                    try {
                        gmailClient.clearGmailApi(user);
                    } catch (IOException e) {
                       log.error(e.getMessage());
                    }
                });
    }
}
