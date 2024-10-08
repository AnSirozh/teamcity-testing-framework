package com.example.teamcity.api.spec;

import com.example.teamcity.api.config.Config;
import com.example.teamcity.api.models.User;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Specifications {

    private static Specifications spec;

    private Specifications() {} // для реализации паттерна Singletone

    public static Specifications getSpec() { // так же для синглтона, чтобы создать 1 экземпляр
        if (spec == null) {
            spec = new Specifications();
        }
        return spec;
    }

    private RequestSpecBuilder reqBuilder() {
        var requestBuilder = new RequestSpecBuilder();
        requestBuilder.addFilter(new RequestLoggingFilter());
        requestBuilder.addFilter(new ResponseLoggingFilter());
        requestBuilder.setContentType(ContentType.JSON);
        requestBuilder.setAccept(ContentType.JSON);
        return requestBuilder;
    }

    public RequestSpecification unauthSpec() { // спецификации для НЕ авторизированного пользователя
        var requestBuilder = reqBuilder();
        return requestBuilder.build();
    }

    public RequestSpecification authSpec(User user) { // спецификации для авторизированного пользователя
        var requestBuilder = reqBuilder();
        requestBuilder.setBaseUri("http://" + Config.getProperty("host"));
        String auth = user.getUsername() + ":" + user.getPassword();
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
        String authHeader = "Basic " + encodedAuth;
        requestBuilder.addHeader("Authorization", authHeader);

        return requestBuilder.build();
    }


}
