package com.ajio.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import com.ajio.utilities.ElementUtil;

public class ProductPage {

    WebDriver driver;
    ElementUtil utilities;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.utilities = new ElementUtil(driver); // ✅ Initialize ElementUtil
    }

    public void searchProduct(String productName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='searchVal']")));
        input.sendKeys(productName);

        WebElement searchBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
        searchBtn.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class,'rilrtl-products-list__item')]")));
    }

    public void selectFirstProduct() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        String originalWindow = driver.getWindowHandle();

        WebElement product = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("(//div[contains(@class,'rilrtl-products-list__item')])[1]")));
        product.click();

        wait.until(driver -> driver.getWindowHandles().size() > 1);
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
    }

    public void selectSizeIfRequired() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            List<WebElement> size6 = driver.findElements(By.xpath("//span[@aria-label='Select Size' and text()='6']"));
            if (!size6.isEmpty()) {
                WebElement sizeOption = wait.until(ExpectedConditions.elementToBeClickable(size6.get(0)));
                sizeOption.click();
                System.out.println("Size 6 selected.");
                return;
            }

            List<WebElement> availableSizes = driver.findElements(By.xpath("//span[@aria-label='Select Size']"));
            if (!availableSizes.isEmpty()) {
                WebElement sizeOption = wait.until(ExpectedConditions.elementToBeClickable(availableSizes.get(0)));
                sizeOption.click();
                System.out.println("First available size selected.");
            } else {
                System.out.println("No size selection required.");
            }
        } catch (Exception e) {
            System.out.println("Size selection not required or not found: " + e.getMessage());
        }
    }

    public void addToCart() {
        try {
            selectSizeIfRequired();

            By addToCartButton = By.xpath("//span[@aria-label='ADD TO BAG' and text()='ADD TO BAG']");
            boolean clicked = utilities.safeClick(addToCartButton, 15);

            if (clicked) {
                System.out.println("✅ Add to Cart clicked.");
            } else {
                throw new RuntimeException("❌ Add to Cart button not clickable.");
            }
        } catch (Exception e) {
            System.out.println("Add to Cart failed: " + e.getMessage());
        }
    }

    public void goToCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.loader")));

            WebElement cartIcon = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//a[@href='/cart' or contains(@aria-label,'Cart')]")));

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cartIcon);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cartIcon);
            System.out.println("Navigated to cart.");
        } catch (Exception e) {
            System.out.println("Cart icon not clickable: " + e.getMessage());
        }
    }
}
