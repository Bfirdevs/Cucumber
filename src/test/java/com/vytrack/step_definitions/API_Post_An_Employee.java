package com.vytrack.step_definitions;

import com.vytrack.utilities.ConfigurationReader;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.testng.AssertJUnit.assertEquals;

public class API_Post_An_Employee {
    RequestSpecification request;
    int EmployeeId;
    Response response;
    String url = ConfigurationReader.getProperty("hrApp.baseUrl")+ "employees/";
    Map employee;

    @Given("Content type and Accept type is Json")
    public void content_type_and_Accept_type_is_Json() {
        request = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON);
    }

    @When("I post a new Employee with {string} id")
    public void i_post_a_new_Employee_with_id(String id) {
        if(id.equals("random")){
            EmployeeId = new Random().nextInt(9999999);
        }else{
            EmployeeId = Integer.parseInt(id);
        }
         employee = new HashMap();
        employee.put("employee_id", EmployeeId);
        employee.put("first_name", "POSTFNAME");
        employee.put("last_name", "POSTLNAME");
        employee.put("email", "EM" + EmployeeId);
        employee.put("phone_number", "202.343.1243");
        employee.put("hire_date", "2003-06-17T04:00:00Z");
        employee.put("job_id", "IT_PROG");
        employee.put("salary", 24000);
        employee.put("commission_pct", null);
        employee.put( "manager_id", null);
        employee.put("department_id", 90);
        response = request.body(employee)
                .when().post(url);
    }

    @Then("Status code is {int}")
    public void status_code_is(int statusCode) {
       assertEquals(response.statusCode(), statusCode);
    }

    @Then("Response Json should contain Employee info")
    public void response_Json_should_contain_Employee_info() {
        Map postResEmployee = response.getBody().as(Map.class);
        // Response Json should contain Employee info
        for(Object key : employee.keySet()){
            Assert.assertEquals(postResEmployee.get(key), employee.get(key));
        }
    }

    @When("I send a Get request with {string} id")
    public void i_send_a_Get_request_with_id(String id) {
        if (!id.equals("random")) {
            EmployeeId = Integer.parseInt(id);
        }
        response = given().accept(ContentType.JSON)
                .when().get(url+EmployeeId);
    }
    @Then("status code is {int}")
    public void status_code_is(Integer statusCode) {
        Assert.assertEquals(response.statusCode(), 200);
    }
    @Then("employee Json Response Data should match the posted Json Data")
    public void employee_Json_Response_Data_should_match_the_posted_Json_Data() {
        Map getResMap = response.body().as(Map.class);
        for(Object key: employee.keySet()){
            System.out.println(key + ": "+ employee.get(key)+ "<>" + getResMap.get(key));
            Assert.assertEquals(getResMap.get(key), employee.get(key));
        }
    }

}
