package me.powerchelle.productmatch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static me.powerchelle.productmatch.JsonReader.readJsonFromFile;

public class MainActivity extends AppCompatActivity implements GridViewAdapter.ItemClickListener {
    private TextView scoreText;
    private GridLayoutManager gridLayoutManager;
    private List<Product> reducedList = null;
    private boolean turnOver = false;
    private int lastSeenPos = -1;
    private int clicked = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView gameBoard = findViewById(R.id.gameBoard);
        scoreText = findViewById(R.id.scoreText);

        JSONObject productJSON = readFromFile(getResources().openRawResource(R.raw.products));
        List<Product> productList = null;

        try {
            productList = parseProductList(productJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        assert productList != null;
        reducedList = generateRandomList(productList);

        GridViewAdapter gridViewAdapter = new GridViewAdapter(this, reducedList);
        gridViewAdapter.setClickListener(this);
        gridLayoutManager = new GridLayoutManager(this, 5);
        gameBoard.setLayoutManager(gridLayoutManager);
        gameBoard.setAdapter(gridViewAdapter);

    }

    private List<Product> generateRandomList(List<Product> productList) {
        List<Product> shuffledList = new ArrayList<>();
        Collections.shuffle(productList);

        for (int i = 0; i < 15; i++) {
            Product copy = new Product(productList.get(i).getId(), productList.get(i).getTitle(), productList.get(i).getImgSrc());
            shuffledList.add(productList.get(i));
            shuffledList.add(copy);
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

    @Override
    public void onItemClick(View view, int position) {
        CardView cardView = view.findViewById(R.id.card_front);
        ImageView imageView = view.findViewById(R.id.icon);

        if (!reducedList.get(position).getFlipped() && !turnOver) {
            if (clicked == 0) lastSeenPos = position;
            reducedList.get(position).setFlipped(true);
            cardView.setCardBackgroundColor(this.getColor(R.color.colorAccent));
            Glide.with(view).load(reducedList.get(position).getImgSrc()).into(imageView);
            view.setClickable(false);
            clicked++;
        } else if (reducedList.get(position).getFlipped() && turnOver) {
            reducedList.get(position).setFlipped(false);
            cardView.setCardBackgroundColor(this.getColor(R.color.cardview_dark_background));
            imageView.setImageResource(R.drawable.shopify_bag);
            view.setClickable(true);
            clicked--;
        }

        if (clicked == 2) {
            turnOver = true;
            // Matched
            if (lastSeenPos != position && reducedList.get(lastSeenPos).getId().equals(reducedList.get(position).getId())) {
                score++;
                scoreText.setText(String.format(this.getString(R.string.score), score));
                view.setClickable(false);
                gridLayoutManager.findViewByPosition(lastSeenPos).setClickable(false);
                turnOver = false;
                clicked = 0;
            } else {
                view.setClickable(true);
                gridLayoutManager.findViewByPosition(lastSeenPos).setClickable(true);
            }

            if (score == 15) {
                Intent intent = new Intent(MainActivity.this, WinActivity.class);
                startActivity(intent);
                finish();
            }
        } else if (clicked == 0) {
            turnOver = false;

        }
    }

}
