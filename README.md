##### About:
Code sample of web test-automation with Cucumber using:
* Java 1.8
* Selenium WebDriver 3.4.0
* Cucumber 1.2.5
* Spring 4.3.8
* Maven 3.3.9
* SauceLabs(work in progress)

##### Test Cases: Cucumber feature files should serve as Test Cases.Click below link:
* [Cucumber feature files](/src/test/resources/features)
* Once you run the proram see cucumber reports in target directory to see more colourful TestCases

##### tested on:
* maven based project developed using IntelliJ
* os : windows 10
* chrome 58 and driver-version: 2.29
* default browser choice : htmlunit 
* works with any browser For eg: to run with chrome:
  systemPropertyVariable 'webdriver.chrome.driver' is already set in pom.xml.Download the latest
  chrome driver binary into /src/test/drivers directory and change browser choice in pom.xml to
  chrome or pass in relevant argumetns via maven commandline
* to run: download the project and run maven comman: mvn verify 
  and see cucumber reports in 'target/cucumber' directory
  
  


