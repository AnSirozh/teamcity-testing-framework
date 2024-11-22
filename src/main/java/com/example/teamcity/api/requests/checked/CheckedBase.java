package com.example.teamcity.api.requests.checked;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.models.BaseModel;
import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

@SuppressWarnings("unchecked")
public final class CheckedBase<T extends BaseModel> extends Request implements CrudInterface {

    /*
     *  Реализует все проверяемые (ассертящие успешный status code ответа и
     *  сериализирующие тело ответа JSON в объект (models)) запросы для CRUD эндпоинта.
     */

    private final UncheckedBase uncheckedBase;

    public CheckedBase(RequestSpecification spec, Endpoint endpoint) {
        super(spec, endpoint);
        this.uncheckedBase = new UncheckedBase(spec, endpoint);
    }

    @Override
    public T create(BaseModel model) {
        var createdModel = (T) uncheckedBase
                .create(model)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(endpoint.getModelClass());

        TestDataStorage.getStorage().addCreatedEntity(endpoint, createdModel);
        return createdModel;
    }

    @Override
    public T read(String id) {
        return (T) uncheckedBase
                .read(id)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(endpoint.getModelClass());
    }

    @Override
    public T update(String id, BaseModel model) {
        return (T) uncheckedBase
                .update(id, model)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(endpoint.getModelClass());
    }

    @Override
    public Object delete(String id) {
        return uncheckedBase
                .delete(id)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().asString();
    }


    public T readWithTimeout(String locator, long timeout, TimeUnit timeoutUnit, long pollInterval, TimeUnit pollIntervalUnit) {
        return await()
                .atMost(timeout, timeoutUnit)
                .pollInterval(pollInterval, pollIntervalUnit)
                .until(() -> attemptRead(locator), Objects::nonNull);
    }

    private T attemptRead(String locator) {
        return (T) uncheckedBase
                .read(locator)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(endpoint.getModelClass());

    }
}