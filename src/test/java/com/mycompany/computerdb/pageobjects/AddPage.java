package com.mycompany.computerdb.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.Map;

/**
 * Created by user
 */
public class AddPage extends Page {

    @FindBy(how = How.ID, using = "name")
    public WebElement name_inputText;

    @FindBy(how = How.ID, using = "introduced")
    public WebElement introducedDate_inputText;

    @FindBy(how = How.ID, using = "discontinued")
    public WebElement discontinuedDate_inputText;

    @FindBy(how = How.ID, using = "company")
    public WebElement company_select;

    @FindBy(how = How.XPATH, using = "//*[@id=\"main\"]/form/div/input")
    public WebElement create_submitButton;

    public AddPage(EventFiringWebDriver driver) {
        super(driver);
    }

    public void fillName(String s) {
        name_inputText.sendKeys(s);
    }
    //checks for required validation
    public boolean isNameInValid() {
        return isFormFieldValid(name_inputText);
    }

    public void fillIntroducedDate(String s) {
        introducedDate_inputText.sendKeys(s);
    }

    //checks for date validation
    public boolean isIntroducedDateInValid() {
        return isFormFieldValid(introducedDate_inputText);
    }

    public void fillDiscontinuedDate(String s) {
        discontinuedDate_inputText.sendKeys(s);
    }

    //checks for date validation
    public boolean isdiscontinuedDateInValid() {
        return isFormFieldValid(discontinuedDate_inputText);
    }

    public void fillCompany(String s) {
        if(!s.isEmpty()) {
            Select select = new Select(company_select);
            select.selectByVisibleText(s);
        }
    }

    public void fillAddComputerForm(Map<String,String> map) {
        fillName(map.get("name"));
        fillIntroducedDate(map.get("introducedDate"));
        fillDiscontinuedDate(map.get("discontinuedDate"));
        fillCompany(map.get("company"));
    }

    public HomePage clickSubmit() {
        create_submitButton.click();
        return new HomePage(driver);
    }
    public void clickSubmitExpectingError() {
        create_submitButton.click();
    }

    public boolean isOnPage() {
        String title = "";
        return this.driver.getTitle() == title;
    }
    //upon validation error inputfield's grandparent node(which is a div)
    // changes class to 'clearfix error'
    private boolean isFormFieldValid(WebElement e) {
        WebElement grandParentNode = e.findElement(By.xpath("..")).findElement(By.xpath(".."));
        return grandParentNode.getAttribute("class").contains("error");
    }

}
