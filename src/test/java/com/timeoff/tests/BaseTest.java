package com.timeoff.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class BaseTest {
    protected WebDriver driver;
    protected ExtentReports extent;
    protected ExtentTest test;
    protected Logger log = LogManager.getLogger(BaseTest.class);

    @BeforeClass
    @Parameters("browser")
    public void setupExtent(@Optional("chrome") String browser) {
        log.info("Starting driver: " + browser);

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("test-output/SparkReport.html");
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        if (browser.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDownTest(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.fail(result.getThrowable());
            log.error("Test failed: " + result.getName());
            String path = takeScreenshot(result.getName());
            try {
                test.addScreenCaptureFromPath(path);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test Passed");
            log.info("Test passed: " + result.getName());
            String path = takeScreenshot(result.getName());
            try {
                test.addScreenCaptureFromPath(path);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.skip("Test Skipped");
            log.warn("Test skipped: " + result.getName());
        }
    }

    @AfterClass
    public void tearDown() {
        log.info("Closing driver");
        driver.quit();
        extent.flush();
        log.info("Extent Report flushed");
    }

    public String takeScreenshot(String testName) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File srcFile = ts.getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String relativePath = "screenshots/" + testName + "_" + timestamp + ".png";
        String destPath = "test-output/" + relativePath;

        File destFile = new File(destPath);
        destFile.getParentFile().mkdirs();
        try {
            Files.copy(srcFile.toPath(), destFile.toPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return relativePath;
    }
}
