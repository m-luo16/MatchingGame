package me.powerchelle.productmatch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductJsonParser {
    private JSONObject jsonObject;

    public ProductJsonParser(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public List<Product> parse() throws JSONException {
        List<Product> products = new ArrayList<>();

        JSONArray productsJson = jsonObject.getJSONArray("products");
        for (int i = 0; i < productsJson.length(); i++) {
            JSONObject product = productsJson.getJSONObject(i);

            String id = product.optString("id");
            String title = product.optString("title");
            JSONObject img = product.optJSONObject("image");
            String src = "";
            if (img != null) {
                src = img.optString("src");
            }
            products.add(new Product(id, title, src));
        }

        return products;
    }
}
