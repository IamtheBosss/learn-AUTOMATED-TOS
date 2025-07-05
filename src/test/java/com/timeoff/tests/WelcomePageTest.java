package com.timeoff.tests;

import com.timeoff.pages.WelcomePage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class WelcomePageTest extends BaseTest {

    @BeforeMethod
    public void setupTest() {
        test = extent.createTest("Welcome Page - Click Login Link");
    }

    @Test
    public void testClickLoginLink() {
        log.info("Start testClickLoginLink");
        WelcomePage welcomePage = new WelcomePage(driver);
        welcomePage.open();
        test.info("Opened welcome page");

        welcomePage.clickLoginLink();
        test.info("Clicked login link");
    }
}
