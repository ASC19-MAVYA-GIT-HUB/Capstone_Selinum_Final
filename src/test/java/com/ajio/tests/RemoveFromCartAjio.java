package com.ajio.tests;

import org.testng.annotations.Test;
import com.ajio.base.BaseTest;
import com.ajio.pages.ProductPage;
import com.ajio.pages.CartPage;
import com.ajio.utilities.ScreenshotUtil;
import org.openqa.selenium.NoSuchWindowException;

public class RemoveFromCartAjio extends BaseTest {

    @Test
    public void removeProductFromCart() {
        test = extent.createTest("Remove Product from Cart");

        try {
            navigateToUrl("https://www.ajio.com");
            test.pass("Navigated to Ajio homepage");

            // ✅ Check if browser window is still open
            try {
                driver.getWindowHandle();
            } catch (NoSuchWindowException e) {
                test.fail("❌ Browser window was closed unexpectedly.");
                return;
            }

            ProductPage product = new ProductPage(driver);
            product.searchProduct("Nike Shoes");
            product.selectFirstProduct();
            product.addToCart();
            product.goToCart();
            test.pass("Product added to cart and navigated to cart page");

            CartPage cart = new CartPage(driver);
            cart.waitForCartToLoad();
            cart.removeProductFromCart();
            test.pass("Product removed from cart");

        } catch (Exception e) {
            try {
                String screenshotPath = ScreenshotUtil.captureScreenshot(driver, "RemoveCartError");
                test.fail("Remove from cart test failed: " + e.getMessage());
                test.addScreenCaptureFromPath(screenshotPath);
            } catch (Exception io) {
                test.fail("Screenshot capture failed: " + io.getMessage());
            }
            e.printStackTrace();
        }
    }
}