package com.example.teamcity.api;

import com.example.teamcity.api.model.User;
import com.example.teamcity.api.spec.Specifications;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

public class BuildConfigurationTest extends BaseApiTest {

    @Test
    public void buildconfigurationTest() {

        var user = User.builder()
                .username("admin")
                .password("admin")
                .build();

        var token = RestAssured
                .given()
                .spec(Specifications.getSpec().authSpec(user))
                .get("/authenticationTest.html?csrf")
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().asString();

        System.out.println(token);

//        var token = RestAssured.given().auth().preemptive().basic("admin", "admin")
//                .when().get("http://172.29.80.1:8111/authenticationTest.html?csrf")
//                .then().assertThat().statusCode(HttpStatus.SC_OK)
//                .extract().asString();
//        System.out.println(token);
    }
}
