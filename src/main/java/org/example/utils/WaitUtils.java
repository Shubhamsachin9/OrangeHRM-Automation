package org.example.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {

    private static final Duration TIMEOUT =
            Duration.ofSeconds(15);

    private WaitUtils() {}

    public static WebElement waitForVisibility(By locator) {

        return new WebDriverWait(
                DriverFactory.getDriver(),
                TIMEOUT)
                .until(ExpectedConditions
                        .visibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickable(By locator) {

        return new WebDriverWait(
                DriverFactory.getDriver(),
                TIMEOUT)
                .until(ExpectedConditions
                        .elementToBeClickable(locator));
    }

    public static WebElement waitForPresence(By locator) {

        return new WebDriverWait(
                DriverFactory.getDriver(),
                TIMEOUT)
                .until(ExpectedConditions
                        .presenceOfElementLocated(locator));
    }

    public static boolean waitForUrlContains(String value) {

        return new WebDriverWait(
                DriverFactory.getDriver(),
                TIMEOUT)
                .until(ExpectedConditions
                        .urlContains(value));
    }

    public static boolean waitForTextPresent(By locator,
                                             String text) {

        return new WebDriverWait(
                DriverFactory.getDriver(),
                TIMEOUT)
                .until(ExpectedConditions
                        .textToBePresentInElementLocated(
                                locator,
                                text));
    }


    public static void waitForInvisibility(By locator) {

        WebDriverWait wait =
                new WebDriverWait(
                        DriverFactory.getDriver(),
                        Duration.ofSeconds(30));

        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }


}