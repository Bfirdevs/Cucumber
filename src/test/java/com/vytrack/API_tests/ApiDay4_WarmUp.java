package com.vytrack.API_tests;

import com.vytrack.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class ApiDay4_WarmUp {
    /*
    Given content type is json
    and limit is 10
    when I send request to Rest API url
    The status code is 200
    Then I should see all the regions
     */
    @Test
    public void test1(){
    Response response = given().accept(ContentType.JSON)
            .and().param("limit", 10)
            .when().get(ConfigurationReader.getProperty("hrApp.baseUrl")+ "regions");
        assertEquals(response.statusCode(), 200);
        JsonPath jsonPath = response.jsonPath();
        List<String> regions = jsonPath.getList("items.region_name");
        System.out.println(regions);
        List<Integer> ids = jsonPath.getList("items.region_id");
        System.out.println(ids);
        assertEquals(jsonPath.getInt("items[0].region_id"), 1);
        assertEquals(jsonPath.getString("items[3].region_name"), "Middle East and Africa");
    }
    @Test
    public void test2(){
        Response response = given().accept(ContentType.JSON)
                .and().param("limit", 10)
                .when().get(ConfigurationReader.getProperty("hrApp.baseUrl")+ "regions");
        assertEquals(response.statusCode(), 200);
        JsonPath jsonPath = response.jsonPath();
        // deserilialize to List<Map> // json file is converted a Map
        List<Map> regions = jsonPath.getList("items", Map.class);
        // expected Map
        Map<Integer, String> expectedRegions = new HashMap<>();
        expectedRegions.put(1, "Europe");
        expectedRegions.put(2,"Americas");
        expectedRegions.put(3,"Asia");
        expectedRegions.put(4, "Middle East and Africa");
        for(Integer regionId : expectedRegions.keySet()){
            System.out.println("Looking for region : "+ regionId);
            for(Map map: regions){
                if(map.get("region_id") == regionId){
                    assertEquals(map.get("region_name"), expectedRegions.get(regionId));
                }
            }
        }
    }
    @Test
    public void test3(){
        Response response = given().accept(ContentType.JSON)
                .and().param("limit", 10)
                .when().get(ConfigurationReader.getProperty("hrApp.baseUrl")+ "regions");
        assertEquals(response.statusCode(), 200);
        JsonPath jsonPath = response.jsonPath();
        // deserilialize to List<Map> // json file is converted a Map
        List<Map> regions = jsonPath.getList("items", Map.class);
        // expected Map
        Map<Integer, String> expectedRegions = new HashMap<>();
        expectedRegions.put(1, "Europe");
        expectedRegions.put(2,"Americas");
        expectedRegions.put(3,"Asia");
        expectedRegions.put(4, "Middle East and Africa");
        for(int i =0; i< regions.size(); i++){
            assertEquals(regions.get(i).get("region_id"), i+1);
            assertEquals(regions.get(i).get("region_name"), expectedRegions.get(i+1));
        }
    }
}
