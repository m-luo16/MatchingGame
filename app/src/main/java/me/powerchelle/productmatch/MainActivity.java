package me.powerchelle.productmatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static me.powerchelle.productmatch.JsonReader.readJsonFromFile;
import static me.powerchelle.productmatch.JsonReader.readJsonFromUrl;

public class MainActivity extends AppCompatActivity {
    private RecyclerView gameBoard;
    private TextView scoreText;
    private int flipped = 0;

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

        assert productList != null;
        final List<Product> reducedList = generateRandomList(productList);

        GridViewAdapter gridViewAdapter = new GridViewAdapter(this, reducedList);

        gameBoard = findViewById(R.id.gameBoard);
        gameBoard.setLayoutManager(new GridLayoutManager(this, 5));
        gameBoard.setAdapter(gridViewAdapter);

    }

    private List<Product> generateRandomList(List<Product> productList) {
        List<Product> shuffledList = new ArrayList<>();
        Collections.shuffle(productList);

        for (int i = 0; i < 15; i++) {
            shuffledList.add(productList.get(i));
            shuffledList.add(productList.get(i));
        }
        Collections.shuffle(shuffledList);
        return shuffledList;
    }

    private JSONObject readFromFile(InputStream is) {
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
