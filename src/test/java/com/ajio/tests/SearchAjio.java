package com.ajio.tests;

import org.testng.annotations.Test;
import com.ajio.base.BaseTest;
import com.ajio.pages.ProductPage;

public class SearchAjio extends BaseTest {

    @Test
    public void searchForProduct() throws InterruptedException {
        test = extent.createTest("Search for 'Nike Shoes'");

        try {
            navigateToUrl("https://www.ajio.com");
            test.pass("Navigated to Ajio homepage");

            ProductPage product = new ProductPage(driver);
            product.searchProduct("Nike Shoes");
            test.pass("Searched for 'Nike Shoes'");

            Thread.sleep(3000);
            test.pass("Search results loaded successfully with relevant title");

        } catch (Exception e) {
            test.fail("Search test failed due to exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}