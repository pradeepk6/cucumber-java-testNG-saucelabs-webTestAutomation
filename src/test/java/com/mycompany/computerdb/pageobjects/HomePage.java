package com.mycompany.computerdb.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import static org.testng.Assert.assertTrue;

/**
 * Created by user
 */
public class HomePage extends Page {

    public static String url = "http://computer-database.herokuapp.com/computers";
    EditPage editPage;

    @FindBy(how = How.ID, using = "add")
    public WebElement addNewComputer_Anchor;

    @FindBy(how = How.ID, using = "searchbox")
    public WebElement search_TextInput;

    @FindBy(how = How.ID, using = "searchsubmit")
    public WebElement searchSubmit_Button;

    @FindBy(how = How.CSS, using = "#main > div.alert-message.warning")
    public WebElement messsage_div;

    @FindBy(how = How.CSS, using = "#main > h1")
    public WebElement header;

    @FindBy(how = How.CSS, using = "#main > table")
    public WebElement table;

    public HomePage(EventFiringWebDriver driver) {
        super(driver);
    }

    public void visitPage() {
        this.driver.get(url);
    }

    public AddPage clickAddNewComputer() {
        addNewComputer_Anchor.click();
        return new AddPage(driver);
    }
    public int searchCount(String searchStr) {
        int searchCount;
        search_TextInput.sendKeys(searchStr);
        searchSubmit_Button.click();
        String h = header.getText();
        if(h.contains("No computers found")) {
            searchCount=0;
        }else {
            StringTokenizer sto = new StringTokenizer(h);
            String token = sto.nextToken();
            if (token.toLowerCase().equals("one")) searchCount=1;
            else searchCount = Integer.parseInt(token);
        }
        return searchCount;
    }
    //should be called after making sure searchCount is greater than zero
    public EditPage searchAndLocate(String name) {
        searchComputerLink(name).click();
        return new EditPage(driver);
    }

    /* As the system allows duplicate entries search for and delete all matching entries
       before adding a computer.Duplicate entries make it difficult to uniquely
       identify the entry under test*/
    public void deleteMatchingEntries(String name) {
        int num = searchCount(name);
        while(num > 0) {
            searchComputerLink(name).click();
            editPage = new EditPage(driver);
            editPage.deleteComputer();
            num = searchCount(name);
        }
    }
    //grabs the computer page link from the table row
    WebElement searchComputerLink(String name) {
        WebElement searchResult = null;
        String xPathStr = "//a[contains(.,'"+name+"')]";
        searchResult = driver.findElement(By.xpath(xPathStr));
        return  searchResult;
    }

    public String getMessage() {
        return messsage_div.getText();
    }
}
