package com.ajio.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

import com.ajio.utilities.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class BaseTest {

    public static WebDriver driver;
    public static ExtentReports extent;
    public static ExtentTest test;

    @BeforeSuite
    public void setupReport() {
        extent = ExtentManager.getInstance();
        driver = new ChromeDriver(); // Or use WebDriverManager
        driver.manage().window().maximize();
    }

    public void navigateToUrl(String url) {
        driver.get(url);
    }

    @AfterSuite
    public void flushReport() {
        extent.flush();
        driver.quit();
    }
}