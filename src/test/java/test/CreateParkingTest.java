package test;

import com.github.javafaker.Faker;
import org.json.JSONObject;
import org.junit.*;
import utils.ParkingUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class CreateParkingTest {
    private static String url;
    private static String parkingSpotNumber;
    private static String licensePlateCar;
    private static String brandCar;
    private static String modelCar;
    private static String colorCar;
    private static String responsibleName;
    private static String apartment;
    private static String block;
    private static Faker faker = new Faker();
    private static JSONObject createdParking;

    @BeforeClass // RODA APENAS UMA VEZ ANTES DOS TESTES
    public static void beforeAll() throws IOException {
        createdParking = ParkingUtils.createdParking();
    }

    @Before // RODA TODAS AS VEZES ANTES DOS TESTES
    public void beforeEach() throws IOException {
        url = "http://localhost:8080/parking-spot";
        parkingSpotNumber = faker.number().digits(3).toString();
        licensePlateCar = (faker.number().digits(6).toString());
        brandCar = "honda";
        modelCar = "Civic";
        colorCar = "red";
        responsibleName = faker.name().fullName();
        apartment = faker.number().digits(2).toString();
        block = faker.number().digits(2).toString();
    }

    @Test
    public void createNewParkingSuccessfully() {
        Map<String, String> body = new HashMap<String, String>();
        body.put("parkingSpotNumber", parkingSpotNumber);
        body.put("licensePlateCar", licensePlateCar);
        body.put("brandCar", brandCar);
        body.put("modelCar", modelCar);
        body.put("colorCar", colorCar);
        body.put("responsibleName", responsibleName);
        body.put("apartment", apartment);
        body.put("block", block);

        given()
                .contentType("application/json")
                .body(body)
                .when()
                .post(url)
                .then()
                .statusCode(201)
                .body("id", is(notNullValue()))
                .body("registrationDate", is(notNullValue()));
    }

    @Test
    public void createdParkingWithEmptyValue() {
        Map<String, String> body = new HashMap<String, String>();
        body.put("brandCar", brandCar);
        body.put("modelCar", modelCar);
        body.put("colorCar", colorCar);
        body.put("responsibleName", responsibleName);
        body.put("apartment", apartment);
        body.put("block", block);

        given()
                .contentType("application/json")
                .body(body)
                .when()
                .post(url)
                .then()
                .statusCode(400)
                .body("error", is("Bad Request"));
    }

    @Test
    public void createdParkingWithExistsByParkingSpotNumber() throws IOException {
        Map<String, String> body = new HashMap<>();
        body.put("parkingSpotNumber",  createdParking.get("parkingSpotNumber").toString());
        body.put("licensePlateCar", licensePlateCar);
        body.put("brandCar", brandCar);
        body.put("modelCar", modelCar);
        body.put("colorCar", colorCar);
        body.put("responsibleName", responsibleName);
        body.put("apartment", apartment);
        body.put("block", block);

        given()
                .contentType("application/json")
                .body(body)
                .when()
                .post(url)
                .then()
                .statusCode(409);
    }

    @Test
    public void createdParkingWithExistsByLicensePlateCar() throws IOException {
        Map<String, String> body = new HashMap<>();
        body.put("parkingSpotNumber", parkingSpotNumber);
        body.put("licensePlateCar", createdParking.get("licensePlateCar").toString());
        body.put("brandCar", brandCar);
        body.put("modelCar", modelCar);
        body.put("colorCar", colorCar);
        body.put("responsibleName", responsibleName);
        body.put("apartment", apartment);
        body.put("block", block);

        given()
                .contentType("application/json")
                .body(body)
                .when()
                .post(url)
                .then()
                .statusCode(409);
    }

    @Test
    public void createdParkingWithExistsByApartmentAndBlock() throws IOException {
        Map<String, String> body = new HashMap<>();
        body.put("parkingSpotNumber", parkingSpotNumber);
        body.put("licensePlateCar",licensePlateCar);
        body.put("brandCar", brandCar);
        body.put("modelCar", modelCar);
        body.put("colorCar", colorCar);
        body.put("responsibleName", responsibleName);
        body.put("apartment",  createdParking.get("apartment").toString());
        body.put("block",  createdParking.get("block").toString());

        given()
                .contentType("application/json")
                .body(body)
                .when()
                .post(url)
                .then()
                .statusCode(409);
    }

    @AfterClass
    public static void afterAll() throws IOException {
        ParkingUtils.deleteParking(createdParking.get("id").toString());
    }
}
