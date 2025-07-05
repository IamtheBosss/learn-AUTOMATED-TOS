package com.timeoff.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class WelcomePage {
    WebDriver driver;

    @FindBy(linkText = "Login")
    WebElement loginLink;

    public WelcomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("https://localhost:7279/");
    }

    public void clickLoginLink() {
        loginLink.click();
    }
}
