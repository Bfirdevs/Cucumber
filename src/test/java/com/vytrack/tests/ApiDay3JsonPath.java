package com.vytrack.tests;

import com.vytrack.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;


import java.io.File;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;

public class ApiDay3JsonPath {
    /*
    given accept type is Json
    when I send Get request to rest url
    http://54.159.136.152:1000/ords/hr/regions
    Then status code is 200 And
    Response content should be json and
    4 regions should be return
    And americas is one of the region name
     */
    // Validation of multiple item  in Json
    @Test
    public void testItemsCountFromResponseBody(){
        given().accept(ContentType.JSON)
                .when().get(ConfigurationReader.getProperty("hrApp.baseUrl")+ "regions")
                .then().assertThat().statusCode(200)
                .and().assertThat().contentType(ContentType.JSON)
                .and().assertThat().body("items.region_id", hasSize(4))
                .and().assertThat().body("items.region_name", hasItems("Americas", "Asia"))
                .and().assertThat().body("items.region_name", hasItem("Europe"));

    }
    /*
    Given accept types is Json
    Param limit 100
    When I send request to
    hrApp.baseUrl/employee
    Then status code 200
    And Response content should be json
    And 100 employees data should be in json response body
     */
    @Test
    public void testWithQueryParameterAndList(){
        given().accept(ContentType.JSON)
                .and().params("limit",100)
                .when().get(ConfigurationReader.getProperty("hrApp.baseUrl")+ "employees")
                .then().statusCode(200)
                .and().contentType(ContentType.JSON)
                .and().assertThat().body("items.employee_id", hasSize(100));
    }

    /*
    Given accept types is Json
    Param limit 100
    and path param is 110
    When I send request to
    hrApp.baseUrl/employee
    Then status code 200
    And Response content should be json
    And 100 employees data should be in json response body
    and following data should be
    first_name: "John"
    last_name : "Chen"
    email : "JCHEN"
     */
    @Test
    public void testWithPathParameter(){
        given().accept(ContentType.JSON)
                .and().params("limit", 100)
                .and().pathParams("employee_id", 110)
                .when().get(ConfigurationReader.getProperty("hrApp.baseUrl")+"employees/{employee_id}")
                .then().statusCode(200)
                .and().contentType(ContentType.JSON)
                .and().assertThat().body("employee_id",  equalTo(110)
                                        ,"first_name", equalTo("John")
                                        , "last_name", equalTo("Chen")
                                        , "email", equalTo("JCHEN"));
    }

     /*
    Given accept types is Json
    Param limit 100
    and path param is 110
    When I send request to
    hrApp.baseUrl/employee
    Then status code 200
    And Response content should be json
    And 100 employees data should be in json response
    All employees should be returned
     */
     @Test
    public void testWithJsonPath(){
         Map<String, Integer> rParamMap = new HashMap<>();
         rParamMap.put("limit", 100);
        Response response=  given().accept(ContentType.JSON)
                 .and().params(rParamMap)
                .and().pathParams("employee_id", 177)
                  .when().get(ConfigurationReader.getProperty("hrApp.baseUrl")+ "employees/{employee_id}");

         JsonPath json = response.jsonPath(); // to get json body and assign to Jsonpath object
         System.out.println(json.getInt("employee_id"));
         System.out.println(json.getInt("salary"));
         System.out.println(json.getString("first_name"));
         System.out.println(json.getString("last_name"));
         System.out.println(json.getString("links.href"));
         System.out.println(json.getString("links[0].href"));

         // assgin all hrefs into a list of strings
         List<String> allHrefs = json.getList("links.href");
         System.out.println(allHrefs);
     }
     @Test
    public void testJsonPathWithLists(){
         Map<String, Integer> rParamMap = new HashMap<>();
         rParamMap.put("limit", 100);
         Response response=  given().accept(ContentType.JSON)
                 .and().params(rParamMap)
                 .when().get(ConfigurationReader.getProperty("hrApp.baseUrl")+ "employees");
         assertEquals(response.statusCode(),200); // check status code
         JsonPath json = new JsonPath(response.asString());
        // JsonPath json = new JsonPath(new File(FilePath.json));
       //  JsonPath json = response.jsonPath();
        // get all employee ids into an arraylist
         List<Integer> EmpIds = new ArrayList<>();
         //List<Integer> EmpIds = json.getList("items.employee_id");;
         EmpIds = json.getList("items.employee_id");
         System.out.println(EmpIds);
         assertEquals(EmpIds.size(), 100);
         //get all emails as list
         List<String> emails = json.getList("items.email");
         System.out.println(emails);

         // get all employee ids that are greater than 150
         List<String> IDs = json.getList("items.findAll{it.employee_id > 150}.employee_id");
         System.out.println(IDs);

         // get all employee lastnames whose salary is more than 7000
         List<String> lastNames = json.getList("items.findAll{it.salary > 10000}.last_name");
         System.out.println(lastNames);
        // how to convert json to java object
         JsonPath jsonPathFromFile = new JsonPath(new File("C:/Users/YildirimPC/Desktop/Employee.json"));
         List<String> emails2 = jsonPathFromFile.getList("items.email");
         System.out.println(emails2);
     }
}

