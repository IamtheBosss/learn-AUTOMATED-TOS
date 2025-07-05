package com.timeoff.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WelcomePage {
    WebDriver driver;

    public WelcomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get("https://localhost:7279/");
    }

    public void clickLoginLink() {
        driver.findElement(By.linkText("Login")).click();
    }
}
