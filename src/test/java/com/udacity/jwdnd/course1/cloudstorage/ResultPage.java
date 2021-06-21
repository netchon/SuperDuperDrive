package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ResultPage {

    @FindBy(id = "home-link")
    private WebElement homeLink;

    private final WebDriver driver;
    private final WebDriverWait webDriverWait;
    private final JavascriptExecutor javascriptExecutor;

    public ResultPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.javascriptExecutor = (JavascriptExecutor) driver;
        this.webDriverWait = new WebDriverWait(driver, 3);
    }

    public void goToHomePage(){
        webDriverWait.until(ExpectedConditions.elementToBeClickable(homeLink));
        javascriptExecutor.executeScript("arguments[0].click()", homeLink);
    }
}
