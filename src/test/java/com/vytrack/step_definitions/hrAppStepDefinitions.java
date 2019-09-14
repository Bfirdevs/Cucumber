package com.vytrack.step_definitions;

import com.vytrack.pages.HRAppDeptEmpPage;
import com.vytrack.utilities.BrowserUtils;
import com.vytrack.utilities.ConfigurationReader;
import com.vytrack.utilities.Driver;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;

public class hrAppStepDefinitions {
    private WebDriver driver = Driver.getDriver();
    private HRAppDeptEmpPage deptEmpPage = new HRAppDeptEmpPage();
    @Given("I am on DeptEmpPage")
    public void i_am_on_DeptEmpPage() {
        driver.get(ConfigurationReader.getProperty("hrApp.url"));
    }

    @When("I search for Department id {int}")
    public void i_search_for_Department_id(int depID) {
        int currentDepId= Integer.parseInt(deptEmpPage.departmentID.getText());
        while(currentDepId != depID){
            deptEmpPage.Next.click();
            BrowserUtils.waitForVisibility(deptEmpPage.departmentID, 5);
            currentDepId= Integer.parseInt(deptEmpPage.departmentID.getText());
        }

    }

    @When("I query database with sql {string}")
    public void i_query_database_with_sql(String string) {

    }
    @Then("UI data and DATAbase must match")
    public void ui_data_and_DATAbase_must_match() {

    }
}
