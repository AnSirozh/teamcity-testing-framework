package com.example.teamcity.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.elements.BasePageElement;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public abstract class BasePage {

    protected static final Duration BASE_WAITING = Duration.ofSeconds(30);
    protected static final Duration LONG_WAITING = Duration.ofMinutes(3);

    protected <T extends BasePageElement> List<T> generatePageElements(
            ElementsCollection collection, Function<SelenideElement, T> creator) {
        return collection.stream().map(creator).toList();
    }

    // ElementCollection: Selenide Element 1, Selenide Element 2 и тд
    // - что под собой подразумевает ElementCollection

    // collection.stream() -> Конвеер: Selenide Element 1, Selenide Element 2 и тд
    // - визуально одно и то же, но для Java у стрима и коллекции разная функциональность

    // creator(Selenide Element 1) -> T -> add to list
    // - вызываем creator для эдемента, преобразуем в тип данных Т и добавляем в лист
    // creator(Selenide Element 2) -> T -> add to list


}
