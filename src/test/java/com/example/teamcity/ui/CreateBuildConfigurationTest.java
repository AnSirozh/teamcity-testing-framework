package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.ui.pages.admin.CreateBuildConfigurationPage;
import com.example.teamcity.ui.pages.admin.ProjectPage;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static com.example.teamcity.api.enums.Endpoint.BUILD_TYPES;
import static com.example.teamcity.api.enums.Endpoint.PROJECTS;
import static io.qameta.allure.Allure.step;

public class CreateBuildConfigurationTest extends BaseUiTest {

    @Test(description = "User should be able to create build configuration", groups = {"Positive"})
    public void userCreatesBuildConfiguration() {
        // подготовка окружения
        step("Login as user");
        loginAs(testData.getUser());
        step("Create Project Page using API");
        superUserCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        // взаимодействие с UI
        step("Create Build Configuration` (http://localhost:8111/admin/createObjectMenu.html)");
        step("Send all build parameters (repository URL)");
        step("Click `Proceed`");
        step("Fix Build configuration name value");
        step("Click `Proceed`");
        CreateBuildConfigurationPage.open(testData.getProject().getId())
                .createForm(REPO_URL)
                .setupBuildConfiguration(testData.getBuildType().getName());

        // проверка состояния API
        step("Check that build configuration was successfully created with correct data on API level");
        var createdBuildType = superUserCheckRequests.<BuildType>getRequest(BUILD_TYPES).read("name:" + testData.getBuildType().getName());
        softy.assertNotNull(createdBuildType);

        // проверка состояния UI
        step("Check that build is visible on Project Page (http://localhost:8111/project/{projectName})");
        ProjectPage.open(testData.getProject().getId())
                .buildName.shouldHave(Condition.exactText(testData.getBuildType().getName()));
    }

    @Test(description = "User should not be able to create build type configuration without build name", groups = {"Negative"})
    public void userCreatesBuildConfigurationWithoutBuildName() {
        // подготовка окружения
        step("Login as user");
        loginAs(testData.getUser());
        step("Create Project Page using API");
        superUserCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        // взаимодействие с UI
        // проверка состояния UI
        step("Create Build Configuration` (http://localhost:8111/admin/createObjectMenu.html)");
        step("Send all build parameters (repository URL)");
        step("Click `Proceed`");
        step("Delete Build configuration name value");
        step("Click `Proceed`");
        step("Check that correct error appears about empty build configuration name");
        CreateBuildConfigurationPage.open(testData.getProject().getId())
                .createForm(REPO_URL).setupBuildConfiguration("")
                .buildTypeNameError.shouldBe(Condition.exactText("Build configuration name must not be empty"));

        // проверка состояния API
        step("Check that build configuration was not created on API level");
        superUserUncheckRequests.getRequest(BUILD_TYPES).read("name:" + testData.getBuildType().getName())
                .then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
    }
}
