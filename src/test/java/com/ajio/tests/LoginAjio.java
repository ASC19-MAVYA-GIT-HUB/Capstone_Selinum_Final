package com.ajio.tests;

import org.testng.annotations.Test;
import com.ajio.base.BaseTest;
import com.ajio.pages.LoginPage;

public class LoginAjio extends BaseTest {

    @Test
    public void loginWithValidPhoneNumber() {
        test = extent.createTest("Login with Valid Phone Number");

        try {
            navigateToUrl("https://www.ajio.com");
            test.pass("Navigated to Ajio homepage");

            LoginPage login = new LoginPage(driver);
            login.clickSignIn();
            test.pass("Clicked Sign In");

            login.enterPhoneNumber("9949325222"); // Replace with a testable number
            test.pass("Entered valid phone number");

            try {
                login.clickContinue(); // Handles modal internally
                login.closeLoginModalIfPresent(); // Optional fallback
                test.pass("Clicked Continue and handled modal");
            } catch (Exception e) {
                test.fail("Failed to click Continue: " + e.getMessage());
            }

        } catch (Exception e) {
            test.fail("Login test failed due to exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}