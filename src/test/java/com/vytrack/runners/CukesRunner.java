package com.vytrack.runners;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
@RunWith(Cucumber.class)
@CucumberOptions(
        tags = {" @negative"},
        features = {
                "src/test/resources/features"  // to specify where are the features
        },
        //feature contains scenarios
        // every scenario is like a test
        // where is the implementation for features

        glue = {
                "com/vytrack/step_definitions/LoginStepDefinitions.java"
        },
        // dry run to generate step definitions automatically
        // we will see them in the console output
        dryRun = false

)
public class CukesRunner {

}
