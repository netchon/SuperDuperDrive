package com.udacity.jwdnd.course1.cloudstorage;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    private final WebDriver driver;

    private final WebDriverWait webDriverWait;

    private final JavascriptExecutor javascriptExecutor;

    public HomePage(WebDriver webDriver){
        this.driver = webDriver;
        PageFactory.initElements(webDriver, this);
        this.javascriptExecutor = (JavascriptExecutor) webDriver;
        this.webDriverWait = new WebDriverWait(webDriver,10);
    }

    public void logout(){
        javascriptExecutor.executeScript("arguments[0].click()", logoutButton);
    }

    public void getHomePageTitle() {
        //webDriverWait.until(ExpectedConditions.elementToBeClickable(logoutButton));
        Assertions.assertEquals("Home", driver.getTitle());
    }
}
