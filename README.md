##### About:
Code sample for test-automation of website: 
http://computer-database.herokuapp.com/computers

The project is built using the following:
* Java 1.8
* Selenium WebDriver 3.4.0
* Cucumber 1.2.5
* Spring 4.3.8
* Maven 3.3.9
* SauceLabs

##### Test Cases: Cucumber feature files should serve as Test Cases.Click below link:
* [Cucumber feature files](/src/test/resources/features)
* Once you run the proram see cucumber reports in target directory to see colourful TestCases

##### how to run:
* need java1.8 and maven3.3.9 to run.
  if you do not have maven installed, project can also be run within a java ide
  as most Java IDEs come with maven bundled.
* download the project and run maven command: mvn verify 
  The above command runs with headless browser htmlunit.
  After running; Cucumber reports will be generated in 'target/cucumber' directory.
  Within that directory click on index.html to see Cucumber reports.
* works with any browser For eg: to run with chrome:
  Download the latest chrome driver binary into /src/test/drivers directory 
  Set'webdriver.chrome.driver' to downloaded file location
  Change browser property in pom.xml to chrome
  or pass the same options via mvn commandline
  <br>For eg:  mvn verify -Dbrowser="chrome" -Dwebdriver.chrome.driver="file location of driver exe file"
* to run on Saucelabs(www.saucelabs.com) cloud set username, accesskey properties in the pom file and
  change the browser property to saucelabs.

##### tested on:
* maven based project developed using IntelliJ
* os : windows 10
* chrome 58 and driver-version: 2.29
* default browser choice : htmlunit 
* Saucelabs


  
  


