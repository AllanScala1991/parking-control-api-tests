package test;

import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.ParkingUtils;

import java.io.IOException;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class FindParkingTest {
    private static String url;
    private static JSONObject firstParking;
    private static JSONObject secondParking;

    @BeforeClass
    public static void beforeAll() throws IOException {
        url = "http://localhost:8080/parking-spot";
        firstParking = ParkingUtils.createdParking();
        secondParking = ParkingUtils.createdParking();
    }

    @Test
    public void findAllParkingSpot() {
        given()
                .when()
                .get(url)
                .then()
                .body("$.size()", is(greaterThan(0)))
                .body("[0].id", is(notNullValue()))
                .body("[0].parkingSpotNumber", is(notNullValue()))
                .body("[0].licensePlateCar", is(notNullValue()))
                .body("[0].brandCar", is(notNullValue()))
                .body("[0].modelCar", is(notNullValue()))
                .body("[0].colorCar", is(notNullValue()))
                .body("[0].registrationDate", is(notNullValue()))
                .body("[0].responsibleName", is(notNullValue()))
                .body("[0].apartment", is(notNullValue()))
                .body("[0].block", is(notNullValue()));

    }

    @Test
    public void findOneParkingSpotWithID() {
        given()
                .when()
                .get(url + "/" + firstParking.get("id"))
                .then()
                .statusCode(200)
                .body("size()", is(greaterThan(0)))
                .body("id", is(equalTo(firstParking.get("id").toString())))
                .body("parkingSpotNumber", is(equalTo(firstParking.get("parkingSpotNumber").toString())))
                .body("licensePlateCar", is(equalTo(firstParking.get("licensePlateCar").toString())))
                .body("brandCar", is(equalTo(firstParking.get("brandCar").toString())))
                .body("modelCar", is(equalTo(firstParking.get("modelCar").toString())))
                .body("colorCar", is(equalTo(firstParking.get("colorCar").toString())))
                .body("registrationDate", is(equalTo(firstParking.get("registrationDate").toString())))
                .body("responsibleName", is(equalTo(firstParking.get("responsibleName").toString())))
                .body("apartment", is(equalTo(firstParking.get("apartment").toString())))
                .body("block", is(equalTo(firstParking.get("block").toString())));
    }

    @Test
    public void findOneParkingSpotWithInvalidID() {
        given()
                .when()
                .get(url + "/5f76cc50-d092-4b76-ba2d-818d166ea432")
                .then()
                .statusCode(404);
    }

    @AfterClass
    public static void afterAll() throws IOException {
        ParkingUtils.deleteParking(firstParking.get("id").toString());
        ParkingUtils.deleteParking(secondParking.get("id").toString());
    }
}
