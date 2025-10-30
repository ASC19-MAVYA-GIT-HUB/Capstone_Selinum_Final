package com.ajio.tests;

import org.testng.annotations.Test;
import com.ajio.base.BaseTest;

public class SmokeTest extends BaseTest {

    @Test
    public void verifyReportCreation() {
        test = extent.createTest("Smoke Test");
        test.pass("Report creation verified.");
    }
}