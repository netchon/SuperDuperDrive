package com.udacity.jwdnd.course1.cloudstorage;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPage {

    @FindBy(id = "inputFirstName")
    private WebElement inputFistName;

    @FindBy(id = "inputLastName")
    private WebElement inputLastName;

    @FindBy(id = "inputUsername")
    private WebElement inputUsername;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "submit-signin")
    private WebElement submitButton;


    private final WebDriver driver;

    public SignUpPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void signUp(String firstName, String lastName, String username, String password){
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + firstName + "';", inputFistName);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + lastName + "';", inputLastName);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + username + "';", inputUsername);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + password + "';", inputPassword);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", submitButton);
    }

}
