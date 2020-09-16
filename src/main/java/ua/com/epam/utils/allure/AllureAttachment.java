package ua.com.epam.utils.allure;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import ua.com.epam.factory.DriverContainer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AllureAttachment {

    @Attachment(value = "Page screenshot", type = "image/png")
    public static byte[] addScreenToAllure() {
        return ((TakesScreenshot) DriverContainer.getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "logs", type = "text/plain", fileExtension = ".log")
    public static byte[] addFileToAllure(String path) throws IOException {
        File file = new File(path);
        return Files.readAllBytes(Paths.get(file.getPath()));
    }

    @Step("Test logs")
    public static void addTestLog(String path) throws IOException {
        addFileToAllure(path);
    }
}
