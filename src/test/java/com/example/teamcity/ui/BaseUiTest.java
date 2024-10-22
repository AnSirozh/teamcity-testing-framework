package com.example.teamcity.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.example.teamcity.BaseTest;
import com.example.teamcity.api.config.Config;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;

import java.util.Map;

public class BaseUiTest extends BaseTest {
    @BeforeSuite(alwaysRun = true)
    public void setupUiTest() {
        Configuration.browser = Config.getProperty("browser");
        Configuration.baseUrl = "http://" + Config.getProperty("host");
        // НЕ ПИШИТЕ UI ТЕСТЫ С ЛОКАЛЬНЫМ БРАУЗЕРОМ
        // А ПОТОМ ЗАПУСКАЕТЕ НА REMOTE BROWSER И ТАМ ОШИБКИ
        Configuration.remote = Config.getProperty("remote");
        Configuration.browserSize = Config.getProperty("browserSize");

        Configuration.browserCapabilities.setCapability("selenoid:options",
                Map.of("enableVNC", true, //ЧТОБЫ ВИДЕТЬ ЭКРАН БРАУЗЕРА ПРИ ПРОХОЖДЕНИИ ТЕСТА
                        "enableLog", true)); //ЧТОБЫ БЫЛИ ЛОГИ
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        Selenide.closeWebDriver();
    }
}
