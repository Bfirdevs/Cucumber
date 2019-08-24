package com.vytrack.step_definitions;

import com.vytrack.utilities.Pages;
import cucumber.api.java.en.Then;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class VehiclesStepDefinitions {
    Pages pages = new Pages();

    @Then("user verifies that default page number is {int}")
    public void user_verifies_that_default_page_number_is(Integer expected) {
        Assert.assertEquals(expected, pages.vehiclesPage().getPageNumber());
    }

    @Then("user clicks on the create a car button")
    public void user_clicks_on_the_create_a_car_button() {
        pages.vehiclesPage().clickToCreateACar();
    }

//            | License Plate | Cybertek |
//            | Driver        | Spartan  |
//            | Location      | Alaska   |
//            | Model Year    | 2019     |
//            | Color         | Black    |
//            | Power         | 500      |
//            | Vehicle Make  | BMW      |
//            | Vehicle Model | X5M      |

    @Then("user enters car information:")
    public void user_enters_car_information(Map<String, String> values) {
        //before reading a data table we can verify if such a key-value pair exists
        if(values.containsKey("License Plate"))pages.vehiclesPage().enterLicensePlate(values.get("License Plate"));
        pages.vehiclesPage().enterDriver(values.get("Driver"));
        pages.vehiclesPage().enterLocation(values.get("Location"));
        pages.vehiclesPage().enterModelYear(values.get("Model Year"));
        pages.vehiclesPage().enterColor(values.get("Color"));
        pages.vehiclesPage().enterPower(values.get("Power"));
    }
    @Then("user enters car information to create a car")
    public void user_enters_car_information_to_create_a_car(List<Map<String, String>> values) {
//       values.get(0).get("Driver");
//        as many rows you have in the data table
//        as many maps ypu will have in the list
//        provide column name as key name and be happy:)
        for(Map<String, String> value: values){
            System.out.println(value);
            pages.vehiclesPage().enterLicensePlate(value.get("License Plate"));
            pages.vehiclesPage().enterDriver(value.get("Driver"));
            pages.vehiclesPage().enterLocation(value.get("Location"));
            pages.vehiclesPage().enterModelYear(value.get("Model Year"));
            pages.vehiclesPage().enterColor(value.get("Color"));
            pages.vehiclesPage().enterPower(value.get("Power"));
        }
    }

    @Then("user clicks save and close")
    public void user_clicks_save_and_close() {
        pages.vehiclesPage().clicksaveAndClose();
    }

    @Then("user verifies the general information is displayed")
    public void user_verifies_the_general_information_is_displayed() {
        Assert.assertTrue(pages.vehiclesPage().verifyGeneralInformationIsDisplayed());

    }

}
