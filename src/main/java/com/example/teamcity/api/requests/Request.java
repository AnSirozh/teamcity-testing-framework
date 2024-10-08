package com.example.teamcity.api.requests;

import com.example.teamcity.api.enums.Endpoint;
import io.restassured.specification.RequestSpecification;

public class Request {
    /*
     * В Request мы храним МЕНЯЮЩИЕСЯ параметры от CRUD эндпоинта к CRUD эндпоинту, а именно:
     * - спецификация (кто делает запрос?)
     * - эндпоинт - куда делаем запрос и в какую сущность сериализуем/десериализуем?
     */

    private final RequestSpecification spec;
    private final Endpoint endpoint;

    public Request(RequestSpecification spec, Endpoint endpoint) {
        this.spec = spec;
        this.endpoint = endpoint;
    }
}
