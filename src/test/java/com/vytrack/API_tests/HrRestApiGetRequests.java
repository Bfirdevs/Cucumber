package com.vytrack.API_tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class HrRestApiGetRequests {
    /* send get reques to Rest URL
     * then response status should be 200
     */
    @Test
    public void simpleGet(){
    when().get("http://54.159.136.152:1000/ords/hr/employees")
            .then().statusCode(200);

    }
    @Test
    public void printResponse(){
        when().get("http://54.159.136.152:1000/ords/hr/countries ").body().prettyPrint();
    }
    @Test
    public void getWithHeaders(){
        with().accept(ContentType.JSON).when().get("http://54.159.136.152:1000/ords/hr/countries/US").then()
                .statusCode(200);
    }
    @Test
    public void negativeGetTest(){
        //when().get("http://54.159.136.152:1000/ords/hr/employees/1231").then().statusCode(404);
        Response response = when().get("http://54.159.136.152:1000/ords/hr/countries/2344");
        Assert.assertEquals(response.statusCode(),404);
       // if(response.statusCode() == 404){
            response.prettyPrint();
       // }
    }
    @Test
    public void GetTest(){
        //when().get("http://54.159.136.152:1000/ords/hr/employees/101").then().statusCode(404);
        Response response = when().get("http://54.159.136.152:1000/ords/hr/employees/101");
        if(response.statusCode() == 200){
            response.prettyPrint();
        }
    }
    @Test
    public void VerifyContentTypeWithAssertType(){
        String url = "http://54.159.136.152:1000/ords/hr/employees/101";
        given().accept(ContentType.JSON).when().get(url)
                .then().assertThat().statusCode(200)
                .and().contentType(ContentType.JSON);
    }
    @Test
    public void verifyFirstName() throws URISyntaxException {
        URI uri =  new URI( "http://54.159.136.152:1000/ords/hr/employees/100");
        given().accept(ContentType.JSON).when().get(uri)
                .then().assertThat().statusCode(200)
                .and().contentType(ContentType.JSON)
                .and().assertThat().body("first_name", Matchers.equalTo("Steven"))
                .and().assertThat().body("employee_id", equalTo(100));
    }
}
