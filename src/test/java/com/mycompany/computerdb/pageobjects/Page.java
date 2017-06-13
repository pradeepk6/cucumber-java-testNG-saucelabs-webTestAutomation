package com.mycompany.computerdb.pageobjects;

/**
 * Created by user
 */

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;


/**
 * Abstract class representation of a Page in the UI. Page object pattern
 */
public abstract class Page {

    protected EventFiringWebDriver driver;

    //String baseurl = "http://computer-database.herokuapp.com/computers";

    public Page(EventFiringWebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    public String getTitle() {
        return driver.getTitle();
    }

}

