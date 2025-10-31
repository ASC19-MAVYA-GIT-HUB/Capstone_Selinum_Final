package com.ajio.tests;

import org.testng.annotations.Test;
 
import com.ajio.base.BaseTest;
 
import com.ajio.utilities.ScreenshotUtil;
 
import java.time.Duration;
 
import org.openqa.selenium.*;
 
import org.openqa.selenium.support.ui.ExpectedConditions;
 
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartQuantityAjio extends BaseTest {

    @Test
 
    public void increaseQuantityInCart() {
 
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
 
        By plusBtn = By.xpath("//*[@id='dCartWrapper']/div[2]/div[2]/div[1]/div[3]/div[2]/div/div[2]/div[2]/div[2]/span");

        try {
 
            // 🧱 Wait for any loader or overlay to disappear
 
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
 
                    By.cssSelector("div.loader, div.spinner, div[role='dialog']")));

            // 🔍 Wait until the + button is visible and interactable
 
            WebElement increaseBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(plusBtn));
 
            wait.until(ExpectedConditions.elementToBeClickable(increaseBtn));

            // 🧭 Scroll into view to avoid interception
 
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", increaseBtn);

            // ✅ Attempt click with fallback
 
            try {
 
                increaseBtn.click();
 
                System.out.println("✅ Quantity increased in cart.");
 
            } catch (ElementClickInterceptedException e) {
 
                System.out.println("⚠️ Click intercepted. Retrying with JS...");
 
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", increaseBtn);
 
                System.out.println("✅ Quantity increased via JS fallback.");
 
            }

        } catch (TimeoutException te) {
 
            ScreenshotUtil.captureScreenshot(driver, "IncreaseQuantity_Timeout");
 
            throw new AssertionError("⏳ Timeout waiting for quantity increase button.", te);

        } catch (NoSuchElementException ne) {
 
            ScreenshotUtil.captureScreenshot(driver, "IncreaseQuantity_NoElement");
 
            throw new AssertionError("🚫 Could not locate the increase quantity button.", ne);

        } catch (Exception e) {
 
            ScreenshotUtil.captureScreenshot(driver, "IncreaseQuantity_Unexpected");
 
            throw new AssertionError("❌ Unexpected error while increasing quantity.", e);
 
        }
 
    }
 
}
 
 
