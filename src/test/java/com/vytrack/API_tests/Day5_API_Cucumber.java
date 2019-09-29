package com.vytrack.API_tests;

import com.vytrack.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class Day5_API_Cucumber {
    /*
    given content type and Accept type is json
    When I post a new Employee with "1234"
    Then Status Code  is 201
    And Response should contain Employee Info
    When I send a get Request with "1234" id
    then status code is 200
    And employee JSON response Data should match the posted Json data
      "employee_id": 1200,
            "first_name": "Firdevs",
            "last_name": "Elif",
            "email": "Turkiye",
            "phone_number": "515.123.4567",
            "hire_date": "2003-06-17T04:00:00Z",
            "job_id": "IT_PROG",
            "salary": 24000,
            "commission_pct": null,
            "manager_id": null,
            "department_id": 90
     */
    @Test
    public void postEmployeeThenGetEmployee(){
        String url = ConfigurationReader.getProperty("hrApp.baseUrl")+ "employees/";
        int randomId = new Random().nextInt(999999);
        Map employee = new HashMap();
        employee.put("employee_id", randomId);
        employee.put("first_name", "POSTFNAME");
        employee.put("last_name", "POSTLNAME");
        employee.put("email", "EM" + randomId);
        employee.put("phone_number", "202.343.1243");
        employee.put("hire_date", "2003-06-17T04:00:00Z");
        employee.put("job_id", "IT_PROG");
        employee.put("salary", 24000);
        employee.put("commission_pct", null);
        employee.put( "manager_id", null);
        employee.put("department_id", 90);
        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(employee)
                .when().post(url);
        assertEquals(response.statusCode(), 201);
        Map postResEmployee = response.getBody().as(Map.class);
        // Response Json should contain Employee info
        for(Object key : employee.keySet()){
            Assert.assertEquals(postResEmployee.get(key), employee.get(key));
        }

        // json response data should match the posted json data
        response = given().accept(ContentType.JSON)
                .when().get(url+randomId);
        assertEquals(response.statusCode(), 200);
        Map getResMap = response.body().as(Map.class);
        for(Object key: employee.keySet()){
            System.out.println(key + ": "+ employee.get(key)+ "<>" + getResMap.get(key));
            assertEquals(getResMap.get(key), employee.get(key));

        }
    }
}
