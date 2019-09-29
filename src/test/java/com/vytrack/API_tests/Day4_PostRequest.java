package com.vytrack.API_tests;

import com.vytrack.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Day4_PostRequest {
     /*
    Given content is json
    And accept type is Json
    When I sen POST request to url
    with request body
    {"region_id" : 5, "region_name" : "Firdevs' Region"}
    Then status code should be 201
    And response body should match request body
     */
    @Test
    public void postNewRegion(){
        String url = ConfigurationReader.getProperty("hrApp.baseUrl")+ "regions/";
   //     String requestJson = "{\"region_id\" : 5, \"region_name\" : \"Firdevs' Region\"}";
        Map requestMap = new HashMap<>();
        requestMap.put("region_id", 59);
        requestMap.put("region_name", "Sinan");
       Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(requestMap)
                .when().post(url);

        System.out.println(response.statusLine());
        System.out.println(response.prettyPrint());
        // then status code should 201
        // And response body should match request body
        assertEquals(response.getStatusCode(), 201);
        Map responseMap = response.body().as(Map.class);
      //  assertEquals(responseMap, requestMap); did not work because in response map there is also links
        assertEquals(responseMap.get("region_id"), requestMap.get("region_id"));
        assertEquals(responseMap.get("region_name"), responseMap.get("region_name"));
    }
    @Test
    public void postUsionPojo(){
        // Pojo Plain Old Java Object
        String url = ConfigurationReader.getProperty("hrApp.baseUrl")+ "regions/";
        Region region = new Region();
        region.setRegion_id(new Random().nextInt(99999));
        region.setRegion_name("Ayse");
        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(region)
                .when().post(url);
        assertEquals(201, response.getStatusCode());
        RegionResponse responseRegion = response.body().as( RegionResponse.class);
        // And response body should match request body
        // region id and region name must match
      // assertEquals(responseRegion.getRegion_id(), region.getRegion_id());
        // serialization java to json
        assertEquals(responseRegion.getRegion_name(), region.getRegion_name());
    }
    /*
    Given content type is json
    And accepttype is Json
    When I send post request to  url
    with request body
    {
        "country_id" : "AR",
        "country_name" : "Argentina",
        "region_id" : 2
    }
    Then status code should be 200
    And response body should match request body
     */
    @Test
    public void postCountry(){
        // check this test later did not work
        String url = ConfigurationReader.getProperty("hrApp.baseUrl")+ "countries/";
        Country country = new Country();
        country.setCountry_id("C");
        country.setCountry_name("C country");
        country.setRegion_id(4);
        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(country)
                .when().post(url);
       assertEquals(201, response.getStatusCode());

        CountryResponse countryResponse = response.body().as( CountryResponse.class);
         assertEquals(countryResponse.getRegion_id(), country.getRegion_id());
        assertEquals(countryResponse.getCountry_name(), country.getCountry_name());
        assertEquals(countryResponse.getCountry_id(), country.getCountry_id());

    }
}
