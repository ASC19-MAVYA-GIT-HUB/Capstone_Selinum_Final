package com.ajio.utilities;

import org.openqa.selenium.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;

public class ScreenshotUtil {

    public static String captureScreenshot(WebDriver driver, String name) {
        try {
            File folder = new File("src/test/resources/Screenshots");
            if (!folder.exists()) {
                folder.mkdirs();
            }

            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String path = folder.getPath() + "/" + name + "_" + timestamp + ".png";
            FileUtils.copyFile(src, new File(path));
            return path;
        } catch (Exception e) {
            System.out.println("Screenshot capture failed: " + e.getMessage());
            return null;
        }
    }
}