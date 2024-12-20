package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.elements.ProjectElement;
import com.example.teamcity.ui.pages.BasePage;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProjectsPage extends BasePage {

    private static final String PROJECTS_URL = "/favorite/projects";

    private ElementsCollection projectElements = $$("div[class*='Subproject__container']");

    private SelenideElement spanFavoriteProjects = $("span[class='ProjectPageHeader__title--ih']");

    private SelenideElement header = $(".MainPanel__router--gF > div");

    // ElementCollection превратить всех в List<ProjectElement>
    // из UI elements преобразовать в List<Object>
    // нужен метод преобразования ElementCollection в List<BasePageElement>

    public ProjectsPage() {
        header.shouldBe(Condition.visible, BASE_WAITING);
    }

    public static ProjectsPage open() {
        return Selenide.open(PROJECTS_URL, ProjectsPage.class);
    }

    public List<ProjectElement> getProjects() {
        return generatePageElements(projectElements, ProjectElement::new);
    }
}
