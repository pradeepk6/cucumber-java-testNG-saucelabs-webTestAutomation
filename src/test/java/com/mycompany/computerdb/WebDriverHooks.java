package com.mycompany.computerdb;

import com.mycompany.computerdb.utils.SauceUtils;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

public class WebDriverHooks {

    String sessionId;
    String username = System.getenv("SAUCE_USERNAME");
    String accesskey = System.getenv("ACCESS_KEY");
    String browser = System.getProperty("browser");
    @Lazy
    @Autowired
    private EventFiringWebDriver driver;

    @Before
    public void setUp(Scenario scenario) {
        driver.manage().window().maximize();
    }

    @After
    public void finish(Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                byte[] screenshot = driver.getScreenshotAs(OutputType.BYTES);
                scenario.embed(screenshot, "image/png");
            } catch (WebDriverException somePlatformsDontSupportScreenshots) {
                System.err.println(somePlatformsDontSupportScreenshots.getMessage());
            }
        }
    }

    @After
    public void teardown(Scenario scenario) throws Throwable {
        if (browser.equals("SAUCELABS")) {
            if (driver != null) {
                driver.quit();
                //sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();
                sessionId = System.getProperty("sauceSessionId");
                SauceUtils.UpdateResults(username, accesskey, !scenario.isFailed(), sessionId);
                //System.out.println("SauceOnDemandSessionID="+ sessionId + "job-name="+ scenario.getName());
            }
        }
    }
}