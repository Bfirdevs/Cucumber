package com.vytrack.API_tests;


import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;
import sun.security.krb5.internal.PAData;


import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.get;
import static org.testng.AssertJUnit.assertEquals;

public class spartanToPojoDeserilization {
    /*
       "id": 15,
       "name": "Meta",
       "gender": "Female",
       "phone": 1938695106
        */
    @Test
    public void spartan_to_pojo(){
        Response response = given().accept(ContentType.JSON)
                .when().get("http://54.159.136.152:8000/api/spartans/15");
        // deserilization
        spartan sparta = response.getBody().as(spartan.class);
        System.out.println(sparta.getName());
        System.out.println(sparta.getGender());
        System.out.println(sparta.getId());
        System.out.println(sparta.getPhone());
        System.out.println("spartan.toString() = " + sparta.toString());

        assertEquals("Meta", sparta.getName());
        assertEquals("Female", sparta.getGender());
        assertEquals(new Integer(15), sparta.getId());
        assertEquals(new Long(1938695106), sparta.getPhone());
    }
    @Test
    public void gSonExample(){
        spartan sparta = new spartan(20, "Vlad", "male", 7033456798L);
        Gson gson = new Gson();
        // Serilaze Spartan object to Json format
        String json = gson.toJson(sparta);
        System.out.println(json);
        String myJson = "{\"id\":25,\"name\":\"Roman\",\"gender\":\"male\",\"phone\":5556352142}";
        // Deserilization--> convert from Json to Java Spartan object
        spartan Roman = gson.fromJson(myJson, spartan.class);
        System.out.println(Roman.toString());
    }
    @Test
    public void list_of_spartans_pojo_deserilization(){
        Response response = given().accept(ContentType.JSON)
                .when().get("http://54.159.136.152:8000/api/spartans/");
        List<spartan> allspartans = response.body().as(new TypeRef<List<spartan>>(){});

        System.out.println(allspartans.get(0));
        spartan first = allspartans.get(0);
        System.out.println(first.toString());
        for(spartan sp : allspartans){
            System.out.println(sp.toString());
        }

        // Using All Spartans class for deserilization
        //TODO: fix the deserilization issue
        System.out.println(" ####### ALL SPARTANS ########");
        AllSpartans allSpartans = response.body().as(AllSpartans.class);
        System.out.println(allSpartans.getSpartanList().get(0).toString());
    }
    /*
    Given accept type and Content type is json
    And request json body is
    {
        "name": "Spartali",
        "gender": "Female",
        "phone": 5556783425
    }
    When user sends POST request to '/spartan/'
    Then status code 201
    And content type should be application/json
    And json payload/response should contain
    " A Spartan is Born!" message and same data what is posted
     */
    @Test
    public void postNewSpartan(){
        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body("{\n" +
                "        \"name\": \"Spartali\",\n" +
                "        \"gender\": \"Female\",\n" +
                "        \"phone\": 5556783425\n" +
                "    }")
                .when().post("http://54.159.136.152:8000/api/spartans/");
        // response validation
        assertEquals(201, response.statusCode());
        assertEquals("application/json", response.contentType());
        // exract message using path
        String mss1 = response.path("success");
        // exract message using jsonpath
        JsonPath json = response.jsonPath();
        String mess2 = json.getString("success");

        assertEquals("A Spartan is Born!", mss1);
        assertEquals("A Spartan is Born!", mess2);

        // asssert name gender phone
        assertEquals("Female", json.getString("data.gender"));
        assertEquals("Spartali", json.getString("data.name"));
        assertEquals(5556783425L, json.getLong("data.phone"));
    }
    @Test
    public void postNewSpartanWithMap(){
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("gender", "Female");
        requestMap.put("name", "Helen");
        requestMap.put("phone", "4564542314L");
        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(requestMap)
                .when().post("http://54.159.136.152:8000/api/spartans/");
        // response validation
        assertEquals(201, response.statusCode());
        assertEquals("application/json", response.contentType());
        // exract message using path
        String mss1 = response.path("success");
        // exract message using jsonpath
        JsonPath json = response.jsonPath();
        String mess2 = json.getString("success");

        assertEquals("A Spartan is Born!", mss1);
        assertEquals("A Spartan is Born!", mess2);

        // asssert name gender phone
        assertEquals("Female", json.getString("data.gender"));
        assertEquals("Spartali", json.getString("data.name"));
        assertEquals(5556783425L, json.getLong("data.phone"));
        // get the id of spartan
        int spartanId = json.getInt("data.id");
        System.out.println("Spartan id is: " + spartanId);

        get("http://54.159.136.152:8000/api/spartans/" + spartanId).body().prettyPrint();
    }
    @Test
    public void post_a_new_spartan_with_pojo_object(){
        // Create a spartan object to be used as a body for post
        // Serilization java object to json
        spartan sprtan1 = new spartan();
        sprtan1.setGender("Female");
        sprtan1.setName("PostSpartan");
        sprtan1.setPhone(5673489412L);

        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(sprtan1)
                .when().post("http://54.159.136.152:8000/api/spartans/");
        // response validation
        assertEquals(201, response.statusCode());
        assertEquals("application/json", response.contentType());
        // exract message using path
        String mss1 = response.path("success");
        // exract message using jsonpath
        JsonPath json = response.jsonPath();
        String mess2 = json.getString("success");

        assertEquals("A Spartan is Born!", mss1);
        assertEquals("A Spartan is Born!", mess2);

        // asssert name gender phone
        assertEquals("Female", json.getString("data.gender"));
        assertEquals("PostSpartan", json.getString("data.name"));
        assertEquals(5673489412L, json.getLong("data.phone"));
        // get the id of spartan
        int spartanId = json.getInt("data.id");
        System.out.println("Spartan id is: " + spartanId);

        get("http://54.159.136.152:8000/api/spartans/" + spartanId).body().prettyPrint();
    }

    public static String intCap(String word){
        return word.substring(0,1).toUpperCase() + word.substring(1);
    }
    @Test
    public void post_a_new_spartan_with_pojo_from_uinames_api(){
        // send request to http://uinames.com/api/ and extract name and gender
        JsonPath uinNamesJson = get("http://uinames.com/api/").body().jsonPath();
// {"name":"Danica", "surname":"Basa","gender":"female","region":"Slovakia"}

        // Create a spartan object to be used as a body for post
        // Serilization java object to json
        spartan sprtan1 = new spartan();
        sprtan1.setGender(intCap(uinNamesJson.getString("gender")));
        sprtan1.setName(uinNamesJson.getString("name") + " " + uinNamesJson.getString("surname"));
        sprtan1.setPhone(5673489412L);

        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body(sprtan1)
                .when().post("http://54.159.136.152:8000/api/spartans/");
        // response validation
        assertEquals(201, response.statusCode());
        assertEquals("application/json", response.contentType());
        // exract message using path
        String mss1 = response.path("success");
        // exract message using jsonpath
        JsonPath json = response.jsonPath();
        String mess2 = json.getString("success");

        assertEquals("A Spartan is Born!", mss1);
        assertEquals("A Spartan is Born!", mess2);

        // asssert name gender phone

        // get the id of spartan
        int spartanId = json.getInt("data.id");
        System.out.println("Spartan id is: " + spartanId);

        get("http://54.159.136.152:8000/api/spartans/" + spartanId).body().prettyPrint();
    }
}
