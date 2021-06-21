package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NotePage {

    @FindBy(id = "note-title")
    private WebElement noteTitleInput;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionInput;

    @FindBy(id = "nav-notes-tab")
    private WebElement navNotesTab;

    @FindBy(id = "noteSubmit")
    private WebElement noteSubmitButton;

    @FindBy(id = "delete-note")
    private WebElement noteDeleteButton;

    @FindBy(id = "edit-note-button")
    private WebElement editNoteButton;

    @FindBy(id = "add-note-button")
    private WebElement addNoteButton;

    private final WebDriver driver;

    private final WebDriverWait webDriverWait;

    private final JavascriptExecutor javascriptExecutor;

    public NotePage(WebDriver webDriver){
        this.driver = webDriver;
        PageFactory.initElements(webDriver, this);
        this.javascriptExecutor = (JavascriptExecutor) webDriver;
        this.webDriverWait = new WebDriverWait(webDriver,5);
        this.goToNavTab();
    }

    public void goToNavTab(){
        webDriverWait.until(ExpectedConditions.elementToBeClickable(navNotesTab));
        javascriptExecutor.executeScript("arguments[0].click()", navNotesTab);
    }

    public void clickEditNoteButton(){
        webDriverWait.until(ExpectedConditions.elementToBeClickable(editNoteButton));
        javascriptExecutor.executeScript("arguments[0].click()", editNoteButton);
    }

    public void clickAddNoteButton(){
        webDriverWait.until(ExpectedConditions.elementToBeClickable(addNoteButton));
        javascriptExecutor.executeScript("arguments[0].click()", addNoteButton);
    }

    public void clickSubmitNoteButton(){
        javascriptExecutor.executeScript("arguments[0].click()", noteSubmitButton);
    }

    public void fillNoteData(String title, String description){
        javascriptExecutor.executeScript("arguments[0].value='" + title + "';", noteTitleInput);
        javascriptExecutor.executeScript("arguments[0].value='" + description + "';", noteDescriptionInput);
    }

    public String getNoteTitleDisplayed(){
        return this.noteTitleInput.getAttribute("value");
    }

    public String getNoteDescriptionDisplayed(){
        return this.noteDescriptionInput.getAttribute("value");
    }


    public void deleteNote(){
        webDriverWait.until(ExpectedConditions.elementToBeClickable(noteDeleteButton));
        javascriptExecutor.executeScript("arguments[0].click()", noteDeleteButton);
    }
}
