package com.ajio.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.ajio.base.BaseTest;
import com.ajio.pages.ProductPage;
import com.ajio.utilities.ExcelUtilities;

public class ExcelDrivenSearchAjio extends BaseTest {

    @DataProvider(name = "productData")
    public Object[][] getProductData() throws Exception {
        String filePath = "src/test/resources/TestData/data1.xlsx";
        String sheetName = "Products";
        return ExcelUtilities.getData(filePath, sheetName);
    }

    @Test(dataProvider = "productData")
    public void searchProduct(String productName) {
        test = extent.createTest("Search Product: " + productName);

        try {
            navigateToUrl("https://www.ajio.com");
            test.pass("Navigated to Ajio homepage");

            ProductPage productPage = new ProductPage(driver);
            productPage.searchProduct(productName);
            test.pass("Searched for product: " + productName);

            Thread.sleep(2000); // Let results load
            if (driver.getPageSource().toLowerCase().contains(productName.split(" ")[0].toLowerCase())) {
                test.pass("Results found for: " + productName);
            } else {
                test.fail("No results found for: " + productName);
            }

        } catch (Exception e) {
            test.fail("Search failed for: " + productName + " — " + e.getMessage());
            e.printStackTrace();
        }
    }
}