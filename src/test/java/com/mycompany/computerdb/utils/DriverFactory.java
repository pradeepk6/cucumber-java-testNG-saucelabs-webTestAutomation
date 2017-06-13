package com.mycompany.computerdb.utils;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    @Bean
    @Scope("cucumber-glue")
    public static EventFiringWebDriver eventFiringWebDriver() {

        if (browser == null) browser = getBrowser();
        Capabilities capabilities;
        switch (browser) {
            case "CHROME":
                capabilities = DesiredCapabilities.chrome();
                driver = new ChromeDriver(capabilities);
                break;
            case "FIREFOX":
                capabilities = DesiredCapabilities.firefox();
                driver = new FirefoxDriver(capabilities);
                break;
            case "EDGE":
                capabilities = DesiredCapabilities.edge();
                driver = new EdgeDriver(capabilities);
                break;
            case "PHANTOMJS":
                capabilities = DesiredCapabilities.phantomjs();
                driver = new PhantomJSDriver(capabilities);
                break;
            case "HTMLUNIT":
                capabilities = DesiredCapabilities.htmlUnit();
                driver = new HtmlUnitDriver(capabilities);
                break;
            case "saucelabs":
                capabilities = new DesiredCapabilities();
                //capabilities.setCapability(CapabilityType.PLATFORM, System.getenv("platform"));
               // capabilities.setCapability(CapabilityType.BROWSER_NAME, System.getenv("browserName"));
               // capabilities.setCapability(CapabilityType.BROWSER_VERSION, System.getenv("version"));
                // capabilities.setCapability(CapabilityType.APPLICATION_NAME, scenario.getName());
               // capabilities.setCapability("name", scenario.getName());
                try{
                    driver = new RemoteWebDriver(new URL(URL), capabilities);
                }catch(MalformedURLException mfe){
                    System.err.println("SauceLabs URL is malformed: " + mfe.getMessage());
                }
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                jobName = capabilities.getCapability("name").toString();
                sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();
        }
        return new EventFiringWebDriver(driver);
    }

    public static String getBrowser() {
        if (browser != null) return browser;
        //system property set via maven failsafe plugin
        browser = System.getProperty("browser");
        if (browser == null) throw new NullPointerException("system property 'browser' must be set.");
        else browser = browser.toUpperCase();
        return browser;
    }
}
