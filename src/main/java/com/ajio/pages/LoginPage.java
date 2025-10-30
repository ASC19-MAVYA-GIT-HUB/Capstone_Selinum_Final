package com.ajio.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {

    WebDriver driver;

    // Locators
    private final By signInButton = By.xpath("//span[contains(text(),'Sign In')]");
    private final By mobileInput = By.xpath("//input[@name='username']");
    private final By continueButton = By.xpath("//button[@type='submit']");
    private final By loginModal = By.id("login-modal");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickSignIn() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement signIn = wait.until(ExpectedConditions.elementToBeClickable(signInButton));
        signIn.click();
    }

    public void enterPhoneNumber(String phoneNumber) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(mobileInput));
        input.sendKeys(phoneNumber);
    }

    public void clickContinue() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(continueButton));
            continueBtn.click();
            System.out.println("Clicked Continue via Selenium.");
        } catch (ElementClickInterceptedException | TimeoutException e) {
            System.out.println("Selenium click failed: " + e.getMessage());

            closeLoginModalIfPresent();
            try {
                Thread.sleep(1000); // Let DOM settle
            } catch (InterruptedException ignored) {}

            try {
                String jsClick = "document.querySelector(\"button[type='submit']\").click();";
                ((JavascriptExecutor) driver).executeScript(jsClick);
                System.out.println("Clicked Continue via raw JS.");
            } catch (Exception jsFail) {
                System.out.println("Raw JS click failed: " + jsFail.getMessage());
                throw new RuntimeException("Continue button could not be clicked even via raw JS.");
            }
        }

        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(loginModal));
        } catch (Exception e) {
            System.out.println("Modal may already be gone: " + e.getMessage());
        }
    }

    public void closeLoginModalIfPresent() {
        try {
            WebElement modal = driver.findElement(loginModal);
            if (modal.isDisplayed()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='none';", modal);
                System.out.println("Login modal forcibly hidden via JS.");
            }
        } catch (Exception e) {
            System.out.println("Login modal not found or already dismissed.");
        }
    }
}
