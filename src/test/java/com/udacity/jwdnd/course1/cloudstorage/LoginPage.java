package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "submit-button")
    private WebElement submitButton;

    @FindBy(id = "error-msg")
    private WebElement errorAlertMessage;

    @FindBy(id = "logout-msg")
    private WebElement logoutAlertMessage;

    @FindBy(id = "signup-link")
    private WebElement signupLink;


    private final WebDriver driver;

    public LoginPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void login(String username, String password){
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + username + "';", inputUsername);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + password + "';", inputPassword);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", submitButton);
    }
}
