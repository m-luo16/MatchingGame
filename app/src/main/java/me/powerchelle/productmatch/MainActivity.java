package me.powerchelle.productmatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

import static me.powerchelle.productmatch.JsonReader.readJsonFromFile;
import static me.powerchelle.productmatch.JsonReader.readJsonFromUrl;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JSONObject productJSON = readFromFile(getResources().openRawResource(R.raw.products));
        List<Product> productList = null;

        try {
            productList = parseProductList(productJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private  JSONObject readFromFile(InputStream is) {
        JSONObject productJSON = null;
        try {
            productJSON = readJsonFromFile(is);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return productJSON;
    }

    private List<Product> parseProductList(JSONObject productJSON) throws JSONException {
        List<Product> products;
        ProductJsonParser productJsonParser = new ProductJsonParser(productJSON);
        products = productJsonParser.parse();

        return products;
    }
}
