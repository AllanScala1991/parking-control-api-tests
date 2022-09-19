package test;

import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.ParkingUtils;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class DeletedParkingTest {

    private static String url;
    private static JSONObject createdParking;

    @BeforeClass
    public static void beforeAll() throws IOException {
        url = "http://localhost:8080/parking-spot";
        createdParking = ParkingUtils.createdParking();
    }

    @Test
    public void deletedParkingSpotSuccessfully() {
        given()
                .when()
                .delete(url + "/" + createdParking.get("id").toString())
                .then()
                .statusCode(200);
    }

    @Test
    public void deletedParkingSpotWithInvalidID() {
        given()
                .when()
                .delete(url + "/5f76cc50-d092-4b76-ba2d-818d166ea432")
                .then()
                .statusCode(404);
    }

}
