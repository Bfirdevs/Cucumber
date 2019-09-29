package com.vytrack.API_tests;

import com.vytrack.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class ApiDay3_GSON {
    @Test
    public void testWithJsonToHashMap(){
        Response response =  given().accept(ContentType.JSON)
                .when().get(ConfigurationReader.getProperty("hrApp.baseUrl")+ "employees/120");
        HashMap<String, String> map = response.as(HashMap.class);
        System.out.println(map);
        System.out.println(map.keySet());
        System.out.println(map.values());
      //  assertEquals(map.get("employee_id"), 120);
        assertEquals(map.get("employee_id"), 120);
        assertEquals(map.get("job_id"), "AC_MGR");
    }
    @Test
    public void convertJsonToListOfMaps(){
        Response response = given().accept(ContentType.JSON)
                .when().get(ConfigurationReader.getProperty("hrApp.baseUrl")+ "departments");
        // convert the response that contains department information into list  list of maps
        // List<Map<String, String>>
        List<Map> departments = response.jsonPath().getList("items", Map.class);
        System.out.println(departments.get(0));
        // assert that first department name is "Administration"
        assertEquals(departments.get(0).get("department_name"), "Administration");
    }
}