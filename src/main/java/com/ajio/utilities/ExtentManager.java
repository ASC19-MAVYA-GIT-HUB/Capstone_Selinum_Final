package com.ajio.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

    private static ExtentReports extent;
    private static final String REPORT_NAME = "Ajio Automation Report";
    private static final String REPORT_PATH = System.getProperty("user.dir") + "/src/test/resources/Reports/AjioReport.html";

    public static ExtentReports getInstance() {
        if (extent == null) {
            // ✅ Print the path to verify where the report will be created
            System.out.println("Extent report will be created at: " + REPORT_PATH);

            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(REPORT_PATH);
            sparkReporter.config().setReportName(REPORT_NAME);
            sparkReporter.config().setDocumentTitle("Ajio Test Execution");
            sparkReporter.config().setTheme(Theme.DARK);

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Framework", "Ajio Automation");
            extent.setSystemInfo("Author", "Mavya");
            extent.setSystemInfo("Environment", "QA");
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        }
        return extent;
    }
}