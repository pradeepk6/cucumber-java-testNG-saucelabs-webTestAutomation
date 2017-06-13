package com.mycompany.computerdb.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user
 */
public class EditPage extends Page {

    @FindBy(how = How.XPATH, using = "//*[@id=\"main\"]/form[2]/input")
    public WebElement delete;

    @FindBy(how = How.XPATH, using = "//*[@id=\"main\"]/form[1]/div/input")
    public WebElement save;

    @FindBy(how = How.ID, using = "name")
    public WebElement name_inputText;

    @FindBy(how = How.ID, using = "introduced")
    public WebElement introducedDate_inputText;

    @FindBy(how = How.ID, using = "discontinued")
    public WebElement discontinuedDate_inputText;

    @FindBy(how = How.ID, using = "company")
    public WebElement company_select;

    @FindBy(how = How.XPATH, using = "//*[@id=\"main\"]/form[1]/div/input")
    public WebElement save_submit;


    @FindBy(how = How.XPATH, using = "//*[@id=\"main\"]/form[2]/input")
    public WebElement delete_submit;

    public EditPage(EventFiringWebDriver driver) {
        super(driver);
    }

    public HomePage deleteComputer() {
        delete_submit.click();
        return new HomePage(driver);
    }

    public void fillName(String s) {
        name_inputText.clear();
        name_inputText.sendKeys(s);
    }

    public void fillIntroducedDate(String s) {
        introducedDate_inputText.clear();
        introducedDate_inputText.sendKeys(s);
    }

    public void fillDiscontinuedDate(String s) {
        discontinuedDate_inputText.clear();
        discontinuedDate_inputText.sendKeys(s);
    }

    public void fillCompany(String companyName) {
        if(!companyName.isEmpty()) {
            //Select select = new Select(company_select);
            WebElement option =
                    company_select.findElement(By.xpath("//option[contains(text(),'" + companyName + "')]"));
            option.click();
        }
    }

    public HomePage clickSubmit() {
        save_submit.click();
        return new HomePage(driver);
    }
    public String getName() {
        return name_inputText.getAttribute("value");
    }
    public String getIntroducedDate() {
        return introducedDate_inputText.getAttribute("value");
    }
    public String getdiscontinuedDate() {
        return discontinuedDate_inputText.getAttribute("value");
    }
    public String getCompany() {
        return new Select(company_select).getFirstSelectedOption().getText();
    }
    //compares input values and stored values
    public boolean compareToFormValues(Map<String,String> compMap) throws ParseException {
        if (!compMap.get("name").equals(this.getName())) return false;
        //process dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1, date2;
        if (compMap.containsKey("introducedDate")) {
            String dateStr1 = compMap.get("introducedDate");
            String dateStr2 = this.getIntroducedDate();
            //if both dates are not empty
            if (!dateStr1.isEmpty() && (!dateStr2.isEmpty())) {
                date1 = dateFormat.parse(dateStr1);
                date2 = dateFormat.parse(dateStr2);
                if (!date1.equals(date2)) return false;
            }
        }
        if (compMap.containsKey("discontinuedDate")) {
            String dateStr1 = compMap.get("introducedDate");
            String dateStr2 = this.getIntroducedDate();
            //if both dates are not empty
            if (!dateStr1.isEmpty() && (!dateStr2.isEmpty())) {
                date1 = dateFormat.parse(dateStr1);
                date2 = dateFormat.parse(dateStr2);
                if (!date1.equals(date2)) return false;
            }
        }
        if (compMap.containsKey("company")) {
            compMap.get("company").equals(this.getCompany());
        }
        return true;
    }

}
