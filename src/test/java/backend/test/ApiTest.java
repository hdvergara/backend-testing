package backend.test;


import backend.api_methods.api_methods;
import backend.utilities.AssertionManager;
import backend.utilities.ConfigProperties;
import backend.utilities.DataGenerator;
import backend.utilities.ReportManager;
import backend.utilities.pojo.CreateBody;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ApiTest {

    @BeforeAll
    static void setup() {
        ReportManager.initializeReport();
    }

    @AfterEach
    void cleanup() {
        ReportManager.flushReport();
    }

    @Test
    @Order(1)
    void createPetTest() {
        ExtentTest report = ReportManager.createTest("VALIDATE PET CREATION");
        AssertionManager assertionManager = new AssertionManager(report);
        report.info("Test initialized");
        int idPet = DataGenerator.randomIdPet();
        String namePet = DataGenerator.randomNamePet();
        Response response = api_methods.postMethod(
                ConfigProperties.getProperty("API.URL"),
                CreateBody.body(idPet, namePet, 3,
                        "Dog", 25, "nameTag", "available"));
        report.info("Executed service");
        assertionManager.hardAssertEquals(response.getStatusCode(), 200);
        report.info("Service response: \n" + response.getBody().prettyPrint());
        assertionManager.softAssertTrue(ConfigProperties.getProperty("API.NAME_PET")
                .equals(response.getBody().jsonPath().get("name")));
        report.pass("Test completed");
        ConfigProperties.writePropertiesFile("API.ID_PET", String.valueOf(idPet));
        ConfigProperties.writePropertiesFile("API.NAME_PET", namePet);
    }

    @Test
    @Order(2)
    void searchPet() {
        ExtentTest report = ReportManager.createTest("SEARCH PET");
        AssertionManager assertionManager = new AssertionManager(report);
        report.info("Test initialized");
        Response response = api_methods.getMethod(
                ConfigProperties.getProperty("API.URL"),
                ConfigProperties.getProperty("API.ID_PET"));
        report.info("Executed service");
        assertionManager.hardAssertEquals(response.getStatusCode(), 200);
        report.info("Service response: \n" + response.getBody().prettyPrint());
        assertionManager.softAssertTrue(ConfigProperties.getProperty("API.NAME_PET")
                .equals(response.getBody().jsonPath().get("name")));
        report.pass("Test completed");
    }

    @Test
    @Order(3)
    void updateAndSearchPet() {
        ExtentTest report = ReportManager.createTest("UPDATE AND SEARCH PET");
        AssertionManager assertionManager = new AssertionManager(report);
        String namePet = DataGenerator.randomNamePet();
        report.info("Test initialized");
        Response response = api_methods.postMethod(
                ConfigProperties.getProperty("API.URL"),
                CreateBody.body(Integer.parseInt(ConfigProperties.getProperty("API.ID_PET")), namePet, 3,
                        "Dog", 25, "nameTag", "sold"));
        report.info("Executed service");
        assertionManager.hardAssertEquals(response.getStatusCode(), 200);
        report.info("Service response: \n" + response.getBody().prettyPrint());
        assertionManager.softAssertTrue(namePet.equals(response.getBody().jsonPath().get("name")));
        report.info("Find pet after upgrade");
        Response responseUpdate = api_methods.getMethod(
                ConfigProperties.getProperty("API.URL"),
                ConfigProperties.getProperty("API.ID_PET"));
        assertionManager.hardAssertEquals(responseUpdate.getStatusCode(), 200);
        report.info("Service response: \n" + responseUpdate.getBody().prettyPrint());
        assertionManager.softAssertTrue(namePet.equals(responseUpdate.getBody().jsonPath().get("name")));
        report.pass("Test completed");
    }
}
