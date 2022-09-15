package utils;
import com.github.javafaker.Faker;
import okhttp3.*;
import org.json.JSONObject;


import java.io.IOException;

public class ParkingUtils {

    public static JSONObject createdParking() throws IOException {
        Faker faker = new Faker();
        String parkingSpotNumber = faker.number().digits(3).toString();
        String licensePlateCar = faker.number().digits(6).toString();
        String brandCar = "Fiat";
        String modelCar = "Stilo";
        String colorCar = "Yellow";
        String responsibleName = faker.name().fullName();
        String apartment = faker.number().digits(2).toString();
        String block = faker.number().digits(2).toString();

        JSONObject payload = new JSONObject();
        payload.put("parkingSpotNumber", parkingSpotNumber);
        payload.put("licensePlateCar", licensePlateCar);
        payload.put("brandCar", brandCar);
        payload.put("modelCar", modelCar);
        payload.put("colorCar", colorCar);
        payload.put("responsibleName", responsibleName);
        payload.put("apartment", apartment);
        payload.put("block", block);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, payload.toString());
        Request request = new Request.Builder()
                .url("http://localhost:8080/parking-spot")
                .post(body)
                .build();

        Response response = new OkHttpClient().newCall(request).execute();
        // TRANSFORMA O RETORNO DE RESPONSE PARA STRING
        String responseData = response.body().string();
        // TRANSFORMA A STRING EM JSON OBJECT
        JSONObject  responseObject = new JSONObject(responseData);
        return  responseObject;
    }

    public static void deleteParking(String id) throws IOException {
        Request request = new Request.Builder()
                .url("http://localhost:8080/parking-spot/" + id)
                .delete()
                .build();

        new OkHttpClient().newCall(request).execute();
    }
}
