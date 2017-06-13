package com.mycompany.computerdb.steps;

import com.mycompany.computerdb.pageobjects.AddPage;
import com.mycompany.computerdb.pageobjects.EditPage;
import com.mycompany.computerdb.pageobjects.HomePage;
import com.mycompany.computerdb.pageobjects.Page;
import cucumber.api.PendingException;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

/**
 * Created by user
 */
public class DeleteSteps {

    @Autowired
    EventFiringWebDriver driver;

    HomePage homePage;
    AddPage addPage;
    EditPage editPage;
    Map<String,String> compMap = new HashMap<>();

    public DeleteSteps() {
    }

    @Before
    public void initPageObjects() {
        //driver init must happen before page init
        driver.get(HomePage.url);
    }

    @Given("^I create a new computer named (.*)$")
    public void i_create_a_new_computer_named(String name) throws Throwable {
        compMap.put("name",name);
        homePage = new HomePage(driver);
        //homePage.visitPage();
        //as system allows duplicates all pre-existing matching entries are deleted
        //as part of test setup
        homePage.deleteMatchingEntries(name);
        homePage.visitPage();
        addPage = homePage.clickAddNewComputer();
        addPage.fillName(name);
        addPage.clickSubmit();
    }

    @Given("^search for the newly added computer and navigate to the computer page$")
    public void search_for_the_newly_added_computer() throws Throwable {
        editPage = homePage.searchAndLocate(compMap.get("name"));
    }
    @When("^I delete the computer$")
    public void i_delete_the_computer() throws Throwable {
        editPage.deleteComputer();
    }

    @Then("^I should see successful delete message$")
    public void i_should_see_successful_delete_message(String msg) throws Throwable {
        assertEquals(msg, homePage.getMessage());
    }

    @Then("^search for the computer should find zero results$")
    public void search_for_the_computer_should_find_zero_results() throws Throwable {
        //search computer by name should fetch zero results
        assertTrue(homePage.searchCount(compMap.get("name")) == 0);
    }
}
