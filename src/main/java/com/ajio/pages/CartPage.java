package com.ajio.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.io.File;
import java.io.IOException;
import com.ajio.utilities.ElementUtil;
import com.ajio.utilities.FileUtilities;

public class CartPage {

    WebDriver driver;
    ElementUtil utilities;

    By increaseQtyButton = By.xpath("//*[contains(@aria-label,'increase') or contains(@class,'plus') or contains(text(),'+')]");
    By removeBtnLocator = By.xpath("//div[@class='delete-btn' and @aria-label='Delete']");
    By cartItemLocator = By.xpath("//div[contains(@class,'cart-item')]");
    By emptyCartLocator = By.xpath("//*[contains(text(),'Your bag is empty') or contains(@class,'emptyCart')]");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.utilities = new ElementUtil(driver); // ✅ Initialize ElementUtil
    }

    public void waitForCartToLoad() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='dCartWrapper']")));
    }

    public void increaseQuantityInCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            // 🧱 Wait for loader to disappear (if any)
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.loader")));

            // 🔍 Locate quantity + button
            By plusBtn = By.xpath("//*[contains(@aria-label,'increase') or contains(@class,'plus') or contains(text(),'+')]");
            WebElement increaseBtn = wait.until(ExpectedConditions.elementToBeClickable(plusBtn));

            // ✅ Click directly
            increaseBtn.click();

            System.out.println("✅ Quantity increased in cart.");
        } catch (NoSuchElementException e) {
            System.out.println("❌ Quantity button not found: " + e.getMessage());
            throw new RuntimeException("❌ Quantity increase failed — element not found.");
        } catch (ElementClickInterceptedException e) {
            System.out.println("⚠️ Click intercepted. Retrying with JS...");

            try {
                WebElement increaseBtn = driver.findElement(By.xpath("//*[contains(@aria-label,'increase') or contains(@class,'plus') or contains(text(),'+')]"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", increaseBtn);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", increaseBtn);
                System.out.println("✅ Quantity increased via JS fallback.");
            } catch (Exception jsEx) {
                System.out.println("❌ JS fallback failed: " + jsEx.getMessage());
                throw new RuntimeException("❌ Quantity increase failed — JS fallback also failed.");
            }
        } catch (Exception e) {
            System.out.println("❌ Unexpected error during quantity increase: " + e.getMessage());
            throw new RuntimeException("❌ Quantity increase failed.");
        }
    }

    public void removeProductFromCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            // 🧱 Wait for loader to disappear (if any)
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.loader")));

            // 🔍 Locate delete/remove button
            By removeBtn = By.xpath("//div[@class='delete-btn' and @aria-label='Delete']");
            WebElement deleteBtn = wait.until(ExpectedConditions.elementToBeClickable(removeBtn));

            // ✅ Click directly
            deleteBtn.click();

            System.out.println("✅ Product removed from cart.");
        } catch (NoSuchElementException e) {
            System.out.println("❌ Remove button not found: " + e.getMessage());
            throw new RuntimeException("❌ Remove failed — element not found.");
        } catch (ElementClickInterceptedException e) {
            System.out.println("⚠️ Click intercepted. Retrying with JS...");

            try {
                WebElement deleteBtn = driver.findElement(By.xpath("//div[@class='delete-btn' and @aria-label='Delete']"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", deleteBtn);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteBtn);
                System.out.println("✅ Product removed via JS fallback.");
            } catch (Exception jsEx) {
                System.out.println("❌ JS fallback failed: " + jsEx.getMessage());
                throw new RuntimeException("❌ Remove failed — JS fallback also failed.");
            }
        } catch (Exception e) {
            System.out.println("❌ Unexpected error during removal: " + e.getMessage());
            throw new RuntimeException("❌ Remove failed.");
        }
    }

    public boolean isCartEmpty() {
        try {
            Thread.sleep(1000); // Wait for cart update
            return driver.findElements(emptyCartLocator).size() > 0
                || driver.findElements(cartItemLocator).isEmpty();
        } catch (Exception e) {
            System.out.println("Error checking cart state: " + e.getMessage());
            return false;
        }
    }
}