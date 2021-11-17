package application.methods;

import org.json.JSONObject;

import java.io.IOException;

import static application.methods.JsonReader.readJsonFromUrl;

public class FoodAPI {
    public void test() throws IOException {
        JSONObject json = readJsonFromUrl("https://world.openfoodfacts.org/api/v0/product/737628064502.json");
        System.out.println(json.toString());
        System.out.println(json.get("id"));
    }
}
