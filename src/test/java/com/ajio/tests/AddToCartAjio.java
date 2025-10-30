package com.ajio.tests;

import org.testng.annotations.Test;
import com.ajio.base.BaseTest;
import com.ajio.pages.ProductPage;

public class AddToCartAjio extends BaseTest {

    @Test
    public void addProductToCart() {
        test = extent.createTest("Add Product to Cart");

        try {
            navigateToUrl("https://www.ajio.com");
            test.pass("Navigated to Ajio homepage");

            ProductPage product = new ProductPage(driver);
            product.searchProduct("Nike Shoes");
            product.selectFirstProduct();
            product.addToCart();
            test.pass("Product added to cart");

        } catch (Exception e) {
            test.fail("Add to Cart test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}