package com.mycompany.computerdb.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by user
 */
@Configuration
public class DriverFactory {
    //SauceLabs fields
    public static final String USERNAME = System.getenv("SAUCE_USERNAME");
    public static final String ACCESS_KEY = System.getenv("SAUCE_ACCESS_KEY");
    public static final String URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:443/wd/hub";
    public static String sessionId;
    public static String jobName;
    private static String browser;
    private static WebDriver driver;

    public DriverFactory() {
    }

    @Lazy
    @Bean
    @Scope("cucumber-glue")
    public static EventFiringWebDriver eventFiringWebDriver() {
        //System.out.println("DriverFactory.webDriver");
        if (browser == null) browser = getBrowser();
        DesiredCapabilities caps;
        switch (browser) {
            case "CHROME":
                caps = DesiredCapabilities.chrome();
                driver = new ChromeDriver(caps);

                break;
            case "FIREFOX":
                caps = DesiredCapabilities.firefox();
                driver = new FirefoxDriver(caps);
                break;
            case "EDGE":
                caps = DesiredCapabilities.edge();
                driver = new EdgeDriver(caps);
                break;
            case "PHANTOMJS":
                caps = DesiredCapabilities.phantomjs();
                driver = new PhantomJSDriver(caps);
                break;
            case "HTMLUNIT":
                caps = DesiredCapabilities.htmlUnit();
                driver = new HtmlUnitDriver(caps);
                break;
            case "SAUCELABS":
                caps = new DesiredCapabilities();
                caps.setCapability(CapabilityType.PLATFORM, System.getenv("platform"));
                caps.setCapability(CapabilityType.BROWSER_NAME, System.getenv("browserName"));
                caps.setCapability(CapabilityType.BROWSER_VERSION, System.getenv("browserVersion"));
                //caps.setCapability(CapabilityType.BROWSER_VERSION, "latest");
                caps.setCapability(CapabilityType.APPLICATION_NAME, "cucumber-java-testNG-saucelabs-webTestAutomation");
                caps.setCapability("name", "");
                //caps.setCapability("chromeOptions", );
                // caps['chromeOptions'] = {'args': ['--headless']}
                try {
                    driver = new RemoteWebDriver(new URL(URL), caps);
                } catch (MalformedURLException mfe) {
                    System.err.println("SauceLabs URL is malformed: " + mfe.getMessage());
                }
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();
                System.setProperty("sauceSessionId", sessionId);
        }
        //return driver;
        return new EventFiringWebDriver(driver);
    }

    public static String getBrowser() {
        //System.out.println("DriverFactory.getBrowser");
        if (browser != null) return browser;
        //system property set via maven failsafe plugin
        browser = System.getProperty("browser");
        if (browser == null) throw new NullPointerException("system property 'browser' must be set.");
        else browser = browser.toUpperCase();
        return browser;
    }
}
