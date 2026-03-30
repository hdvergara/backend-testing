package com.petstore.automation.test;

import com.petstore.automation.api.ApiMethods;
import com.petstore.automation.assertions.AssertionManager;
import com.petstore.automation.config.ConfigProperties;
import com.petstore.automation.data.DataGenerator;
import com.petstore.automation.model.CreateBody;
import com.petstore.automation.reporting.allure.AllureLabels;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@Epic(AllureLabels.EPIC_PET_STORE)
@Feature(AllureLabels.FEATURE_PET_LIFECYCLE)
@Owner("QA Automation")
@Link(name = "Swagger Petstore", url = "https://petstore.swagger.io/")
@ExtendWith(AllureJunit5.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ApiTest {

    private int idPet;
    private String namePet;

    @Story(AllureLabels.STORY_CREATE)
    @DisplayName("POST /pet — crear mascota y validar nombre en respuesta")
    @Description("Flujo de alta: generación de datos, invocación REST y aserciones duras y suaves.")
    @Severity(SeverityLevel.BLOCKER)
    @Test
    @Order(1)
    void createPetTest() {
        AssertionManager assertionManager = new AssertionManager();

        Allure.step("Generar datos de prueba", () -> {
            idPet = DataGenerator.randomIdPet();
            namePet = DataGenerator.randomNamePet();
            Allure.parameter("petId", idPet);
            Allure.parameter("petName", namePet);
        });

        String baseUrl = ConfigProperties.getProperty("API.URL");
        Response response = Allure.step("Ejecutar POST /pet (create)", () ->
                ApiMethods.createPet(baseUrl,
                        CreateBody.body(idPet, namePet, 3,
                                "Dog", 25, "nameTag", "available")));

        Allure.step("Validar código HTTP 200", () ->
                assertThat(response.getStatusCode()).isEqualTo(200));

        Allure.step("Validar nombre en cuerpo JSON", () ->
                assertionManager.softAssertTrue(namePet.equals(response.getBody().jsonPath().get("name"))));

        assertionManager.assertAllSoftAssertions();
    }

    @Story(AllureLabels.STORY_READ)
    @DisplayName("GET /pet/{id} — consultar mascota creada")
    @Description("Valida que el recurso persistido coincide con el alta previa (orden de ejecución @Order).")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    @Order(2)
    void searchPet() {
        AssertionManager assertionManager = new AssertionManager();
        String baseUrl = ConfigProperties.getProperty("API.URL");

        Response response = Allure.step("Ejecutar GET /pet/{id}", () ->
                ApiMethods.getPetById(baseUrl, String.valueOf(idPet)));

        Allure.step("Validar código HTTP 200", () ->
                assertThat(response.getStatusCode()).isEqualTo(200));

        Allure.step("Validar nombre de mascota", () ->
                assertionManager.softAssertTrue(namePet.equals(response.getBody().jsonPath().get("name"))));

        assertionManager.assertAllSoftAssertions();
    }

    @Story(AllureLabels.STORY_UPDATE)
    @DisplayName("PUT /pet + GET — actualizar y verificar estado sold")
    @Description("Actualización completa (PUT) y comprobación con GET.")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    @Order(3)
    void updateAndSearchPet() {
        AssertionManager assertionManager = new AssertionManager();
        String updatedName = DataGenerator.randomNamePet();
        String baseUrl = ConfigProperties.getProperty("API.URL");

        Response response = Allure.step("Ejecutar PUT /pet (update)", () ->
                ApiMethods.updatePet(baseUrl,
                        CreateBody.body(idPet, updatedName, 3,
                                "Dog", 25, "nameTag", "sold")));

        Allure.step("Validar código HTTP 200 (update)", () ->
                assertThat(response.getStatusCode()).isEqualTo(200));

        Allure.step("Validar nombre tras actualización", () ->
                assertionManager.softAssertTrue(updatedName.equals(response.getBody().jsonPath().get("name"))));

        Response responseUpdate = Allure.step("Ejecutar GET /pet/{id} tras update", () ->
                ApiMethods.getPetById(baseUrl, String.valueOf(idPet)));

        Allure.step("Validar código HTTP 200 (get)", () ->
                assertThat(responseUpdate.getStatusCode()).isEqualTo(200));

        Allure.step("Validar nombre consistente en segunda lectura", () ->
                assertionManager.softAssertTrue(updatedName.equals(responseUpdate.getBody().jsonPath().get("name"))));

        assertionManager.assertAllSoftAssertions();
        namePet = updatedName;
    }
}
