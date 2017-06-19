package com.mycompany.computerdb.steps;

import com.mycompany.computerdb.pageobjects.AddPage;
import com.mycompany.computerdb.pageobjects.EditPage;
import com.mycompany.computerdb.pageobjects.HomePage;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertTrue;

/**
 * Created by user
 */
public class EditSteps {

    @Autowired
    WebDriver driver;

    HomePage homePage;
    AddPage addPage;
    EditPage editPage;
    Map<String,String> compMap = new HashMap<>();
    Map<String,String> editedCompMap = new HashMap<>();

    public EditSteps() {
    }

    @Before
    public void initPageObjects() {
        //driver init must happen before page init
        driver.get(HomePage.url);
    }

    @Given("^I add a computer with details$")
    public void i_add_a_computer_with_details(List<Map<String,String>> dataList) throws Throwable {
        compMap = dataList.get(0);
        homePage = new HomePage(driver);
        /*as system allows duplicates all pre-existing matching entries are deleted
          as part of test setup */
        homePage.deleteMatchingEntries(compMap.get("name"));
        //arrive at add page
        homePage.visitPage();
        addPage = homePage.clickAddNewComputer();
        //add computer details
        addPage.fillName(compMap.get("name"));
        addPage.fillIntroducedDate(compMap.get("introducedDate"));
        addPage.fillDiscontinuedDate(compMap.get("discontinuedDate"));
        addPage.fillCompany(compMap.get("company"));
        addPage.clickSubmit();
    }


    @When("^I edit the computer with$")
    public void i_edit_the_computer_with(List<Map<String,String>> dataList) throws Throwable {
        editedCompMap = dataList.get(0);
         /*as system allows duplicates all pre-existing matching entries are deleted
          as part of test setup */
        homePage.deleteMatchingEntries(editedCompMap.get("name"));
        //arrive at add page
        homePage.visitPage();
        //search computer by name
        if (homePage.searchCount(compMap.get("name")) > 0 ) {
            editPage = homePage.searchAndLocate(compMap.get("name"));
            editPage.fillName(editedCompMap.get("name"));
            editPage.fillIntroducedDate(editedCompMap.get("introducedDate"));
            editPage.fillDiscontinuedDate(editedCompMap.get("discontinuedDate"));
            editPage.fillCompany(editedCompMap.get("company"));
            editPage.clickSubmit();
        }
    }

    @Then("^success message should be displayed \"(.*)\"$")
    public void success_message_should_be_displayed(String msg) throws Throwable {
        assertTrue(homePage.getMessage().equals(msg));
    }

    @Then("^search for the computer to read the changes$")
    public void search_for_the_computer_to_read_the_changes() throws Throwable {
        //search computer by name
        if (homePage.searchCount(editedCompMap.get("name")) > 0 ) {
            editPage = homePage.searchAndLocate(editedCompMap.get("name"));
            assertTrue(editPage.compareToFormValues(editedCompMap));
        }
        else Assert.fail();
    }

}
