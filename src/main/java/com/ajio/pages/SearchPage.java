package com.ajio.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchPage {

    WebDriver driver;

    // Locators
    By searchBox = By.name("searchVal");
    By searchButton = By.xpath("//button[@type='submit']");

    public SearchPage(WebDriver driver) {
        this.driver = driver;
    }

    public void searchProduct(String productName) {
        driver.findElement(searchBox).sendKeys(productName);
        driver.findElement(searchButton).click();
    }
}