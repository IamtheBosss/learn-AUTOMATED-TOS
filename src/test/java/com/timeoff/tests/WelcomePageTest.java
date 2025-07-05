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
        WelcomePage welcomePage = new WelcomePage(driver);
        welcomePage.open();
        test.info("Opened Welcome Page");

        welcomePage.clickLoginLink();
        test.info("Clicked Login Link");
    }
}
