package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CredentialPage {

    @FindBy(id = "credential-url")
    private WebElement credentialUrlInput;

    @FindBy(id = "credential-username")
    private WebElement credentialUsernameInput;

    @FindBy(xpath = "//*[@id=\"credential-password\"]")
    private WebElement credentialPasswordInput;


    @FindBy(id = "credentialTable")
    private WebElement credentialTable;

    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentialsTab;

    @FindBy(id = "add-credential-button")
    private WebElement addCredentialsButton;

    @FindBy(id = "edit-credential-button")
    private WebElement editCredentialButton;

    @FindBy(id = "credentialSubmit")
    private WebElement credentialSubmitButton;

    @FindBy(id = "delete-credential")
    private WebElement credentialDeleteButton;

    private final WebDriver driver;

    private final WebDriverWait webDriverWait;

    private final JavascriptExecutor javascriptExecutor;

    public CredentialPage(WebDriver webDriver){
        this.driver = webDriver;
        PageFactory.initElements(webDriver, this);
        this.javascriptExecutor = (JavascriptExecutor) webDriver;
        this.webDriverWait = new WebDriverWait(webDriver,5);
        this.goToCredentialTab();
    }

    public void clickAddCredentialButton(){
        webDriverWait.until(ExpectedConditions.elementToBeClickable(addCredentialsButton));
        javascriptExecutor.executeScript("arguments[0].click()", addCredentialsButton);
    }

    public void clickCredentialSubmitButton(){
        javascriptExecutor.executeScript("arguments[0].click()", credentialSubmitButton);
    }

    public void clickEditCredentialButton(int position){
        webDriverWait.until(ExpectedConditions.elementToBeClickable(editCredentialButton));
        List<WebElement> listWebElementsButtons = driver.findElements(By.id("edit-credential-button"));
        javascriptExecutor.executeScript("arguments[0].click()", listWebElementsButtons.get(position-1));
    }

    public void fillCredentialData(String url, String username, String password){
        javascriptExecutor.executeScript("arguments[0].value='" + url + "';", credentialUrlInput);
        javascriptExecutor.executeScript("arguments[0].value='" + username + "';", credentialUsernameInput);
        javascriptExecutor.executeScript("arguments[0].value='" + password + "';", credentialPasswordInput);
    }

    public void deleteCredential(){
        webDriverWait.until(ExpectedConditions.elementToBeClickable(credentialDeleteButton));
        javascriptExecutor.executeScript("arguments[0].click()", credentialDeleteButton);
    }


    public void goToCredentialTab(){
        webDriverWait.until(ExpectedConditions.elementToBeClickable(navCredentialsTab));
        javascriptExecutor.executeScript("arguments[0].click()", navCredentialsTab);
    }


    public String geturlDisplayed(int position){
        List<WebElement> listWebElements = driver.findElements(By.id("credentialUrlText"));
        return listWebElements.get(position-1).getAttribute("innerHTML");
    }

    public String getUsernameDisplayed(int position){
        List<WebElement> listWebElements = driver.findElements(By.id("credentialUsernameText"));
        return listWebElements.get(position-1).getAttribute("innerHTML");    }

    public String getEncryptedPassword(int position){
        List<WebElement> listWebElements = driver.findElements(By.id("credentialPasswordText"));
        return listWebElements.get(position-1).getAttribute("innerHTML");
    }

    public String getUrlDisplayedInModel(){
        return this.credentialUrlInput.getAttribute("value");
    }

    public String getUsernameDisplayedInModel(){
        return this.credentialUsernameInput.getAttribute("value");
    }

}
