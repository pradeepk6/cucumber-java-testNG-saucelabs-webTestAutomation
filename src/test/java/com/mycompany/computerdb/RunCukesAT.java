package com.mycompany.computerdb;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;


@CucumberOptions(
        tags = {"@delete,@add,@edit"},
        features = "src/test/resources/features",
        glue = "com.mycompany.computerdb",
        monochrome = true,
        format = {"pretty", "html:target/cucumber"})
public class RunCukesAT extends AbstractTestNGCucumberTests {
}
