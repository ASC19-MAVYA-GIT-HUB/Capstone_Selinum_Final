@Test

public void increaseQuantityInCart() {
 
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
 
    // Use robust XPath for the "+" button

    By plusBtn = By.xpath("//button[@aria-label='Increase quantity']");
 
    try {

        // Wait for loaders or overlays to disappear

        wait.until(ExpectedConditions.invisibilityOfElementLocated(

                By.cssSelector("div.loader, div.spinner, div[role='dialog']")));
 
        // Wait until the "+" button is visible and clickable

        WebElement increaseBtn = wait.until(ExpectedConditions.elementToBeClickable(plusBtn));
 
        // Scroll into view to avoid interception

        ((JavascriptExecutor) driver).executeScript(

                "arguments[0].scrollIntoView({block: 'center'});", increaseBtn);
 
        // Click the button (with JS fallback)

        try {

            increaseBtn.click();

            System.out.println("✅ Quantity increased in cart.");

        } catch (ElementClickInterceptedException e) {

            System.out.println("⚠️ Click intercepted. Retrying with JS...");

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", increaseBtn);

            System.out.println("✅ Quantity increased via JS fallback.");

        }
 
    } catch (TimeoutException te) {

        ScreenshotUtil.captureScreenshot(driver, "IncreaseQuantity_Timeout");

        throw new AssertionError("⏳ Timeout waiting for quantity increase button.", te);

    } catch (NoSuchElementException ne) {

        ScreenshotUtil.captureScreenshot(driver, "IncreaseQuantity_NoElement");

        throw new AssertionError("🚫 Could not locate the increase quantity button.", ne);

    } catch (Exception e) {

        ScreenshotUtil.captureScreenshot(driver, "IncreaseQuantity_Unexpected");

        throw new AssertionError("❌ Unexpected error while increasing quantity.", e);

    }
 
}

 
