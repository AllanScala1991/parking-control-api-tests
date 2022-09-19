package test;

import com.github.javafaker.Faker;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.ParkingUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class UpdateParkingTest {
    private static String url;
    private static JSONObject createdParking;
    private static String parkingSpotNumber;
    private static String licensePlateCar;
    private static String brandCar;
    private static String modelCar;
    private static String colorCar;
    private static String responsibleName;
    private static String apartment;
    private static String block;
    private static Faker faker = new Faker();

    @BeforeClass
    public static void beforeAll() throws IOException {
        url = "http://localhost:8080/parking-spot";
        createdParking = ParkingUtils.createdParking();
    }

    @Test
    public void updatedParkingSpotModelCar() {
        Map<String, String> payload = new HashMap<>();
        payload.put("modelCar", "updated model car");
        payload.put("parkingSpotNumber", createdParking.get("parkingSpotNumber").toString());
        payload.put("licensePlateCar", createdParking.get("licensePlateCar").toString());
        payload.put("brandCar", createdParking.get("brandCar").toString());
        payload.put("colorCar", createdParking.get("colorCar").toString());
        payload.put("responsibleName", createdParking.get("responsibleName").toString());
        payload.put("apartment", createdParking.get("apartment").toString());
        payload.put("block", createdParking.get("block").toString());

        given()
                .contentType("application/json")
                .body(payload)
                .when()
                .put(url + "/" + createdParking.get("id").toString())
                .then()
                .statusCode(200)
                .body("modelCar", is(equalTo("updated model car")));
    }

    @Test
    public void updatedParkingSpotColorCarWithInvalidID() {
        Map<String, String> payload = new HashMap<>();
        payload.put("modelCar", createdParking.get("modelCar").toString());
        payload.put("parkingSpotNumber", createdParking.get("parkingSpotNumber").toString());
        payload.put("licensePlateCar", createdParking.get("licensePlateCar").toString());
        payload.put("brandCar", createdParking.get("brandCar").toString());
        payload.put("colorCar", "updated color");
        payload.put("responsibleName", createdParking.get("responsibleName").toString());
        payload.put("apartment", createdParking.get("apartment").toString());
        payload.put("block", createdParking.get("block").toString());

        given()
                .contentType("application/json")
                .body(payload)
                .when()
                .put(url + "/" + "5f76cc50-d092-4b76-ba2d-818d166ea432")
                .then()
                .statusCode(404);
    }

    @AfterClass
    public static void afterAll() throws IOException {
        ParkingUtils.deleteParking(createdParking.get("id").toString());
    }
}
