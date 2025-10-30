package com.ajio.tests;

import org.testng.annotations.Test;
import com.ajio.base.BaseTest;
import com.ajio.pages.ProductPage;
import com.ajio.pages.CartPage;
import com.ajio.utilities.ScreenshotUtil;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartQuantityAjio extends BaseTest {

    @Test
    public void increaseQuantityInCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            // 🧱 Wait for loader to disappear (if any)
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.loader")));

            // 💤 Let DOM settle
            Thread.sleep(2000);

            // 🔍 Locate quantity + button
            By plusBtn = By.xpath("//*[contains(@aria-label,'increase') or contains(@class,'plus') or contains(text(),'+')]");

            // ✅ Wait for presence first
            wait.until(ExpectedConditions.presenceOfElementLocated(plusBtn));

            // ✅ Then wait for clickability
            WebElement increaseBtn = wait.until(ExpectedConditions.elementToBeClickable(plusBtn));

            // ✅ Try direct click
            try {
                increaseBtn.click();
                System.out.println("✅ Quantity increased in cart.");
            } catch (ElementClickInterceptedException e) {
                System.out.println("⚠️ Click intercepted. Retrying with JS...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", increaseBtn);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", increaseBtn);
                System.out.println("✅ Quantity increased via JS fallback.");
            }

        } catch (Exception e) {
            System.out.println("❌ Unexpected error during quantity increase: " + e.getMessage());
            throw new RuntimeException("❌ Quantity increase failed.");
        }
    }
}