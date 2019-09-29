package com.vytrack.API_tests.PutDeleteAuthorizationSchemaVAlidation;

import com.vytrack.utilities.ConfigurationReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;

public class SpartanDeletePut {

    @BeforeClass
    public static void setup(){
        RestAssured.baseURI = ConfigurationReader.getProperty("spartan.baseuri");
    }
    @Test
    public void updateExistingSpartan_Put_test(){
        // seriliaze this object to Json
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("name", "Ferdi");
        requestMap.put("gender", "Female");
        requestMap.put("phone", "677539542L");
        given().contentType(ContentType.JSON)
                .and().body(requestMap)
                .and().pathParam("id", 6)
                .when().put("spartans/{id}")
                .then().assertThat().statusCode(204);
    }
    @Test
    public void deleteExistingSpartan_Delete_Request_test(){
        Random random = new Random();
        int idToDelete = random.nextInt(112);
        System.out.println(idToDelete);
        given().pathParam("id", idToDelete)
                .when().delete("spartans/{id}")
                .then().assertThat().statusCode(204);
        /*
        expect().that().statusCode(204)
                .given().pathParam("id",idToDelete)
                .when().delete("spartans/{id}");
    */
    }

}
