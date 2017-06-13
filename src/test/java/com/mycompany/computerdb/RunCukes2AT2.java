package com.mycompany.computerdb;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
        tags = {""},
        features = "src/test/resources/features",
        glue="com.mycompany.computerdb.steps",
        monochrome=true,
        format={"pretty", "html:target/cucumber"})
public class RunCukes2AT2 extends AbstractTestNGCucumberTests{
}
