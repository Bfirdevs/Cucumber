package com.vytrack.runners;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                "json:target/cucumber.json",
                "html:target/default-cucumber-reports",
                "rerun:target/rerun.txt" // this line is for failed API_tests
        },
      tags = {"@ApiPost"},
        // tags={~@navigation}, // will ignore nevagation test
        features = {"src/test/resources/features/API",
           //     "src/test/resources/HrApp/login"//to specify where are the HrApp
        },
        //feature contains scenarios
        //every scenario is like a test
        //where is the implementation for HrApp
        glue = {"com/vytrack/step_definitions"},
        //dry run - to generate step definitions automatically
        //you will see them in the console output
        dryRun = false

)
public class CukesRunner {

}
