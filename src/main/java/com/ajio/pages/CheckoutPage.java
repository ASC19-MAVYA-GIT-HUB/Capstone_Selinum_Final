package com.ajio.pages;

import java.time.Duration;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class CheckoutPage {

    WebDriver driver;

    By proceedButton = By.xpath("//button[contains(@class,'shipping-button') and contains(text(),'Proceed to shipping')]");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public void proceedToCheckout() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            // 🧱 Wait for loader to disappear
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.loader")));

            // 📌 Wait for checkout button
            WebElement proceed = wait.until(ExpectedConditions.elementToBeClickable(proceedButton));

            // 🧭 Scroll and click via JS
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", proceed);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", proceed);

            System.out.println("✅ Proceed to shipping clicked.");
        } catch (TimeoutException e) {
            System.out.println("❌ Proceed to shipping button not found.");
            throw new RuntimeException("Checkout button not found: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Checkout flow failed: " + e.getMessage());
            throw new RuntimeException("❌ Checkout flow interrupted.");
        }
    }

    public boolean isLoginModalVisible() {
        return driver.findElements(By.xpath("//div[contains(@class,'login-modal')]")).size() > 0;
    }

    public boolean isLoginToastVisible() {
        return driver.findElements(By.xpath("//div[contains(text(),'Please login')]")).size() > 0;
    }
    public void validateCheckoutFlow() {
        try {
            Thread.sleep(2000); // Let page settle

            String currentUrl = driver.getCurrentUrl();
            boolean modalVisible = isLoginModalVisible();
            boolean toastVisible = isLoginToastVisible();

            if (currentUrl.contains("cart") || modalVisible || toastVisible) {
                System.out.println("⚠️ Blocked by login modal, toast, or still on cart. Checkout requires authentication");
            } else {
                System.out.println("✅ Checkout flow passed. Navigated to: " + currentUrl);
            }
        } catch (Exception e) {
            System.out.println("❌ Checkout validation failed: " + e.getMessage());
        }
    }
}