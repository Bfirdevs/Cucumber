package com.vytrack.API_tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import io.restassured.response.Response;
import org.junit.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class JsonToCollections {
    //I converted Json file into a Map<String, Object>
    /*"employee_id": 105,
    "first_name": "David",
    "last_name": "Austin",
    "email": "DAUSTIN",
    "phone_number": "590.423.4569",
    "hire_date": "2005-06-25T04:00:00Z",
    "job_id": "IT_PROG",
    "salary": 4800,
    "commission_pct": null,
    "manager_id": 103,
    "department_id": 60,
    "links": [
        {
            "rel": "self",
            "href": "http://54.159.136.152:1000/ords/hr/employees/105"
        },
        {
            "rel": "edit",
            "href": "http://54.159.136.152:1000/ords/hr/employees/105"
        },
        {
            "rel": "describedby",
            "href": "http://54.159.136.152:1000/ords/hr/metadata-catalog/employees/item"
        },
        {
            "rel": "collection",
            "href": "http://54.159.136.152:1000/ords/hr/employees/"
        }
         */
    @Test
    public void hrApiEmployee_jsondata_to_javaobject(){
        Response response = given().accept(ContentType.JSON)
                .pathParam("employee_id", 105)
                .when().get("http://54.159.136.152:1000/ords/hr/employees/{employee_id}");
        response.prettyPrint();
        Map<String,Object> employeeMap = response.body().as(Map.class);
        System.out.println(employeeMap.toString());
        String firstName = (String) employeeMap.get("first_name");
        System.out.println("firstName : " + firstName);
        assertEquals("David", firstName);
        double  salary = (Double)employeeMap.get("salary");
        System.out.println("salary:" + salary);
        assertEquals(4800.5,salary, 0.5 );
        //delta 0.5 means that 0.5 difference will be discarded
    }
    // List<Map<String, Object>>
    @Test
    public void convertAll_spartans_to_list_of_maps(){
        Response response = given().accept(ContentType.JSON)
                .when().get("http://54.159.136.152:8000/api/spartans/");

        List<Map<String, ?>> jsonListOfMap = response.body().as(List.class);

        //print all data of first spartan
        System.out.println(jsonListOfMap.get(0));

        Map<String, ?> first = jsonListOfMap.get(0);
        System.out.println("first = " + first);

        System.out.println(first.get("name"));

        int counter = 1;
        for( Map<String, ?> spartan : jsonListOfMap ) {
            System.out.println(counter+ " - spartan = " + spartan);
            counter++;
        }
    }
    @Test
    public void regions_data_to_collect(){
        Response response = given().accept(ContentType.JSON)
                .when().get("http://54.159.136.152:1000/ords/hr/regions/");
        Map<String, ?> dataMap = response.body().as(Map.class);
        System.out.println(dataMap);
        // exctract europe Americas Asia from above the map

       List<Map<String, ?>> regionsList = (List<Map<String, ?>>) dataMap.get("items");
        System.out.println(regionsList.get(0).get("region_name"));
        System.out.println(regionsList.get(1).get("region_name"));
        System.out.println(regionsList.get(2).get("region_name"));
    }

}
