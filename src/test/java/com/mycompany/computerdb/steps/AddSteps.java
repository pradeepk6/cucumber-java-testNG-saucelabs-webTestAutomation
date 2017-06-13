package com.mycompany.computerdb.steps;

import com.mycompany.computerdb.pageobjects.AddPage;
import com.mycompany.computerdb.pageobjects.EditPage;
import com.mycompany.computerdb.pageobjects.HomePage;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;

import java.util.*;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;


/**
 * Created by user
 */
public class AddSteps {

    @Autowired
    EventFiringWebDriver driver;

    HomePage homePage;
    AddPage addPage;
    EditPage editPage;
    Map<String,String> compMap = new HashMap<>();

    public AddSteps() {
    }

    @Before
    public void initPageObjects() {
        //driver init must happen before page init
        driver.get(HomePage.url);
    }

    @Given("^I am on Home page$")
    public void i_am_on_Home_page() throws Throwable {
        homePage = new HomePage(driver);
        //homePage.visitPage();
        //navigate to Add Page
        addPage = homePage.clickAddNewComputer();
    }

    @When("^I fill in (.*),(.*),(.*),(.*) and submit$")
    public void i_fill_in_and_submit(String name, String introducedDate,
                    String discontinuedDate, String company ) throws Throwable {
        //as system allows duplicates all pre-existing matching entries are deleted
        //as part of test setup
        homePage.visitPage();
        homePage.deleteMatchingEntries(name);
        //fill in form
        homePage.visitPage();
        addPage = homePage.clickAddNewComputer();
        addPage.fillName(name);
        compMap.put("name",name);
        addPage.fillIntroducedDate(introducedDate);
        compMap.put("introducedDate",introducedDate);
        addPage.fillDiscontinuedDate(discontinuedDate);
        compMap.put("discontinuedDate",discontinuedDate);
        addPage.fillCompany(company);
        compMap.put("company",company);
        addPage.clickSubmit();
    }

    @Then("^I should see a success message$")
    public void i_should_see_a_success_message(String msg) throws Throwable {
        assertEquals(msg, homePage.getMessage());
    }

    @When("^I do not fill in (.*) and submit$")
    public void i_do_not_fill_in_and_submit(String name ) throws Throwable {
        addPage.fillName(name);
        addPage.clickSubmitExpectingError();
    }

    @Then("^I should see Required validation failure$")
    public void i_should_see_Required_validation() throws Throwable {
        assertTrue(addPage.isNameInValid());
    }

    @When("^I fill in (.*) for name and (.*) for introduced_date and submit$")
    public void i_fill_in_name_for_name_and_date_for_introduced_date_and_submit(String name, String date) throws Throwable {

        addPage.fillName(name);
        addPage.fillIntroducedDate(date);
        addPage.clickSubmitExpectingError();
    }

    @Then("^I should see introduced_date validation failure$")
    public void i_should_see_introduced_date_validation_failure() throws Throwable {
        assertTrue(addPage.isIntroducedDateInValid());
    }

    @When("^I fill in (.*) for name and (.*) for discontinued_date and submit$")
    public void i_fill_in_name_for_name_and_date_for_discontinued_date_and_submit(String name, String discontinued_date ) throws Throwable {
        addPage.fillName(name);
        addPage.fillDiscontinuedDate(discontinued_date);
        addPage.clickSubmitExpectingError();
    }

    @Then("^I should see discontinued_date validation failure$")
    public void i_should_see_discontinued_date_validation_failure() throws Throwable {
        assertTrue(addPage.isdiscontinuedDateInValid());
    }

    @Then("^search for the computer should match details$")
    public void search_for_the_computer_should_match_details() throws Throwable {
        //search computer by name
        if (homePage.searchCount(compMap.get("name")) > 0 ) {
            editPage = homePage.searchAndLocate(compMap.get("name"));
            assertTrue(editPage.compareToFormValues(compMap));
        }
        else Assert.fail();

    }

}
