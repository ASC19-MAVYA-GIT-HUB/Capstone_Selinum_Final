package com.ajio.utilities;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class ElementUtil {

    WebDriver driver;

    public ElementUtil(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement waitForElement(By locator, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (TimeoutException e) {
            System.out.println("❌ Element not found: " + locator);
            return null;
        }
    }

    public WebElement waitForClickable(By locator, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (TimeoutException e) {
            System.out.println("❌ Element not clickable: " + locator);
            return null;
        }
    }

    public boolean safeClick(By locator, int timeoutSeconds) {
        WebElement element = waitForClickable(locator, timeoutSeconds);
        if (element != null) {
            try {
                element.click();
                return true;
            } catch (Exception e) {
                System.out.println("⚠️ Selenium click failed, trying JS click: " + locator);
                try {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                    return true;
                } catch (Exception jsEx) {
                    System.out.println("❌ JS click also failed: " + locator);
                }
            }
        }
        return false;
    }
}